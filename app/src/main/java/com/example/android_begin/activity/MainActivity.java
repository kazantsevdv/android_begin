package com.example.android_begin.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.android_begin.BaseActyvity;
import com.example.android_begin.CityAddDialog;
import com.example.android_begin.R;
import com.example.android_begin.fragments.CitySelectionFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends BaseActyvity implements CityAddDialog.AddButtonListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            BottomNavigationView navView = findViewById(R.id.nav_view);
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_home, R.id.navigation_settings, R.id.navigation_info)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(navView, navController);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.navigation_settings:
                Intent intentSet = new Intent(this, SettingsActivity.class);
                startActivity(intentSet);
                return true;
            case R.id.navigation_info:
                Intent intentInfo = new Intent(this, InfoActivity.class);
                startActivity(intentInfo);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAddButtonClicked(String text) {
        CitySelectionFragment fragment = null;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
            if (navHostFragment != null) {
                fragment = (CitySelectionFragment) navHostFragment.getChildFragmentManager().getFragments().get(0);
            }

        } else {
            fragment = (CitySelectionFragment) getSupportFragmentManager().findFragmentById(R.id.cities);
        }
        if (fragment != null) {
            fragment.AddCity(text);
        }
    }
}
