package com.example.android_begin.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.android_begin.R;
import com.example.android_begin.ShPref;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class SettingFragment extends Fragment {
    public static final String Preff = "PREFF";
    private static final String IsDarkTheme = "IS_DARK_THEME";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {

        Spinner theme = view.findViewById(R.id.sp_theme);
        if (ShPref.isDarkTheme(Objects.requireNonNull(getContext()))) {
            theme.setSelection(0);
        } else {
            theme.setSelection(1);
        }
        theme.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setDarkTheme(position == 0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    protected void setDarkTheme(boolean isDarkTheme) {
        SharedPreferences sharedPref = Objects.requireNonNull(getActivity()).getSharedPreferences(Preff, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(IsDarkTheme, isDarkTheme);
        editor.apply();
    }

}
