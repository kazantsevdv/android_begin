package com.example.android_begin.internet;

import android.content.Context;

import com.example.android_begin.R;
import com.example.android_begin.WeatherData;
import com.example.android_begin.model.WeatherRequest;

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
            weatherData = new WeatherData(weatherRequest.getName(), strTemp, (int) weatherRequest.getMain().getTemp(), strHumidity, strWind, weatherRequest.getWeather()[0].getIcon());
        }
        return weatherData;
    }
}
