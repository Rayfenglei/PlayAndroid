package com.example.ray.playviewandroid.presenter.login;

import com.example.ray.playviewandroid.base.BasePresenter;
import com.example.ray.playviewandroid.bean.BaseResponse;
import com.example.ray.playviewandroid.bean.FirstSystemBean;
import com.example.ray.playviewandroid.model.impl.main.SystemModel;
import com.example.ray.playviewandroid.model.interfaces.ISystemModel;
import com.example.ray.playviewandroid.presenter.interfaces.ISystemPresenter;
import com.example.ray.playviewandroid.util.LogUtil;
import com.example.ray.playviewandroid.view.interfaces.ISystemView;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SystemPresenter<PV extends ISystemView> extends BasePresenter<PV> implements ISystemPresenter {
    private static final String TAG = "SystemPresenter";
    private ISystemModel mSystemModel = new SystemModel();

    @Override
    public void loadSystemData() {
        mSystemModel.getFirstSystemData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<List<FirstSystemBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<List<FirstSystemBean>> listBaseResponse) {
                        LogUtil.i(TAG,"FirstSystemBean size "+ listBaseResponse.getData().size());
                        mViewRef.get().doShowData(listBaseResponse.getData());
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
}
