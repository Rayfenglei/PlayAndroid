package com.example.ray.playviewandroid.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ray.playviewandroid.R;
import com.example.ray.playviewandroid.base.BaseActivity;
import com.example.ray.playviewandroid.bean.ArticleBean;
import com.example.ray.playviewandroid.bean.HotKeyBean;
import com.example.ray.playviewandroid.bean.HotWebBean;
import com.example.ray.playviewandroid.constants.PlayViewConstants;
import com.example.ray.playviewandroid.presenter.login.SearchPresenter;
import com.example.ray.playviewandroid.view.adapter.CommonAdapter;
import com.example.ray.playviewandroid.view.adapter.RecyclerOnScrollListener;
import com.example.ray.playviewandroid.view.interfaces.ISearchView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class SearchArticleActivity extends BaseActivity<ISearchView, SearchPresenter<ISearchView>> implements ISearchView {
    private static final String TAG = "SearchArticleActivity";
    private String mSearchKey;
    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;
    private CommonAdapter mAdapter;
    private List<ArticleBean> articleBeanList = new ArrayList<>();
    private int clickPosition;
    private boolean isCollect;
    private static int mPage = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_article);
        getIntentData();
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        mRecyclerView = findViewById(R.id.rv_search_article);
        mToolbar = findViewById(R.id.bar_search_article);

    }

    @Override
    public void initData() {
        mPresenter.doSearch(mSearchKey,mPage);
        mToolbar.setTitle(mSearchKey);
        setSupportActionBar(mToolbar);

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
        mRecyclerView.addOnScrollListener(new RecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                mAdapter.setLoadState(mAdapter.LOADING);
                mPage++;
                mPresenter.doSearch(mSearchKey,mPage);
            }
        });
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG," "+view.getId());
                Intent intent = new Intent();
                intent.putExtra(PlayViewConstants.ARTICLE_COLLECT,isCollect);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    @Override
    protected SearchPresenter<ISearchView> createPresenter() {
        return new SearchPresenter<>();
    }

    @Override
    public void showHotKey(List<HotKeyBean> keyList) {

    }

    @Override
    public void showHotWeb(List<HotWebBean> webList) {

    }

    @Override
    public void showHistory(List<ArticleBean> articlesList) {

    }

    @Override
    public void showSearcher(List<ArticleBean> articlesList) {
        articleBeanList.addAll(articlesList);
        mAdapter.setDataList(articlesList);
        mAdapter.setLoadState(mAdapter.LOADING_COMPLETE);
    }


    @Override
    public void onItemClick(View view) {

    }

    public static void startActivityForActivity(Activity activity, String key, int requestCode){
        WeakReference<Activity> rfActivity = new WeakReference<>(activity);
        Intent intent = new Intent(rfActivity.get(),SearchArticleActivity.class);
        intent.putExtra("key",key);
        activity.startActivityForResult(intent,requestCode);
    }

    private void getIntentData(){
        mSearchKey = getIntent().getStringExtra("key");
    }

    private CommonAdapter.OnItemClickListener onItemClickListener = new CommonAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v,int position) {
            clickPosition = position;
            switch (v.getId()){
                case R.id.iv_common_rv_like:
//                    if (SharedPreferencesUtils.getBoolean(context, PlayViewConstants.LOGIN_STATE,false)){
//                        collect(position);
//                    }else {
//                        startActivity(new Intent(context, LoginActivity.class));
//                        context.finish();
//                    }
                    break;
                default:
                    ArticleActivity.startActivityForActivity(
                            SearchArticleActivity.this,
                            articleBeanList.get(position).getLink(),
                            articleBeanList.get(position).getTitle(),
                            articleBeanList.get(position).getId(),
                            articleBeanList.get(position).isCollect(),
                            PlayViewConstants.REQUEST_ARTICLE_ACTIVITY);

                    break;
            }
        }
    };

}
