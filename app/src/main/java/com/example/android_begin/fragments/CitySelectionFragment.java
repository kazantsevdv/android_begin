package com.example.android_begin.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_begin.App;
import com.example.android_begin.R;
import com.example.android_begin.ShPref;
import com.example.android_begin.activity.MapsActivity;
import com.example.android_begin.activity.WeatherActivity;
import com.example.android_begin.adapter.CityAdapter;
import com.example.android_begin.dialog.CityAddDialog;
import com.example.android_begin.room.City;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class CitySelectionFragment extends Fragment {

    public static final String CONTAINER = "container";
    private RecyclerView rvCity;
    private String currentCity = "Moscow";
    private boolean isExistWeather;
    private CityAdapter cityAdapter;
    private View view;
    private Disposable disposable;
    private FrameLayout emptyView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_city_selection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        currentCity = ShPref.getCurCity(requireActivity());
        initViews(view);
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvCity.setLayoutManager(layoutManager);
        cityAdapter = new CityAdapter();
        rvCity.setAdapter(cityAdapter);
        cityAdapter.SetOnItemClickListener((view, cityName) -> {
            currentCity = cityName;
            showWeather();
        });
        getAllCity();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        //super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.my_location) {
            currentCity = "";
            showWeather();
            return true;
        }
        if (item.getItemId() == R.id.map) {
            Intent intent = new Intent(getActivity(), MapsActivity.class);
            //intent.putExtra(CONTAINER, currentCity);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getAllCity() {
        disposable = App.instance.getDatabase().cityDAO().getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(citys -> {
                    if (citys.size() == 0)
                        emptyView.setVisibility(View.VISIBLE);
                    else emptyView.setVisibility(View.GONE);
                    cityAdapter.addData(citys);
                        }
                );
    }

    @Override
    public void onPause() {
        super.onPause();
        ShPref.setCurCity(requireActivity(), currentCity);
    }

    private void showWeather() {
        if (isExistWeather) {
            WeatherFragment detail = WeatherFragment.newInstance(currentCity);
            FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.weather_detail, detail);  // замена фрагмента
            ft.commit();
        } else {
            Intent intent = new Intent(getActivity(), WeatherActivity.class);
            intent.putExtra(CONTAINER, currentCity);
            startActivity(intent);
        }
    }

    private void initViews(View view) {
        rvCity = view.findViewById(R.id.rv_city);
        emptyView = view.findViewById(R.id.no_data);
        FloatingActionButton fab = view.findViewById(R.id.fab_add_city);
        fab.setOnClickListener(v -> {
            CityAddDialog dialog = new CityAddDialog();
            dialog.show(getParentFragmentManager(), "Диалог");
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isExistWeather = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
        if (isExistWeather) {
            showWeather();
        }
    }

    public void AddCity(String text) {
        App.instance.getDatabase().cityDAO().insert(new City(text))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Long>() {
                    @Override
                    public void onSuccess(Long aLong) {
                        Snackbar.make(view, R.string.city_add, Snackbar.LENGTH_LONG)
                                .setAction(R.string.Cancel, v -> App.instance.getDatabase().cityDAO().delete(new City(text))
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe()).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null)
            disposable.dispose();
    }
}


