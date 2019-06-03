package com.example.ray.playviewandroid.model.interfaces;

import com.example.ray.playviewandroid.bean.BaseResponse;
import com.example.ray.playviewandroid.bean.LoginBean;

import io.reactivex.Observable;

public interface ILoginRegisterModel {
    Observable<BaseResponse<LoginBean>> Logining(String user, String pw);
    Observable<BaseResponse<LoginBean>> Register(String user, String pw, String repw);
}
