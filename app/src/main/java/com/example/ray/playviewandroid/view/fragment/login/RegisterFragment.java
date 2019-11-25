package com.example.ray.playviewandroid.view.fragment.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.ray.playviewandroid.R;
import com.example.ray.playviewandroid.base.BaseFragment;
import com.example.ray.playviewandroid.presenter.login.LoginRegisterPresenter;
import com.example.ray.playviewandroid.view.activity.LoginActivity;
import com.example.ray.playviewandroid.view.fragment.login.LoginFragment;
import com.example.ray.playviewandroid.view.interfaces.ILoginRegisterView;

public class RegisterFragment extends BaseFragment<ILoginRegisterView, LoginRegisterPresenter<ILoginRegisterView>> implements ILoginRegisterView,View.OnClickListener {
    private EditText etRgUser,etRgPwd,etRgRePwd;
    private Button btRegister,btBack;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_register);
        return view;
    }

    @Override
    public void initView() {
        etRgUser = view.findViewById(R.id.et_fg_username);
        etRgPwd = view.findViewById(R.id.et_fg_password);
        etRgRePwd = view.findViewById(R.id.et_fg_repassword);
        btRegister = view.findViewById(R.id.bt_fg_register);
        btBack = view.findViewById(R.id.bt_fg_back);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        btRegister.setOnClickListener(this);
        btBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_fg_register:
                mPresenter.doLogin(etRgUser.getText().toString(),etRgPwd.getText().toString(),etRgRePwd.getText().toString());
                break;
            case R.id.bt_fg_back:
                ((LoginActivity) getActivity()).replaceFragment(new LoginFragment());
                break;
        }
    }

    @Override
    protected LoginRegisterPresenter<ILoginRegisterView> createPresenter() {
        return new LoginRegisterPresenter<>();
    }

    @Override
    public void onSuccess() {
        showToast(R.string.registersuccess);
        ((LoginActivity) getActivity()).replaceFragment(new LoginFragment());
    }

    @Override
    public void onFailed(String error) {
        showToast(error);
    }
    @Override
    protected boolean setFragmentTarget() {
        return false;
    }
}
