package com.example.android_begin;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class ShPref {
    public static final String Preff = "PREFF";
    public static final String IsDarkTheme = "IS_DARK_THEME";
    public static final String CURPOS = "CURPOS";


    public static boolean isDarkTheme(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(Preff, MODE_PRIVATE);
        return sharedPref.getBoolean(IsDarkTheme, true);
    }

    public static void setCurPos(Context context, int curPos) {
        SharedPreferences sharedPref = context.getSharedPreferences(Preff, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(CURPOS, curPos);
        editor.apply();
    }

    public static int getCurPos(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(Preff, MODE_PRIVATE);
        return sharedPref.getInt(CURPOS, 0);
    }
}
