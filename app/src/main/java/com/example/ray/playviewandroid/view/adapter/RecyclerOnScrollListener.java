package com.example.ray.playviewandroid.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.ray.playviewandroid.util.LogUtil;

public abstract class RecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    //用来标记是否正在向上滑动
    private boolean isSlidingUpward = false;

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        // 当不滑动时
        if (newState == RecyclerView.SCROLL_STATE_IDLE){
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            //得到当前显示的最后一个item的view
            View lastChildView = recyclerView.getLayoutManager().getChildAt(recyclerView.getLayoutManager().getChildCount()-2);
            if (lastChildView == null){
                return;
            }
            //通过这个lastChildView得到这个view当前的position值
            int lastPosition  = recyclerView.getLayoutManager().getPosition(lastChildView);
            //判断lastPosition是不是最后一个position
            //如果两个条件都满足则说明是真正的滑动到了底部
            if(lastPosition == recyclerView.getLayoutManager().getItemCount()-2 && isSlidingUpward){
                onLoadMore();
            }

        }
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        // 大于0表示正在向上滑动，小于等于0表示停止或向下滑动
        LogUtil.i("RecyclerOnScrollListener","onScrolled dy "+dy);
        isSlidingUpward = dy > 0;
    }

    public abstract void onLoadMore();
}
