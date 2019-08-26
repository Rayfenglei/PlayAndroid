package com.example.ray.playviewandroid.presenter.login;

import com.example.ray.playviewandroid.base.BasePresenter;

import com.example.ray.playviewandroid.bean.ArticlesBean;
import com.example.ray.playviewandroid.bean.BaseResponse;
import com.example.ray.playviewandroid.bean.TabBean;
import com.example.ray.playviewandroid.model.impl.main.PublicModel;
import com.example.ray.playviewandroid.model.interfaces.IPublicModel;
import com.example.ray.playviewandroid.presenter.interfaces.IPublicPresenter;
import com.example.ray.playviewandroid.util.LogUtil;
import com.example.ray.playviewandroid.view.interfaces.IPublicView;
import java.util.List;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PublicPresenter<PV extends IPublicView> extends BasePresenter<PV> implements IPublicPresenter {
    private static final String TAG = "PublicPresenter";
    private IPublicModel publicModel = new PublicModel();

    @Override
    public void loadPublicTab() {
        publicModel.getWxTabs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<List<TabBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<List<TabBean>> listBaseResponse) {
                        LogUtil.i(TAG, " " + listBaseResponse.toString());
                        mViewRef.get().showPublicTab(listBaseResponse.getData());
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
    public void loadPublicArticle(int pageNum, int id) {

    }

    @Override
    public void loadMorePublicArticle(int pageNum, int id) {

    }

    @Override
    public void collectArticles(int id) {

    }

    @Override
    public void unCollectArticles(int id) {

    }
}

