package com.example.ray.playviewandroid.cache;


import android.text.TextUtils;
import android.util.Log;
import android.util.LruCache;
import com.example.ray.playviewandroid.bean.BaseResponse;
import com.example.ray.playviewandroid.util.LogUtil;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


public class MemoryCache implements ICache {
    private static final String TAG = "CacheData";
    private LruCache<String, String> mCache;
    private static MemoryCache mInstance;

    private MemoryCache() {
        //获取系统分配给每个应用程序的最大内存，每个应用系统分配32M
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int mCacheSize = maxMemory / 8;
        //给LruCache分配1/8 4M
        mCache = new LruCache<String, String>(mCacheSize) {
            @Override
            protected int sizeOf(String key, String value) {
                try {
                    return value.getBytes("UTF-8").length;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return value.getBytes().length;
                }
            }
        };
    }

    public static MemoryCache getInstance(){
        if (mInstance == null){
            synchronized (MemoryCache.class){
                if (mInstance == null){
                    mInstance = new MemoryCache();
                }
            }
        }
        return mInstance;
    }

    @Override
    public <T extends BaseResponse> Observable<T> get(final String key) {
        return  Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                String result = mCache.get(key);
                LogUtil.i(TAG, "load from memory key : "+key+" data : "+ result);
                if (TextUtils.isEmpty(result)) {
                    emitter.onComplete();
                } else {
                    T t = (T) new Gson().fromJson(result,BaseResponse.class);
                    emitter.onNext(t);
                }

            }
        });
    }

    @Override
    public <T extends BaseResponse> void put(String key, T t) {
        if (null != t) {
            Log.v(TAG, "save to memory: " + key+" t : "+t.toString());
            mCache.put(key, t.toString());
        }
    }
}
