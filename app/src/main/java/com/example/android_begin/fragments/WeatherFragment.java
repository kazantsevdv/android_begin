package com.example.android_begin.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.android_begin.Container;
import com.example.android_begin.R;
import com.example.android_begin.activity.HistActivity;
import com.google.android.material.button.MaterialButton;

import java.util.Objects;

public class WeatherFragment extends Fragment {
    public static final String CONTAINER = "container";
    private Container container;

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
        container = (Container) Objects.requireNonNull(getArguments()).getSerializable(WeatherFragment.CONTAINER);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {
        TextView city = view.findViewById(R.id.tv_city);
        TextView wind = view.findViewById(R.id.tv_wind);
        TextView humidity = view.findViewById(R.id.tv_humidity);
        TextView temperature = view.findViewById(R.id.tv_temperature);
        String strTemperature = container.data.getTemperature() + "℃";
        temperature.setText(strTemperature);
        String strHumidity = container.data.getHumidity() + " " + getString(R.string.humidity_val);
        humidity.setText(strHumidity);
        String strWind = getString(R.string.wind_sped) + " " + container.data.getWind() + " m/c";
        wind.setText(strWind);
        city.setText(container.cityName);
        MaterialButton btHist = view.findViewById(R.id.ib_hist);
        btHist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HistActivity.class);
                intent.putExtra(HistActivity.CITY, container.id);
                startActivity(intent);
            }
        });

    }


}
