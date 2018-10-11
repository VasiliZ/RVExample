package com.github.vasiliz.myapplication.chat.ui;

import com.github.vasiliz.myapplication.chat.entities.ChatMessage;

public interface ChatView {
    void sendMessage();
    void messageReceives(ChatMessage pMessage);

}
