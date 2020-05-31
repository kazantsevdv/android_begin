package com.example.android_begin.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android_begin.Publisher;
import com.example.android_begin.PublisherGetter;
import com.example.android_begin.R;

public class MainActivity extends AppCompatActivity implements PublisherGetter {
    private Publisher publisher = new Publisher();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public Publisher getPublisher() {
        return publisher;
    }
}
