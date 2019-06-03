package com.example.ray.playviewandroid.base;

import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.ray.playviewandroid.util.ActivityUtil;
import com.example.ray.playviewandroid.util.NetBroadcastReceiver;


public abstract class BaseActivity<PV, PT extends BasePresenter<PV>> extends AppCompatActivity implements NetBroadcastReceiver.NetChangeListener, IViewConstraint {

    /**
     * 表示层的引用
     */
    public PT mPresenter;
    /**
     * 该Activity实例，命名为context是因为大部分方法都只需要context，写成context使用更方便
     *
     * @warn 不能在子类中创建
     */
    protected BaseActivity context = null;
    /**
     * 该Activity的界面，即contentView
     *
     * @warn 不能在子类中创建
     */
    protected View view = null;
    /**
     * 布局解释器
     *
     * @warn 不能在子类中创建
     */
    protected LayoutInflater inflater = null;
    /**
     * Fragment管理器
     *
     * @warn 不能在子类中创建
     */
    protected FragmentManager fragmentManager = null;
    protected BaseFragment fragment = null;



    protected NetBroadcastReceiver netBroadcastReceiver;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachToView((PV) this);
        }

        context = this;
        fragmentManager = getSupportFragmentManager();

        inflater = getLayoutInflater();

        //注册网络状态接收器
        registerBroadcastReceiver();
        // 添加到Activity工具类
        ActivityUtil.getInstance().addActivity(this);

        // 执行初始化方法
        initView();


    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        // 状态栏沉浸，4.4+生效 <<<<<<<<<<<<<<<<<
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.color_33cc99);//状态背景色，可传drawable资源*/
        // 状态栏沉浸，4.4+生效 >>>>>>>>>>>>>>>>>

        //tvBaseTitle = findView(R.id.tvBaseTitle);//绑定默认标题TextView
    }

    protected abstract PT createPresenter();

    /*
    * 或者防止系统字体大小影响应用字体大小
    * ConstantUtil.TEXTVIEWSIZE是设值的一个静态常量，
    * 当TEXTVIEWSIZE=1的时候，会显示系统标准字体大小，这个时候即使系统修改了字体大小，也不会影响到应用程序的字体大小。
    * 如果想要字体放大，设值其值>1即可。如果想要字体缩小，设值其值<1即可。
    * */
    @Override
    protected void onResume() {
        super.onResume();
        Resources resources = this.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.fontScale = 1;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    @Override
    protected void onDestroy() {
        // Activity销毁时，提示系统回收
        // 移除Activity
        ActivityUtil.getInstance().removeActivity(this);

        if (netBroadcastReceiver!=null){
            unregisterReceiver(netBroadcastReceiver);
        }
        inflater = null;
        fragmentManager = null;
        view = null;
        context = null;

        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 点击手机上的返回键，返回上一层
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 移除Activity
            ActivityUtil.getInstance().removeActivity(this);
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }
    /*
    * 网络状态广播接受注册
    * */
    private void registerBroadcastReceiver() {
        //注册广播
        if (netBroadcastReceiver == null) {
            netBroadcastReceiver = new NetBroadcastReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(netBroadcastReceiver, filter);
            //设置监听
            netBroadcastReceiver.setNetChangeListener(this);
        }
    }
    /*
    * 重写方法 状态改变方法
    * */
    @Override
    public void onNetChange(boolean netWorkState) {
        if (netWorkState){
            Log.i("onNetChange","is connect");
        }else {
            Log.i("onNetChange","is not connect");
        }
    }
    /* show toast
     * @str 发出的信息
     * */
    public void showToast(String str){
        Toast.makeText(this ,str, Toast.LENGTH_SHORT).show();
    }
    public void showToast(int strid){
        Toast.makeText(this ,strid, Toast.LENGTH_SHORT).show();
    }

}
