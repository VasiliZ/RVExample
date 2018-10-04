package com.github.vasiliz.myapplication;

import java.util.Map;

public class User {

    private String mEmail;
    private boolean mOnline;
    private Map<String, Boolean> mContacts;

    public static final boolean OFFLINE = false;
    public static final boolean ONLINE = true;

    public User() {
    }

    public User(String pEmail, boolean pOnline, Map<String, Boolean> pContacts) {
        mEmail = pEmail;
        mOnline = pOnline;
        mContacts = pContacts;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String pEmail) {
        mEmail = pEmail;
    }

    public boolean isOnline() {
        return mOnline;
    }

    public void setOnline(boolean pOnline) {
        mOnline = pOnline;
    }

    public Map<String, Boolean> getContacts() {
        return mContacts;
    }

    public void setContacts(Map<String, Boolean> pContacts) {
        mContacts = pContacts;
    }
}
