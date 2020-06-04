package com.example.android_begin;

import java.io.Serializable;

public class Container implements Serializable {
    public int id;
    public String cityName = "";
    public boolean showWind = false;
    public boolean showHumidity = false;
    public WeatherData data;
}
