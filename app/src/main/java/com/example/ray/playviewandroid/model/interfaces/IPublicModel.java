package com.example.ray.playviewandroid.model.interfaces;

import com.example.ray.playviewandroid.bean.ArticlesBean;
import com.example.ray.playviewandroid.bean.BaseResponse;
import com.example.ray.playviewandroid.bean.TabBean;

import java.util.List;

import io.reactivex.Observable;

public interface IPublicModel extends ICollection {
    Observable<BaseResponse<List<TabBean>>> getWxTabs();
    Observable<BaseResponse<ArticlesBean>> getWxArticles(int pageNum, int id);
}
