package com.example.ray.playviewandroid.presenter.interfaces;

public interface ILoginPresenter {
    void doLogin(final String user, String password);
    void doLogin(String user,String password,String repassword);
}
