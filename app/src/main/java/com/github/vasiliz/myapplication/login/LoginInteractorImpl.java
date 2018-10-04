package com.github.vasiliz.myapplication.login;

public class LoginInteractorImpl implements LoginInteractor {

    private LoginRepository mLoginRepository;

    public LoginInteractorImpl(){
        mLoginRepository = new LoginRepositoryImpl();
    }

    @Override
    public void checkAlreadyAuthtenticated() {
        mLoginRepository.chackAlreadyAuthenticated();
    }

    @Override
    public void doSignUp(String pEmail, String pPassword) {
        mLoginRepository.signUp(pEmail, pPassword);
    }

    @Override
    public void doSignIn(String pEmail, String pPassword) {
        mLoginRepository.signIn(pEmail, pPassword);
    }

}
