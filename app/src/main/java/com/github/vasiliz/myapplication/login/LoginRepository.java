package com.github.vasiliz.myapplication.login;

public interface LoginRepository {
    void signUp(String pEmail, String pPassword);
    void signIn(String pEmail, String pPassword);
    void chackAlreadyAuthenticated();
}
