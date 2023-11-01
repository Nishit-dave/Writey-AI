package com.app.writy.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class ID {

    public static void setBoolean(Context con, String prefname, String name, Boolean value) {
        SharedPreferences.Editor editor = con.getSharedPreferences(prefname, Context.MODE_PRIVATE).edit();
        editor.putBoolean(name, value);
        editor.apply();
    }

    public static Boolean getBoolean(Context con, String prefname, String name, Boolean defaultvalue) {
        return con.getSharedPreferences(prefname, 0).getBoolean(name, defaultvalue);
    }

    public static void setInt(Context con, String prefname, String name, int value) {
        SharedPreferences.Editor editor = con.getSharedPreferences(prefname, Context.MODE_PRIVATE).edit();
        editor.putInt(name, value);
        editor.apply();
    }

    public static int getInt(Context con, String prefname, String name, int defaultvalue) {
        return con.getSharedPreferences(prefname, 0).getInt(name, defaultvalue);
    }

    public static void setString(Context con, String prefname, String name, String value) {
        SharedPreferences.Editor editor = con.getSharedPreferences(prefname, Context.MODE_PRIVATE).edit();
        editor.putString(name, value);
        editor.apply();
    }

    public static String getString(Context con, String prefname, String name, String defaultvalue) {
        return con.getSharedPreferences(prefname, 0).getString(name, defaultvalue);
    }
}
