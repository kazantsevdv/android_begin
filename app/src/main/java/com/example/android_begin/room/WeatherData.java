package com.example.android_begin.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class WeatherData implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long data;
    private String cityName;
    private String temperature;
    private String humidity;
    private String wind;
    private int tempVal;
    private String iconId;

    public WeatherData(String cityName, long data, String temperature, int tempVal, String strHumidity, String strWind, String iconId) {
        this.cityName = cityName;
        this.data = data;
        this.temperature = temperature;
        this.tempVal = tempVal;
        this.humidity = strHumidity;
        this.wind = strWind;
        this.iconId = iconId;
    }

    public WeatherData(String cityName) {
        this.cityName = cityName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }

    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    public int getTempVal() {
        return tempVal;
    }

    public void setTempVal(int tempVal) {
        this.tempVal = tempVal;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
