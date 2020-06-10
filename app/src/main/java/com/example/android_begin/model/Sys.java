package com.example.android_begin.model;

public class Sys {
    private long sunrise;
    private long sunset;

    public long getSunrise() {
        return sunrise * 1000;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public long getSunset() {
        return sunset * 1000;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }
}
