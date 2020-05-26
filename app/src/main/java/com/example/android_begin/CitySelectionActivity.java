package com.example.android_begin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CitySelectionActivity extends AppCompatActivity {
    public static final String CITY = "CITY";
    public static final String SWITCH_WIND = "SWITCH_WIND";
    public static final String SWITCH_HUMIDITY = "SWITCH_PRESSURE";

    Spinner spinner;
    private EditText inputCity;
    private Switch wind;
    private Switch humidity;
    List<String> listCitysRsources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        listCitysRsources = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.city)));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_selection);
        ListView listCity = findViewById(R.id.lw_city);
        inputCity = findViewById(R.id.et_city);
        wind = findViewById(R.id.sw_wind);
        humidity = findViewById(R.id.sw_humidity);
        Button selectButton = findViewById(R.id.button);

        listCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                inputCity.setText(listCitysRsources.get(i));
            }
        });
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!inputCity.getText().toString().equals("")) {
                    Intent intent = new Intent(CitySelectionActivity.this, WeatherActivity.class);
                    intent.putExtra(CITY, inputCity.getText().toString());
                    intent.putExtra(SWITCH_WIND, wind.isChecked());
                    intent.putExtra(SWITCH_HUMIDITY, humidity.isChecked());

                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.No_city_set, Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


}
