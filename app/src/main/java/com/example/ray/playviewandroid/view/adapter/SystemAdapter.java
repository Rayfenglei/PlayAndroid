package com.example.ray.playviewandroid.view.adapter;

import android.content.Context;
import android.widget.TextView;
import com.example.ray.playviewandroid.R;
import com.example.ray.playviewandroid.base.BaseRecyclerAdapter;
import com.example.ray.playviewandroid.base.BaseViewHolder;
import com.example.ray.playviewandroid.bean.FirstSystemBean;

import java.util.List;

public class SystemAdapter extends BaseRecyclerAdapter<FirstSystemBean> {
    private Context context;

    public SystemAdapter(Context context, List<FirstSystemBean> dataList, int layoutId) {
        super(context, dataList, layoutId);
        this.context = context;
    }

    @Override
    protected void bindData(BaseViewHolder holder, FirstSystemBean data) {
        TextView mTvFirst = holder.getView(R.id.tv_system_first_item);
        TextView mTvSecond = holder.getView(R.id.tv_system_second_item);
        mTvFirst.setText(data.getName());
        StringBuilder str = new StringBuilder();
        for (FirstSystemBean.ChildrenBean bean : data.getChildren()){
            str.append(bean.getName()).append("  ");
        }
        mTvSecond.setText(str);
    }
}
