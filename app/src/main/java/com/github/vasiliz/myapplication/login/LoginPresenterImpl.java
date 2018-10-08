package com.github.vasiliz.myapplication.login;

import com.github.vasiliz.myapplication.lib.EventBus;
import com.github.vasiliz.myapplication.lib.GreenRobotEventBus;
import com.github.vasiliz.myapplication.login.events.LoginEvent;
import com.github.vasiliz.myapplication.login.ui.LoginView;

import org.greenrobot.eventbus.Subscribe;

public class LoginPresenterImpl implements LoginPresenter {

    EventBus mEventBus;
    LoginView mLoginView;
    LoginInteractor mLoginInteractor;

    public LoginPresenterImpl(final LoginView pLoginView) {
        mLoginView = pLoginView;
        mEventBus = GreenRobotEventBus.getInstance();
        mLoginInteractor = new LoginInteractorImpl();
    }

    @Override
    public void onCreate() {
        mEventBus.register(this);
    }

    @Override
    public void onDestroy() {
        mLoginView = null;
        mEventBus.unregister(this);
    }

    @Override
    public void checkForAutentificatedUser() {
        if (mLoginView != null) {
            mLoginView.disableInputs();
            mLoginView.showProgress();
        }
        mLoginInteractor.checkAlreadyAuthtenticated();
    }

    @Override
    public void validateLogin(final String pLogin, final String pPassword) {
        if (mLoginView != null) {
            mLoginView.disableInputs();
            mLoginView.showProgress();
        }
        mLoginInteractor.doSignIn(pLogin, pPassword);
    }

    @Override
    public void registeredNewUser(final String pLogin, final String pPassword) {
        if (mLoginView != null) {
            mLoginView.disableInputs();
            mLoginView.showProgress();
        }

        mLoginInteractor.doSignUp(pLogin, pPassword);
    }


    @Override
    @Subscribe
    public void onEventMainThread(final LoginEvent pLoginEvent) {
        switch (pLoginEvent.getEventType()) {
            case LoginEvent.ON_SIGNIN_ERROR:
                onSignInError(pLoginEvent.getErrorMessage());
                break;
            case LoginEvent.ON_SIGNUP_ERROR:
                onSignUpError(pLoginEvent.getErrorMessage());
                break;
            case LoginEvent.ON_SIGNIN_SUCCESS:
                onSignInSuccess();
                break;
            case LoginEvent.ON_SIGNUP_SUCCESS:
                onSignUpSuccess();
                break;
            case LoginEvent.ON_FILED_ON_RECOVER_SESSION:
                onFiledToRecoverSession();
                break;
        }
    }

    private void onSignInSuccess() {
        mLoginView.navigateToMainScreen();
    }

    private void onSignUpSuccess() {
        mLoginView.newUserSuccess();
    }

    private void onSignInError(final String pError) {
        mLoginView.hideProgress();
        mLoginView.enableInputs();
        mLoginView.loginError(pError);
    }

    private void onSignUpError(final String pError) {
        mLoginView.hideProgress();
        mLoginView.enableInputs();
        mLoginView.loginError(pError);
    }

    private void onFiledToRecoverSession() {
        mLoginView.hideProgress();
        mLoginView.enableInputs();
    }
}
