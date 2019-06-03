package com.example.ray.playviewandroid.model.interfaces;

import com.example.ray.playviewandroid.bean.ArticlesBean;
import com.example.ray.playviewandroid.bean.BannerDataBean;
import com.example.ray.playviewandroid.bean.BaseResponse;
import com.example.ray.playviewandroid.bean.CollectionsBean;
import java.util.List;
import io.reactivex.Observable;

public interface IHomeModel extends ICollection{
    Observable<BaseResponse<List<BannerDataBean>>> getBannerData();
    Observable<BaseResponse<ArticlesBean>> getArticles(int pageNum);
    Observable<BaseResponse<CollectionsBean>> getCollectionsData( int pageNum);
}
