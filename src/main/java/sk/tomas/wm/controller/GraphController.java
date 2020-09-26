package sk.tomas.wm.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import sk.tomas.wm.dao.WeatherDao;
import sk.tomas.wm.entity.WeatherEntity;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/graph")
public class GraphController {

    private static DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    private WeatherDao weatherDao;

    @Autowired
    public GraphController(WeatherDao weatherDao) {
        this.weatherDao = weatherDao;
    }

    @RequestMapping
    public String index(Model model) {
        List<WeatherEntity> today = weatherDao.getToday();

        List<String> labels = new ArrayList<>();
        List<Double> temperature = new ArrayList<>();
        List<Double> humidity = new ArrayList<>();
        List<Double> pressure = new ArrayList<>();

        for (WeatherEntity weather : today) {
            labels.add(weather.getCreated().format(FORMAT));
            temperature.add(((double) weather.getTemperature()) / 100);
            humidity.add(((double) weather.getHumidity()) / 100);
            pressure.add(((double) weather.getPressure()) / 10_000);
        }

        model.addAttribute("labels", labels.toArray());
        model.addAttribute("temperature", temperature.toArray());
        model.addAttribute("humidity", humidity.toArray());
        model.addAttribute("pressure", pressure.toArray());
        model.addAttribute("time", "Dnes");
        return "index.html";
    }


}
