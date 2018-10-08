package com.github.vasiliz.myapplication.contactlist;

import com.github.vasiliz.myapplication.contactlist.events.ContactListEvents;

public interface ContactListPresenter {

    void onCreate();
    void signOff();
    String getCurrentUserEmail();
    void onEventMainThread(ContactListEvents pContactListEvents);

}
