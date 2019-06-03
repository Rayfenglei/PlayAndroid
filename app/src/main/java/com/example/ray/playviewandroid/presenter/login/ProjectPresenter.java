package com.example.ray.playviewandroid.presenter.login;

import com.example.ray.playviewandroid.base.BasePresenter;
import com.example.ray.playviewandroid.bean.ArticlesBean;
import com.example.ray.playviewandroid.bean.BaseResponse;
import com.example.ray.playviewandroid.bean.TabBean;
import com.example.ray.playviewandroid.model.impl.main.ProjectModel;
import com.example.ray.playviewandroid.model.interfaces.IProjectModel;
import com.example.ray.playviewandroid.presenter.interfaces.IProjectPresenter;
import com.example.ray.playviewandroid.util.LogUtil;
import com.example.ray.playviewandroid.view.interfaces.IProjectView;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProjectPresenter<PV extends IProjectView> extends BasePresenter<PV> implements IProjectPresenter {
    private static final String TAG =  "ProjectPresenter";
    private IProjectModel projectModel = new ProjectModel();

    @Override
    public void loadProjectTab() {
        projectModel.getProjectTab()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<List<TabBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<List<TabBean>> listBaseResponse) {
                        LogUtil.i(TAG, " " + listBaseResponse.toString());
                        mViewRef.get().showProjectTab(listBaseResponse.getData());
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
    public void loadArticle(int pageNum, int id){
        projectModel.getProjectArticles(pageNum,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<ArticlesBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<ArticlesBean> articlesBeanBaseResponse) {
                        LogUtil.i(TAG,"getSecondArticle onNext "+ articlesBeanBaseResponse.toString());
                        mViewRef.get().showAriticle(articlesBeanBaseResponse.getData().getDatas());
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG,"getSecondArticle onError "+ e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void collectArticles(int id) {
        projectModel.collectArticles(id)
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
        projectModel.unCollectArticles(id)
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
