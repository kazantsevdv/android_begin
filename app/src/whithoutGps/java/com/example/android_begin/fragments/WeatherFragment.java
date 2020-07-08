package com.example.android_begin.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
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
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.android_begin.App;
import com.example.android_begin.R;
import com.example.android_begin.internet.OpenWeatherRepo;
import com.example.android_begin.internet.ParsData;
import com.example.android_begin.room.WeatherData;
import com.example.android_begin.view.TemperatureView;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static android.content.Context.LOCATION_SERVICE;

public class WeatherFragment extends Fragment {
    public static final String CITY_NAME = "city_name";
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 21454;
    private SwipeRefreshLayout refresh;
    private TextView humidity;
    private TextView temperature;
    private TextView wind;
    private ConstraintLayout content;
    private FrameLayout errView;
    private TemperatureView temperatureView;
    private ImageView imageView;
    private TextView city;
    private String cityName;

    public static WeatherFragment newInstance(String cityName) {
        WeatherFragment Fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putString(CITY_NAME, cityName);
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
        cityName = requireArguments().getString(CITY_NAME, "Moscow");
        if (cityName.equals("")) cityName = "Moscow";


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadWeatherDataByLocations();
            }
            showEr();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);

        loadWeatherData(cityName);

    }

    private void initViews(View view) {
        refresh = view.findViewById(R.id.refresh);
        refresh.setOnRefreshListener(() -> {

            loadWeatherData(cityName);


        });
        city = view.findViewById(R.id.tv_city);
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

    void loadWeatherDataByLocations() {
        boolean loadRez = false;
        LocationManager mLocManager = (LocationManager) requireActivity().getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);

        } else {
            Location loc = Objects.requireNonNull(mLocManager).getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (loc != null) {
                loadRez = true;
                OpenWeatherRepo.getApiService().loadWeatherByLocations(loc.getLatitude(), loc.getLongitude())
                        .subscribeOn(Schedulers.io())
                        .map(weatherRequest -> new ParsData(App.instance).getWeatherData(weatherRequest))
                        .doOnSuccess(weatherData -> App.instance.getDatabase().weatherDAO().insert(weatherData))
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> refresh.setRefreshing(true))
                        .doFinally(() -> refresh.setRefreshing(false))
                        .subscribe(new DisposableSingleObserver<WeatherData>() {
                                       @Override
                                       public void onSuccess(WeatherData weatherData) {
                                           content.setVisibility(View.VISIBLE);
                                           errView.setVisibility(View.GONE);
                                           fillView(weatherData);
                                       }

                                       @Override
                                       public void onError(Throwable e) {
                                           showEr();
                                       }
                                   }
                        );
            }

            if (!loadRez) {
                showEr();
            }
        }
    }

    private void showEr() {
        refresh.setRefreshing(false);
        content.setVisibility(View.GONE);
        errView.setVisibility(View.VISIBLE);
    }

    void loadWeatherData(final String city) {

        OpenWeatherRepo.getApiService().loadWeather(city)
                .subscribeOn(Schedulers.io())
                .map(weatherRequest -> new ParsData(App.instance).getWeatherData(weatherRequest))
                .doOnSuccess(weatherData -> App.instance.getDatabase().weatherDAO().insert(weatherData))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> refresh.setRefreshing(true))
                .doFinally(() -> refresh.setRefreshing(false))
                .subscribe(new DisposableSingleObserver<WeatherData>() {
                               @Override
                               public void onSuccess(WeatherData weatherData) {
                                   content.setVisibility(View.VISIBLE);
                                   errView.setVisibility(View.GONE);
                                   fillView(weatherData);
                               }

                               @Override
                               public void onError(Throwable e) {
                                   content.setVisibility(View.GONE);
                                   errView.setVisibility(View.VISIBLE);
                               }
                           }
                );
    }

    private void fillView(WeatherData weatherData) {
        city.setText(weatherData.getCityName());
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
