package com.example.ray.playviewandroid.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class SharedPreferencesUtils {

    private static final String SharedPreferencesName = "playview_android";

    public static SharedPreferences getInstance(Context ctx){
        SharedPreferences sp = ctx.getSharedPreferences(SharedPreferencesName,
                Context.MODE_PRIVATE);
        return sp;
    }

    public static void clear(Context ctx){
        SharedPreferences sp = ctx.getSharedPreferences(SharedPreferencesName,
                Context.MODE_PRIVATE);
        sp.edit().clear().apply();
    }


    public static void putBoolean(Context ctx, String key, boolean value) {
        SharedPreferences sp = ctx.getSharedPreferences(SharedPreferencesName,
                Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(Context ctx, String key, boolean defValue) {
        SharedPreferences sp = ctx.getSharedPreferences(SharedPreferencesName,
                Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }

    public static void putString(Context ctx, String key, String value) {
        SharedPreferences sp = ctx.getSharedPreferences(SharedPreferencesName,
                Context.MODE_PRIVATE);
        sp.edit().putString(key, value).apply();
    }

    public static String getString(Context ctx, String key, String defValue) {
        SharedPreferences sp = ctx.getSharedPreferences(SharedPreferencesName,
                Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }

    public static void putInt(Context ctx, String key, int value) {
        SharedPreferences sp = ctx.getSharedPreferences(SharedPreferencesName,
                Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).apply();
    }

    public static int getInt(Context ctx, String key, int defValue) {
        SharedPreferences sp = ctx.getSharedPreferences(SharedPreferencesName,
                Context.MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }

    public static void putLong(Context ctx, String key, long value) {
        SharedPreferences sp = ctx.getSharedPreferences(SharedPreferencesName,
                Context.MODE_PRIVATE);
        sp.edit().putLong(key, value).apply();
    }

    public static Long getLong(Context ctx, String key, long defValue) {
        SharedPreferences sp = ctx.getSharedPreferences(SharedPreferencesName,
                Context.MODE_PRIVATE);
        return sp.getLong(key, defValue);
    }

    public static void remove(Context ctx, String key) {
        SharedPreferences sp = ctx.getSharedPreferences(SharedPreferencesName,
                Context.MODE_PRIVATE);
        sp.edit().remove(key).apply();
    }
    public static void putSet(Context ctx, String key, Set<String> value){
        SharedPreferences sp = ctx.getSharedPreferences(SharedPreferencesName,
                Context.MODE_PRIVATE);
        sp.edit().putStringSet(key, value).apply();
    }
    public static Set<String> getSet(Context ctx, String key, Set<String> defValue){
        SharedPreferences sp = ctx.getSharedPreferences(SharedPreferencesName,
                Context.MODE_PRIVATE);
        return sp.getStringSet(key,defValue);
    }
}