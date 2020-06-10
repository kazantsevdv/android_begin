package com.example.android_begin;

import java.io.Serializable;

public class WeatherData implements Serializable {
    private String cityName;


    public WeatherData(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }
}
