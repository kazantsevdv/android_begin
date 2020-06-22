package com.example.android_begin.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.android_begin.Container;
import com.example.android_begin.R;
import com.example.android_begin.dialog.searchCityDialog;
import com.example.android_begin.fragments.WeatherFragment;

public class WeatherActivityDialog extends BaseActyvity {
    public static final String SEARCH_DIALOG = "SEARCH_DIALOG";
    public static final String CURRENT_CITY = "CURRENT_CITY";
    private searchCityDialog dialog;
    private String currentCity = "Moscow";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new searchCityDialog();
        setContentView(R.layout.activity_weather);
        if (savedInstanceState != null) {
            currentCity = savedInstanceState.getString(CURRENT_CITY, "");
        }
        openWeather(currentCity);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(CURRENT_CITY, currentCity);
    }

    private void openWeather(String city) {
        WeatherFragment details = WeatherFragment.newInstance(getContainer(city));
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, details)
                .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_weather_activity_dialog, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_search) {
            dialog.show(getSupportFragmentManager(), SEARCH_DIALOG);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Container getContainer(String city) {
        Container container = new Container();
        container.cityName = city;
        return container;
    }

    public void onDialogResult(String strCity) {
        currentCity = strCity;
        openWeather(currentCity);
    }
}
