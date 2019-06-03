package com.example.ray.playviewandroid.model.impl.login;


import com.example.ray.playviewandroid.bean.BaseResponse;
import com.example.ray.playviewandroid.bean.LoginBean;
import com.example.ray.playviewandroid.model.http.method.NetworkServer;
import com.example.ray.playviewandroid.model.interfaces.ILoginRegisterModel;

import io.reactivex.Observable;


public class LoginRegisterModel implements ILoginRegisterModel {

    @Override
    public Observable<BaseResponse<LoginBean>> Logining(String user, String pw) {
        return NetworkServer.getInstance().getNetworkDatas().login(user,pw);
    }

    @Override
    public Observable<BaseResponse<LoginBean>> Register(String user, String pw, String repw) {
        return NetworkServer.getInstance().getNetworkDatas().register(user,pw,repw);
    }
}
