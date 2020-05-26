package com.example.ray.playviewandroid.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    private LayoutInflater layoutInflater;
    private List<T> dataList;
    private int layoutId;

    private OnItemClickListener itemClickListener;
    private OnItemLongClickListener itemLongClickListener;

    public BaseRecyclerAdapter(Context context, List<T> dataList, int layoutId) {
        this.layoutInflater = LayoutInflater.from(context);
        this.dataList = dataList;
        this.layoutId = layoutId;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(layoutId, parent, false);
        return new BaseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        bindData(holder, dataList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 防止出现dataList为空时引发的空指针异常
                if (itemClickListener != null && dataList != null) {
                    int position = holder.getLayoutPosition();
                    int lastPosition = dataList.size() - 1;
                    // 判断当前获取的位置小于或等于数组最大index时，才执行下列操作
                    if(position <= lastPosition) {
                        itemClickListener.onItemClick(dataList.get(position), position, holder.itemView);
                    }
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (itemLongClickListener != null) {
                    int position = holder.getLayoutPosition();
                    itemLongClickListener.onItemLongClick(dataList.get(position), position, holder.itemView);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        if (dataList == null) return 0;
        return dataList.size();
    }

    protected abstract void bindData(BaseViewHolder holder, T data);

    public void setDataList(List<T> dataList){
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(Object data, int position, View view);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(Object data, int position, View view);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.itemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.itemLongClickListener = onItemLongClickListener;
    }

    public void setNewData(List<T> newDatas) {
        if (newDatas != null) {
            if (dataList != null) {
                dataList.clear();
            } else {
                dataList = new ArrayList<>();
            }
            dataList.addAll(newDatas);
            notifyDataSetChanged();
        }
    }

    public List<T> getDataList(){
        return dataList;
    }

    public OnItemClickListener getItemClickListener(){
        return itemClickListener;
    }

}