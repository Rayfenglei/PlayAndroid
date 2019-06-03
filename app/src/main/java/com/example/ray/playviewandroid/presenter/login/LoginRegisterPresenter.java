package com.example.ray.playviewandroid.presenter.login;

import com.example.ray.playviewandroid.base.BasePresenter;
import com.example.ray.playviewandroid.bean.BaseResponse;
import com.example.ray.playviewandroid.bean.LoginBean;
import com.example.ray.playviewandroid.model.impl.login.LoginRegisterModel;
import com.example.ray.playviewandroid.model.interfaces.ILoginRegisterModel;

import com.example.ray.playviewandroid.presenter.interfaces.ILoginPresenter;
import com.example.ray.playviewandroid.util.LogUtil;
import com.example.ray.playviewandroid.util.SharedPreferencesUtils;
import com.example.ray.playviewandroid.view.interfaces.ILoginRegisterView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/*
* 登录 注册
* */
public class LoginRegisterPresenter<PV extends ILoginRegisterView> extends BasePresenter<PV> implements ILoginPresenter {
    private static final String TAG = "LoginRegisterPresenter";
    /*
    * model层 引用
    * */
    private ILoginRegisterModel mLoginModel = new LoginRegisterModel();

    //登陆
    @Override
    public void doLogin(final String user, String password) {
        mLoginModel.Logining(user,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<LoginBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(BaseResponse<LoginBean> loginBaseResponse) {
                        if (loginBaseResponse.getErrorCode() ==0 ){
                            mViewRef.get().onSuccess();
                        }else {
                            mViewRef.get().onFailed(null);
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG," onError "+ e.getMessage());
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }

    //注册
    @Override
    public void doLogin(String user,String password,String repassword){
        mLoginModel.Register(user,password,repassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<LoginBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<LoginBean> loginBeanBaseResponse) {
                        if (loginBeanBaseResponse.getErrorCode() ==0 ){
                            mViewRef.get().onSuccess();
                        }else {
                            mViewRef.get().onFailed(loginBeanBaseResponse.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG," onError "+ e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
