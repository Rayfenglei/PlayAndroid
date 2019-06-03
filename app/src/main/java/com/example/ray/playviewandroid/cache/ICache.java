package com.example.ray.playviewandroid.cache;

import com.example.ray.playviewandroid.bean.BaseResponse;
import io.reactivex.Observable;

public interface ICache {
    <T extends BaseResponse> Observable<T> get(String key);
    <T extends BaseResponse> void put(String key, T t);
}
