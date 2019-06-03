package com.example.ray.playviewandroid.model.impl.main;

import com.example.ray.playviewandroid.bean.BaseResponse;
import com.example.ray.playviewandroid.model.http.method.NetworkServer;
import com.example.ray.playviewandroid.model.interfaces.ICollection;

import io.reactivex.Observable;

public class ArticleModel implements ICollection {
    @Override
    public Observable<BaseResponse> collectArticles(int id) {
        return NetworkServer.getInstance().getNetworkDatas().collectArticles(id);
    }

    @Override
    public Observable<BaseResponse> unCollectArticles(int id) {
        return NetworkServer.getInstance().getNetworkDatas().unCollectArticles(id);
    }
}
