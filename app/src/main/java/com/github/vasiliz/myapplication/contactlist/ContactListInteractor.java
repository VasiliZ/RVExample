package com.github.vasiliz.myapplication.contactlist;

public interface ContactListInteractor {
    void subscribeForContactEvents();
    void unSubscribeForContactEvents();
    void destroyContactListListener();
    void removeContact(String pEmail);
}
