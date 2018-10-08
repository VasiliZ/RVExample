package com.github.vasiliz.myapplication.contactlist;

public interface ContactListSessionInteractor {

    void signOff();
    String getCurrentUserEmail();
    void changeConnectionStatus(boolean pStatus);
}
