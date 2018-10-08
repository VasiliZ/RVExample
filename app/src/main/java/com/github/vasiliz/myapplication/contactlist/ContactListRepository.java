package com.github.vasiliz.myapplication.contactlist;

public interface ContactListRepository {

    void signOff();
    String getCurrentEmail();
    void changeUserConnectionStatus(boolean pOnline);
    void destroyContactListListener();
    void subscribeForContactListUpdates();
    void unSubscribeForContactListUpdates();



}
