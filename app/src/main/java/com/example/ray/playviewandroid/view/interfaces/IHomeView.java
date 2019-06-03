package com.example.ray.playviewandroid.view.interfaces;

import com.example.ray.playviewandroid.bean.ArticleBean;
import com.example.ray.playviewandroid.bean.BannerDataBean;

import java.util.List;

public interface IHomeView extends ICollectionView {
    void showBannerData(List<BannerDataBean> bannerDataList); //展示轮播图
    void showArticles(List<ArticleBean> articlesList); //展示首页文章
    void showMoreArticles(List<ArticleBean> articleList);  //加载更多文章
    void autoRefresh();//强制刷新
    void backTop();
}
