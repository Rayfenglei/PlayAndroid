package com.example.ray.playviewandroid.base;

import java.lang.ref.WeakReference;

public class BasePresenter<PV> {

    /**
     * View层的引用
     * 使用弱引用，在View的onCreate时创建，View的destroy时释放
     * 作用是防止出现MVP引发的内存泄漏
      */
    protected WeakReference<PV> mViewRef;

    /**
     *  将View和Presenter绑定
     *  在Activity的onCreate时执行
     *  在Fragment的onCreateView时执行
     * @param view
     */
    public void attachToView(PV view) {
        mViewRef = new WeakReference<>(view);
    }

    /**
     * 将View和Presenter解绑
     * 在Activity的onDetroy时执行
     * 在Fragment的onDetroy时执行
     */
    public void deAttachFromView() {
        mViewRef.clear();
    }

    /**
     * 判断当钱Presenter是否持有View的实例
     * @return
     */
    protected boolean isViewAttached() {
        if(mViewRef != null && mViewRef.get() != null) {
            return true;
        }
        return false;
    }
}
