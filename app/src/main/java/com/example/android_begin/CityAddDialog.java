package com.example.android_begin;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class CityAddDialog extends BottomSheetDialogFragment {
    private AddButtonListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_city, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        final TextInputEditText tiNewCity = view.findViewById(R.id.ti_new_city);
        final MaterialButton btAddCity = view.findViewById(R.id.bt_add);
        tiNewCity.requestFocus();
        btAddCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newCity = Objects.requireNonNull(tiNewCity.getText()).toString();
                if (newCity.equals("")) {
                    btAddCity.setError("no city");
                } else {
                    listener.onAddButtonClicked(newCity);
                    dismiss();
                }
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (AddButtonListener) context;
    }

    public interface AddButtonListener {
        void onAddButtonClicked(String text);
    }

}
