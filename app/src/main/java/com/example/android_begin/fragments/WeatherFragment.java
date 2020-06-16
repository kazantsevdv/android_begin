package com.example.android_begin.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.android_begin.Container;
import com.example.android_begin.DataRepo;
import com.example.android_begin.R;
import com.example.android_begin.TemperatureView;
import com.example.android_begin.model.WeatherRequest;

import java.util.Date;

public class WeatherFragment extends Fragment {
    public static final String CONTAINER = "container";
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
        loadWeatherData(container.cityName);
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
        new Thread() {
            @Override
            public void run() {
                weatherData = DataRepo.getWeatherData(city);
                if (weatherData == null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            refresh.setRefreshing(false);
                            errView.setVisibility(View.VISIBLE);
                            if (getActivity() != null) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                                builder.setTitle(R.string.error)
                                        .setMessage(R.string.error_get_data)
                                        .setIcon(R.drawable.ic_baseline_error_outline_24)
                                        .setCancelable(true)
                                        .setPositiveButton(R.string.ok, null)
                                        .create()
                                        .show();
                            }
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (getActivity() != null) {
                                refresh.setRefreshing(false);
                                content.setVisibility(View.VISIBLE);
                                fillView();
                            }
                        }
                    });
                }
            }
        }.start();
    }

    private void fillView() {
        String strTemperature = weatherData.getMain().getTemp() + " ℃";
        temperature.setText(strTemperature);
        String strHumidity = weatherData.getMain().getHumidity() + " " + getString(R.string.humidity_val);
        humidity.setText(strHumidity);
        String strWind = getString(R.string.wind_sped) + " " + weatherData.getWind().getSpeed() + " m/c";
        wind.setText(strWind);
        weatherIconTextView.setText(setWeatherIcon(weatherData.getWeather()[0].getId(), weatherData.getSys().getSunrise(), weatherData.getSys().getSunset()));
        float val = weatherData.getMain().getTemp();
        temperatureView.setValue((int) val);
    }

    private String setWeatherIcon(int actualId, long sunrise, long sunset) {
        int id = actualId / 100;
        String icon = "";

        if (actualId == 800) {
            long currentTime = new Date().getTime();
            if (currentTime >= sunrise && currentTime < sunset) {
                icon = getString(R.string.weather_sunny);
            } else {
                icon = getString(R.string.weather_clear_night);
            }
        } else {
            switch (id) {
                case 2: {
                    icon = getString(R.string.weather_thunder);
                    break;
                }
                case 3: {
                    icon = getString(R.string.weather_drizzle);
                    break;
                }
                case 5: {
                    icon = getString(R.string.weather_rainy);
                    break;
                }
                case 6: {
                    icon = getString(R.string.weather_snowy);
                    break;
                }
                case 7: {
                    icon = getString(R.string.weather_foggy);
                    break;
                }
                case 8: {
                    icon = getString(R.string.weather_cloudy);
                    break;
                }
            }
        }
        return icon;
    }
}
