package com.example.ray.playviewandroid.presenter.login;

import com.example.ray.playviewandroid.base.BasePresenter;
import com.example.ray.playviewandroid.bean.ArticlesBean;
import com.example.ray.playviewandroid.bean.BannerDataBean;
import com.example.ray.playviewandroid.bean.BaseResponse;
import com.example.ray.playviewandroid.model.impl.main.HomeModel;
import com.example.ray.playviewandroid.model.interfaces.IHomeModel;
import com.example.ray.playviewandroid.presenter.interfaces.IHomePresenter;
import com.example.ray.playviewandroid.util.LogUtil;
import com.example.ray.playviewandroid.view.interfaces.IHomeView;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter<PV extends IHomeView> extends BasePresenter<PV> implements IHomePresenter {
    private static final String TAG = "HomePresenter";
    private IHomeModel mHomeModel = new HomeModel();

    @Override
    public void loadBanner() {
        mHomeModel.getBannerData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<List<BannerDataBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<List<BannerDataBean>> listBaseResponse) {
                        LogUtil.i(TAG,"onNext "+ listBaseResponse.toString());
                        mViewRef.get().showBannerData(listBaseResponse.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG,"loadBanner onError "+ e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void loadArticles(int page) {
        mHomeModel.getArticles(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<ArticlesBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(BaseResponse<ArticlesBean> articlesBeanBaseResponse) {
                        mViewRef.get().showArticles(articlesBeanBaseResponse.getData().getDatas());
                    }
                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG,"loadArticles onError "+ e.getMessage());
                    }
                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void loadMoreArticles(int pageNum) {
        mHomeModel.getArticles(pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<ArticlesBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(BaseResponse<ArticlesBean> articlesBeanBaseResponse) {
                            mViewRef.get().showMoreArticles(articlesBeanBaseResponse.getData().getDatas());
                    }
                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG,"loadMoreArticles onError "+ e.getMessage());
                    }
                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void collectArticles(int id) {
        mHomeModel.collectArticles(id)
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
        mHomeModel.unCollectArticles(id)
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
