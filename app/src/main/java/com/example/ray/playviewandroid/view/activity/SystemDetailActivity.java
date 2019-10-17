package com.example.ray.playviewandroid.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.ray.playviewandroid.R;
import com.example.ray.playviewandroid.base.BaseActivity;
import com.example.ray.playviewandroid.base.BasePresenter;
import com.example.ray.playviewandroid.bean.FirstSystemBean;
import com.example.ray.playviewandroid.view.adapter.DetailViewpagerAdapter;
import com.example.ray.playviewandroid.view.fragment.main.SystemDetailFragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


public class SystemDetailActivity extends BaseActivity {
    private static final String TAG = "SystemDetailActivity";
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private FirstSystemBean mFirstSystemBean;
    private DetailViewpagerAdapter mAdapter;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private List<String> mSecondTitle = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_systemdetail);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        mToolbar = findViewById(R.id.bar_systemdetail);
        mViewPager = findViewById(R.id.viewpager_systemdetail);
        mTabLayout = findViewById(R.id.tab_system_detail);
    }

    @Override
    public void initData() {
        getData();
        mToolbar.setTitle(mFirstSystemBean.getName());
        setSupportActionBar(mToolbar);
        mAdapter = new DetailViewpagerAdapter(getSupportFragmentManager(),mFragmentList,mSecondTitle);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void initEvent() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    /*
    * 获取其它活动传入的数据
    * */
    private void getData(){

        Intent intent = getIntent();
        mFirstSystemBean = (FirstSystemBean) intent.getSerializableExtra("firstSystemBean");

        for (FirstSystemBean.ChildrenBean childrenBean : mFirstSystemBean.getChildren()){
            mSecondTitle.add(childrenBean.getName());
            mFragmentList.add(SystemDetailFragment.newInstance(childrenBean.getId()));
        }
    }

    /**
     * 给其他活动需要传入数据并跳转到该活动时调用
     *
     * @param context              活动
     * @param firstSystemBean      一级知识
     */
    public static void startActivityForFragment(Context context, FirstSystemBean firstSystemBean){
        WeakReference<Context> rfContext = new WeakReference<>(context);
        Intent intent = new Intent(rfContext.get(),SystemDetailActivity.class);
        intent.putExtra("firstSystemBean",firstSystemBean);
        context.startActivity(intent);
    }
}
