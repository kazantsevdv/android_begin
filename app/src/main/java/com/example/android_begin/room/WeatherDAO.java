package com.example.android_begin.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface WeatherDAO {
    @Query("SELECT * FROM weatherdata order by data Desc")
    Maybe<List<WeatherData>> getAll();

    @Query("SELECT * FROM weatherdata WHERE cityName LiKE :cityName order by data Desc")
    Maybe<List<WeatherData>> getByCity(String cityName);

    @Insert
    void insert(WeatherData weatherData);
}
