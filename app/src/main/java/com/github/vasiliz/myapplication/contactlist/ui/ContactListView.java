package com.github.vasiliz.myapplication.contactlist.ui;

import com.github.vasiliz.myapplication.contactlist.entities.User;

public interface ContactListView {

    void onContactAdded(User pUser);
    void onContactChanged(User pUser);
    void onContactRemoved(User pUser);

}
