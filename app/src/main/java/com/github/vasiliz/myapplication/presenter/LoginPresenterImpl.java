package com.github.vasiliz.myapplication.presenter;

import android.text.TextUtils;

import com.github.vasiliz.myapplication.R;
import com.github.vasiliz.myapplication.model.ILoginModel;
import com.github.vasiliz.myapplication.model.LoginModelImpl;
import com.github.vasiliz.myapplication.view.IViewLogin;

public class LoginPresenterImpl implements LoginPresenter {

    private IViewLogin mIViewLogin;
    private ILoginModel mILoginModel;

    public LoginPresenterImpl(final IViewLogin pIViewLogin){
        mIViewLogin = pIViewLogin;
        mILoginModel = new LoginModelImpl();
    }

    @Override
    public void validateCredentials(final String username, final String password0) {
        if (!TextUtils.isEmpty(password0)&&!isPasswordValid(password0)){
            mIViewLogin.setPasswordError(R.string.password_error);
            return;
        }

        if (TextUtils.isEmpty(username)){
            mIViewLogin.setUserNameError(R.string.user_name_error_empty);
            return;
        }else if (!isEmailValid(username)){
            mIViewLogin.setUserNameError(R.string.validation_email_error);

        }
        mIViewLogin.showProgress(true);
        mILoginModel.login(username, password0);
    }

    private boolean isEmailValid(final String pUsername) {
        return pUsername.contains("@");
    }

    private boolean isPasswordValid(final String pPassword){
        return pPassword.length() > 4;
    }
}
