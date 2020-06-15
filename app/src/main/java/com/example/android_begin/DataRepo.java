package com.example.android_begin;

import com.example.android_begin.model.WeatherRequest;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataRepo {
    private static final String OPEN_WEATHER_API_KEY = "e1c7641d2f37202b27e9d0e06852f460";
    private static final String OPEN_WEATHER_API_URL =
            "https://api.openweathermap.org/data/2.5/weather?q=%s&units=metric";
    private static final String KEY = "x-api-key";
    static String[] cityArray = {"Moscow", "Samara", "Vologda", "Saratov", "Omsk"};


    private static List<WeatherData> weatherData;

    private static List<WeatherHist> weatherHists = new ArrayList<>();

    public static List<WeatherHist> getWeatherHists() {
        return weatherHists;
    }

    static {
        weatherData = new ArrayList<>();
        for (String listCity : cityArray) {
            weatherData.add(new WeatherData(listCity));
        }
    }

    public static List<WeatherData> getWeather() {
        return weatherData;
    }

    public static int addCity(String name) {
        weatherData.add(new WeatherData(name));
        return weatherData.size() - 1;
    }

    public static int deleteCityLast() {
        int id = weatherData.size() - 1;
        weatherData.remove(id);
        return id;
    }

    public static WeatherRequest getWeatherData(String city) {
        try {
            URL url = new URL(String.format(OPEN_WEATHER_API_URL, city));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty(KEY, OPEN_WEATHER_API_KEY);
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder rawData = new StringBuilder(1024);
            String tempVariable;
            while ((tempVariable = reader.readLine()) != null) {
                rawData.append(tempVariable).append("\n");
            }
            reader.close();
            Gson gson = new Gson();
            WeatherRequest weatherRequest = gson.fromJson(rawData.toString(), WeatherRequest.class);
            if (weatherRequest != null) {
                weatherHists.add(new WeatherHist(new Date(), city, weatherRequest.getMain().getTemp(), weatherRequest.getMain().getPressure(), weatherRequest.getMain().getHumidity()));
            }

            return weatherRequest;
        } catch (Exception exc) {
            exc.printStackTrace();
            return null;
        }
    }
}
