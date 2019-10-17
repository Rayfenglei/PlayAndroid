package com.example.ray.playviewandroid.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.example.ray.playviewandroid.IMyAidlInterface;
import com.example.ray.playviewandroid.IMyAidlListener;


public class AIDLService extends Service {
    private IMyAidlListener mListenerOne;
    private IMyAidlListener mListenerTwo;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    private IBinder iBinder = new IMyAidlInterface.Stub() {
        //方式一
        @Override
        public void methodOne(String aString, IBinder listener) throws RemoteException {
            mListenerOne = IMyAidlListener.Stub.asInterface(listener);
        }
        //方式二
        @Override
        public void methodTwo(String aString, IMyAidlListener listener) throws RemoteException {
            mListenerTwo = listener;
        }
    };
}
