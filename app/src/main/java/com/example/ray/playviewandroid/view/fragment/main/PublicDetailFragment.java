package com.example.ray.playviewandroid.view.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ray.playviewandroid.R;
import com.example.ray.playviewandroid.base.BaseFragment;
import com.example.ray.playviewandroid.bean.ArticleBean;
import com.example.ray.playviewandroid.constants.PlayViewConstants;
import com.example.ray.playviewandroid.presenter.login.PublicDetailPresenter;
import com.example.ray.playviewandroid.util.AppUtil;
import com.example.ray.playviewandroid.util.LogUtil;
import com.example.ray.playviewandroid.util.NetworkUtil;
import com.example.ray.playviewandroid.util.SharedPreferencesUtils;
import com.example.ray.playviewandroid.view.activity.ArticleActivity;
import com.example.ray.playviewandroid.view.activity.LoginActivity;
import com.example.ray.playviewandroid.view.adapter.CommonAdapter;
import com.example.ray.playviewandroid.view.adapter.RecyclerOnScrollListener;
import com.example.ray.playviewandroid.view.interfaces.IPublicDetailView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class PublicDetailFragment extends BaseFragment<IPublicDetailView, PublicDetailPresenter<IPublicDetailView>> implements IPublicDetailView {
    private static final String TAG = "SystemDetailFragment";
    private int mCid;
    private RecyclerView mRecyclerView;
    private CommonAdapter mAdapter;
    private List<ArticleBean> articleBeanList = new ArrayList<>();
    private int clickPosition;

    private SmartRefreshLayout mRefreshLayout;
    private int mPageNum = 0;  //用于刷新
    private boolean isRefresh = false;  //是否为向上刷新
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_public_detail);
        initView();
        initData();
        initEvent();
        return view;
    }

    @Override
    public void initView() {
        mRecyclerView = view.findViewById(R.id.rv_public_detail);
        mRefreshLayout = view.findViewById(R.id.srl_public_detail);
    }

    @Override
    public void initData() {
        getData();
        mPresenter.loadPublicArticle(0,mCid);
        mAdapter = new CommonAdapter(context,R.layout.recycler_common_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(context,DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(context,R.drawable.recycler_divider));
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    public void initEvent() {
        mAdapter.setOnItemClickListener(onItemClickListener);
        initRefreshView();
//        mRecyclerView.addOnScrollListener(new RecyclerOnScrollListener() {
//            @Override
//            public void onLoadMore() {
//                mAdapter.setLoadState(mAdapter.LOADING);
//                LOAD_PAGE++;
//                mPresenter.loadPublicArticle(LOAD_PAGE,mCid);
//            }
//        });
    }

    @Override
    protected PublicDetailPresenter<IPublicDetailView> createPresenter() {
        return new PublicDetailPresenter<>();
    }

    @Override
    public void showArticle(List<ArticleBean> articlesList) {
        articleBeanList.addAll(articlesList);
        mAdapter.setDataList(articlesList);
//        mAdapter.setLoadState(mAdapter.LOADING_COMPLETE);
    }

    @Override
    public void showMoreArticle(List<ArticleBean> articlesList) {
        if (isRefresh) {
            if (!AppUtil.isEmptyList(articleBeanList)) {
                articleBeanList.clear();
            }
            mRefreshLayout.finishRefresh();
        } else {
            if (AppUtil.isEmptyList(articleBeanList)) {
                mRefreshLayout.finishLoadmoreWithNoMoreData();
            } else {
                mRefreshLayout.finishLoadmore(1000);
            }
        }
        articleBeanList.addAll(articlesList);
        mAdapter.onAddData(articlesList);
    }

    @Override
    public void showCollectSuccess() {
        mAdapter.setCollectState(clickPosition,true);
    }

    @Override
    public void showUnCollectSuccess() {
        mAdapter.setCollectState(clickPosition,false);
    }

    @Override
    public void collect(int position) {
        if (!articleBeanList.get(position).isCollect()){
            mPresenter.collectArticles(articleBeanList.get(position).getId());
        }else {
            mPresenter.unCollectArticles(articleBeanList.get(position).getId());
        }
    }

    private CommonAdapter.OnItemClickListener onItemClickListener = new CommonAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v,int position) {
            clickPosition = position;
            switch (v.getId()){
                case R.id.iv_common_rv_like:
                    if (SharedPreferencesUtils.getBoolean(context, PlayViewConstants.LOGIN_STATE,false)){
                        collect(position);
                    }else {
                        startActivity(new Intent(context, LoginActivity.class));
                        context.finish();
                    }
                    break;
                default:
                    ArticleActivity.startActivityForFragment(getActivity(),
                            PublicDetailFragment.this,
                            articleBeanList.get(position).getLink(),
                            articleBeanList.get(position).getTitle(),
                            articleBeanList.get(position).getId(),
                            articleBeanList.get(position).isCollect(),
                            PlayViewConstants.REQUEST_ARTICLE_ACTIVITY);

                    break;
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK){
            return;
        }
        ArticleBean article = articleBeanList.get(clickPosition);
        switch (requestCode){
            case PlayViewConstants.REQUEST_ARTICLE_ACTIVITY:
                boolean isCollect = data.getBooleanExtra(PlayViewConstants.ARTICLE_COLLECT, false);
                if (isCollect != article.isCollect()){
                    article.setCollect(isCollect);
                    mAdapter.notifyItemChanged(clickPosition);
                }
                break;
        }
    }

    public void getData(){
        Bundle bundle = getArguments();
        if (bundle != null) {
            mCid = bundle.getInt("PublicCid", -1);
        }
    }
    /*
     * Fragment 创建时传递数据 通过 fragment.setArguments(Bundle) 传递数据
     * */
    public static Fragment newInstance(int id){
        Bundle bundle = new Bundle();
        bundle.putInt("PublicCid", id);
        PublicDetailFragment fragment = new PublicDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    private void initRefreshView() {
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                LogUtil.i(TAG," onLoadmore ");
                mPageNum++;
                isRefresh = false;
                mPresenter.loadMorePublicArticle(mPageNum,mCid);
            }
        });

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if (NetworkUtil.isNetworkConnected(context)) {
                    LogUtil.i(TAG," onRefresh ");
                    mPageNum = 0;
                    isRefresh = true;
                    mPresenter.loadMorePublicArticle(0, mCid);
                }
            }
        });
    }
}
