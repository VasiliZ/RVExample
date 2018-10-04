package com.github.vasiliz.myapplication.login;

public interface LoginInteractor {
    void checkAlreadyAuthtenticated();
    void doSignUp(String pEmail, String pPassword);
    void doSignIn(String pEmail, String pPassword);
}
