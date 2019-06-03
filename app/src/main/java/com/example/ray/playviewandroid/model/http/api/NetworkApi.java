package com.example.ray.playviewandroid.model.http.api;


import com.example.ray.playviewandroid.bean.ArticlesBean;
import com.example.ray.playviewandroid.bean.BannerDataBean;
import com.example.ray.playviewandroid.bean.BaseResponse;
import com.example.ray.playviewandroid.bean.CollectionsBean;
import com.example.ray.playviewandroid.bean.FirstSystemBean;
import com.example.ray.playviewandroid.bean.HotKeyBean;
import com.example.ray.playviewandroid.bean.HotWebBean;
import com.example.ray.playviewandroid.bean.LoginBean;
import com.example.ray.playviewandroid.bean.TabBean;

import java.util.List;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NetworkApi {
    /**
     * 登陆
     * http://www.wanandroid.com/user/login
     */
    @POST("user/login")
    @FormUrlEncoded
    Observable<BaseResponse<LoginBean>> login(
            @Field("username") String userName,
            @Field("password") String password
    );


    /**
     * 注册
     * http://www.wanandroid.com/user/register
     */
    @POST("user/register")
    @FormUrlEncoded
    Observable<BaseResponse<LoginBean>> register(@Field("username") String username,
                                                 @Field("password") String password,
                                                 @Field("repassword") String rePassword//确认密码
    );

    /**
     * 退出登陆
     * http://www.wanandroid.com/user/logout/json
     */
    @GET("user/logout/json")
    Observable<BaseResponse<LoginBean>> logout();

    /**
     * 收藏站内文章
     * http://www.wanandroid.com/lg/collect/1165/json
     */
    @POST("lg/collect/{id}/json")
    Observable<BaseResponse> collectArticles(@Path("id") int id);
    /**
     * 文章列表取消收藏
     * http://www.wanandroid.com/lg/uncollect_originId/2333/json
     */
    @POST("lg/uncollect_originId/{id}/json")
    Observable<BaseResponse> unCollectArticles(@Path("id") int id);
    /**
     * 获取收藏列表
     * http://www.wanandroid.com/lg/collect/list/{pageNum}/json
     */
    @GET("lg/collect/list/{pageNum}/json")
    Observable<BaseResponse<CollectionsBean>> getCollectionsData(@Path("pageNum") int pageNum);
    /**
     * 收藏列表下取消收藏
     * http://www.wanandroid.com/lg/uncollect/2805/json
     */
    @POST("lg/uncollect/{id}/json")
    @FormUrlEncoded
    Observable<BaseResponse> unCollection(@Path("id") int id,//收藏在我的收藏列表的id
                                          @Field("originId") int originId);//收藏在原始文章列表的id);

    /**
     * 首页banner，即轮播图
     * http://www.wanandroid.com/banner/json
     */
    @GET("banner/json")
    Observable<BaseResponse<List<BannerDataBean>>> getBannerData();


    /**
     * 首页文章列表
     * http://www.wanandroid.com/article/list/1/json
     */
    @GET("article/list/{pageNum}/json")
    Observable<BaseResponse<ArticlesBean>> getArticles(@Path("pageNum") int pageNum);


    /**
     * 获得大概项目列表
     * http://www.wanandroid.com/project/tree/json
     */
    @GET("project/tree/json")
    Observable<BaseResponse<List<TabBean>>> getProjectTab();

    /**
     * 获得详细项目列表
     * http://www.wanandroid.com/project/list/1/json?cid=294
     */
    @GET("project/list/{pageNum}/json")
    Observable<BaseResponse<ArticlesBean>> getProjectArticles(@Path("pageNum") int pageNum,//页数
                                                              @Query("cid") int id//具体项目列表的id
    );
    /**
     * 获取搜索热词
     *  https://www.wanandroid.com/friend/json
     */
    @GET("friend/json")
    Observable<BaseResponse<List<HotWebBean>>> getHotWeb();

    /**
     * 获取搜索热词
     * http://www.wanandroid.com//hotkey/json
     */
    @GET("hotkey/json")
    Observable<BaseResponse<List<HotKeyBean>>> getHotKey();
    /**
     * 搜索
     * http://www.wanandroid.com/article/query/0/json
     */
    @POST("article/query/{pageNum}/json")
    @FormUrlEncoded
    Observable<BaseResponse<ArticlesBean>> getSearchArticles(@Field("k") String key,//关键字
                                                             @Path("pageNum") int pageNum//页数
    );
    /**
     * 一级知识体系的目录
     * http://www.wanandroid.com/tree/json
     */
    @GET("tree/json")
    Observable<BaseResponse<List<FirstSystemBean>>> getFirstSystemData();

    /**
     * 二级知识体系文章列表
     * http://www.wanandroid.com/article/list/0/json?cid=60
     */
    @GET("article/list/{pageNum}/json")
    Observable<BaseResponse<ArticlesBean>> getSecondSystemArticles(@Path("pageNum") int pageNum, @Query("cid") int id);
    /**
     * 获得公众号tab
     * http://wanandroid.com/wxarticle/chapters/json
     */
    @GET("wxarticle/chapters/json")
    Observable<BaseResponse<List<TabBean>>> getWxTabs();

    /**
     * 获得某个公共号的文章列表
     * http://wanandroid.com/wxarticle/list/408/1/json
     * pageNum默认从1开始
     */
    @GET("wxarticle/list/{id}/{pageNum}/json")
    Observable<BaseResponse<ArticlesBean>> getWxArticles(
            @Path("pageNum") int pageNum,//某个公众号的页码
            @Path("id") int id //某个公众号id
    );
}
