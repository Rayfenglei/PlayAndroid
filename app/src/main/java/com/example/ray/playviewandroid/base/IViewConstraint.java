package com.example.ray.playviewandroid.base;

/**
 * Activity和Fragment的公共逻辑接口
 * 约束页面的初始化方法，使View结构清晰
 * @use Activity或Fragment implements IViewConstraint
 */
public interface IViewConstraint {

    String ACTION_EXIT_APP = "ACTION_EXIT_APP";

    /**
     * UI显示方法(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)
     *
     * @must Activity-在子类onCreate方法内初始化View(setContentView)后调用；Fragment-在子类onCreateView方法内初始化View后调用
     */
    void initView();

    /**
     * Data数据方法(存在数据获取或处理代码，但不存在事件监听代码)
     *
     * @must Activity-在子类onCreate方法内初始化View(setContentView)后调用；Fragment-在子类onCreateView方法内初始化View后调用
     */
    void initData();

    /**
     * Event事件方法(只要存在事件监听代码就是)
     *
     * @must Activity-在子类onCreate方法内初始化View(setContentView)后调用；Fragment-在子类onCreateView方法内初始化View后调用
     */
    void initEvent();

}
