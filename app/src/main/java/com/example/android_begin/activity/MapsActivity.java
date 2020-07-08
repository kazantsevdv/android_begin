package com.example.android_begin.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.example.android_begin.App;
import com.example.android_begin.R;
import com.example.android_begin.internet.OpenWeatherRepo;
import com.example.android_begin.internet.ParsData;
import com.example.android_begin.room.WeatherData;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMapLongClickListener(latLng -> OpenWeatherRepo.getApiService().loadWeatherByLocations(latLng.latitude, latLng.longitude)
                .subscribeOn(Schedulers.io())
                .map(weatherRequest -> new ParsData(App.instance).getWeatherData(weatherRequest))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<WeatherData>() {
                               @Override
                               public void onSuccess(WeatherData weatherData) {
                                   String str = String.format("%s: %s", weatherData.getCityName(), weatherData.getTemperature());
                                   Toast.makeText(MapsActivity.this, str, Toast.LENGTH_SHORT).show();
                                   mMap.addMarker(new MarkerOptions()
                                           .position(latLng)
                                           .snippet(weatherData.getTemperature())
                                           .flat(true)
                                           .title(weatherData.getCityName()));
                               }

                               @Override
                               public void onError(Throwable e) {
                               }
                           }
                ));
    }
}