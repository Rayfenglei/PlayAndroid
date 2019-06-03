package com.example.ray.playviewandroid.view.fragment.main;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.ray.playviewandroid.R;
import com.example.ray.playviewandroid.base.BaseFragment;
import com.example.ray.playviewandroid.base.BaseRecyclerAdapter;
import com.example.ray.playviewandroid.bean.FirstSystemBean;
import com.example.ray.playviewandroid.presenter.login.SystemPresenter;
import com.example.ray.playviewandroid.view.activity.SearchActivity;
import com.example.ray.playviewandroid.view.activity.SystemDetailActivity;
import com.example.ray.playviewandroid.view.adapter.SystemAdapter;
import com.example.ray.playviewandroid.view.interfaces.ISystemView;

import java.util.ArrayList;
import java.util.List;

public class SystemFragment extends BaseFragment<ISystemView, SystemPresenter<ISystemView>> implements ISystemView,BaseRecyclerAdapter.OnItemClickListener,View.OnClickListener {
    private static final String TAG = "SystemFragment";

    private RecyclerView mRvSystem;
    private List<FirstSystemBean> mDataList;
    private SystemAdapter mAdapter;
    private EditText etSearchBar;
    private ImageView imSearchBar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_system);
        initView();
        initData();
        initEvent();
        return view;
    }


    @Override
    public void initView() {

        mRvSystem = view.findViewById(R.id.rv_system);
        etSearchBar = view.findViewById(R.id.et_searcher_bar);
        imSearchBar = view.findViewById(R.id.im_searcher_bar);

    }

    @Override
    public void initData() {

        mDataList = new ArrayList<>();
        mAdapter = new SystemAdapter(context,mDataList,R.layout.recycler_system_list);
        mRvSystem.setLayoutManager(new LinearLayoutManager(context));
        mRvSystem.setItemAnimator(new DefaultItemAnimator());
        mRvSystem.setAdapter(mAdapter);
        mPresenter.loadSystemData();
    }



    @Override
    public void initEvent() {
        mAdapter.setOnItemClickListener(this);
        etSearchBar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.et_searcher_bar){
            startActivity(new Intent(getContext(), SearchActivity.class));
        }
    }

    @Override
    public void onItemClick(Object data, int position, View view) {
        if (data instanceof FirstSystemBean){
            FirstSystemBean firstSystemBean = (FirstSystemBean) data;
            //跳转到二级页面
            SystemDetailActivity.startActivityForFragment(context,firstSystemBean);
        }
    }

    @Override
    protected SystemPresenter<ISystemView> createPresenter() {
        return new SystemPresenter<>();
    }

    @Override
    public void doShowData(List<FirstSystemBean> data) {
        mDataList = data;
        mAdapter.setDataList(data);
    }
    private void toSearchActivity(){
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        Pair<View,String> etPair = new Pair<>((View)etSearchBar,getString(R.string.trans_edit));
        Pair<View,String> imPair = new Pair<>((View)imSearchBar,getString(R.string.trans_image));
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(),imPair,etPair);
        startActivity(intent,options.toBundle());
    }
}
