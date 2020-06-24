package com.example.android_begin;

import android.app.Application;

public class App extends Application {
    static public App context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}

