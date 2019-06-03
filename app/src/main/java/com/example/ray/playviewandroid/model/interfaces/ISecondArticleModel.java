package com.example.ray.playviewandroid.model.interfaces;

import com.example.ray.playviewandroid.bean.ArticlesBean;
import com.example.ray.playviewandroid.bean.BaseResponse;
import io.reactivex.Observable;

public interface ISecondArticleModel extends ICollection{
    Observable<BaseResponse<ArticlesBean>> getSecondSystemArticles(int pageNum, int cid);
}
