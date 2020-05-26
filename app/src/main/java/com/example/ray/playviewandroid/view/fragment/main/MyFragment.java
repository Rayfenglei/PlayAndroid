package com.example.ray.playviewandroid.view.fragment.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ray.playviewandroid.R;
import com.example.ray.playviewandroid.base.BaseFragment;
import com.example.ray.playviewandroid.base.BasePresenter;


public class MyFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_my);

        return view;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void initView() {
       // StatusBarTwoUtil.immersiveInFragments(context, Color.TRANSPARENT, 1);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }
    @Override
    protected boolean setFragmentTarget() {
        return false;
    }
}
