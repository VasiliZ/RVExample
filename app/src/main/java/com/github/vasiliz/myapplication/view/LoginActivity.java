package com.github.vasiliz.myapplication.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.vasiliz.myapplication.R;
import com.github.vasiliz.myapplication.events.CanceledEvent;
import com.github.vasiliz.myapplication.events.PasswordErrorEvent;
import com.github.vasiliz.myapplication.events.SuccessEvent;
import com.github.vasiliz.myapplication.presenter.LoginPresenter;
import com.github.vasiliz.myapplication.presenter.LoginPresenterImpl;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class LoginActivity extends AppCompatActivity implements IViewLogin {

    private AutoCompleteTextView mEmailView;
    private LoginPresenter mLoginPresenter;
    private EditText mPassword;
    private ProgressBar mProgressBar;
    private View mViewLoginForm;
    private Button mSnackbar;

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mEmailView = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mProgressBar = findViewById(R.id.login_progress);
        mViewLoginForm = findViewById(R.id.login_form);
        mSnackbar = findViewById(R.id.snackbar);

        final Button loginButton = findViewById(R.id.sing_in_button);
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                attemptLogin();
            }
        });

        mSnackbar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Snackbar.make(mViewLoginForm, "this a simple Snackbar", Snackbar.LENGTH_SHORT).setAction("Close", new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        v.setVisibility(View.INVISIBLE);
                    }
                }).show();
            }
        });

        mLoginPresenter = new LoginPresenterImpl(this);

    }

    private void attemptLogin() {
        mEmailView.setError(null);
        mPassword.setError(null);

        String email = mEmailView.getText().toString();
        String password = mPassword.getText().toString();

        mLoginPresenter.validateCredentials(email, password);

    }

    @Override
    public void showProgress(final boolean showProgress) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mViewLoginForm.setVisibility(showProgress ? View.GONE : View.VISIBLE);
        mViewLoginForm.animate().setDuration(shortAnimTime).alpha(showProgress ? 0 : 1).setListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                mViewLoginForm.setVisibility(showProgress ? View.GONE : View.VISIBLE);
            }
        });

        mProgressBar.setVisibility(showProgress ? View.VISIBLE : View.GONE);
        mProgressBar.animate().setDuration(shortAnimTime).alpha(showProgress ? 1 : 0).setListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressBar.setVisibility(showProgress ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public void setUserNameError(final int messageResId) {
        mEmailView.setError(getString(messageResId));
        mEmailView.requestFocus();
    }

    @Override
    public void setPasswordError(final int messageResId) {
        mPassword.setError(getString(messageResId));
        mPassword.requestFocus();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(SuccessEvent pSuccessEvent) {
        showProgress(false);
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCancelEvent(CanceledEvent pCanceledEvent) {
        showProgress(false);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPasswordErrorEvent(PasswordErrorEvent pPasswordErrorEvent) {
        showProgress(false);
        setPasswordError(R.string.password_error);
    }
}