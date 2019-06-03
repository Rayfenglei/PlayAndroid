package com.example.ray.playviewandroid.model.interfaces;

import com.example.ray.playviewandroid.bean.BaseResponse;
import io.reactivex.Observable;

public interface ICollection {
    Observable<BaseResponse> collectArticles(int id);
    Observable<BaseResponse> unCollectArticles(int id);
}
