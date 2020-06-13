package com.example.android_begin;

import java.util.Date;

public class WeatherHist {
    private Date Date;
    private String cityName;
    private float temp;
    private int pressure;
    private int humidity;

    public WeatherHist(java.util.Date date, String cityName, float temp, int pressure, int humidity) {
        Date = date;
        this.cityName = cityName;
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
}
