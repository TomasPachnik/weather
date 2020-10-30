package sk.tomas.wm.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sk.tomas.wm.dto.WeatherDto;
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

    @GetMapping
    public List<WeatherDto> getToday(@RequestParam(name = "period", required = false, defaultValue = "today") String period) {
        int dividend;

        switch (period) {
            case "week":
                dividend = 6;
                break;
            case "threeDays":
                dividend = 2;
                break;
            case "twoDays":
                dividend = 1;
                break;
            case "today":
            default:
                dividend = 0;
        }

        return weatherDao.getDays(dividend)
                .stream()
                .map(entity -> mapper.map(entity, WeatherDto.class))
                .collect(Collectors.toList());
    }

}
