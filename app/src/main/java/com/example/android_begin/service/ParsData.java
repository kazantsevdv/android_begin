package com.example.android_begin.service;

import android.content.Context;

import com.example.android_begin.R;
import com.example.android_begin.WeatherData;
import com.example.android_begin.model.WeatherRequest;

import java.util.Date;

public class ParsData {
    private WeatherData weatherData = null;
    private Context context;

    public ParsData(Context context) {
        this.context = context;

    }

    public WeatherData getWeatherData(WeatherRequest weatherRequest) {
        if (weatherRequest != null) {
            String strTemp = weatherRequest.getMain().getTemp() + " ℃";
            String strHumidity = weatherRequest.getMain().getHumidity() + " " + context.getString(R.string.humidity_val);
            String strWind = context.getString(R.string.wind_sped) + " " + weatherRequest.getWind().getSpeed() + " m/c";
            String weatherIcon = setWeatherIcon(weatherRequest.getWeather()[0].getId(), weatherRequest.getSys().getSunrise(), weatherRequest.getSys().getSunset());
            weatherData = new WeatherData(weatherRequest.getName(), strTemp, (int) weatherRequest.getMain().getTemp(), strHumidity, strWind, weatherIcon);
        }
        return weatherData;
    }

    private String setWeatherIcon(int actualId, long sunrise, long sunset) {
        int id = actualId / 100;
        String icon = "";
        if (actualId == 800) {
            long currentTime = new Date().getTime();
            if (currentTime >= sunrise && currentTime < sunset) {
                icon = context.getString(R.string.weather_sunny);
            } else {
                icon = context.getString(R.string.weather_clear_night);
            }
        } else {
            switch (id) {
                case 2: {
                    icon = context.getString(R.string.weather_thunder);
                    break;
                }
                case 3: {
                    icon = context.getString(R.string.weather_drizzle);
                    break;
                }
                case 5: {
                    icon = context.getString(R.string.weather_rainy);
                    break;
                }
                case 6: {
                    icon = context.getString(R.string.weather_snowy);
                    break;
                }
                case 7: {
                    icon = context.getString(R.string.weather_foggy);
                    break;
                }
                case 8: {
                    icon = context.getString(R.string.weather_cloudy);
                    break;
                }
            }
        }
        return icon;
    }
}
