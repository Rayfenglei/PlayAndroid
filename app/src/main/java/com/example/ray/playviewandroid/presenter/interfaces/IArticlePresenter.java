package com.example.ray.playviewandroid.presenter.interfaces;

public interface IArticlePresenter {
    void collectArticles(int id); //收藏首页文章
    void unCollectArticles(int id);//取消收藏
}
