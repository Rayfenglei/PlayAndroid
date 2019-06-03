package com.example.ray.playviewandroid.util;

import android.util.Log;

public class LogUtil {

    /**
     * @param TAG
     * @param msg
     */
    public static void d(String TAG, String msg) {
        if (!SettingUtil.isReleased) {
            Log.d(TAG, msg);
        }
    }

    /**
     * @param TAG
     * @param msg
     * @param tr
     */
    public static void d(String TAG, String msg, Throwable tr) {
        if (!SettingUtil.isReleased) {
            Log.d(TAG, msg, tr);
        }
    }

    /**
     * @param TAG
     * @param msg
     */
    public static void v(String TAG, String msg) {
        if (!SettingUtil.isReleased) {
            Log.v(TAG, msg);
        }
    }

    /**
     * @param TAG
     * @param msg
     */
    public static void i(String TAG, String msg) {
        if (!SettingUtil.isReleased) {
            Log.i(TAG, msg);
        }
    }

    /**
     * @param TAG
     * @param msg
     */
    public static void e(String TAG, String msg) {
        if (!SettingUtil.isReleased) {
            Log.e(TAG, msg);
        }
    }

    /**
     * @param TAG
     * @param msg
     */
    public static void w(String TAG, String msg) {
        if (!SettingUtil.isReleased) {
            Log.w(TAG, msg);
        }
    }

}
