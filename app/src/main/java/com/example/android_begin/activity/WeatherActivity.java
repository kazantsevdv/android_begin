package com.example.android_begin.activity;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.android_begin.R;
import com.example.android_begin.fragments.CitySelectionFragment;
import com.example.android_begin.fragments.WeatherFragment;

import java.util.Objects;

public class WeatherActivity extends BaseActyvity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }
        if (savedInstanceState == null) {
            String arg = Objects.requireNonNull(getIntent().getExtras()).getString(CitySelectionFragment.CONTAINER);
            WeatherFragment details = WeatherFragment.newInstance(arg);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, details)
                    .commit();
        }
    }
}
