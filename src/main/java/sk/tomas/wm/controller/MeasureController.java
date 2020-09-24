package sk.tomas.wm.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sk.tomas.wm.Weather;
import sk.tomas.wm.dao.WeatherDao;
import sk.tomas.wm.entity.WeatherEntity;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/monitor")
public class MeasureController {

    private WeatherDao weatherDao;
    private ModelMapper mapper;

    @Autowired
    public MeasureController(WeatherDao weatherDao, ModelMapper mapper) {
        this.weatherDao = weatherDao;
        this.mapper = mapper;
    }

    @PostMapping
    public String allWithPagination(@RequestBody Weather weather) {
        WeatherEntity entity = mapper.map(weather, WeatherEntity.class);
        entity.setCreated(OffsetDateTime.now());
        weatherDao.save(entity);
        return "ok";
    }

}
