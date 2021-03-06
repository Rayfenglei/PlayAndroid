package com.example.ray.playviewandroid.view.fragment.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ray.playviewandroid.R;
import com.example.ray.playviewandroid.base.BaseFragment;
import com.example.ray.playviewandroid.bean.ArticleBean;
import com.example.ray.playviewandroid.bean.BannerDataBean;
import com.example.ray.playviewandroid.constants.PlayViewConstants;
import com.example.ray.playviewandroid.presenter.login.HomePresenter;
import com.example.ray.playviewandroid.util.AppUtil;
import com.example.ray.playviewandroid.util.LogUtil;
import com.example.ray.playviewandroid.util.NetworkUtil;
import com.example.ray.playviewandroid.util.SharedPreferencesUtils;
import com.example.ray.playviewandroid.view.activity.ArticleActivity;
import com.example.ray.playviewandroid.view.activity.LoginActivity;
import com.example.ray.playviewandroid.view.activity.MainActivity;
import com.example.ray.playviewandroid.view.activity.SearchActivity;
import com.example.ray.playviewandroid.view.adapter.HomeAdapter;
import com.example.ray.playviewandroid.view.adapter.RecyclerOnScrollListener;
import com.example.ray.playviewandroid.view.interfaces.IHomeView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class HomeFragment extends BaseFragment<IHomeView, HomePresenter<IHomeView>> implements IHomeView, View.OnClickListener {

    private static final String TAG = "HomeFragment";
    private static int LOAD_PAGE;
    private RecyclerView mRecyclerView;
    private HomeAdapter mHomeAdapter;
    private FloatingActionButton btfloat;
    private List<ArticleBean> articleBeanList;
    private int clickPosition;
    private EditText etSearchBar;
    private ImageView imSearchBar;
    private SmartRefreshLayout mRefreshLayout;
    private int mPageNum = 1;//首页文章页数
    private boolean isRefresh = false; //是否在上拉刷新

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG, "onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_home);
        LOAD_PAGE = 0;
        Log.i(TAG, "onCreateView");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        initEvent();
        Log.i(TAG, "onResume");
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach");
    }

    @Override
    protected boolean setFragmentTarget() {
        return false;
    }

    @Override
    public void initView() {
        mRecyclerView = view.findViewById(R.id.rv_home);
        btfloat = view.findViewById(R.id.bt_home_float);
        mRefreshLayout = view.findViewById(R.id.refreshlayout_main);
        etSearchBar = view.findViewById(R.id.et_searcher_bar);
        imSearchBar = view.findViewById(R.id.im_searcher_bar);
    }

    @Override
    public void initData() {
        articleBeanList = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mHomeAdapter = new HomeAdapter(context, getActivity());
        mHomeAdapter.setOnItemClickListener1(onItemClickListener);
        mRecyclerView.setAdapter(mHomeAdapter);
        mPresenter.loadArticles(LOAD_PAGE);
        mPresenter.loadBanner();
    }

    @Override
    public void initEvent() {
        btfloat.setOnClickListener(this);
        etSearchBar.setOnClickListener(this);

        /*
         * 防止数据没有加载 banner下边出现 分割线
         * */
        DividerItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.recycler_divider));
        mRecyclerView.addItemDecoration(itemDecoration);
        initRefreshView();
//        mRecyclerView.addOnScrollListener(new RecyclerOnScrollListener() {
//            @Override
//            public void onLoadMore() {
//                mHomeAdapter.setLoadState(mHomeAdapter.LOADING);
//                LOAD_PAGE++;
//                mPresenter.loadMoreArticles(LOAD_PAGE);
//            }
//        });
    }

    @Override
    protected HomePresenter<IHomeView> createPresenter() {
        return new HomePresenter<>();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_home_float:
                backTop();
                break;
            case R.id.et_searcher_bar:
                toSearchActivity();
                break;
        }
    }

    @Override
    public void showBannerData(List<BannerDataBean> bannerDataList) {
        mHomeAdapter.setBannerData(bannerDataList);
        LogUtil.i(TAG, " showBannerData size " + bannerDataList.size());
    }

    @Override
    public void showArticles(List<ArticleBean> articleList) {
        articleBeanList.addAll(articleList);
        mHomeAdapter.setNewData(articleList);
        LogUtil.i(TAG, " showArticles size " + articleList.size());
    }

    @Override
    public void showMoreArticles(List<ArticleBean> articleList) {
        if (isRefresh) {
            if (!AppUtil.isEmptyList(articleBeanList)) {
                articleBeanList.clear();
            }
            mRefreshLayout.finishRefresh();
        } else {
            if (AppUtil.isEmptyList(articleList)) {
                mRefreshLayout.finishLoadmoreWithNoMoreData();
            } else {
                mRefreshLayout.finishLoadmore(1000);
            }
        }
        articleBeanList.addAll(articleList);
        mHomeAdapter.onAddData(articleList);
//        mHomeAdapter.setLoadState(mHomeAdapter.LOADING_COMPLETE);
        LogUtil.i(TAG, " showMoreArticles size " + articleList.size());
    }

    @Override
    public void showCollectSuccess() {
        mHomeAdapter.setCollectState(clickPosition, true);
    }

    @Override
    public void showUnCollectSuccess() {
        mHomeAdapter.setCollectState(clickPosition, false);
    }

    @Override
    public void autoRefresh() {

    }

    @Override
    public void collect(int position) {
        if (!articleBeanList.get(position).isCollect()) {
            mPresenter.collectArticles(articleBeanList.get(position).getId());
        } else {
            mPresenter.unCollectArticles(articleBeanList.get(position).getId());
        }
    }

    @Override
    public void backTop() {
        mRecyclerView.scrollToPosition(0);
    }

    /**
     * item＋item里的控件点击监听事件
     */
    private HomeAdapter.OnItemClickListener1 onItemClickListener = new HomeAdapter.OnItemClickListener1() {
        //viewName可区分item及item内部控件
        @Override
        public void onItemClick(View v, HomeAdapter.ViewName viewName, int position) {
            clickPosition = position;
            switch (v.getId()) {
                case R.id.iv_common_rv_like:
                    if (SharedPreferencesUtils.getBoolean(context, PlayViewConstants.LOGIN_STATE, false)) {
                        collect(position);
                    } else {
                        startActivity(new Intent(context, LoginActivity.class));
                        context.finish();
                    }
                    break;
                default:
                    ArticleActivity.startActivityForFragment(getActivity(),
                            HomeFragment.this,
                            articleBeanList.get(position).getLink(),
                            articleBeanList.get(position).getTitle(),
                            articleBeanList.get(position).getId(),
                            articleBeanList.get(position).isCollect(),
                            PlayViewConstants.REQUEST_ARTICLE_ACTIVITY);

                    break;
            }
        }

        @Override
        public void onItemLongClick(View v) {

        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtil.i(TAG, " " + data.getBooleanExtra(PlayViewConstants.ARTICLE_COLLECT, false));
        if (resultCode != RESULT_OK) {
            return;
        }

        ArticleBean article = articleBeanList.get(clickPosition);
        switch (requestCode) {
            case PlayViewConstants.REQUEST_ARTICLE_ACTIVITY:
                boolean isCollect = data.getBooleanExtra(PlayViewConstants.ARTICLE_COLLECT, false);
                if (isCollect != article.isCollect()) {
                    article.setCollect(isCollect);
                    mHomeAdapter.notifyItemChanged(clickPosition + 1);
                }
                break;
        }
    }

    private void toSearchActivity() {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        Pair<View, String> etPair = new Pair<>((View) etSearchBar, getString(R.string.trans_edit));
        Pair<View, String> imPair = new Pair<>((View) imSearchBar, getString(R.string.trans_image));
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),etPair,imPair);
        startActivity(intent, options.toBundle());
    }

    /*
     * 下拉刷新 setOnRefreshListener
     * 下拉加载 setOnLoadmoreListener
     * */
    private void initRefreshView() {
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPageNum++;
                isRefresh = false;
                mPresenter.loadMoreArticles(mPageNum);
            }
        });

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if (!NetworkUtil.isNetworkConnected(context)) {
                    refreshlayout.finishRefresh();
                    AppUtil.toastShow(context, "网络不可用");
                } else {
                    mPageNum = 1;
                    isRefresh = true;
                    mPresenter.loadMoreArticles(mPageNum);
                }
            }
        });
    }
}
