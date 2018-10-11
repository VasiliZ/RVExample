package com.github.vasiliz.myapplication.chat;

public class ChatSessionInteractorImpl implements ChatSessionInteractor {
    private ChatRepository mChatRepository;

    public ChatSessionInteractorImpl(){
        mChatRepository = new ChatRepositoryImpl();
    }

    @Override
    public void changeConnectionStatus(boolean pOnline) {


    }
}
