package com.example.android_begin;

import java.io.Serializable;

public class WeatherData implements Serializable {
    private String cityName;
    private String temperature;
    private String humidity;
    private String wind;
    private String weatherIconText;
    private int tempVal;

    public WeatherData(String cityName, String temperature, int tempVal, String strHumidity, String strWind, String weatherIconText) {
        this.cityName = cityName;
        this.temperature = temperature;
        this.tempVal = tempVal;
        this.humidity = strHumidity;
        this.wind = strWind;
        this.weatherIconText = weatherIconText;
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

    public String getWeatherIconText() {
        return weatherIconText;
    }

    public String getCityName() {
        return cityName;
    }
}
