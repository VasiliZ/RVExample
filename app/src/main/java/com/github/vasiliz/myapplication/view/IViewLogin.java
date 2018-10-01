package com.github.vasiliz.myapplication.view;

public interface IViewLogin {

    void showProgress(boolean showProgress);

    void setUserNameError(int messageResId);

    void setPasswordError(int messageResId);

    void successAction();

}
