package com.github.vasiliz.myapplication.login;

import com.github.vasiliz.myapplication.login.events.LoginEvent;

public interface LoginPresenter {
    void onCreate();
    void onDestroy();
    void checkForAutentificatedUser();
    void validateLogin(String pLogin, String pPassword);
    void registeredNewUser(String pLogin, String pPassword);
    void onEventMainThread(LoginEvent pLoginEvent);
}
