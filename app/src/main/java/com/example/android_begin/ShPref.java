package com.example.android_begin;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class ShPref {
    public static final String Preff = "PREFF";
    public static final String IsDarkTheme = "IS_DARK_THEME";
    public static final String CURPOS = "CUR_CITY";


    public static boolean isDarkTheme(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(Preff, MODE_PRIVATE);
        return sharedPref.getBoolean(IsDarkTheme, true);
    }

    public static void setCurCity(Context context, String city) {
        SharedPreferences sharedPref = context.getSharedPreferences(Preff, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(CURPOS, city);
        editor.apply();
    }

    public static String getCurCity(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(Preff, MODE_PRIVATE);
        return sharedPref.getString(CURPOS, "Moscow");
    }
}
