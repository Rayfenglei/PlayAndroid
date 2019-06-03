package com.example.ray.playviewandroid.model.impl.main;

import com.example.ray.playviewandroid.bean.ArticlesBean;
import com.example.ray.playviewandroid.bean.BaseResponse;
import com.example.ray.playviewandroid.bean.TabBean;
import com.example.ray.playviewandroid.model.http.method.NetworkServer;
import com.example.ray.playviewandroid.model.interfaces.IProjectModel;

import java.util.List;

import io.reactivex.Observable;

public class ProjectModel implements IProjectModel {
    @Override
    public Observable<BaseResponse<List<TabBean>>> getProjectTab() {
        return NetworkServer.getInstance().getNetworkDatas().getProjectTab();
    }

    @Override
    public Observable<BaseResponse<ArticlesBean>> getProjectArticles(int pageNum, int id) {
        return NetworkServer.getInstance().getNetworkDatas().getProjectArticles(pageNum,id);
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
