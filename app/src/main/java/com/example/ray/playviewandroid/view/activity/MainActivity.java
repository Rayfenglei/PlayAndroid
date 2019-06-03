package com.example.ray.playviewandroid.view.activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.ray.playviewandroid.R;
import com.example.ray.playviewandroid.base.BaseActivity;
import com.example.ray.playviewandroid.base.BasePresenter;
import com.example.ray.playviewandroid.view.adapter.NavigationAdapter;
import com.example.ray.playviewandroid.view.fragment.main.HomeFragment;
import com.example.ray.playviewandroid.view.fragment.main.MyFragment;
import com.example.ray.playviewandroid.view.fragment.main.ProjectFragment;
import com.example.ray.playviewandroid.view.fragment.main.PublicFragment;
import com.example.ray.playviewandroid.view.fragment.main.SystemFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private List<Fragment> fragmentList;
    private NavigationAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        mViewPager = findViewById(R.id.vp_navigation);
        mTabLayout = findViewById(R.id.tl_navigation);

    }

    @Override
    public void initData() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new SystemFragment());
        fragmentList.add(new ProjectFragment());
        fragmentList.add(new PublicFragment());
        fragmentList.add(new MyFragment());

        adapter = new NavigationAdapter(getSupportFragmentManager(),fragmentList,this);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void initEvent() {
        setTabLayout();

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    private void setTabLayout(){
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //改变Tab 状态
                (tab.getCustomView().findViewById(R.id.iv_navi_tab)).setSelected(true);
                (tab.getCustomView().findViewById(R.id.tv_navi_tab)).setSelected(true);
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                (tab.getCustomView().findViewById(R.id.iv_navi_tab)).setSelected(false);
                (tab.getCustomView().findViewById(R.id.tv_navi_tab)).setSelected(false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // 为绑定viewpager后的TabLayout的tabs设置自定义view
        for (int i = 0; i < fragmentList.size(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            tab.setCustomView(adapter.setCustomView(i));
        }
        //打开指定界面
        mTabLayout.getTabAt(0).select();
    }
}
