package com.example.android_begin.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_begin.CityAddDialog;
import com.example.android_begin.Container;
import com.example.android_begin.DataRepo;
import com.example.android_begin.R;
import com.example.android_begin.ShPref;
import com.example.android_begin.activity.WeatherActivity;
import com.example.android_begin.adapter.CityAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class CitySelectionFragment extends Fragment {

    public static final String CONTAINER = "container";
    private RecyclerView rvCity;
    private int currentPosition = 0;
    private boolean isExistWeather;
    private CityAdapter cityAdapter;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_city_selection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        currentPosition = ShPref.getCurPos(requireActivity());
        initViews(view);
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvCity.setLayoutManager(layoutManager);
        cityAdapter = new CityAdapter(DataRepo.getWeather());
        rvCity.setAdapter(cityAdapter);
        if (currentPosition > cityAdapter.getItemCount() - 1) {
            currentPosition = 0;
        }
        cityAdapter.SetOnItemClickListener(new CityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                currentPosition = position;
                showWeather();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        ShPref.setCurPos(requireActivity(), currentPosition);
    }

    private void showWeather() {
        if (isExistWeather) {
            WeatherFragment detail = WeatherFragment.newInstance(getContainer());
            FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.weather_detail, detail);  // замена фрагмента
            ft.commit();
        } else {
            Intent intent = new Intent(getActivity(), WeatherActivity.class);
            intent.putExtra(CONTAINER, getContainer());
            startActivity(intent);
        }
    }

    private void initViews(View view) {
        rvCity = view.findViewById(R.id.rv_city);
        FloatingActionButton fab = view.findViewById(R.id.fab_add_city);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CityAddDialog dialog = new CityAddDialog();
                dialog.show(requireFragmentManager(), "Диалог");
            }
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

    private Container getContainer() {
        Container container = new Container();
        container.id = currentPosition;
        container.cityName = DataRepo.getWeather().get(currentPosition).getCityName();
        return container;
    }

    public void AddCity(String text) {
        cityAdapter.notifyItemInserted(DataRepo.addCity(text));
        Snackbar.make(view, R.string.city_add, Snackbar.LENGTH_LONG)
                .setAction(R.string.Cancel, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cityAdapter.notifyItemRemoved(DataRepo.deleteCityLast());
                    }
                }).show();
    }
}


