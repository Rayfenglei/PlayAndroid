package com.example.ray.playviewandroid.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment<PV, PT extends BasePresenter<PV>> extends Fragment implements IViewConstraint {
    private static final String TAG = "BaseFragment";
    /**
     * 表示层的引用
     */
    public PT mPresenter;
    /**
     * 添加该Fragment的Activity
     *
     * @warn 不能在子类中创建
     */
    protected BaseActivity context = null;
    /**
     * 该Fragment全局视图
     *
     * @must 非abstract子类的onCreateView中return view;
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
     * 添加这个Fragment视图的布局
     *
     * @warn 不能在子类中创建
     */
    @Nullable
    protected ViewGroup container = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = (BaseActivity) getActivity();
        this.inflater = inflater;
        this.container = container;
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachToView((PV) this);
        }
        return view;
    }
    protected abstract PT createPresenter();
    /**
     * 设置界面布局
     *
     * @param layoutResID
     * @warn 最多调用一次
     * @use 在onCreateView后调用
     */
    public void setContentView(int layoutResID) {
        setContentView(inflater.inflate(layoutResID, container, false));
    }

    /**
     * 设置界面布局
     *
     * @param v
     * @warn 最多调用一次
     * @use 在onCreateView后调用
     */
    public void setContentView(View v) {
        setContentView(v, null);
    }

    /**
     * 设置界面布局
     *
     * @param v
     * @param params
     * @warn 最多调用一次
     * @use 在onCreateView后调用
     */
    public void setContentView(View v, ViewGroup.LayoutParams params) {
        view = v;
    }

    //运行线程<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**
     * 在UI线程中运行，建议用这个方法代替runOnUiThread
     *
     * @param action
     */
    public final void runUiThread(Runnable action) {
        if (context == null) {
            Log.w(TAG, "runUiThread  context == null >> return;");
            return;
        }
        context.runOnUiThread(action);
    }

    //运行线程>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    /**
     * 可用于 打开activity以及activity之间的通讯（传值）等；一些通讯相关基本操作（打电话、发短信等）
     */
    protected Intent intent = null;

    public Intent getIntent() {
        return context.getIntent();
    }

    //启动Activity<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**
     * 打开新的Activity，向左滑入效果
     *
     * @param intent
     */
    public void toActivity(Intent intent) {
        toActivity(intent, true);
    }

    /**
     * 打开新的Activity
     *
     * @param intent
     * @param showAnimation
     */
    public void toActivity(Intent intent, boolean showAnimation) {
        toActivity(intent, -1, showAnimation);
    }

    /**
     * 打开新的Activity，向左滑入效果
     *
     * @param intent
     * @param requestCode
     */
    public void toActivity(Intent intent, int requestCode) {
        toActivity(intent, requestCode, true);
    }

    /**
     * 打开新的Activity
     *
     * @param intent
     * @param requestCode
     * @param showAnimation
     */
    public void toActivity(final Intent intent, final int requestCode, final boolean showAnimation) {
        runUiThread(new Runnable() {
            @Override
            public void run() {
                if (intent == null) {
                    return;
                }
                //fragment中使用context.startActivity会导致在fragment中不能正常接收onActivityResult
                if (requestCode < 0) {
                    startActivity(intent);
                } else {
                    startActivityForResult(intent, requestCode);
                }
                if (showAnimation) {
                    //context.overridePendingTransition(R.anim.right_push_in, R.anim.hold);
                } else {
                    //context.overridePendingTransition(R.anim.null_anim, R.anim.null_anim);
                }
            }
        });
    }
    //启动Activity>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    /**
     * 销毁并回收内存
     *
     * @warn 子类如果要使用这个方法内用到的变量，应重写onDestroy方法并在super.onDestroy();前操作
     */
    @Override
    public void onDestroy() {
        if (view != null) {
            try {
                view.destroyDrawingCache();
            } catch (Exception e) {
                Log.w(TAG, "onDestroy  try { view.destroyDrawingCache();" +
                        " >> } catch (Exception e) {\n" + e.getMessage());
            }
        }
        super.onDestroy();
        view = null;
        inflater = null;
        container = null;
        intent = null;
        context = null;
    }

    /*
     * show toast
     * @str 发出的信息
     * */
    public void showToast(String str){
        context.showToast(str);
    }
    public void showToast(int strId){
        context.showToast(strId);
    }
}
