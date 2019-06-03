package com.example.ray.playviewandroid.model.impl.main;

import com.example.ray.playviewandroid.bean.ArticlesBean;
import com.example.ray.playviewandroid.bean.BannerDataBean;
import com.example.ray.playviewandroid.bean.BaseResponse;
import com.example.ray.playviewandroid.bean.CollectionsBean;
import com.example.ray.playviewandroid.model.http.method.NetworkServer;
import com.example.ray.playviewandroid.model.interfaces.IHomeModel;

import java.util.List;

import io.reactivex.Observable;

public class HomeModel implements IHomeModel {
    @Override
    public Observable<BaseResponse<List<BannerDataBean>>> getBannerData() {
        return NetworkServer.getInstance().getNetworkDatas().getBannerData();
    }

    @Override
    public Observable<BaseResponse<ArticlesBean>> getArticles(int pageNum) {
        return NetworkServer.getInstance().getNetworkDatas().getArticles(pageNum);
    }

    @Override
    public Observable<BaseResponse<CollectionsBean>> getCollectionsData(int pageNum) {
        return NetworkServer.getInstance().getNetworkDatas().getCollectionsData(pageNum);
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
