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
import com.example.ray.playviewandroid.bean.TabBean;
import com.example.ray.playviewandroid.constants.PlayViewConstants;
import com.example.ray.playviewandroid.presenter.login.ProjectPresenter;
import com.example.ray.playviewandroid.util.SharedPreferencesUtils;
import com.example.ray.playviewandroid.view.activity.ArticleActivity;
import com.example.ray.playviewandroid.view.activity.LoginActivity;
import com.example.ray.playviewandroid.view.adapter.CommonAdapter;
import com.example.ray.playviewandroid.view.adapter.RecyclerOnScrollListener;
import com.example.ray.playviewandroid.view.interfaces.IProjectView;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class ProjectDetailFragment extends BaseFragment<IProjectView, ProjectPresenter<IProjectView>> implements IProjectView{
    private static final String TAG = "ProjectDetailFragment";
    private int id;
    private RecyclerView mRecyclerView;
    private CommonAdapter mAdapter;
    private List<ArticleBean> articleBeanList = new ArrayList<>();
    private int clickPosition;
    private static int LOAD_PAGE = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_project_detail);
        initView();
        initData();
        initEvent();
        return view;
    }

    @Override
    protected ProjectPresenter<IProjectView> createPresenter() {
        return new ProjectPresenter<>();
    }

    @Override
    public void initView() {
        mRecyclerView = view.findViewById(R.id.rv_project_detail);
    }

    @Override
    public void initData() {
        getData();
        mPresenter.loadArticle(LOAD_PAGE,id);
        mAdapter = new CommonAdapter(context,R.layout.recyeler_item_list);
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
        mRecyclerView.addOnScrollListener(new RecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                mAdapter.setLoadState(mAdapter.LOADING);
                LOAD_PAGE++;
                mPresenter.loadArticle(LOAD_PAGE,id);
            }
        });
    }

    @Override
    public void showAriticle(List<ArticleBean> articlesList) {
        articleBeanList.addAll(articlesList);
        mAdapter.setDataList(articlesList);
        mAdapter.setLoadState(mAdapter.LOADING_COMPLETE);
    }

    @Override
    public void showProjectTab(List<TabBean> tabBeans) {

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
                            ProjectDetailFragment.this,
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
            id = bundle.getInt("ArticleCid", -1);
        }
    }
    /*
     * Fragment 创建时传递数据 通过 fragment.setArguments(Bundle) 传递数据
     * */
    public static Fragment newInstance(int cid){
        Bundle bundle = new Bundle();
        bundle.putInt("ArticleCid", cid);
        ProjectDetailFragment systemDetailFragment = new ProjectDetailFragment();
        systemDetailFragment.setArguments(bundle);
        return systemDetailFragment;
    }

}
