package sk.tomas.wm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sk.tomas.wm.entity.WeatherEntity;

import java.util.List;

public interface WeatherDao extends JpaRepository<WeatherEntity, Long> {

    @Query("SELECT e FROM WEATHER e WHERE e.created  > CURRENT_DATE order by created asc")
    List<WeatherEntity> getToday();

}
