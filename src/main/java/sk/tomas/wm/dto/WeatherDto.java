package sk.tomas.wm.dto;

import lombok.Data;

import java.text.MessageFormat;

@Data
public class WeatherDto {

    private long pressure;
    private int temperature;
    private int humidity;
    private int voltage;

    public String print() {
        return MessageFormat.format("Actual weather measurement: pressure: {0}, temperature: {1}, humidity: {2}, voltage: {3}.",
                pressure, temperature, humidity, voltage);
    }

}
