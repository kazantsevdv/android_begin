package com.example.android_begin;

import java.io.Serializable;

public class Container implements Serializable {
    public String cityName = "";
    public boolean showWind = false;
    public boolean showHumidity = false;
    public WeatherData data;
}
