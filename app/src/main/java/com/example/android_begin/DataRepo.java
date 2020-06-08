package com.example.android_begin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DataRepo {
    static String[] cityArray = {"Москва", "Самара", "Вологда", "Саратов", "Новосибирск", "Омск", "Екатеринбург",};

    private static List<WeatherData> weatherData;
    private static Map<Integer, List<WeatherHist>> weatherHist;

    static {
        weatherData = new ArrayList<>();

        Random r = new Random();
        for (String listCity : cityArray) {
            weatherData.add(new WeatherData(listCity, r.nextInt(50), r.nextInt(20), r.nextInt(50)));
        }
        weatherHist = new HashMap<>();
        for (int i = 0; i < cityArray.length; i++) {
            weatherHist.put(i, cityHist());
        }
    }

    static List<WeatherHist> cityHist() {
        Random r = new Random();
        long timestamp = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        List<WeatherHist> weatherHists = new ArrayList<>();
        for (int j = 1; j < 14; j++) {
            calendar.setTimeInMillis(timestamp);
            calendar.add(Calendar.DAY_OF_MONTH, -j);
            String data = calendar.get(Calendar.DAY_OF_MONTH) + "." + calendar.get(Calendar.MONTH) + "." + calendar.get(Calendar.YEAR);
            weatherHists.add(new WeatherHist(data, r.nextInt(50)));
        }
        return weatherHists;
    }

    public static List<WeatherData> getWeather() {
        return weatherData;
    }

    public static List<WeatherHist> getWeatherHist(int pos) {
        return weatherHist.get(pos);
    }

    public static int addCity(String name) {
        Random r = new Random();
        weatherData.add(new WeatherData(name, r.nextInt(50), r.nextInt(20), r.nextInt(50)));
        int key = weatherData.size() - 1;
        weatherHist.put(key, cityHist());
        return key;
    }

    public static int deleteCityLast() {
        int id = weatherData.size() - 1;
        weatherData.remove(id);
        weatherHist.remove(id);
        return id;
    }
}
