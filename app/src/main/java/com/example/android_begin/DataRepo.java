package com.example.android_begin;

import com.example.android_begin.model.WeatherRequest;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataRepo {
    private static final String OPEN_WEATHER_API_KEY = "762ee61f52313fbd10a4eb54ae4d4de2";
    private static final String OPEN_WEATHER_API_URL =
            "https://api.openweathermap.org/data/2.5/weather?q=%s&units=metric";
    private static final String KEY = "x-api-key";
    static String[] cityArray = {"Moscow", "Samara", "Vologda", "Saratov", "Omsk"};


    private static List<WeatherData> weatherData;

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
        Random r = new Random();
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
            return gson.fromJson(rawData.toString(), WeatherRequest.class);
        } catch (Exception exc) {
            exc.printStackTrace();
            return null;
        }
    }
}
