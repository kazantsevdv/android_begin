package com.example.android_begin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_begin.DataRepo;
import com.example.android_begin.R;
import com.example.android_begin.adapter.HistAdapter;

public class HistFragment extends Fragment {

    private RecyclerView rvHist;
    private HistAdapter histAdapter;

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
        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                histAdapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                histAdapter.getFilter().filter(newText);
                return true;
            }
        });


    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvHist.setLayoutManager(layoutManager);
        histAdapter = new HistAdapter(DataRepo.getWeatherHists());

        rvHist.setAdapter(histAdapter);
    }

    private void initViews(View view) {
        rvHist = view.findViewById(R.id.rv_hist);
    }

}


