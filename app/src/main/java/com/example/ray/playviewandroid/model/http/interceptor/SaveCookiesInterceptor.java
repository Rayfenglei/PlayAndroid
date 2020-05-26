package com.example.ray.playviewandroid.model.http.interceptor;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.ray.playviewandroid.util.SharedPreferencesUtils;
import java.io.IOException;
import java.util.HashSet;
import okhttp3.Interceptor;
import okhttp3.Response;
/**
 * 保存cookie
 * */
public class SaveCookiesInterceptor implements Interceptor {
    private Context mContext;
    public SaveCookiesInterceptor(Context context){
        mContext=context.getApplicationContext();
    }

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();

            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
            }
            SharedPreferencesUtils.putSet(mContext,"Cookies",cookies);

        }
        return originalResponse;
    }

    /**
     * 清除本地Cookie
     */
    public void clearCookie() {
        SharedPreferencesUtils.clear(mContext);
    }
}
