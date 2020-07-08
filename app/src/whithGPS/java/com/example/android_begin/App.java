package com.example.android_begin;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.android_begin.room.City;
import com.example.android_begin.room.WeatherDatabase;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class App extends Application {
    static public App instance;
    private WeatherDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, WeatherDatabase.class, "database")
                .fallbackToDestructiveMigration()
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        App.instance.getDatabase().cityDAO().insert(new City(getString(R.string.current_location)))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new DisposableSingleObserver<Long>() {
                                    @Override
                                    public void onSuccess(Long aLong) {

                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }
                                });

                    }
                })
                .build();
    }

    public WeatherDatabase getDatabase() {
        return database;
    }
}

