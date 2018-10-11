package com.github.vasiliz.myapplication.chat;

import com.github.vasiliz.myapplication.chat.events.ChatEvent;

public interface ChatPresenter {
    void onCreate();
    void onPause();
    void onResume();
    void onDestroy();

    void setChatRecipient(String pRecipient);
    void sendMessage(String pMessage);
    void onEventMainThread(ChatEvent pChatEvent);


}
