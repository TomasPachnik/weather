package sk.tomas.wm;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class Weather {

    private OffsetDateTime created;
    private long pressure;
    private int temperature;
    private int humidity;
}
