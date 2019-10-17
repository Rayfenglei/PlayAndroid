// IMyAidlInterface.aidl
package com.example.ray.playviewandroid;

// Declare any non-default types here with import statements
//方式二
import com.example.ray.playviewandroid.IMyAidlListener;

interface IMyAidlInterface {
    //方式一
    void methodOne( String aString,IBinder listener);
    //方式二
    void methodTwo( String aString,IMyAidlListener listener);
}
