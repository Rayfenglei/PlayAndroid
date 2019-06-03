package com.example.ray.playviewandroid.view.interfaces;

import com.example.ray.playviewandroid.bean.ArticleBean;
import com.example.ray.playviewandroid.bean.TabBean;

import java.util.List;

public interface IProjectView extends ICollectionView {
    void showAriticle(List<ArticleBean> articlesList);
    void showProjectTab(List<TabBean> tabBeans);
}
