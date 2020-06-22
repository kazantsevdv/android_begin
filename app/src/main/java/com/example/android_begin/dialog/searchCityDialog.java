package com.example.android_begin.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.android_begin.R;
import com.example.android_begin.activity.WeatherActivityDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class searchCityDialog extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_search_city, container);
        initView(view);
        return view;
    }

    private void initView(View view) {
        final TextInputEditText tiCity = view.findViewById(R.id.ti_city);
        MaterialButton btSerch = view.findViewById(R.id.bt_search);
        btSerch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strCity = Objects.requireNonNull(tiCity.getText()).toString();
                if (!strCity.equals("")) {
                    dismiss();
                    ((WeatherActivityDialog) requireActivity()).onDialogResult(strCity);
                } else {
                    tiCity.setError("no city");
                }

            }
        });
    }
}
