package com.example.ray.playviewandroid.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

public class NetBroadcastReceiver extends BroadcastReceiver {
    private NetChangeListener netChangeListener;
    @Override
    public void onReceive(Context context, Intent intent) {
        // 如果相等的话就说明网络状态发生了变化
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            boolean netWorkState = NetworkUtil.isNetworkConnected(context);
            if (netChangeListener != null){
                netChangeListener.onNetChange(netWorkState);
            }
        }
    }

    // 网络状态变化接口
    public interface NetChangeListener {
        void onNetChange(boolean netWorkState);
    }

    public void setNetChangeListener(NetChangeListener netChangeListener){
        this.netChangeListener = netChangeListener;
    }
}
