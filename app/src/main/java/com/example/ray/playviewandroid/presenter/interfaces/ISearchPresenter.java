package com.example.ray.playviewandroid.presenter.interfaces;

public interface ISearchPresenter {
    void getHotKey();
    void getHotWeb();
    void doSearch(String key,int pagNum);
    int getRandomColor();
}
