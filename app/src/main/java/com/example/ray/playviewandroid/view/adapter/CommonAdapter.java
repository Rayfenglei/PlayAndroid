package com.example.ray.playviewandroid.view.adapter;

import android.annotation.SuppressLint;
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
import com.example.ray.playviewandroid.util.LogUtil;

import java.util.ArrayList;
import java.util.List;


public class CommonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private static final String TAG = "CommonAdapter";
    // 当前加载状态，默认为加载完成
    private int loadState = 2;
    public final int LOADING = 1;
    public final int LOADING_COMPLETE = 2;
    public final int LOADING_END = 3;
    private Context context;
    private List<ArticleBean> dataList = new ArrayList<>();
    private int layoutId;

    public CommonAdapter(Context context,int layoutId) {
        this.context = context;
        this.layoutId = layoutId;
    }

    private class CommonViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        TextView tvAuthor;
        TextView tvNiceDate;
        TextView tvType;
        ImageView ivCollect;

        CommonViewHolder(View view){
            super(view);
            tvTitle =  view.findViewById(R.id.tv_common_rv_title);
            tvAuthor = view.findViewById(R.id.tv_common_rv_author);
            tvNiceDate = view.findViewById(R.id.tv_common_rv_date);
            tvType = view.findViewById(R.id.tv_common_rv_type);
            ivCollect = view.findViewById(R.id.iv_common_rv_like);

            view.setOnClickListener(CommonAdapter.this);
            ivCollect.setOnClickListener(CommonAdapter.this);
        }
    }
    private class ProjectViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        TextView tvAuthor;
        TextView tvNiceDate;
        TextView tvDescribe;
        ImageView ivCollect;
        ImageView ivPic;
        ProjectViewHolder(View view){
            super(view);
            tvTitle =  view.findViewById(R.id.tv_item_title);
            tvAuthor = view.findViewById(R.id.tv_item_author);
            tvNiceDate = view.findViewById(R.id.tv_item_date);
            ivCollect = view.findViewById(R.id.iv_item_like);
            ivPic = view.findViewById(R.id.iv_item_pic);
            tvDescribe = view.findViewById(R.id.tv_item_desc);
            view.setOnClickListener(CommonAdapter.this);
            ivCollect.setOnClickListener(CommonAdapter.this);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (layoutId == R.layout.recyeler_item_list){
            View view = LayoutInflater.from(context).inflate(R.layout.recyeler_item_list, viewGroup, false);
            return new ProjectViewHolder(view);
        }
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_common_list, viewGroup, false);
        return new CommonViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof CommonViewHolder){
            CommonViewHolder commonViewHolder = (CommonViewHolder) viewHolder;
            commonViewHolder.tvAuthor.setText(dataList.get(position).getAuthor());
            commonViewHolder.tvNiceDate.setText(dataList.get(position).getNiceDate());
            commonViewHolder.tvTitle.setText(dataList.get(position).getTitle());
            commonViewHolder.tvType.setText(dataList.get(position).getSuperChapterName()+" / "+dataList.get(position).getChapterName());
            if (dataList.get(position).isCollect()){
                commonViewHolder.ivCollect.setSelected(true);
            }else {
                commonViewHolder.ivCollect.setSelected(false);
            }
            commonViewHolder.itemView.setTag(position);
            commonViewHolder.ivCollect.setTag(position);

        }else if(viewHolder instanceof ProjectViewHolder){

            ProjectViewHolder projectViewHolder = (ProjectViewHolder) viewHolder;
            projectViewHolder.tvAuthor.setText(dataList.get(position).getAuthor());
            projectViewHolder.tvNiceDate.setText(dataList.get(position).getNiceDate());
            projectViewHolder.tvTitle.setText(dataList.get(position).getTitle());
            projectViewHolder.tvDescribe.setText(dataList.get(position).getDesc());
            Glide.with(context).load(dataList.get(position).getEnvelopePic()).into(projectViewHolder.ivPic);
            if (dataList.get(position).isCollect()){
                projectViewHolder.ivCollect.setSelected(true);
            }else {
                projectViewHolder.ivCollect.setSelected(false);
            }
            projectViewHolder.itemView.setTag(position);
            projectViewHolder.ivCollect.setTag(position);
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setLoadState(int loadState){
        this.loadState = loadState;
        notifyDataSetChanged();
    }

    public void setDataList(List<ArticleBean> newDatas) {
        if (newDatas != null) {
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
    public void setCollectState(int position,boolean state){
        dataList.get(position).setCollect(state);
        notifyItemChanged(position);
    }

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener  {
        void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View view) {
        int position = (int) view.getTag();
        if (mOnItemClickListener != null){
            mOnItemClickListener.onItemClick(view,position);
        }
    }
}
