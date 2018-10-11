package com.github.vasiliz.myapplication.chat;

public interface ChatRepository {
    void sendMessage(String pDMessage);
    void setReceiver(String pReceiver);

    void destroyChatListener();
    void subscribeForChatUpdates();
    void unSubscribeForChatUpdates();

    void changeUserConnectionStatus(boolean pOnline);

}
