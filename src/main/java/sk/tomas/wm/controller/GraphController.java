package sk.tomas.wm.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sk.tomas.wm.Weather;
import sk.tomas.wm.dao.WeatherDao;
import sk.tomas.wm.entity.WeatherEntity;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/graph")
public class GraphController {

    private static DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("HH:mm");
    private static DateTimeFormatter DAY_FORMAT = DateTimeFormatter.ofPattern("EEEE", Locale.forLanguageTag("SK"));

    private WeatherDao weatherDao;

    @Autowired
    public GraphController(WeatherDao weatherDao) {
        this.weatherDao = weatherDao;
    }

    @RequestMapping
    public String index(@RequestParam(name = "period", required = false, defaultValue = "today") String period, Model model) {
        calculate(period, model);
        return "index.html";
    }

    private void calculate(String period, Model model) {
        List<WeatherEntity> weatherInfo;
        List<String> labels = new ArrayList<>();
        List<Double> temperature = new ArrayList<>();
        List<Double> humidity = new ArrayList<>();
        List<Double> pressure = new ArrayList<>();
        OffsetDateTime last = OffsetDateTime.MIN;
        String time;
        switch (period) {
            case "week":
                weatherInfo = weatherDao.getDays(6);
                time = "Posledný týždeň";
                break;
            case "threeDays":
                weatherInfo = weatherDao.getDays(2);
                time = "Posledné tri dni";
                break;
            case "twoDays":
                weatherInfo = weatherDao.getDays(1);
                time = "Posledné dva dni";
                break;
            case "today":
            default:
                weatherInfo = weatherDao.getDays(0);
                time = "Dnes";
        }

        //get actual weather info and remove it from list
        WeatherEntity actualWeather = weatherInfo.remove(weatherInfo.size() - 1);

        Map<OffsetDateTime, List<WeatherEntity>> map = groupByHour(weatherInfo);

        for (Map.Entry<OffsetDateTime, List<WeatherEntity>> item : map.entrySet()) {
            Weather weather = average(item.getValue());

            //if next day, show day name instead of time
            if (item.getKey().truncatedTo(ChronoUnit.DAYS).isAfter(last.truncatedTo(ChronoUnit.DAYS))) {
                last = item.getKey();
                labels.add(item.getKey().plusMinutes(30).format(DAY_FORMAT).toUpperCase());
            } else {
                labels.add(item.getKey().plusMinutes(30).format(FORMAT));
            }

            temperature.add(((double) weather.getTemperature()) / 100);
            humidity.add(((double) weather.getHumidity()) / 100);
            pressure.add(((double) weather.getPressure()) / 10_000);
        }

        labels.add(actualWeather.getCreated().format(FORMAT));
        temperature.add(((double) actualWeather.getTemperature()) / 100);
        humidity.add(((double) actualWeather.getHumidity()) / 100);
        pressure.add(((double) actualWeather.getPressure()) / 10_000);

        model.addAttribute("labels", labels.toArray());
        model.addAttribute("temperature", temperature.toArray());
        model.addAttribute("humidity", humidity.toArray());
        model.addAttribute("pressure", pressure.toArray());
        model.addAttribute("time", time);
    }

    private Map<OffsetDateTime, List<WeatherEntity>> groupByHour(List<WeatherEntity> weatherInfo) {
        Map<OffsetDateTime, List<WeatherEntity>> result = new LinkedHashMap<>();
        for (WeatherEntity item : weatherInfo) {
            OffsetDateTime created = item.getCreated();
            OffsetDateTime truncated = created.truncatedTo(ChronoUnit.HOURS);
            if (result.get(truncated) == null) {
                List<WeatherEntity> list = new ArrayList<>();
                list.add(item);
                result.put(truncated, list);
            } else {
                result.get(truncated).add(item);
            }
        }
        return result;
    }

    private Weather average(List<WeatherEntity> list) {
        Weather result = new Weather();

        for (WeatherEntity item : list) {
            result.setTemperature(result.getTemperature() + item.getTemperature());
            result.setPressure(result.getPressure() + item.getPressure());
            result.setHumidity(result.getHumidity() + item.getHumidity());
        }

        result.setTemperature(result.getTemperature() / list.size());
        result.setPressure(result.getPressure() / list.size());
        result.setHumidity(result.getHumidity() / list.size());

        return result;
    }

}
