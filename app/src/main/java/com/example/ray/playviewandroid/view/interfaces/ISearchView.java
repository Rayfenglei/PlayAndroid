package com.example.ray.playviewandroid.view.interfaces;

import android.view.View;

import com.example.ray.playviewandroid.bean.ArticleBean;
import com.example.ray.playviewandroid.bean.HotKeyBean;
import com.example.ray.playviewandroid.bean.HotWebBean;

import java.util.List;

public interface ISearchView {
    void showHotKey(List<HotKeyBean> keyList);
    void showHotWeb(List<HotWebBean> webList);
    void showHistory(List<ArticleBean> articlesList);
    void showSearcher(List<ArticleBean> articlesList);
    void onItemClick(View view);
}
