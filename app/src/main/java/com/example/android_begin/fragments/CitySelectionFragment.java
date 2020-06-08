package com.example.android_begin.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_begin.CityAdapter;
import com.example.android_begin.Container;
import com.example.android_begin.DataRepo;
import com.example.android_begin.Publisher;
import com.example.android_begin.PublisherGetter;
import com.example.android_begin.R;
import com.example.android_begin.activity.WeatherActivity;

import java.util.Objects;

public class CitySelectionFragment extends Fragment {
    public static final String CURRENT_CITY = "CurrentCity";
    public static final String IS_SHOW_HUMIDITY = "isShowHumidity";
    public static final String IS_SHOW_WIND = "isShowWind";
    public static final String CONTAINER = "container";
    private RecyclerView rvCity;
    private int currentPosition = 0;
    private boolean isShowHumidity = false;
    private boolean isShowWind = false;
    private boolean isExistWeather;
    private Publisher publisher;
    private WeatherFragment detail;
    private CityAdapter cityAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_city_selection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvCity.setLayoutManager(layoutManager);
        cityAdapter = new CityAdapter(DataRepo.getWeather());
        rvCity.setAdapter(cityAdapter);
        cityAdapter.SetOnItemClickListener(new CityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                currentPosition = position;
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
        rvCity = view.findViewById(R.id.rv_city);
        Switch wind = view.findViewById(R.id.sw_wind);
        final EditText edNewCity = view.findViewById(R.id.et_city);
        ImageButton ibAddCity = view.findViewById(R.id.ib_addcity);
        ibAddCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = edNewCity.getText().toString();
                if (s.equals("")) {
                    Toast.makeText(getContext(), "no city", Toast.LENGTH_SHORT).show();
                } else {
                    cityAdapter.notifyItemInserted(DataRepo.addCity(s));
                }
            }
        });
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
        container.id = currentPosition;
        container.cityName = DataRepo.getWeather().get(currentPosition).getCityName();
        container.showHumidity = isShowHumidity;
        container.showWind = isShowWind;
        container.data = DataRepo.getWeather().get(currentPosition);
        return container;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        publisher = ((PublisherGetter) context).getPublisher();
    }
}
