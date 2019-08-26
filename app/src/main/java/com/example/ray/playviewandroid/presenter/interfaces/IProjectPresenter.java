package com.example.ray.playviewandroid.presenter.interfaces;

public interface IProjectPresenter extends ICollectionPresenter {
    void loadProjectTab();
    void loadArticle(int pageNum, int id);
    void loadMoreArticle(int pageNum, int id);
}
