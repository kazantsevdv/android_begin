package com.example.android_begin;

public class WeatherHist {
    private String data;
    private int temperature;

    public WeatherHist(String data, int temperature) {
        this.data = data;
        this.temperature = temperature;
    }

    public String getData() {
        return data;
    }

    public int getTemperature() {
        return temperature;
    }


}
