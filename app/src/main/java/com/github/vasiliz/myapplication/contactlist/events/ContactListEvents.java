package com.github.vasiliz.myapplication.contactlist.events;

import com.github.vasiliz.myapplication.contactlist.entities.User;

public class ContactListEvents {

    private User mUser;
    private int mEventType;

    public static final int ON_CONTACT_ADDED = 0;
    public static final int ON_CONTACT_CHANGED = 1;
    public static final int ON_CONTACT_REMOVED = 2;

    public User getUser() {
        return mUser;
    }

    public void setUser(User pUser) {
        mUser = pUser;
    }

    public int getEventType() {
        return mEventType;
    }

    public void setEventType(int pEventType) {
        mEventType = pEventType;
    }
}
