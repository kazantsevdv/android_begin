package com.example.android_begin;

import java.io.Serializable;

public class WeatherData implements Serializable {
    int temperature;
    int wind;
    int humidity;

    public WeatherData(int temperature, int wind, int humidity) {
        this.temperature = temperature;
        this.wind = wind;
        this.humidity = humidity;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getWind() {
        return wind;
    }

    public int getHumidity() {
        return humidity;
    }
}
