package sk.tomas.wm.controller;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sk.tomas.wm.dto.WeatherDto;
import sk.tomas.wm.dao.WeatherDao;
import sk.tomas.wm.entity.WeatherEntity;

import java.time.OffsetDateTime;

@Slf4j
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
    public String allWithPagination(@RequestBody WeatherDto weather) {
        log.info(weather.print());
        WeatherEntity entity = mapper.map(weather, WeatherEntity.class);
        entity.setCreated(OffsetDateTime.now());
        weatherDao.save(entity);
        return "ok";
    }

}
