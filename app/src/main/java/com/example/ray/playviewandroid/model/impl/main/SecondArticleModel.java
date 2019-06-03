package com.example.ray.playviewandroid.model.impl.main;

import com.example.ray.playviewandroid.bean.ArticlesBean;
import com.example.ray.playviewandroid.bean.BaseResponse;
import com.example.ray.playviewandroid.model.http.method.NetworkServer;
import com.example.ray.playviewandroid.model.interfaces.ISecondArticleModel;

import io.reactivex.Observable;

public class SecondArticleModel implements ISecondArticleModel {
    @Override
    public Observable<BaseResponse<ArticlesBean>> getSecondSystemArticles(int pageNum, int cid) {
        return NetworkServer.getInstance().getNetworkDatas().getSecondSystemArticles(pageNum,cid);
    }

    @Override
    public Observable<BaseResponse> collectArticles(int id) {
        return NetworkServer.getInstance().getNetworkDatas().collectArticles(id);
    }

    @Override
    public Observable<BaseResponse> unCollectArticles(int id) {
        return NetworkServer.getInstance().getNetworkDatas().unCollectArticles(id);
    }
}
