package com.example.android_begin;

import java.io.Serializable;

public class WeatherData implements Serializable {
    private String cityName;
    private String temperature;
    private String humidity;
    private String wind;
    private int tempVal;
    private String iconId;


    public WeatherData(String cityName, String temperature, int tempVal, String strHumidity, String strWind, String iconId) {
        this.cityName = cityName;
        this.temperature = temperature;
        this.tempVal = tempVal;
        this.humidity = strHumidity;
        this.wind = strWind;
        this.iconId = iconId;
    }

    public String getIconId() {
        return iconId;
    }

    public int getTempVal() {
        return tempVal;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getWind() {
        return wind;
    }

    public WeatherData(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }
}
