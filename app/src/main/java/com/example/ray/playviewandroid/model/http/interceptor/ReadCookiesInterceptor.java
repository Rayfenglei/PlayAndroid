package com.example.ray.playviewandroid.model.http.interceptor;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.example.ray.playviewandroid.util.SharedPreferencesUtils;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
/*
* 读取cookie
* */
public class ReadCookiesInterceptor implements Interceptor {

    private Context mContext;

    public ReadCookiesInterceptor(Context context) {
        mContext = context;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

        HashSet<String> preferences = (HashSet)SharedPreferencesUtils.getSet(mContext,"Cookies", new HashSet<String>());
        for (String cookie : preferences) {
            builder.addHeader("Cookie", cookie);
        }
        return chain.proceed(builder.build());
    }

}
