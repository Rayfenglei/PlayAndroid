package com.example.ray.playviewandroid.model.interfaces;

import com.example.ray.playviewandroid.bean.BaseResponse;
import com.example.ray.playviewandroid.bean.FirstSystemBean;

import java.util.List;

import io.reactivex.Observable;

public interface ISystemModel {
    Observable<BaseResponse<List<FirstSystemBean>>> getFirstSystemData();
}
