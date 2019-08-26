package com.example.ray.playviewandroid.presenter.interfaces;

public interface IPublicPresenter extends ICollectionPresenter {
    void loadPublicTab();
    void loadPublicArticle(int pageNum, int id);
    void loadMorePublicArticle(int pageNum, int id);
}
