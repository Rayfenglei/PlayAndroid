package com.example.ray.playviewandroid.cache;

import android.text.TextUtils;

import com.example.ray.playviewandroid.PlayApplication;
import com.example.ray.playviewandroid.bean.BaseResponse;
import com.example.ray.playviewandroid.constants.PlayViewConstants;
import com.example.ray.playviewandroid.util.LogUtil;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DiskCache implements ICache {
    private static final String TAG = "CacheData";
    private String CACHE_PATH = PlayApplication.getContext().getPackageResourcePath()+"playView/";

    @Override
    public <T extends BaseResponse> Observable<T> get(final String key) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {

                String filename = CACHE_PATH + key;
                String result = readTextFromSDcard(filename);
                LogUtil.i(TAG, "load from disk: " + result);
                if (TextUtils.isEmpty(result)) {
                    emitter.onComplete();
                } else {
                    T t = (T) new Gson().fromJson(result,BaseResponse.class);
                    emitter.onNext(t);
                }

            }
        });
    }

    @Override
    public <T extends BaseResponse> void put(final String key, final T t) {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                LogUtil.i(TAG, "save to disk: " + key);

                String filename = CACHE_PATH + key;
                String result = t.toString();
                saveText2Sdcard(filename, result);
                emitter.onNext(t);
                emitter.onComplete();

            }
        }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe();
    }

    private void saveText2Sdcard(String fileName, String text) {

        File file = new File(fileName);
//        File parentFile = file.getParentFile();
//        if (!parentFile.exists()) {
//            parentFile.mkdirs();
//        }
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(text);
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            file.delete();
            LogUtil.i(TAG,"file  save failed");
        }
        LogUtil.i(TAG,"file  save success");
    }

    private String readTextFromSDcard(String fileName) {

        File file = new File(fileName);
        if (!file.exists()) {
            return null;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            int availableLength = fileInputStream.available();
            byte[] buffer = new byte[availableLength];
            fileInputStream.read(buffer);
            fileInputStream.close();

            return new String(buffer, "UTF-8");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
