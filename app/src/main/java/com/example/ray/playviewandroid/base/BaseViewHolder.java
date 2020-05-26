package com.example.ray.playviewandroid.base;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ray.playviewandroid.PlayApplication;
import com.example.ray.playviewandroid.R;

public class BaseViewHolder extends RecyclerView.ViewHolder {

    // SparseArray 比 HashMap 更省内存，在某些条件下性能更好，只能存储 key 为 int 类型的数据，
    // 用来存放 View 以减少 findViewById 的次数
    private SparseArray<View> viewSparseArray;

    public BaseViewHolder(final View itemView) {
        super(itemView);
        viewSparseArray = new SparseArray<>();
    }

    /**
     * 根据 ID 来获取 View
     *
     * @param viewId viewID
     * @param <T>    泛型
     * @return 将结果强转为 View 或 View 的子类型
     */
    public <T extends View> T getView(int viewId) {
        // 先从缓存中找，找打的话则直接返回
        // 如果找不到则 findViewById ，再把结果存入缓存中
        View view = viewSparseArray.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            viewSparseArray.put(viewId, view);
        }
        return (T) view;
    }

    public BaseViewHolder setText(int viewId, CharSequence text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public BaseViewHolder setText(int viewId, int textId) {
        TextView tv = getView(viewId);
        tv.setText(textId);
        return this;
    }

    public BaseViewHolder setTextDrawableTop(int viewId, int drawableTop) {
        TextView tv = getView(viewId);
        Drawable icon = itemView.getContext().getDrawable(drawableTop);
        icon.setBounds(new Rect(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight()));
        tv.setCompoundDrawables(null, icon, null, null);
        return this;
    }

    public BaseViewHolder setViewVisibility(int viewId, int visibility) {
        getView(viewId).setVisibility(visibility);
        return this;
    }

    public BaseViewHolder setImageResource(int viewId, int resourceId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resourceId);
        return this;
    }

    public BaseViewHolder setImagePath(int viewId, String path) {
        ImageView imageView = getView(viewId);
        Glide
                .with(PlayApplication.getContext())
                .load(path)
                .apply(new RequestOptions().placeholder(R.drawable.ic_launcher_foreground))
                .into(imageView);
        return this;
    }

    public BaseViewHolder setImageVisible(int viewId, boolean visible) {
        ImageView imageView = getView(viewId);
        if (visible) {
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }
        return this;
    }

}
