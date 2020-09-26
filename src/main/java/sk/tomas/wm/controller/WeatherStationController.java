package sk.tomas.wm.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sk.tomas.wm.Weather;
import sk.tomas.wm.dao.WeatherDao;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/weather")
public class WeatherStationController {

    private WeatherDao weatherDao;
    private ModelMapper mapper;

    @Autowired
    public WeatherStationController(WeatherDao weatherDao, ModelMapper mapper) {
        this.weatherDao = weatherDao;
        this.mapper = mapper;
    }

    @GetMapping("/today")
    public List<Weather> getLast10Measures() {
        return weatherDao.getToday().stream().map(entity -> mapper.map(entity, Weather.class)).collect(Collectors.toList());
    }

}
