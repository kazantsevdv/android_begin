package com.example.android_begin.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.android_begin.Container;
import com.example.android_begin.Publisher;
import com.example.android_begin.PublisherGetter;
import com.example.android_begin.R;
import com.example.android_begin.WeatherData;
import com.example.android_begin.activity.WeatherActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class CitySelectionFragment extends Fragment {
    public static final String CURRENT_CITY = "CurrentCity";
    public static final String IS_SHOW_HUMIDITY = "isShowHumidity";
    public static final String IS_SHOW_WIND = "isShowWind";
    public static final String CONTAINER = "container";
    private static List<WeatherData> weatherData = new ArrayList<>();
    private ListView listCity;
    private int currentPosition = 0;
    private boolean isShowHumidity = false;
    private boolean isShowWind = false;
    private boolean isExistWeather;
    private String[] listCityResources;
    private Publisher publisher;
    private WeatherFragment detail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_city_selection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initList();
    }

    private void initList() {
        listCityResources = getResources().getStringArray(R.array.city);
        Random r = new Random();
        for (int i = 0; i < listCityResources.length; i++) {
            weatherData.add(new WeatherData(r.nextInt(50), r.nextInt(20), r.nextInt(50)));
        }
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_list_item_activated_1, listCityResources);
        listCity.setAdapter(adapter);
        listCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                currentPosition = i;
                showWeather();
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        if (detail != null)
            publisher.unsubscribe(detail);
    }

    private void showWeather() {
        if (isExistWeather) {
            if (detail != null)
                publisher.unsubscribe(detail);
            detail = WeatherFragment.newInstance(getContainer());
            FragmentTransaction ft = Objects.requireNonNull(getFragmentManager()).beginTransaction();
            ft.replace(R.id.weather_detail, detail);  // замена фрагмента
            ft.commit();
            publisher.subscribe(detail);
        } else {
            Intent intent = new Intent(getActivity(), WeatherActivity.class);
            intent.putExtra(CONTAINER, getContainer());
            startActivity(intent);
        }
    }

    private void initViews(View view) {
        listCity = view.findViewById(R.id.lw_city);
        Switch wind = view.findViewById(R.id.sw_wind);
        wind.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isShowWind = isChecked;
                if (isExistWeather) {
                    publisher.notifyWind(isChecked);
                }
            }
        });
        Switch humidity = view.findViewById(R.id.sw_humidity);
        humidity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isShowHumidity = isChecked;
                if (isExistWeather) {
                    publisher.notifyHum(isChecked);
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isExistWeather = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(CURRENT_CITY, 0);
            isShowHumidity = savedInstanceState.getBoolean(IS_SHOW_HUMIDITY, false);
            isShowWind = savedInstanceState.getBoolean(IS_SHOW_WIND, false);
            listCity.setSelection(currentPosition);
        }
        if (isExistWeather) {
            showWeather();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(CURRENT_CITY, currentPosition);
        outState.putBoolean(IS_SHOW_HUMIDITY, isShowHumidity);
        outState.putBoolean(IS_SHOW_WIND, isShowWind);
        super.onSaveInstanceState(outState);
    }

    private Container getContainer() {
        Container container = new Container();
        container.cityName = listCityResources[currentPosition];
        container.showHumidity = isShowHumidity;
        container.showWind = isShowWind;
        container.data = weatherData.get(currentPosition);
        return container;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        publisher = ((PublisherGetter) context).getPublisher();
    }
}
