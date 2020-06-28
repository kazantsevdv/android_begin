package com.example.android_begin;

import android.app.Application;

import androidx.room.Room;

import com.example.android_begin.room.WeatherDatabase;

public class App extends Application {
    static public App instance;
    private WeatherDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, WeatherDatabase.class, "database")
                .fallbackToDestructiveMigration()
                .build();
    }

    public WeatherDatabase getDatabase() {
        return database;
    }
}

