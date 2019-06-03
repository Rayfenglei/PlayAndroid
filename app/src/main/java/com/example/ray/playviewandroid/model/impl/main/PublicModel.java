package com.example.ray.playviewandroid.model.impl.main;

import com.example.ray.playviewandroid.bean.ArticlesBean;
import com.example.ray.playviewandroid.bean.BaseResponse;
import com.example.ray.playviewandroid.bean.TabBean;
import com.example.ray.playviewandroid.model.http.method.NetworkServer;
import com.example.ray.playviewandroid.model.interfaces.IPublicModel;

import java.util.List;

import io.reactivex.Observable;

public class PublicModel implements IPublicModel {
    @Override
    public Observable<BaseResponse<List<TabBean>>> getWxTabs() {
        return NetworkServer.getInstance().getNetworkDatas().getWxTabs();
    }

    @Override
    public Observable<BaseResponse<ArticlesBean>> getWxArticles(int pageNum, int id) {
        return NetworkServer.getInstance().getNetworkDatas().getWxArticles(pageNum,id);
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
