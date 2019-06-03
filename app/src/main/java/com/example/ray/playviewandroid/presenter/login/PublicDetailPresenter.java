package com.example.ray.playviewandroid.presenter.login;

import com.example.ray.playviewandroid.base.BasePresenter;
import com.example.ray.playviewandroid.bean.ArticlesBean;
import com.example.ray.playviewandroid.bean.BaseResponse;
import com.example.ray.playviewandroid.bean.TabBean;
import com.example.ray.playviewandroid.model.impl.main.PublicModel;
import com.example.ray.playviewandroid.model.interfaces.IPublicModel;
import com.example.ray.playviewandroid.presenter.interfaces.IPublicPresenter;
import com.example.ray.playviewandroid.util.LogUtil;
import com.example.ray.playviewandroid.view.interfaces.IPublicDetailView;
import com.example.ray.playviewandroid.view.interfaces.IPublicView;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PublicDetailPresenter<PV extends IPublicDetailView> extends BasePresenter<PV> implements IPublicPresenter {
    private static final String TAG = "PublicDetailPresenter";
    private IPublicModel publicModel = new PublicModel();

    @Override
    public void loadPublicTab() {

    }

    @Override
    public void loadPublicArticle(int pageNum, int id) {
        publicModel.getWxArticles(pageNum,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<ArticlesBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<ArticlesBean> articlesBeanBaseResponse) {
                        LogUtil.i(TAG, " " + articlesBeanBaseResponse.toString());
                        mViewRef.get().showAriticle(articlesBeanBaseResponse.getData().getDatas());
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "collectArticles onError " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void collectArticles(int id) {
        publicModel.collectArticles(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        mViewRef.get().showCollectSuccess();
                        LogUtil.i(TAG," "+baseResponse.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG,"collectArticles onError "+ e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void unCollectArticles(int id) {
        publicModel.unCollectArticles(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        mViewRef.get().showUnCollectSuccess();
                        LogUtil.i(TAG," "+baseResponse.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG,"unCollectArticles onError "+ e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}