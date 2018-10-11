package com.github.vasiliz.myapplication.addcontact.events;

public class AddContactEvent {

    private boolean mError = false;

    public boolean isError() {
        return mError;
    }

    public void setError(boolean pError) {
        mError = pError;
    }
}
