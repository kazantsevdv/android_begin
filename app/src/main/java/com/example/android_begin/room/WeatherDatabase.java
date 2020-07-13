package com.example.android_begin.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {WeatherData.class, City.class}, version = 5)
public abstract class WeatherDatabase extends RoomDatabase {
    public abstract WeatherDAO weatherDAO();

    public abstract SityDAO cityDAO();

}
