package com.example.ray.playviewandroid.presenter.login;

import com.example.ray.playviewandroid.base.BasePresenter;
import com.example.ray.playviewandroid.bean.ArticlesBean;
import com.example.ray.playviewandroid.bean.BaseResponse;
import com.example.ray.playviewandroid.model.impl.main.SecondArticleModel;
import com.example.ray.playviewandroid.model.interfaces.ISecondArticleModel;
import com.example.ray.playviewandroid.presenter.interfaces.ISystemDetailPresenter;
import com.example.ray.playviewandroid.util.LogUtil;
import com.example.ray.playviewandroid.view.interfaces.ISystemDetailView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SystemDetailPresenter<PV extends ISystemDetailView> extends BasePresenter<PV> implements ISystemDetailPresenter {
    private static final String TAG = "SystemDetailPresenter";
    private ISecondArticleModel secondArticleModel = new SecondArticleModel();
    @Override
    public void getSecondArticle(int num,int cid) {
        secondArticleModel.getSecondSystemArticles(num,cid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<ArticlesBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<ArticlesBean> articlesBeanBaseResponse) {
                        LogUtil.i(TAG,"getSecondArticle onNext "+ articlesBeanBaseResponse.toString());
                        mViewRef.get().showArticle(articlesBeanBaseResponse.getData().getDatas());
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
    public void getMoreSecondArticle(int num, int cid) {
        secondArticleModel.getSecondSystemArticles(num,cid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<ArticlesBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<ArticlesBean> articlesBeanBaseResponse) {
                        LogUtil.i(TAG,"getMoreSecondArticle onNext "+ articlesBeanBaseResponse.toString());
                        mViewRef.get().showMoreArticle(articlesBeanBaseResponse.getData().getDatas());
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG,"getMoreSecondArticle onError "+ e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void collectArticles(int id) {
        secondArticleModel.collectArticles(id)
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
        secondArticleModel.unCollectArticles(id)
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
