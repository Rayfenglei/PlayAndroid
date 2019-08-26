package com.example.ray.playviewandroid.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ray.playviewandroid.R;
import com.example.ray.playviewandroid.bean.ArticleBean;
import com.example.ray.playviewandroid.bean.BannerDataBean;
import com.example.ray.playviewandroid.constants.PlayViewConstants;
import com.example.ray.playviewandroid.view.activity.ArticleActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import java.util.ArrayList;
import java.util.List;


public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{

    private static final int ITEM_TYPE_HEADER = 0x000010;
    private static final int ITEM_TYPE_FOOTER = 0x000011;
    private static final int ITEM_TYPE_NORMAL= 0x000012;
    // 当前加载状态，默认为加载完成
//    private int loadState = 2;
//    public final int LOADING = 1;
//    public final int LOADING_COMPLETE = 2;
//    public final int LOADING_END = 3;

    private Context context;
    private List<ArticleBean> dataList = new ArrayList<>();
    private List<String> images = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private List<BannerDataBean> bannerList = new ArrayList<>();
    private Activity activity;
    public HomeAdapter(Context context,Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return ITEM_TYPE_HEADER;
        }else if (position == dataList.size()+1){
            return ITEM_TYPE_FOOTER;
        }else {
            return ITEM_TYPE_NORMAL;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View itemView;
        switch (viewType){
            case ITEM_TYPE_HEADER:
                itemView = LayoutInflater.from(context).inflate(R.layout.recycler_home_head, viewGroup, false);
                viewHolder = new HeaderHolder(itemView);
                break;
//            case ITEM_TYPE_FOOTER:
//                itemView = LayoutInflater.from(context).inflate(R.layout.recycler_refresh_footer, viewGroup, false);
//                viewHolder = new FooterHolder(itemView);
//                break;
            default:
                itemView = LayoutInflater.from(context).inflate(R.layout.recycler_common_list, viewGroup, false);
                viewHolder = new NormalHolder(itemView);
                break;

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof HeaderHolder){
            HeaderHolder holder = (HeaderHolder) viewHolder;
            holder.setBanner(titles,images);
        }
//        else if (viewHolder instanceof FooterHolder){
//            FooterHolder holder = (FooterHolder) viewHolder;
//            switch (loadState){
//                case LOADING:
//                    holder.tvLoading.setVisibility(View.VISIBLE);
//                    holder.tvLoadEnd.setVisibility(View.GONE);
//                    break;
//                case LOADING_COMPLETE:
//                    holder.tvLoading.setVisibility(View.GONE);
//                    holder.tvLoadEnd.setVisibility(View.GONE);
//                    break;
//                case LOADING_END:
//                    holder.tvLoading.setVisibility(View.GONE);
//                    holder.tvLoadEnd.setVisibility(View.VISIBLE);
//                    break;
//            }
//        }
        else {
            NormalHolder holder = (NormalHolder) viewHolder;
            ArticleBean bean = dataList.get(position-1);
            holder.setItem(bean);
            holder.itemView.setTag(position-1);
            holder.ivCollect.setTag(position-1);

        }
    }

    @Override
    public int getItemCount() {
        return dataList.size()+1;
    }

    class NormalHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        TextView tvAuthor;
        TextView tvNiceDate;
        TextView tvType;
        ImageView ivCollect;
        NormalHolder(View view){
            super(view);
            tvTitle =  view.findViewById(R.id.tv_common_rv_title);
            tvAuthor = view.findViewById(R.id.tv_common_rv_author);
            tvNiceDate = view.findViewById(R.id.tv_common_rv_date);
            tvType = view.findViewById(R.id.tv_common_rv_type);
            ivCollect = view.findViewById(R.id.iv_common_rv_like);

            view.setOnClickListener(HomeAdapter.this);
            ivCollect.setOnClickListener(HomeAdapter.this);
        }

        void setItem(ArticleBean bean){
             tvTitle.setText(bean.getTitle());
             tvAuthor.setText(bean.getAuthor());
             tvNiceDate.setText(bean.getNiceDate());
             tvType.setText(bean.getSuperChapterName()+" / "+bean.getChapterName());

             if (bean.isCollect()){
                ivCollect.setSelected(true);
             }else {
                 ivCollect.setSelected(false);
             }
        }
    }

    class HeaderHolder extends RecyclerView.ViewHolder{
        Banner mBanner;

        HeaderHolder(View view){
            super(view);
            mBanner = view.findViewById(R.id.banner_home);
        }
        void setBanner(final List<String> titles, final List<String> images){
            //设置banner样式
            mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
            //设置图片加载器
            mBanner.setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    Glide.with(context).load(path).into(imageView);
                }
            });
            //设置标题集合
            mBanner.setBannerTitles(titles);
            //设置图片集合
            mBanner.setImages(images);
            //设置banner动画效果
            mBanner.setBannerAnimation(Transformer.Accordion);
            //设置自动轮播，默认为true
            mBanner.isAutoPlay(true);
            //设置轮播时间
            mBanner.setDelayTime(2000);
            //设置指示器位置（当banner模式中有指示器时）
            mBanner.setIndicatorGravity(BannerConfig.RIGHT);
            //点击事件
            mBanner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    ArticleActivity.startActivityForActivity(activity,
                            bannerList.get(position).getUrl(),
                            bannerList.get(position).getTitle(),
                            bannerList.get(position).getId(),
                            false,
                            PlayViewConstants.REQUEST_BANNER);
                }
            });
            //banner设置方法全部调用完毕时最后调用
            mBanner.start();
        }
    }

    /*private class FooterHolder extends RecyclerView.ViewHolder{
        TextView tvLoading;
        TextView tvLoadEnd;
        FooterHolder(View view){
            super(view);
            tvLoading = view.findViewById(R.id.tv_loading);
            tvLoadEnd = view.findViewById(R.id.tv_load_end);
        }
    }*/

    public void setNewData(List<ArticleBean> newDatas) {
        if (newDatas != null) {
//            if (dataList != null) {
//                dataList.clear();
//            } else {
//                dataList = new ArrayList<>();
//            }
            dataList.addAll(newDatas);
            notifyDataSetChanged();
        }
    }
    public void onAddData(List<ArticleBean> newDatas) {
        if (newDatas != null) {
            dataList.addAll(newDatas);
            notifyDataSetChanged();
        }
    }

    public void setBannerData(List<BannerDataBean> bannerData) {
        bannerList = bannerData;
        if (bannerData != null) {
            for (BannerDataBean bean : bannerData){
                images.add(bean.getImagePath());
                titles.add(bean.getTitle());
            }
            notifyItemChanged(0);
        }
    }

/*
    public void setLoadState(int loadState){
        this.loadState = loadState;
        notifyDataSetChanged();
    }
*/

    public void setCollectState(int position,boolean state){
        dataList.get(position).setCollect(state);
        notifyItemChanged(position+1);
    }
//=======================以下为item中的button控件点击事件处理===================================

    //item里面有多个控件可以点击（item+item内部控件）
    public enum ViewName {
        ITEM,
        PRACTISE
    }

    public interface OnItemClickListener1 {
        void onItemClick(View v, ViewName viewName, int position);
        void onItemLongClick(View v);
    }
    private OnItemClickListener1 mOnItemClickListener;

    public void setOnItemClickListener1(OnItemClickListener1 listener) {
        this.mOnItemClickListener = listener;
    }
    @Override
    public void onClick(View view) {
        int position = (int) view.getTag();
        int id = dataList.get(position).getId();
        if (mOnItemClickListener != null){
            switch (view.getId()){
                case R.id.rv_home:
                    mOnItemClickListener.onItemClick(view,ViewName.ITEM,position);
                    break;
                default:
                    mOnItemClickListener.onItemClick(view,ViewName.PRACTISE,position);
                    break;
            }
        }
    }
}
