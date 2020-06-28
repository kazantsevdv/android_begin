package com.example.android_begin.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class City {

    @PrimaryKey
    @NonNull
    private String cityName;

    public City(@NonNull String cityName) {
        this.cityName = cityName;
    }

    @NonNull
    public String getCityName() {
        return cityName;
    }

    public void setCityName(@NonNull String cityName) {
        this.cityName = cityName;
    }
}
