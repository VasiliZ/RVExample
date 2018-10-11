package com.github.vasiliz.myapplication.addcontact;

import com.github.vasiliz.myapplication.addcontact.events.AddContactEvent;

public interface AddContactPresenter {

    void onShow();
    void onDestroy();

    void addContact(String pEmail);
    void onEventMainThread(AddContactEvent pAddContactEvent);

}
