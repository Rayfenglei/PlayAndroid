package com.example.ray.playviewandroid.view.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.example.ray.playviewandroid.R;
import com.example.ray.playviewandroid.base.BaseActivity;
import com.example.ray.playviewandroid.constants.PlayViewConstants;
import com.example.ray.playviewandroid.presenter.login.LoginRegisterPresenter;
import com.example.ray.playviewandroid.util.SharedPreferencesUtils;
import com.example.ray.playviewandroid.view.fragment.login.LoginFragment;
import com.example.ray.playviewandroid.view.fragment.login.RegisterFragment;
import com.example.ray.playviewandroid.view.interfaces.ILoginRegisterView;


public class LoginActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (SharedPreferencesUtils.getBoolean(context,PlayViewConstants.LOGIN_STATE,false)){
            toMainActivity();
        }
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        replaceFragment(new LoginFragment());
    }

    @Override
    public void initEvent() {

    }

    @Override
    protected LoginRegisterPresenter<ILoginRegisterView> createPresenter() {
        return null;
    }
    /**
     *
     */
    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_login_register, fragment).commit();
    }
    /**
    * 跳转注册页面
    *
    * */
    public void toRegisterFragment(){
        replaceFragment(new RegisterFragment());
    }
    /*
    * 进入App
    *
    * */
    public void toMainActivity(){
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
