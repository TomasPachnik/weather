package sk.tomas.wm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.tomas.wm.entity.WeatherEntity;

import java.util.List;

public interface WeatherDao extends JpaRepository<WeatherEntity, Long> {

     List<WeatherEntity> findTop10ByOrderByCreatedDesc();

}
