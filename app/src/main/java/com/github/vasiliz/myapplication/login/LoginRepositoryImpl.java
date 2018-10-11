package com.github.vasiliz.myapplication.login;

import android.support.annotation.NonNull;

import com.github.vasiliz.myapplication.contactlist.entities.User;
import com.github.vasiliz.myapplication.domain.FirebaseHelper;
import com.github.vasiliz.myapplication.lib.EventBus;
import com.github.vasiliz.myapplication.lib.GreenRobotEventBus;
import com.github.vasiliz.myapplication.login.events.LoginEvent;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class LoginRepositoryImpl implements LoginRepository {

    private FirebaseHelper mFirebaseHelper;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference mMyUserDatabaseReference;

    public LoginRepositoryImpl() {
        mFirebaseHelper = FirebaseHelper.getInstance();
        mDatabaseReference = mFirebaseHelper.getDatabaseReference();
        mMyUserDatabaseReference = mFirebaseHelper.getMyUserReference();
    }

    @Override
    public void signUp(final String pEmail, final String pPassword) {
        try {

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(pEmail, pPassword)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {

                        @Override
                        public void onSuccess(final AuthResult pAuthResult) {
                            postEvent(LoginEvent.ON_SIGNUP_SUCCESS);
                            signIn(pEmail, pPassword);
                        }
                    }).addOnFailureListener(new OnFailureListener() {

                @Override
                public void onFailure(@NonNull final Exception pE) {
                    postEvent(LoginEvent.ON_SIGNUP_ERROR, pE.getMessage());
                }
            });
        } catch (final Exception pE) {
            postEvent(LoginEvent.ON_SIGNUP_ERROR, pE.getMessage());
        }
    }

    @Override
    public void signIn(final String pEmail, final String pPassword) {
        try {
            final FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signInWithEmailAndPassword(pEmail, pPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {

                @Override
                public void onSuccess(final AuthResult pAuthResult) {
                    mMyUserDatabaseReference = mFirebaseHelper.getMyUserReference();
                    mMyUserDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull final DataSnapshot pDataSnapshot) {
                            initSignIn(pDataSnapshot);
                        }

                        @Override
                        public void onCancelled(@NonNull final DatabaseError pDatabaseError) {
                            postEvent(LoginEvent.ON_SIGNIN_ERROR, pDatabaseError.getMessage());
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {

                @Override
                public void onFailure(@NonNull final Exception pE) {
                    postEvent(LoginEvent.ON_SIGNIN_ERROR, pE.getMessage());
                }
            });
        } catch (final Exception pE) {
            postEvent(LoginEvent.ON_SIGNIN_ERROR, pE.getMessage());
        }

    }

    @Override
    public void chackAlreadyAuthenticated() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            mMyUserDatabaseReference = mFirebaseHelper.getMyUserReference();
            mMyUserDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull final DataSnapshot pDataSnapshot) {
                    initSignIn(pDataSnapshot);
                }

                @Override
                public void onCancelled(@NonNull final DatabaseError pDatabaseError) {
                    postEvent(LoginEvent.ON_SIGNIN_ERROR, pDatabaseError.getMessage());
                }
            });
        } else {
            postEvent(LoginEvent.ON_FILED_ON_RECOVER_SESSION);
        }
    }

    private void registredNewUser() {
        final String email = mFirebaseHelper.getAuthUserEmail();
        if (email != null) {
            final User currentUser = new User(email, true, null);
            mMyUserDatabaseReference.setValue(currentUser);
        }
    }

    private void initSignIn(final DataSnapshot pDataSnapshot) {
        final User currentUser = pDataSnapshot.getValue(User.class);
        if (currentUser == null) {
            registredNewUser();
        }

        mFirebaseHelper.changeUserConectedStatus(User.ONLINE);
        postEvent(LoginEvent.ON_SIGNIN_SUCCESS);
    }

    private void postEvent(final int pType) {
        postEvent(pType, null);
    }

    private void postEvent(final int type, final String errorMessage) {
        final LoginEvent loginEvent = new LoginEvent();
        loginEvent.setEventType(type);
        if (errorMessage != null) {
            loginEvent.setErrorMessage(errorMessage);
        }

        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(loginEvent);
    }
}
