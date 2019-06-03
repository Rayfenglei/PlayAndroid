package com.example.ray.playviewandroid.presenter.interfaces;

public interface IHomePresenter extends ICollectionPresenter{
    void loadBanner();//加载首页banner数据
    void loadArticles(int page);//加载首页文章数据
    void loadMoreArticles(int pageNum);//加载更多首页文章数据

}
