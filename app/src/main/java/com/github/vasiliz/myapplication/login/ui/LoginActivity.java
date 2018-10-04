package com.github.vasiliz.myapplication.login.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.github.vasiliz.myapplication.R;
import com.github.vasiliz.myapplication.login.LoginPresenter;
import com.github.vasiliz.myapplication.login.LoginPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginView {

    public static final String TAG = LoginActivity.class.getSimpleName();
    private LoginPresenter mLoginPresenter;

    @BindView(R.id.edit_text_email)
    EditText mEmail;
    @BindView(R.id.edit_text_password)
    EditText mPassword;
    @BindView(R.id.sign_in)
    Button mSignIn;
    @BindView(R.id.sign_up)
    Button mSignUp;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.main_conteiner)
    RelativeLayout mMainConteiner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mLoginPresenter = new LoginPresenterImpl(this);
        mLoginPresenter.onCreate();
        mLoginPresenter.checkForAutentificatedUser();
    }

    @Override
    public void enableInputs() {
        setInputs(true);
    }

    @Override
    public void disableInputs() {
            setInputs(false);
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    @OnClick(R.id.sign_up)
    public void handleSignUp() {
        mLoginPresenter.registeredNewUser(mEmail.getText().toString(), mPassword.getText().toString());
    }

    @Override
    @OnClick(R.id.sign_in)
    public void handleSignIn() {
        mLoginPresenter.validateLogin(mEmail.getText().toString(), mPassword.getText().toString());
    }

    @Override
    public void navigateToMainScreen() {
        Log.d(TAG, "navigateToMainScreen: gogogo");
    }

    @Override
    public void loginError(String pError) {
            mPassword.setText("");
            String msg = String.format(getString(R.string.loginError), pError);
            mPassword.setText(msg);
    }

    @Override
    public void newUserSuccess() {
        Snackbar.make(mMainConteiner, R.string.success_login, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void newUserError(String pError) {
        mPassword.setText("");
        String msg = String.format(getString(R.string.signup_error), pError);
        mPassword.setText(msg);
    }

    private void setInputs(boolean enabled){
        mSignIn.setEnabled(true);
        mSignUp.setEnabled(true);
        mPassword.setEnabled(true);
        mEmail.setEnabled(true);
    }
}