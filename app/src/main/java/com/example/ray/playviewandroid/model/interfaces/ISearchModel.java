package com.example.ray.playviewandroid.model.interfaces;

import com.example.ray.playviewandroid.bean.ArticlesBean;
import com.example.ray.playviewandroid.bean.BaseResponse;
import com.example.ray.playviewandroid.bean.HotKeyBean;
import com.example.ray.playviewandroid.bean.HotWebBean;

import java.util.List;

import io.reactivex.Observable;

public interface ISearchModel {
    Observable<BaseResponse<List<HotWebBean>>> getHotWeb();
    Observable<BaseResponse<List<HotKeyBean>>> getHotKey();
    Observable<BaseResponse<ArticlesBean>> getSearchArticles(String key, int pageNum);
}
