package com.example.ray.playviewandroid.view.interfaces;

import com.example.ray.playviewandroid.bean.ArticleBean;

import java.util.List;

public interface ISystemDetailView extends ICollectionView {
    void showArticle(List<ArticleBean> articlesList);
    void showMoreArticle(List<ArticleBean> articlesList);

}
