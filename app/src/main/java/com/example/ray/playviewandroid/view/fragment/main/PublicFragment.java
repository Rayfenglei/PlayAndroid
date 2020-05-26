package com.example.ray.playviewandroid.view.fragment.main;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.ray.playviewandroid.R;
import com.example.ray.playviewandroid.base.BaseFragment;
import com.example.ray.playviewandroid.bean.TabBean;
import com.example.ray.playviewandroid.presenter.login.PublicPresenter;
import com.example.ray.playviewandroid.view.activity.SearchActivity;
import com.example.ray.playviewandroid.view.adapter.DetailViewpagerAdapter;
import com.example.ray.playviewandroid.view.fragment.main.PublicDetailFragment;
import com.example.ray.playviewandroid.view.interfaces.IPublicView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class PublicFragment extends BaseFragment<IPublicView, PublicPresenter<IPublicView>> implements IPublicView,View.OnClickListener{
    private static final String TAG = "PublicFragment";
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private DetailViewpagerAdapter mViewAdapter;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private List<String> mTabTitles = new ArrayList<>();
    private EditText etSearchBar;
    private ImageView imSearchBar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_public);

        return view;
    }

    @Override
    protected PublicPresenter<IPublicView> createPresenter() {
        return new PublicPresenter<>();
    }
    @Override
    protected boolean setFragmentTarget() {
        return true;
    }
    @Override
    public void initView() {
        mTabLayout = view.findViewById(R.id.tab_public);
        mViewPager = view.findViewById(R.id.viewpager_public);
        etSearchBar = view.findViewById(R.id.et_searcher_bar);
        imSearchBar = view.findViewById(R.id.im_searcher_bar);
    }

    @Override
    public void initData() {
        Log.i(TAG,"initdata");
        mPresenter.loadPublicTab();
        mViewAdapter = new DetailViewpagerAdapter(context.getSupportFragmentManager(),mFragmentList,mTabTitles);
        mViewPager.setAdapter(mViewAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void initEvent() {
        etSearchBar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.et_searcher_bar){
            toSearchActivity();
        }
    }

    @Override
    public void showPublicTab(List<TabBean> tabBeans) {
        mTabTitles.clear();
        mFragmentList.clear();
        for (TabBean bean : tabBeans){
            mTabTitles.add(bean.getName());
            mFragmentList.add(PublicDetailFragment.newInstance(bean.getId()));
        }
        mViewAdapter.notifyDataSetChanged();
    }


    private void toSearchActivity(){
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        Pair<View,String> etPair = new Pair<>((View)etSearchBar,getString(R.string.trans_edit));
        Pair<View,String> imPair = new Pair<>((View)imSearchBar,getString(R.string.trans_image));
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(),imPair,etPair);
        startActivity(intent,options.toBundle());
    }
}
