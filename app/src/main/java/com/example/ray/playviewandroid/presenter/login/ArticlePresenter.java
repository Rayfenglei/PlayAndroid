package com.example.ray.playviewandroid.presenter.login;

import com.example.ray.playviewandroid.base.BasePresenter;
import com.example.ray.playviewandroid.bean.BaseResponse;
import com.example.ray.playviewandroid.model.impl.main.ArticleModel;
import com.example.ray.playviewandroid.model.interfaces.ICollection;
import com.example.ray.playviewandroid.presenter.interfaces.IArticlePresenter;
import com.example.ray.playviewandroid.util.LogUtil;
import com.example.ray.playviewandroid.view.interfaces.IArticleView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ArticlePresenter <PV extends IArticleView> extends BasePresenter<PV> implements IArticlePresenter {
    private static final String TAG = "ArticlePresenter";
    private ICollection mArticleModel = new ArticleModel();

    @Override
    public void collectArticles(int id) {
        mArticleModel.collectArticles(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        mViewRef.get().showCollectSuccess();
                        LogUtil.i(TAG,"collectArticles "+baseResponse.toString());
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
        mArticleModel.unCollectArticles(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        mViewRef.get().showUnCollectSuccess();
                        LogUtil.i(TAG,"unCollectArticles "+baseResponse.toString());
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
