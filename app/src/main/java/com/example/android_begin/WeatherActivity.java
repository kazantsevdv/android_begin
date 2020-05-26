package com.example.android_begin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WeatherActivity extends AppCompatActivity {
    TextView city;
    TextView wind;
    TextView humidity;
    private String cityName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        city = findViewById(R.id.tv_city);
        wind = findViewById(R.id.tv_wind);
        humidity=findViewById(R.id.tv_humidity);
        cityName = getIntent().getExtras().getString(CitySelectionActivity.CITY, "NaN");
        city.setText(cityName);
        wind.setVisibility(getIntent().getExtras().getBoolean(CitySelectionActivity.SWITCH_WIND,false)? View.VISIBLE:View.INVISIBLE);
        humidity.setVisibility(getIntent().getExtras().getBoolean(CitySelectionActivity.SWITCH_HUMIDITY,false)? View.VISIBLE:View.INVISIBLE);
        findViewById(R.id.bt_www).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://wikipedia.org/wiki/"+ cityName);
                Intent browser = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(browser);

            }
        });
    }
}
