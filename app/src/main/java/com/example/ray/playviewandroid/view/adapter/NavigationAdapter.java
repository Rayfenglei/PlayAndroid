package com.example.ray.playviewandroid.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ray.playviewandroid.R;

import java.util.List;

public class NavigationAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    private Context context;

    public NavigationAdapter(FragmentManager fm, List<Fragment> fragmentList, Context context) {
        super(fm);
        this.fragmentList = fragmentList;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragmentList == null ? null : this.fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
    //自定义导航栏
    public View setCustomView(int position){
        View view = LayoutInflater.from(context).inflate(R.layout.tablayout_navigation, null);
        TextView textView = view.findViewById(R.id.tv_navi_tab);
        ImageView imageView = view.findViewById(R.id.iv_navi_tab);
        switch (position){
            case 0:
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.tab_bottom_home,null));
                textView.setText(R.string.bottom_tab_home);
                break;
            case 1:
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.tab_bottom_system,null));
                textView.setText(R.string.bottom_tab_system);
                break;
            case 2:
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.tab_bottom_project,null));
                textView.setText(R.string.bottom_tab_project);
                break;
            case 3:
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.tab_bottom_public,null));
                textView.setText(R.string.bottom_tab_public);
                break;
            case 4:
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.tab_bottom_my,null));
                textView.setText(R.string.bottom_tab_my);
                break;

        }
        return view;
    }
}
