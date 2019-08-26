package com.example.ray.playviewandroid.model.http.method;

import android.content.Context;

import com.example.ray.playviewandroid.PlayApplication;
import com.example.ray.playviewandroid.model.http.api.NetworkApi;
import com.example.ray.playviewandroid.model.http.interceptor.AddCookiesInterceptor;
import com.example.ray.playviewandroid.model.http.interceptor.CacheInterceptor;
import com.example.ray.playviewandroid.model.http.interceptor.SaveCookiesInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkServer {
    private String base_url = "https://www.wanandroid.com/";
    private volatile static NetworkServer mInstance;
    private NetworkApi mService;
    private Context mContext;
    private NetworkServer(){
        mContext = PlayApplication.getContext();
    }
    public static NetworkServer getInstance(){
        if (mInstance == null){
            synchronized (NetworkServer.class){
                if (mInstance == null)
                {
                    mInstance = new NetworkServer();
                }
            }
        }
        return mInstance;
    }
    /*
    * synchronized 保持同步 切换太快数据不加载
    * */
    public synchronized NetworkApi getNetworkDatas(){
        if (mService == null){
            Cache cache = new Cache(new File(mContext.getCacheDir(), "HttpCache"), 1024*1024*10);
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder().cache(cache)
                                            .retryOnConnectionFailure(true)
                                            .connectTimeout(5, TimeUnit.SECONDS)
                                            .readTimeout(5,TimeUnit.SECONDS)
                                            .addInterceptor(new AddCookiesInterceptor(mContext))
                                            .addInterceptor(new SaveCookiesInterceptor(mContext))
                                            .addInterceptor(new CacheInterceptor())
                                            .build();
            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(base_url)
                                    .client(okHttpClient)
                                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
            mService = retrofit.create(NetworkApi.class);
        }
        return mService;
    }

}
