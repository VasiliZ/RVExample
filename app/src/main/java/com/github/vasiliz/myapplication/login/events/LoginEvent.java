package com.github.vasiliz.myapplication.login.events;

public class LoginEvent {

    public static final int ON_SIGNIN_ERROR = 0;
    public static final int ON_SIGNUP_ERROR = 1;
    public static final int ON_SIGNIN_SUCCESS = 2;
    public static final int ON_SIGNUP_SUCCESS = 3;
    public static final int ON_FILED_ON_RECOVER_SESSION = 4;

    private int mEventType;
    private String mErrorMessage;

    public int getEventType() {
        return mEventType;
    }


    public void setEventType(int pEventType) {
        mEventType = pEventType;
    }

    public String getErrorMessage() {
        return mErrorMessage;
    }

    public void setErrorMessage(String pErrorMessage) {
        mErrorMessage = pErrorMessage;
    }
}
