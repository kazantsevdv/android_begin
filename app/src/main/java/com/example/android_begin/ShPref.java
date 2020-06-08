package com.example.android_begin;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class ShPref {
    public static final String Preff = "PREFF";
    public static final String IsDarkTheme = "IS_DARK_THEME";

    public static boolean isDarkTheme(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(Preff, MODE_PRIVATE);
        return sharedPref.getBoolean(IsDarkTheme, true);
    }
}
