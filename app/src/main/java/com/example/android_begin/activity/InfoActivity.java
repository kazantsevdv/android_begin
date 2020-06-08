package com.example.android_begin.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.android_begin.R;
import com.example.android_begin.fragments.InfoFragment;

public class InfoActivity extends BaseActyvity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        InfoFragment infoFragment = new InfoFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, infoFragment)
                .commit();
    }
}
