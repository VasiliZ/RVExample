package com.github.vasiliz.myapplication.contactlist;

import com.github.vasiliz.myapplication.domain.FirebaseHelper;
import com.google.firebase.database.ChildEventListener;

public class ContactListRepositotyImpl implements ContactListRepository {

    private ChildEventListener mContactListEventListener;
    private FirebaseHelper mFirebaseHelper;

    public ContactListRepositotyImpl(){
        mFirebaseHelper = FirebaseHelper.getInstance();
    }

    @Override
    public void signOff() {
        mFirebaseHelper.signOff();

    }

    @Override
    public String getCurrentEmail() {
       return mFirebaseHelper.getAuthUserEmail();
    }

    @Override
    public void changeUserConnectionStatus(boolean pOnline) {
        mFirebaseHelper.changeUserConectedStatus(pOnline);
    }

    @Override
    public void destroyContactListListener() {

    }

    @Override
    public void subscribeForContactListUpdates() {

    }

    @Override
    public void unSubscribeForContactListUpdates() {

    }
}
