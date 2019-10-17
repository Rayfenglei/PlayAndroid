package com.example.ray.playviewandroid.view.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;
/*
* FragmnetPageAdapter在每次切换页面时，只是将Fragment进行分离，适合页面较少的Fragment使用以保存一些内存，对系统内存不会多大影响
* FragmentPageStateAdapter在每次切换页面的时候，是将Fragment进行回收，适合页面较多的Fragment使用，这样就不会消耗更多的内存
* */
public class DetailViewpagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragmentList;
    private List<String> mSecondTitle;

    public DetailViewpagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> mSecondTitle) {
        super(fm);
        this.fragmentList = fragmentList;
        this.mSecondTitle = mSecondTitle;
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragmentList == null ? null : this.fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList == null ? 0 : fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mSecondTitle.get(position);
    }
}
