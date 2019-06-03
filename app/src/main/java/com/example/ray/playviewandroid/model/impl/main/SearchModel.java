package com.example.ray.playviewandroid.model.impl.main;

import com.example.ray.playviewandroid.bean.ArticlesBean;
import com.example.ray.playviewandroid.bean.BaseResponse;
import com.example.ray.playviewandroid.bean.HotKeyBean;
import com.example.ray.playviewandroid.bean.HotWebBean;
import com.example.ray.playviewandroid.model.http.method.NetworkServer;
import com.example.ray.playviewandroid.model.interfaces.ISearchModel;

import java.util.List;

import io.reactivex.Observable;

public class SearchModel implements ISearchModel {
    @Override
    public Observable<BaseResponse<List<HotWebBean>>> getHotWeb() {
        return NetworkServer.getInstance().getNetworkDatas().getHotWeb();
    }

    @Override
    public Observable<BaseResponse<List<HotKeyBean>>> getHotKey() {
        return NetworkServer.getInstance().getNetworkDatas().getHotKey();
    }

    @Override
    public Observable<BaseResponse<ArticlesBean>> getSearchArticles(String key, int pageNum) {
        return NetworkServer.getInstance().getNetworkDatas().getSearchArticles(key,pageNum);
    }
}
