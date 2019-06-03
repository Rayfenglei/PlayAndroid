package com.example.ray.playviewandroid;

import android.app.Application;
import android.content.Context;

public class PlayApplication extends Application {
    private static PlayApplication context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
    public static Context getContext() {
        return context;
    }
}
