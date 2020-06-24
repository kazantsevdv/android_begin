package com.example.android_begin.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.android_begin.DataRepo;
import com.example.android_begin.fragments.WeatherFragment;
import com.example.android_begin.internet.ParsData;
import com.example.android_begin.model.WeatherRequest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WeatherService extends Service {

    private ExecutorService es;

    @Override
    public void onCreate() {
        super.onCreate();
        es = Executors.newFixedThreadPool(1);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String city = intent.getStringExtra(WeatherFragment.CITY_NAME);
        Run run = new Run(startId, city, this);
        es.execute(run);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class Run implements Runnable {
        String city;
        int startId;
        Context context;

        public Run(int startId, String city, Context context) {
            this.city = city;
            this.startId = startId;
            this.context = context;
        }

        public void run() {
            Intent intent = new Intent(WeatherFragment.BROADCAST_ACTION);
            WeatherRequest weatherRequest = DataRepo.getWeatherData(city);
            intent.putExtra(WeatherFragment.CITY_NAME, new ParsData(context).getWeatherData(weatherRequest));
            sendBroadcast(intent);
            stop();
        }

        void stop() {
            stopSelfResult(startId);
        }
    }
}
