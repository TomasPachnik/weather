package sk.tomas.wm;

import lombok.Data;

import java.text.MessageFormat;

@Data
public class Weather {

    private long pressure;
    private int temperature;
    private int humidity;

    public String print() {
        return MessageFormat.format("Actual weather measurement: pressure: {0}, temperature: {1}, humidity: {2}.", pressure, temperature, humidity);
    }

}
