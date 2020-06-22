package com.example.android_begin.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.android_begin.Container;
import com.example.android_begin.R;
import com.example.android_begin.WeatherData;
import com.example.android_begin.model.WeatherRequest;
import com.example.android_begin.service.WeatherService;
import com.example.android_begin.view.TemperatureView;

public class WeatherFragment extends Fragment {
    public static final String CONTAINER = "container";
    public static final String CITY_NAME = "CITY_NAME";
    public final static String BROADCAST_ACTION = "weather.servicebackbroadcast";
    private Container container;
    private final Handler handler = new Handler();
    private WeatherRequest weatherData;
    private SwipeRefreshLayout refresh;
    private TextView humidity;
    private TextView temperature;
    private TextView wind;
    private ConstraintLayout content;
    private FrameLayout errView;
    private TextView weatherIconTextView;
    private TemperatureView temperatureView;
    private BroadcastReceiver br;

    public static WeatherFragment newInstance(Container container) {
        WeatherFragment Fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putSerializable(CONTAINER, container);
        Fragment.setArguments(args);
        return Fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        container = (Container) requireArguments().getSerializable(WeatherFragment.CONTAINER);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initBR();
        loadWeatherData(container.cityName);
    }

    private void initBR() {
        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                refresh.setRefreshing(false);
                WeatherData weatherData = (WeatherData) intent.getSerializableExtra(CITY_NAME);
                if (weatherData != null) {
                    content.setVisibility(View.VISIBLE);
                    errView.setVisibility(View.GONE);
                    fillView(weatherData);
                } else {
                    content.setVisibility(View.GONE);
                    errView.setVisibility(View.VISIBLE);
                }
            }
        };
    }

    @Override
    public void onPause() {
        super.onPause();
        requireContext().unregisterReceiver(br);
    }

    @Override
    public void onResume() {
        super.onResume();
        requireContext().registerReceiver(br, new IntentFilter(BROADCAST_ACTION));
    }

    private void initViews(View view) {
        refresh = view.findViewById(R.id.refresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadWeatherData(container.cityName);
            }
        });
        weatherIconTextView = view.findViewById(R.id.weather_icon);
        TextView city = view.findViewById(R.id.tv_city);
        city.setText(container.cityName);
        wind = view.findViewById(R.id.tv_wind);
        humidity = view.findViewById(R.id.tv_humidity);
        temperature = view.findViewById(R.id.tv_temperature);
        content = view.findViewById(R.id.cl_content);
        content.setVisibility(View.GONE);
        errView = view.findViewById(R.id.error);
        errView.setVisibility(View.GONE);
        temperatureView = view.findViewById(R.id.tv_view);
    }

    void loadWeatherData(final String city) {
        refresh.setRefreshing(true);
        Intent intent = new Intent(requireActivity(), WeatherService.class).putExtra(CITY_NAME, city);
        // стартуем сервис
        if (getActivity() != null)
            requireActivity().startService(intent);
    }

    private void fillView(WeatherData weatherData) {
        temperature.setText(weatherData.getTemperature());
        humidity.setText(weatherData.getHumidity());
        wind.setText(weatherData.getWind());
        weatherIconTextView.setText(weatherData.getWeatherIconText());
        temperatureView.setValue(weatherData.getTempVal());
    }
}
