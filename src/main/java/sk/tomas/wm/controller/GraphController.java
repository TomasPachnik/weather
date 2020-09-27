package sk.tomas.wm.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sk.tomas.wm.dao.WeatherDao;
import sk.tomas.wm.entity.WeatherEntity;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
        int dividend;
        String time;
        switch (period) {
            case "week":
                weatherInfo = weatherDao.getDays(6);
                dividend = 7;
                time = "Posledný týždeň";
                break;
            case "threeDays":
                weatherInfo = weatherDao.getDays(2);
                dividend = 3;
                time = "Posledné tri dni";
                break;
            case "twoDays":
                weatherInfo = weatherDao.getDays(1);
                dividend = 2;
                time = "Posledné dva dni";
                break;
            case "today":
            default:
                weatherInfo = weatherDao.getDays(0);
                dividend = 1;
                time = "Dnes";
        }

        for (int i = 0; i < weatherInfo.size(); i++) {

            //if every x entry or last entry
            if (i % dividend == 0 || (weatherInfo.size() - 1 == i)) {
                WeatherEntity weather = weatherInfo.get(i);
                //if next day, show day name instead of time
                if (weatherInfo.get(i).getCreated().truncatedTo(ChronoUnit.DAYS).isAfter(last.truncatedTo(ChronoUnit.DAYS))) {
                    last = weather.getCreated();
                    labels.add(weather.getCreated().format(DAY_FORMAT).toUpperCase());
                } else {
                    labels.add(weather.getCreated().format(FORMAT));
                }

                temperature.add(((double) weather.getTemperature()) / 100);
                humidity.add(((double) weather.getHumidity()) / 100);
                pressure.add(((double) weather.getPressure()) / 10_000);
            }
        }

        model.addAttribute("labels", labels.toArray());
        model.addAttribute("temperature", temperature.toArray());
        model.addAttribute("humidity", humidity.toArray());
        model.addAttribute("pressure", pressure.toArray());
        model.addAttribute("time", time);
    }

}
