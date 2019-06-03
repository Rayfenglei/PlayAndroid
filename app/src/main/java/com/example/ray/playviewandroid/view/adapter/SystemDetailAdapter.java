package com.example.ray.playviewandroid.view.adapter;

import android.content.Context;

import com.example.ray.playviewandroid.base.BaseRecyclerAdapter;
import com.example.ray.playviewandroid.base.BaseViewHolder;
import com.example.ray.playviewandroid.bean.FirstSystemBean;

import java.util.List;

public class SystemDetailAdapter extends BaseRecyclerAdapter<FirstSystemBean.ChildrenBean> {

    public SystemDetailAdapter(Context context, List<FirstSystemBean.ChildrenBean> dataList, int layoutId) {
        super(context, dataList, layoutId);
    }

    @Override
    protected void bindData(BaseViewHolder holder, FirstSystemBean.ChildrenBean data) {

    }
}
