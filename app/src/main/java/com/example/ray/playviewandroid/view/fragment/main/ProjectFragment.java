package com.example.ray.playviewandroid.view.fragment.main;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.ray.playviewandroid.R;
import com.example.ray.playviewandroid.base.BaseFragment;
import com.example.ray.playviewandroid.bean.ArticleBean;
import com.example.ray.playviewandroid.bean.TabBean;
import com.example.ray.playviewandroid.presenter.login.ProjectPresenter;
import com.example.ray.playviewandroid.view.activity.SearchActivity;
import com.example.ray.playviewandroid.view.adapter.DetailViewpagerAdapter;
import com.example.ray.playviewandroid.view.fragment.main.ProjectDetailFragment;
import com.example.ray.playviewandroid.view.interfaces.IProjectView;

import java.util.ArrayList;
import java.util.List;

public class ProjectFragment extends BaseFragment<IProjectView, ProjectPresenter<IProjectView>> implements IProjectView,View.OnClickListener{
    private static final String TAG = "ProjectFragment";
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private EditText etSearchBar;
    private ImageView imSearchBar;
    private DetailViewpagerAdapter mAdapter;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private List<String> mTabTitles = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_project);
        initView();
        initData();
        initEvent();
        return view;
    }

    @Override
    protected ProjectPresenter<IProjectView> createPresenter() {
        return new ProjectPresenter<>();
    }

    @Override
    public void initView() {
        mTabLayout = view.findViewById(R.id.tab_project);
        mViewPager = view.findViewById(R.id.viewpager_project);
        etSearchBar = view.findViewById(R.id.et_searcher_bar);
        imSearchBar = view.findViewById(R.id.im_searcher_bar);
    }

    @Override
    public void initData() {
        mPresenter.loadProjectTab();
        mAdapter = new DetailViewpagerAdapter(context.getSupportFragmentManager(),mFragmentList,mTabTitles);
        mViewPager.setAdapter(mAdapter);
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
    public void showProjectTab(List<TabBean> tabBeans) {
        mTabTitles.clear();
        mFragmentList.clear();
        for (TabBean bean : tabBeans){
            mTabTitles.add(bean.getName());
            mFragmentList.add(ProjectDetailFragment.newInstance(bean.getId()));
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showAriticle(List<ArticleBean> articlesList) {

    }

    @Override
    public void showCollectSuccess() {

    }
    @Override
    public void showUnCollectSuccess() {

    }
    @Override
    public void collect(int position) {

    }
    private void toSearchActivity(){
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        Pair<View,String> etPair = new Pair<>((View)etSearchBar,getString(R.string.trans_edit));
        Pair<View,String> imPair = new Pair<>((View)imSearchBar,getString(R.string.trans_image));
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(),imPair,etPair);
        startActivity(intent,options.toBundle());
    }
}
