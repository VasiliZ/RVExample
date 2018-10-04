package com.github.vasiliz.myapplication.domain;

import android.support.annotation.NonNull;

import com.github.vasiliz.myapplication.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class FirebaseHelper {

    private DatabaseReference mDatabaseReference;
    private static final String SEPARATOR = "___";
    private static final String CHAT_PATH = "chats";
    private static final String USER_PATH = "users";
    private static final String CONTACTS_PATH = "contacts";

    private static class SingletonHolder {
        private static final FirebaseHelper INSTANCE = new FirebaseHelper();
    }

    public static FirebaseHelper getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public FirebaseHelper (){
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public DatabaseReference getDatabaseReference(){
        return mDatabaseReference;
    }

    public String getAuthUserEmail(){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = null;
        if (user != null){
            email = user.getEmail();
        }
        return email;
    }

    public DatabaseReference getUserReference(final String pEmail){
        DatabaseReference userReference = null;
        if (pEmail!=null){
            final String emailKey = pEmail.replace(".", "_");
            userReference = mDatabaseReference.getRoot().child(USER_PATH).child(emailKey);
        }
        return userReference;
    }

    public DatabaseReference getMyUserReference(){
        return getUserReference(getAuthUserEmail());
    }

    public DatabaseReference getContactsReference(final String pEmail){
        return getUserReference(pEmail).child(CONTACTS_PATH);
    }

    public DatabaseReference getMyContactsReference(){
        return getContactsReference(getAuthUserEmail());
    }

    public DatabaseReference getOneContactReference(final String pMainEmail, final String pChildEmail){
        final String childKey = pChildEmail.replace(".", "_");
        return getUserReference(pMainEmail).child(CONTACTS_PATH).child(childKey);
    }

    public DatabaseReference getChatsReference(final String receiver){
        final String keySender = getAuthUserEmail().replace(".","_");
        final String keyReceiver = receiver.replace(".", "_");
        String keyChat = keySender  + SEPARATOR + keySender;

        if (keySender.compareTo(keyReceiver)>0){
            keyChat = keySender + SEPARATOR + keyReceiver;
        }

        return mDatabaseReference.getRoot().child(CHAT_PATH).child(keyChat);
    }

    public void changeUserConectedStatus(final boolean pOnline){
        if (getMyUserReference()!=null){
            final Map<String, Object> online = new HashMap<>();
            online.put("online", pOnline);
            getMyUserReference().updateChildren(online);
            notifyContactsOfConnectionChange(pOnline);
        }
    }

    public void notifyContactsOfConnectionChange(final boolean pOnline, final boolean pSignOff){
        final String mYEmail = getAuthUserEmail();

        getMyContactsReference().addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull final DataSnapshot pDataSnapshot) {
                for (final DataSnapshot child: pDataSnapshot.getChildren()){
                    final String email = child.getKey();
                    final DatabaseReference databaseReference = getOneContactReference(email, mYEmail);
                    databaseReference.setValue(pOnline);
                }
                if (pSignOff){
                    FirebaseAuth.getInstance().signOut();
                }

            }

            @Override
            public void onCancelled(@NonNull final DatabaseError pDatabaseError) {

            }
        });
    }

    public void notifyContactsOfConnectionChange(final boolean pOnline){
        notifyContactsOfConnectionChange(pOnline, false);
    }

    public void signOff(){
        notifyContactsOfConnectionChange(User.OFFLINE, true);
    }
}
