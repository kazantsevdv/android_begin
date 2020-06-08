package com.example.android_begin.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.android_begin.R;
import com.example.android_begin.fragments.SettingFragment;

public class SettingsActivity extends BaseActyvity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        SettingFragment settingFragment = new SettingFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, settingFragment)
                .commit();
    }
}
