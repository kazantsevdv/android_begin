package com.example.android_begin.activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.android_begin.BateryReceiver;
import com.example.android_begin.NetworkReceiver;
import com.example.android_begin.R;
import com.example.android_begin.ShPref;
import com.example.android_begin.dialog.CityAddDialog;
import com.example.android_begin.fragments.CitySelectionFragment;
import com.example.android_begin.fragments.WeatherFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;


public class MainActivity extends BaseActyvity implements CityAddDialog.AddButtonListener {
    private static final int RC_SIGN_IN = 40404;
    private static final String TAG = "GoogleAuth";
    private static final String serverClientId = "1087198181903-5np4h2gmnsorg9h89307a25h7kea680v.apps.googleusercontent.com";

    private GoogleSignInClient googleSignInClient;
    private BroadcastReceiver airplaneReceiver = new NetworkReceiver();
    private BroadcastReceiver batReceiver = new BateryReceiver();
    private com.google.android.gms.common.SignInButton buttonSignIn;

    private AppBarConfiguration appBarConfiguration;
    private GoogleSignInClient googleSignInClient1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_hist, R.id.navigation_settings, R.id.navigation_info)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (R.id.navigation_home != Objects.requireNonNull(navController.getCurrentDestination()).getId()) {
                WeatherFragment detail = WeatherFragment.newInstance(ShPref.getCurCity(this));
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.weather_detail, detail);  // замена фрагмента
                ft.commit();
            }
        }
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        NavigationView navView = findViewById(R.id.nav_view);
        View headerview = navView.getHeaderView(0);
        buttonSignIn = headerview.findViewById(R.id.sign_in_button);
        buttonSignIn.setSize(SignInButton.SIZE_WIDE);
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();

            }
        });

        registerReceiver(airplaneReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        registerReceiver(batReceiver, new IntentFilter(Intent.ACTION_BATTERY_LOW));
        initNotificationChannel();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }



    @Override
    public void onAddButtonClicked(String text) {
        CitySelectionFragment fragment = null;
        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            fragment = (CitySelectionFragment) navHostFragment.getChildFragmentManager().getFragments().get(0);
        }
        if (fragment != null) {
            fragment.AddCity(text);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(airplaneReceiver);
        unregisterReceiver(batReceiver);
    }

    private void initNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel("2", getString(R.string.notifications), importance);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Проверим, входил ли пользователь в это приложение через Google
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            // Пользователь уже входил, сделаем кнопку недоступной
            buttonSignIn.setEnabled(false);
            // Обновим почтовый адрес этого пользователя и выведем его на экран
            updateUI(account.getEmail());
        }
    }

    // Получаем результаты аутентификации от окна регистрации пользователя
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            buttonSignIn.setEnabled(false);
            updateUI(account.getEmail());
        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }

    // Обновляем данные о пользователе на экране
    private void updateUI(String idToken) {
        NavigationView navView = findViewById(R.id.nav_view);
        View headerview = navView.getHeaderView(0);
        TextView token = headerview.findViewById(R.id.token);
        token.setText(idToken);
    }

}
