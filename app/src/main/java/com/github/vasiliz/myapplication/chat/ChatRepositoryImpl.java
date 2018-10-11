package com.github.vasiliz.myapplication.chat;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.github.vasiliz.myapplication.chat.entities.ChatMessage;
import com.github.vasiliz.myapplication.chat.events.ChatEvent;
import com.github.vasiliz.myapplication.domain.FirebaseHelper;
import com.github.vasiliz.myapplication.lib.EventBus;
import com.github.vasiliz.myapplication.lib.GreenRobotEventBus;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

public class ChatRepositoryImpl implements ChatRepository {

    private String mReceiver;
    private FirebaseHelper mFirebaseHelper;
    private ChildEventListener mChildEventListener;

    public ChatRepositoryImpl() {
        mFirebaseHelper = FirebaseHelper.getInstance();
    }

    @Override
    public void sendMessage(String pDMessage) {
        Log.d("msg", pDMessage);
        String keySender = mFirebaseHelper.getAuthUserEmail().replace(".", "_");
        ChatMessage chatMessage = new ChatMessage(pDMessage, keySender);
        DatabaseReference databaseReference = mFirebaseHelper.getChatsReference(mReceiver);
        databaseReference.push().setValue(chatMessage);
    }

    @Override
    public void setReceiver(String pReceiver) {
        Log.d("recipient", pReceiver);
        mReceiver = pReceiver;
    }

    @Override
    public void destroyChatListener() {
        mChildEventListener = null;
    }

    @Override
    public void subscribeForChatUpdates() {
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {

                @Override
                public void onChildAdded(@NonNull DataSnapshot pDataSnapshot, @Nullable String pS) {
                    ChatMessage chatMessage = pDataSnapshot.getValue(ChatMessage.class);
                    String msgSender = chatMessage.getpSender();
                    msgSender = msgSender.replace("_", ".");

                    String currentUserEmail = mFirebaseHelper.getAuthUserEmail();
                    chatMessage.setpSentByMe(msgSender.equals(currentUserEmail));

                    ChatEvent  chatEvent = new ChatEvent(chatMessage);
                    EventBus eventBus = GreenRobotEventBus.getInstance();
                    eventBus.post(chatEvent);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot pDataSnapshot, @Nullable String pS) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot pDataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot pDataSnapshot, @Nullable String pS) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError pDatabaseError) {

                }

            };
            mFirebaseHelper.getChatsReference(mReceiver).addChildEventListener(mChildEventListener);

        }
    }

    @Override
    public void unSubscribeForChatUpdates() {
        if (mChildEventListener != null){
            mFirebaseHelper.getChatsReference(mReceiver).removeEventListener(mChildEventListener);
        }
    }

    @Override
    public void changeUserConnectionStatus(boolean pOnline) {

    }
}
