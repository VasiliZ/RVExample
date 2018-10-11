package com.github.vasiliz.myapplication.chat;

public class ChatInteractorImpl implements ChatInteractor {
    private ChatRepository mChatRepository;

    public ChatInteractorImpl(){
        mChatRepository = new ChatRepositoryImpl();
    }

    @Override
    public void sendMessage(String pMessage) {
        mChatRepository.sendMessage(pMessage);
    }

    @Override
    public void setRecipient(String pRecipient) {
        mChatRepository.setReceiver(pRecipient);
    }

    @Override
    public void destroyChatListener() {
        mChatRepository.destroyChatListener();
    }

    @Override
    public void subscribeForChatUpdates() {
        mChatRepository.subscribeForChatUpdates();
    }

    @Override
    public void unSubscribeForChatUpdates() {
        mChatRepository.unSubscribeForChatUpdates();
    }
}
