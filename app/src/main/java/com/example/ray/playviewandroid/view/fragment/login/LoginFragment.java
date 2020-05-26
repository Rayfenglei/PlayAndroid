package com.example.ray.playviewandroid.view.fragment.login;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.ray.playviewandroid.R;
import com.example.ray.playviewandroid.base.BaseFragment;
import com.example.ray.playviewandroid.constants.PlayViewConstants;
import com.example.ray.playviewandroid.presenter.login.LoginRegisterPresenter;
import com.example.ray.playviewandroid.util.SharedPreferencesUtils;
import com.example.ray.playviewandroid.view.activity.LoginActivity;
import com.example.ray.playviewandroid.view.interfaces.ILoginRegisterView;

public class LoginFragment extends BaseFragment<ILoginRegisterView, LoginRegisterPresenter<ILoginRegisterView>> implements ILoginRegisterView,View.OnClickListener{
    private EditText etUser;
    private EditText etPassword;
    private Button btLogin;
    private Button btExit;
    private TextView tvRegister;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_login);
        return view;
    }

    @Override
    protected boolean setFragmentTarget() {
        return false;
    }

    @Override
    public void initView() {
        etUser = view.findViewById(R.id.et_username);
        etPassword = view.findViewById(R.id.et_password);
        btLogin = view.findViewById(R.id.bt_login);
        btExit = view.findViewById(R.id.bt_exit);
        tvRegister = view.findViewById(R.id.tv_register);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        btLogin.setOnClickListener(this);
        btExit.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
    }

    @Override
    protected LoginRegisterPresenter<ILoginRegisterView> createPresenter() {
        return new LoginRegisterPresenter<>();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_login:
                if (TextUtils.isEmpty(etPassword.getText())){
                    showToast(R.string.loginempty);
                }else if (TextUtils.isEmpty(etUser.getText())){
                    showToast(R.string.loginempty);
                }else {
                    mPresenter.doLogin(etUser.getText().toString(),etPassword.getText().toString());
                }
                break;
            case R.id.bt_exit:
                getActivity().finish();
                break;
            case R.id.tv_register:
                ((LoginActivity)getActivity()).toRegisterFragment();
                break;
        }
    }

    @Override
    public void onSuccess() {

        //成功登陆后保存登录状态
        SharedPreferencesUtils.putString(context,PlayViewConstants.USER,etUser.getText().toString());
        SharedPreferencesUtils.putString(context,PlayViewConstants.PASSWORD,etPassword.getText().toString());
        SharedPreferencesUtils.putBoolean(context, PlayViewConstants.LOGIN_STATE,true);
        ((LoginActivity)getActivity()).toMainActivity();

    }

    @Override
    public void onFailed(String error) {
        showToast(R.string.loginfailed);
    }


}
