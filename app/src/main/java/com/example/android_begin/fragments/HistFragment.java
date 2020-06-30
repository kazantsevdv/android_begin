package com.example.android_begin.fragments;

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
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_begin.App;
import com.example.android_begin.R;
import com.example.android_begin.adapter.HistAdapter;
import com.example.android_begin.room.WeatherData;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.schedulers.Schedulers;

public class HistFragment extends Fragment {

    private RecyclerView rvHist;
    private HistAdapter histAdapter;
    private FrameLayout emptyView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initRecyclerView();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_hist, menu);
        MenuItem search = menu.findItem(R.id.action_search);
        final SearchView searchText = (SearchView) search.getActionView();
        searchText.setOnCloseListener(() -> {
            loadAllData();
            return false;
        });
        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String cityFilter = String.format("%%%s%%", query);
                App.instance.getDatabase().weatherDAO().getByCity(cityFilter)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DisposableMaybeObserver<List<WeatherData>>() {
                            @Override
                            public void onSuccess(List<WeatherData> weatherData) {
                                histAdapter = new HistAdapter(weatherData);
                                rvHist.setAdapter(histAdapter);
                                if (weatherData.size() == 0) emptyView.setVisibility(View.VISIBLE);
                                else emptyView.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError(Throwable e) {
                                emptyView.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onComplete() {
                                emptyView.setVisibility(View.VISIBLE);
                            }
                        });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvHist.setLayoutManager(layoutManager);
        loadAllData();
    }

    private void loadAllData() {
        App.instance.getDatabase().weatherDAO().getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableMaybeObserver<List<WeatherData>>() {
                    @Override
                    public void onSuccess(List<WeatherData> weatherData) {
                        histAdapter = new HistAdapter(weatherData);
                        rvHist.setAdapter(histAdapter);
                        if (weatherData.size() == 0) emptyView.setVisibility(View.VISIBLE);
                        else emptyView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        emptyView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onComplete() {
                        emptyView.setVisibility(View.VISIBLE);
                    }
                });
    }

    private void initViews(View view) {
        rvHist = view.findViewById(R.id.rv_hist);
        emptyView = view.findViewById(R.id.no_data);
    }

}


