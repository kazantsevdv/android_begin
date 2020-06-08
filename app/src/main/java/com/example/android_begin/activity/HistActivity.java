package com.example.android_begin.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_begin.BaseActyvity;
import com.example.android_begin.DataRepo;
import com.example.android_begin.R;
import com.example.android_begin.adapter.HistAdapter;

import java.util.Objects;

public class HistActivity extends BaseActyvity {

    public static final String CITY = "City";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fr_hist);
        initViews();
    }

    private void initViews() {
        int pos = Objects.requireNonNull(getIntent().getExtras()).getInt(CITY, 0);
        RecyclerView recyclerView = findViewById(R.id.rv_hist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        HistAdapter histAdapter = new HistAdapter(DataRepo.getWeatherHist(pos));
        recyclerView.setAdapter(histAdapter);

    }


}