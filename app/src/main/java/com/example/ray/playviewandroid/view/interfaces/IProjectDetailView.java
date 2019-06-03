package com.example.ray.playviewandroid.view.interfaces;

import com.example.ray.playviewandroid.bean.ArticleBean;

import java.util.List;

public interface IProjectDetailView extends ICollectionView {
    void showAriticle(List<ArticleBean> articlesList);
}