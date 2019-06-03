package com.example.ray.playviewandroid.view.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class DetailViewpagerAdapter extends FragmentPagerAdapter {
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
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mSecondTitle.get(position);
    }
}
