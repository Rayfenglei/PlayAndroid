package com.example.ray.playviewandroid.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import com.example.ray.playviewandroid.R;
import com.example.ray.playviewandroid.base.BaseActivity;
import com.example.ray.playviewandroid.constants.PlayViewConstants;
import com.example.ray.playviewandroid.presenter.login.ArticlePresenter;
import com.example.ray.playviewandroid.util.CommonUtil;
import com.example.ray.playviewandroid.util.LogUtil;
import com.example.ray.playviewandroid.util.SharedPreferencesUtils;
import com.example.ray.playviewandroid.view.interfaces.IArticleView;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebConfig;
import com.just.agentweb.DefaultWebClient;

import java.lang.ref.WeakReference;


public class ArticleActivity extends BaseActivity<IArticleView, ArticlePresenter<IArticleView>> implements IArticleView{
    private static final String TAG = "ArticleActivity";
    private String url;
    private String title;
    private int id;
    private boolean isCollect;

    private Toolbar mToolbar;
    private FrameLayout mLayout;
    private AgentWeb mAgentWeb;
    private MenuItem mCollectItem;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        mToolbar = findViewById(R.id.bar_article);
        mLayout = findViewById(R.id.layout_article);

    }

    @Override
    public void initData() {
        getIntentData();
        mToolbar.setTitle(title);
        setSupportActionBar(mToolbar);

        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mLayout,new LinearLayout.LayoutParams(-1,-1))
                .useDefaultIndicator(getResources().getColor(R.color.tab_text_selected))
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
                .createAgentWeb()
                .ready()
                .go(url);
    }

    @Override
    public void initEvent() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
    protected ArticlePresenter<IArticleView> createPresenter() {
        return new ArticlePresenter<>();
    }

    /*
    * 替换ActionBar 菜单
    * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.article_menu,menu);
        mCollectItem = menu.findItem(R.id.item_collect);
        if (isCollect){
            mCollectItem.setTitle(R.string.collect_done);
        }else {
            mCollectItem.setTitle(R.string.collect);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_share:
                CommonUtil.shareInfo(context,title+"\n"+url);
                break;
            case R.id.item_collect:
                doCollect();
                break;
            case R.id.item_browser:
                CommonUtil.openWebSite(context,url);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent = new Intent();
        intent.putExtra(PlayViewConstants.ARTICLE_COLLECT,isCollect);
        setResult(RESULT_OK,intent);
        finish();
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        //清空缓存
        AgentWebConfig.clearDiskCache(context);
        super.onDestroy();
    }

    @Override
    public void showCollectSuccess() {
        CommonUtil.showShortToast(context,R.string.collect_success);
        isCollect = true;
        mCollectItem.setTitle(R.string.collect_done);
    }

    @Override
    public void showUnCollectSuccess() {
        CommonUtil.showShortToast(context,R.string.collect_cancel);
        isCollect = false;
        mCollectItem.setTitle(R.string.collect);
    }

    private void doCollect(){
        if (!SharedPreferencesUtils.getBoolean(context,PlayViewConstants.LOGIN_STATE,false)) {
            startActivity(new Intent(this, LoginActivity.class));
            showToast(getString(R.string.first_login));
        }
        if (isCollect){
            mPresenter.unCollectArticles(id);
        }else {
            mPresenter.collectArticles(id);
        }
    }
    /*
    * 从fragment 打开 activity
    *
    * @param activity
    * @param fragment
    * */
    public static void startActivityForFragment(Activity activity,Fragment fragment, String url, String title, int id, boolean isCollect, int requestCode){
        WeakReference<Activity> rfActivity = new WeakReference<>(activity);
        Intent intent = new Intent(rfActivity.get(),ArticleActivity.class);
        intent.putExtra("url",url);
        intent.putExtra("title",title);
        intent.putExtra("id",id);
        intent.putExtra("isCollect",isCollect);
        fragment.startActivityForResult(intent,requestCode);
    }

    /*
     * 从activity 打开 activity
     *
     * @param activity
     * @param fragment
     * */
    public static void startActivityForActivity(Activity activity, String url, String title, int id, boolean isCollect, int requestCode){
        WeakReference<Activity> rfActivity = new WeakReference<>(activity);
        Intent intent = new Intent(rfActivity.get(),ArticleActivity.class);
        intent.putExtra("url",url);
        intent.putExtra("title",title);
        intent.putExtra("id",id);
        intent.putExtra("isCollect",isCollect);
        activity.startActivityForResult(intent,requestCode);
    }

    private void getIntentData(){
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        id = getIntent().getIntExtra("id",-1);
        isCollect = getIntent().getBooleanExtra("isCollect",false);
    }

}
