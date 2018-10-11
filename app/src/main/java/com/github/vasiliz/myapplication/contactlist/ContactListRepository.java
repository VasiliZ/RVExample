package com.github.vasiliz.myapplication.contactlist;

import com.github.vasiliz.myapplication.contactlist.entities.User;

public interface ContactListRepository {

    void signOff();
    String getCurrentEmail();
    void changeUserConnectionStatus(boolean pOnline);
    void destroyContactListListener();
    void subscribeForContactListUpdates();
    void unSubscribeForContactListUpdates();
    void removeContact(String pEmail);



}
