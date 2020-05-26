package com.example.ray.playviewandroid.presenter.login;

import android.util.Log;

import com.example.ray.playviewandroid.base.BasePresenter;
import com.example.ray.playviewandroid.bean.ArticlesBean;
import com.example.ray.playviewandroid.bean.BaseResponse;
import com.example.ray.playviewandroid.bean.HotKeyBean;
import com.example.ray.playviewandroid.bean.HotWebBean;
import com.example.ray.playviewandroid.cache.CacheManager;
import com.example.ray.playviewandroid.cache.DiskCache;
import com.example.ray.playviewandroid.cache.ICache;
import com.example.ray.playviewandroid.cache.MemoryCache;
import com.example.ray.playviewandroid.constants.PlayViewConstants;
import com.example.ray.playviewandroid.model.impl.main.SearchModel;
import com.example.ray.playviewandroid.model.interfaces.ISearchModel;
import com.example.ray.playviewandroid.presenter.interfaces.ISearchPresenter;
import com.example.ray.playviewandroid.util.LogUtil;
import com.example.ray.playviewandroid.view.interfaces.ISearchView;

import java.util.List;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SearchPresenter<PV extends ISearchView> extends BasePresenter<PV> implements ISearchPresenter {
    private static final String TAG = "SearchPresenter";
    private ISearchModel searchModel = new SearchModel();
    private String key = "http://www.wanandroid.com//hotkey/json";
    private ICache mDiskCache = new DiskCache();

    @Override
    public void getHotKey() {
        Observable<BaseResponse<List<HotKeyBean>>> cache = Observable.create(new ObservableOnSubscribe<BaseResponse<List<HotKeyBean>>>() {
            @Override
            public void subscribe(ObservableEmitter<BaseResponse<List<HotKeyBean>>> emitter) throws Exception {
                BaseResponse<List<HotKeyBean>> data = CacheManager.getInstance().getFoodListData();
                // 在操作符 concat 中，只有调用 onComplete 之后才会执行下一个 Observable
                if (data != null){ // 如果缓存数据不为空，则直接读取缓存数据，而不读取网络数据
                    Log.e(TAG, "\nsubscribe: 读取缓存数据:" );
                    emitter.onNext(data);
                }else {
                    Log.e(TAG, "\nsubscribe: 读取网络数据:" );
                    emitter.onComplete();
                }


            }
        });
        Observable<BaseResponse<List<HotKeyBean>>> network = searchModel.getHotKey();

        final Disposable subscribe = Observable.concat(MemoryCache.getInstance().<BaseResponse<List<HotKeyBean>>>get(TAG)
                ,mDiskCache.<BaseResponse<List<HotKeyBean>>>get(TAG)
                ,network)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse<List<HotKeyBean>>>() {
                    @Override
                    public void accept(BaseResponse<List<HotKeyBean>> listBaseResponse) throws Exception {
                        Log.e(TAG, "accept: 读取数据成功:" + listBaseResponse.toString());
                        mViewRef.get().showHotKey(listBaseResponse.getData());
                        mDiskCache.put(TAG,listBaseResponse);
                        MemoryCache.getInstance().put(TAG,listBaseResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "subscribe 失败:" + Thread.currentThread().getName());
                        Log.e(TAG, "accept: 读取数据失败：" + throwable.getMessage());
                    }
                });

//        searchModel.getHotKey()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<BaseResponse<List<HotKeyBean>>>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(BaseResponse<List<HotKeyBean>> listBaseResponse) {
//                        LogUtil.i(TAG,"gethotkey is success"+listBaseResponse.toString());
//                        mViewRef.get().showHotKey(listBaseResponse.getData());
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        LogUtil.i(TAG,"gethotkey is onError");
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
    }

    @Override
    public void getHotWeb() {
        searchModel.getHotWeb()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<List<HotWebBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<List<HotWebBean>> listBaseResponse) {
                        LogUtil.i(TAG,"getHotWeb is success"+listBaseResponse.toString());
                        mViewRef.get().showHotWeb(listBaseResponse.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG,"getHotWeb is onError");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void doSearch(String key,int pagNum) {
        searchModel.getSearchArticles(key,pagNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<ArticlesBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<ArticlesBean> articlesBeanBaseResponse) {
                        LogUtil.i(TAG,"gethotkey is success"+articlesBeanBaseResponse.toString());
                        mViewRef.get().showSearcher(articlesBeanBaseResponse.getData().getDatas());
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG,"gethotkey is onError");
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public int getRandomColor() {
        int randomNum = new Random().nextInt();
        int position = randomNum % PlayViewConstants.TAB_COLORS.length;
        if (position < 0) {
            position = -position;
        }
        return PlayViewConstants.TAB_COLORS[position];
    }
}
