package com.example.android_begin.internet;

import com.example.android_begin.model.WeatherRequest;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherApi {
    @GET("weather")
    Single<WeatherRequest> loadWeather(@Query("q") String city);

    @GET("weather")
    Single<WeatherRequest> loadWeatherByLocations(@Query("lat") double lat, @Query("lon") double lon);
}
