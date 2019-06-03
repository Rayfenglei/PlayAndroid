package com.example.ray.playviewandroid.cache;

import com.example.ray.playviewandroid.bean.BaseResponse;
import com.example.ray.playviewandroid.bean.HotKeyBean;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class CacheManager {
    private volatile static CacheManager mInstance ;
    private BaseResponse<List<HotKeyBean>> footListJson;
    private CacheManager() {
    }

    public static CacheManager getInstance() {
        if (mInstance == null){
            synchronized (CacheManager.class){
                if (mInstance == null){
                    mInstance = new CacheManager();
                }
            }
        }
        return mInstance;
    }

    public BaseResponse<List<HotKeyBean>> getFoodListData(){
        return this.footListJson;
    }

    public void setFoodListData(BaseResponse<List<HotKeyBean>> data){
        this.footListJson = data;
    }
}
