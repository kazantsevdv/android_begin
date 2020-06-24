package com.example.android_begin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.android_begin.App;
import com.example.android_begin.Container;
import com.example.android_begin.R;
import com.example.android_begin.WeatherData;
import com.example.android_begin.internet.OpenWeatherRepo;
import com.example.android_begin.internet.ParsData;
import com.example.android_begin.model.WeatherRequest;
import com.example.android_begin.view.TemperatureView;
import com.squareup.picasso.Picasso;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class WeatherFragment extends Fragment {
    public static final String CONTAINER = "container";
    public static final String CITY_NAME = "CITY_NAME";
    public static final String BROADCAST_ACTION = "service_broadcast";
    private Container container;
    private SwipeRefreshLayout refresh;
    private TextView humidity;
    private TextView temperature;
    private TextView wind;
    private ConstraintLayout content;
    private FrameLayout errView;
    private TemperatureView temperatureView;
    private ImageView imageView;

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
        imageView = view.findViewById(R.id.imageView3);
    }

    void loadWeatherData(final String city) {
        refresh.setRefreshing(true);
        OpenWeatherRepo.getApiService().loadWeather(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<WeatherRequest, WeatherData>() {
                    @Override
                    public WeatherData apply(WeatherRequest weatherRequest) throws Exception {
                        return new ParsData(App.context).getWeatherData(weatherRequest);
                    }
                })
                .subscribe(new DisposableSingleObserver<WeatherData>() {
                               @Override
                               public void onSuccess(WeatherData weatherData) {
                                   refresh.setRefreshing(false);
                                   content.setVisibility(View.VISIBLE);
                                   errView.setVisibility(View.GONE);
                                   fillView(weatherData);
                               }

                               @Override
                               public void onError(Throwable e) {
                                   content.setVisibility(View.GONE);
                                   errView.setVisibility(View.VISIBLE);
                                   refresh.setRefreshing(false);
                               }
                           }
                );
    }

    private void fillView(WeatherData weatherData) {
        temperature.setText(weatherData.getTemperature());
        humidity.setText(weatherData.getHumidity());
        wind.setText(weatherData.getWind());
        temperatureView.setValue(weatherData.getTempVal());
        String s = String.format("https://openweathermap.org/img/wn/%s@2x.png", weatherData.getIconId());
        Picasso.get()
                .load(s)
                .into(imageView);
    }
}
