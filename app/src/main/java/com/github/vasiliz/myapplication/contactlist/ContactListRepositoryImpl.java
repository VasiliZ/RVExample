package com.github.vasiliz.myapplication.contactlist;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.github.vasiliz.myapplication.contactlist.entities.User;
import com.github.vasiliz.myapplication.contactlist.events.ContactListEvents;
import com.github.vasiliz.myapplication.domain.FirebaseHelper;
import com.github.vasiliz.myapplication.lib.EventBus;
import com.github.vasiliz.myapplication.lib.GreenRobotEventBus;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class ContactListRepositoryImpl implements ContactListRepository {

    private ChildEventListener mContactListEventListener;
    private FirebaseHelper mFirebaseHelper;

    public ContactListRepositoryImpl() {
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
        mContactListEventListener = null;
    }

    @Override
    public void subscribeForContactListUpdates() {
        if (mContactListEventListener == null) {
            mContactListEventListener = new ChildEventListener() {


                @Override
                public void onChildAdded(@NonNull DataSnapshot pDataSnapshot, @Nullable String pS) {
                    String email = pDataSnapshot.getKey();
                    email = email.replace("_", ".");
                    boolean online = ((Boolean) pDataSnapshot.getValue()).booleanValue();
                    User user = new User(email, online, null);
                    postEvent(ContactListEvents.ON_CONTACT_ADDED, user);
                    Log.d("TAG", "onChildadded event");

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot pDataSnapshot, @Nullable String pS) {
                    String email = pDataSnapshot.getKey();
                    email = email.replace("_", ".");
                    boolean b = ((Boolean)pDataSnapshot.getValue()).booleanValue();
                    User user = new User(email, b, null);
                    postEvent(ContactListEvents.ON_CONTACT_CHANGED, user);
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot pDataSnapshot) {
                    String email = pDataSnapshot.getKey();
                    email = email.replace("_", ".");
                    boolean b = ((Boolean)pDataSnapshot.getValue()).booleanValue();
                    User user = new User(email, b, null);
                    postEvent(ContactListEvents.ON_CONTACT_REMOVED, user);
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot pDataSnapshot, @Nullable String pS) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError pDatabaseError) {

                }
            };
        }
        mFirebaseHelper.getMyContactsReference().addChildEventListener(mContactListEventListener);
    }

    @Override
    public void unSubscribeForContactListUpdates() {
        if (mContactListEventListener != null){
            mFirebaseHelper.getMyContactsReference().removeEventListener(mContactListEventListener);
        }
    }

    @Override
    public void removeContact(String pEmail) {
        String currerntUserEmail = mFirebaseHelper.getAuthUserEmail();
        mFirebaseHelper.getOneContactReference(currerntUserEmail, pEmail).removeValue();
        mFirebaseHelper.getOneContactReference(pEmail, currerntUserEmail).removeValue();
    }

    private void postEvent(int pType, User pUser) {
        ContactListEvents contactListEvents = new ContactListEvents();
        contactListEvents.setEventType(pType);
        contactListEvents.setUser(pUser);
        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(contactListEvents);

    }
}
