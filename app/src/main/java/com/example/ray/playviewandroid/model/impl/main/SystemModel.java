package com.example.ray.playviewandroid.model.impl.main;

import com.example.ray.playviewandroid.bean.BaseResponse;
import com.example.ray.playviewandroid.bean.FirstSystemBean;
import com.example.ray.playviewandroid.model.http.method.NetworkServer;
import com.example.ray.playviewandroid.model.interfaces.ISystemModel;

import java.util.List;

import io.reactivex.Observable;

public class SystemModel implements ISystemModel {
    @Override
    public Observable<BaseResponse<List<FirstSystemBean>>> getFirstSystemData() {
        return NetworkServer.getInstance().getNetworkDatas().getFirstSystemData();
    }
}
