package com.github.vasiliz.myapplication.contactlist.ui;

import com.github.vasiliz.myapplication.contactlist.entities.User;

public interface OnItemClickListener {

    void onItemClickListener(User pUser);
    void onItemLongClick(User pUser);

}
