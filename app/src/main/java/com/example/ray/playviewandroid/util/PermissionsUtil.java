package com.example.ray.playviewandroid.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;


import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

public class PermissionsUtil {
    //权限
    public static final  String[] COMMON_PERMISSION = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};
    public static final int REQUEST_CODE_PERMISSION = 5555;

    //申请权限
    public static void requestPermissions(Activity activity) {
        List<String> ps = new ArrayList<>();
        for (int i = 0; i < COMMON_PERMISSION.length; i++) {
            //判断权限是否通过
            if (ActivityCompat.checkSelfPermission(activity, COMMON_PERMISSION[i]) != PackageManager.PERMISSION_GRANTED) {
                ps.add(COMMON_PERMISSION[i]);
            }
        }
        //把未通过的权限添加到申请列表
        if (!ps.isEmpty()) {
            String[] p = new String[ps.size()];
            for (int i = 0; i < ps.size(); i++) {
                p[i] = ps.get(i);
            }
            //申请权限
            ActivityCompat.requestPermissions(activity, p, REQUEST_CODE_PERMISSION);
        }
    }


    //判断权限是否全部通过
    public static boolean isPermissionGranted(Activity activity) {
        boolean granted = true;
        for (int i = 0; i < COMMON_PERMISSION.length; i++) {

            if (ActivityCompat.checkSelfPermission(activity, COMMON_PERMISSION[i]) != PackageManager.PERMISSION_GRANTED) {
                granted = false;
                break;
            }
        }
        return granted;
    }
}
