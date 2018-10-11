package com.github.vasiliz.myapplication.chat.entities;

import com.google.firebase.database.Exclude;

public class ChatMessage {

    String pMessage;
    String pSender;
    @Exclude
    boolean pSentByMe;

    public ChatMessage(){}

    public ChatMessage(String pPMessage, String pPSender) {
        pMessage = pPMessage;
        pSender = pPSender;
    }

    public String getpMessage() {
        return pMessage;
    }

    public void setpMessage(String pPMessage) {
        pMessage = pPMessage;
    }

    public String getpSender() {
        return pSender;
    }

    public void setpSender(String pPSender) {
        pSender = pPSender;
    }

    public boolean ispSentByMe() {
        return pSentByMe;
    }

    public void setpSentByMe(boolean pPSentByMe) {
        pSentByMe = pPSentByMe;
    }
}
