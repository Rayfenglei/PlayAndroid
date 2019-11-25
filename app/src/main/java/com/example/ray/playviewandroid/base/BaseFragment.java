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

    private boolean isInitData = false; //标志位,判断数据是否初始化
    private boolean isFragmentVisible = false; //标志位,判断fragment是否可见
    private boolean isViewCreated = false; //标志位,判断view已经加载完成 避免空指针操作
    private boolean isFirstVisible = true;


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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated =true;//此时view已经加载完成，设置其为true
        initView();
        initEvent();
        if (isFragmentVisible && isFirstVisible) {
            Log.e(TAG, "Adapter 默认展示的那个 Fragment ，或者隔 tab 选中的时候  requestData 推迟到 onCreateView 后 ");
            initData();

            isFirstVisible = false;
        }

    }

    /**
     * 懒加载方法
     */
    public void lazyInitData(){
        Log.i(TAG,"isInitData : "+isInitData+" isViewCreated :"+ isViewCreated +" isFragmentVisible : "+ isFragmentVisible);
        if(setFragmentTarget()){
            if(!isInitData && isFragmentVisible && isViewCreated){//如果数据还没有被加载过，并且fragment已经可见，view已经加载完成
                initData();//加载数据
                isInitData=true;//是否已经加载数据标志重新赋值为true
            }
        }else {
            if(!isInitData && isFragmentVisible && isViewCreated){//如果数据还没有被加载过，并且fragment已经可见，view已经加载完成

                initData();//加载数据
                isInitData=true;//是否已经加载数据标志重新赋值为true
            }else if (!isInitData && getParentFragment()==null && isViewCreated){

                initData();
                isInitData=true;
            }
        }

    }

    /**
     * 设置Fragment target，
     * 嵌套 true  ，单层 false
     *
     */
    protected abstract boolean setFragmentTarget();
    /**
     * 可见状态改变时调用
     * @param isVisibleToUser
     * */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //走这里分发可见状态情况有两种，1. 已缓存的 Fragment 被展示的时候 2. 当前 Fragment 由可见变成不可见的状态时
        // 对于默认 tab 和 间隔 checked tab 需要等到 isViewCreated = true 后才可以通过此通知用户可见，
        // 这种情况下第一次可见不是在这里通知 因为 isViewCreated = false 成立，可见状态在 onActivityCreated 中分发
        // 对于非默认 tab，View 创建完成  isViewCreated =  true 成立，走这里分发可见状态，mIsFirstVisible 此时还为 false  所以第一次可见状态也将通过这里分发
        this.isFragmentVisible =isVisibleToUser;//将fragment是否可见值赋给标志isVisibleToUser
        lazyInitData();//懒加载
    }

    /**
     * fragment生命周期中onViewCreated之后的方法 在这里调用一次懒加载 避免第一次可见不加载数据
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lazyInitData();//懒加载
    }

    /**
     * Fragment显示隐藏监听
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            lazyInitData();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewCreated = false;
        isInitData = false;
        isFirstVisible = true;
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
