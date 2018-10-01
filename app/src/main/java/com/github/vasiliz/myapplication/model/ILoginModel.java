package com.github.vasiliz.myapplication.model;

public interface ILoginModel {

    interface OnloginFinishedListener{

        void onCanceled();

        void OnPasswordError();

        void onSuccess();
    }

    void login(String userName, String password, OnloginFinishedListener pListener);

}
