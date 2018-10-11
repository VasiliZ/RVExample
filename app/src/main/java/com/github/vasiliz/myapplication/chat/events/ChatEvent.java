package com.github.vasiliz.myapplication.chat.events;

import com.github.vasiliz.myapplication.chat.entities.ChatMessage;

public class ChatEvent {

    private ChatMessage mChatMessage;

    public ChatEvent(ChatMessage pChatMessage) {
        mChatMessage = pChatMessage;
    }

    public ChatMessage getChatMessage() {
        return mChatMessage;
    }

    public void setChatMessage(ChatMessage pChatMessage) {
        mChatMessage = pChatMessage;
    }
}
