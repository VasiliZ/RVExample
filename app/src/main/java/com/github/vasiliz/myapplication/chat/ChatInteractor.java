package com.github.vasiliz.myapplication.chat;

public interface ChatInteractor {

    void sendMessage(String pMessage);
    void setRecipient(String pRecipient);

    void destroyChatListener();
    void subscribeForChatUpdates();
    void unSubscribeForChatUpdates();

}
