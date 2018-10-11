package com.github.vasiliz.myapplication.addcontact;

import android.support.annotation.NonNull;

import com.github.vasiliz.myapplication.addcontact.events.AddContactEvent;
import com.github.vasiliz.myapplication.contactlist.entities.User;
import com.github.vasiliz.myapplication.domain.FirebaseHelper;
import com.github.vasiliz.myapplication.lib.EventBus;
import com.github.vasiliz.myapplication.lib.GreenRobotEventBus;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AddContactRepositoryImpl implements AddContactRepository {
    private FirebaseHelper mFirebaseHelper;
    private User mUser;

    public AddContactRepositoryImpl(){
        mFirebaseHelper = FirebaseHelper.getInstance();
    }

    @Override
    public void addContact(final String pEmail) {
        final String KEY = pEmail.replace(".", "_");


        final DatabaseReference userReference = mFirebaseHelper.getUserReference(pEmail);
        Query query = userReference;
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot pDataSnapshot) {

                AddContactEvent addContactEvent = new AddContactEvent();
                mUser = pDataSnapshot.getValue(User.class);
                if (mUser != null){
                    boolean online = mUser.isOnline();
                    DatabaseReference databaseReference = mFirebaseHelper.getMyContactsReference();
                    databaseReference.child(KEY).setValue(online);

                    String currentUserEmailKey = mFirebaseHelper.getAuthUserEmail();
                    currentUserEmailKey = currentUserEmailKey.replace(".", "_");
                    DatabaseReference reference = mFirebaseHelper.getContactsReference(pEmail);
                    reference.child(currentUserEmailKey).setValue(true);
                }else {
                    addContactEvent.setError(true);
                }

                EventBus eventBus = GreenRobotEventBus.getInstance();
                eventBus.post(addContactEvent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError pDatabaseError) {

            }
        });
    }
}
