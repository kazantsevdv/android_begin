package com.example.android_begin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.android_begin.Container;
import com.example.android_begin.Observer;
import com.example.android_begin.R;

public class WeatherFragment extends Fragment implements Observer {

    public static final String CONTAINER = "container";
    private Container container;
    private TextView wind;
    private TextView humidity;

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
        return inflater.inflate(R.layout.fr_weather, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        container = (Container) getArguments().getSerializable(WeatherFragment.CONTAINER);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {
        TextView city = view.findViewById(R.id.tv_city);
        wind = view.findViewById(R.id.tv_wind);
        humidity = view.findViewById(R.id.tv_humidity);
        TextView temperature = view.findViewById(R.id.tv_temperature);
        temperature.setText(new StringBuilder().append(container.data.getTemperature()).append("℃").toString());
        humidity.setText(new StringBuilder().append(container.data.getHumidity()).append(" ").append(getString(R.string.humidity_val)).toString());
        wind.setText(new StringBuilder().append(getString(R.string.wind_sped)).append(" ").append(container.data.getWind()).append(" m/c").toString());
        city.setText(container.cityName);
        wind.setVisibility(container.showWind ? View.VISIBLE : View.INVISIBLE);
        humidity.setVisibility(container.showHumidity ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void updateWind(boolean show) {
        if (wind != null)
            wind.setVisibility(show ? View.VISIBLE : View.INVISIBLE);

    }

    @Override
    public void updateHumidity(boolean show) {
        if (humidity != null)
            humidity.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }
}
