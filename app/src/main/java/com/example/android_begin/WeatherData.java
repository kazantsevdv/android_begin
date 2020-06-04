package com.example.android_begin;

import java.io.Serializable;

public class WeatherData implements Serializable {
    private String cityName;
    private int temperature;
    private int wind;
    private int humidity;

    public WeatherData(String cityName, int temperature, int wind, int humidity) {
        this.cityName = cityName;
        this.temperature = temperature;
        this.wind = wind;
        this.humidity = humidity;
    }

    public String getCityName() {
        return cityName;
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
