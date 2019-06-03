package com.example.ray.playviewandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.example.ray.playviewandroid.presenter.login.LoginRegisterPresenter;
import com.example.ray.playviewandroid.util.PermissionsUtil;
import com.example.ray.playviewandroid.view.activity.LoginActivity;
import com.example.ray.playviewandroid.view.activity.MainActivity;


public class SplashActivity extends AppCompatActivity {
    private LoginRegisterPresenter presenter = new LoginRegisterPresenter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
    }

    private void initView(){
        if (PermissionsUtil.isPermissionGranted(this)){

            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }else {
            PermissionsUtil.requestPermissions(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PermissionsUtil.REQUEST_CODE_PERMISSION){
            if (PermissionsUtil.isPermissionGranted(this)){
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }else {
                finish();
            }
        }

    }
}
