package com.example.ray.playviewandroid.view.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.example.ray.playviewandroid.R;
import com.example.ray.playviewandroid.base.BaseActivity;
import com.example.ray.playviewandroid.bean.ArticleBean;
import com.example.ray.playviewandroid.bean.HotKeyBean;
import com.example.ray.playviewandroid.bean.HotWebBean;
import com.example.ray.playviewandroid.constants.PlayViewConstants;
import com.example.ray.playviewandroid.presenter.login.SearchPresenter;

import com.example.ray.playviewandroid.view.defineview.FlowLayout;
import com.example.ray.playviewandroid.view.interfaces.ISearchView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class SearchActivity extends BaseActivity<ISearchView, SearchPresenter<ISearchView>> implements ISearchView, View.OnClickListener {
    private static final String TAG = "SearchActivity";

    private FlowLayout layoutHotKey,layoutHotWeb;
    private List<HotKeyBean> hotKeyList = new ArrayList<>();
    private List<HotWebBean> hotWebList = new ArrayList<>();
    private EditText etSearch;
    private ImageView imSearch;
    private TextView tvDoSearch;
    private ImageView igBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        initData();
        initEvent();
    }


    @Override
    protected SearchPresenter<ISearchView> createPresenter() {
        return new SearchPresenter<>();
    }

    @Override
    public void initView() {
        layoutHotKey = findViewById(R.id.flowlayout_hotkey);
        layoutHotWeb = findViewById(R.id.flowlayout_hotweb);
        tvDoSearch = findViewById(R.id.tv_search);
        igBack = findViewById(R.id.im_searcher_back);
        etSearch = findViewById(R.id.et_searcher);
        imSearch = findViewById(R.id.im_searcher);
    }

    @Override
    public void initData() {
        mPresenter.getHotKey();
        mPresenter.getHotWeb();
    }

    @Override
    public void initEvent() {
        tvDoSearch.setOnClickListener(this);
        igBack.setOnClickListener(this);
    }

    @Override
    public void showHotKey(List<HotKeyBean> keyList) {
        hotKeyList = keyList;

        List<View> views = new ArrayList<>();
        for (int i = 0 ; i < hotKeyList.size(); i++){
            TextView textView = new TextView(this);
            textView.setText(hotKeyList.get(i).getName());
            textView.setId(R.id.hotkey_id);
            ViewGroup.MarginLayoutParams  layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT,
                    ViewGroup.MarginLayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(5,5,5,5);
            textView.setLayoutParams(layoutParams);
            textView.setBackgroundColor(mPresenter.getRandomColor());
            textView.setOnClickListener(this);
            views.add(textView);
        }
        layoutHotKey.setChildren(views);

    }

    @Override
    public void showHotWeb(List<HotWebBean> webList) {
        hotWebList = webList;
        Random random = new Random();
        List<View> views = new ArrayList<>();

        for (int i = 0 ; i < 10; i++){
            TextView textView = new TextView(this);
            textView.setId(R.id.hotweb_id);
            textView.setText(hotWebList.get(random.nextInt(hotWebList.size())).getName());
            ViewGroup.MarginLayoutParams  layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT,
                    ViewGroup.MarginLayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(5,5,5,5);
            textView.setLayoutParams(layoutParams);
            textView.setBackgroundColor(mPresenter.getRandomColor());
            textView.setOnClickListener(this);
            views.add(textView);
        }
        layoutHotWeb.setChildren(views);
    }

    @Override
    public void showHistory(List<ArticleBean> articlesList) {

    }

    @Override
    public void showSearcher(List<ArticleBean> articlesList) {

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.im_searcher_back:
                finish();
                break;
            case R.id.tv_search:
                toSearchResults(etSearch.getText().toString());
                break;
            case R.id.hotkey_id:
                toSearchResults(((TextView) view).getText().toString());
                break;
            case R.id.hotweb_id:
                showToast(((TextView) view).getText().toString());
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(View view) {

    }
    private void toSearchResults(String key){
        SearchArticleActivity.startActivityForActivity(this,key,1);
    }
}
