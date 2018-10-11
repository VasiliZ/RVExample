package com.github.vasiliz.myapplication.chat;

import com.github.vasiliz.myapplication.chat.entities.ChatMessage;
import com.github.vasiliz.myapplication.chat.events.ChatEvent;
import com.github.vasiliz.myapplication.chat.ui.ChatView;
import com.github.vasiliz.myapplication.contactlist.entities.User;
import com.github.vasiliz.myapplication.lib.EventBus;
import com.github.vasiliz.myapplication.lib.GreenRobotEventBus;

import org.greenrobot.eventbus.Subscribe;

public class ChatPresenterImpl implements ChatPresenter {

    private ChatView mChatView;
    private EventBus mEventBus;
    private ChatInteractor mChatInteractor;
    private ChatSessionInteractor mChatSessionInteractor;

    public ChatPresenterImpl(ChatView pChatView) {
        mChatView = pChatView;
        mEventBus = GreenRobotEventBus.getInstance();

        mChatInteractor = new ChatInteractorImpl();
        mChatSessionInteractor = new ChatSessionInteractorImpl();
    }

    @Override
    public void onCreate() {
        mEventBus.register(this);
    }

    @Override
    public void onPause() {
        mChatInteractor.unSubscribeForChatUpdates();
        mChatSessionInteractor.changeConnectionStatus(User.OFFLINE);

    }

    @Override
    public void onResume() {
        mChatInteractor.subscribeForChatUpdates();
        mChatSessionInteractor.changeConnectionStatus(User.ONLINE);
    }

    @Override
    public void onDestroy() {
        mEventBus.unregister(this);
        mChatInteractor.destroyChatListener();
        mChatView = null;
    }

    @Override
    public void setChatRecipient(String pRecipient) {
        mChatInteractor.setRecipient(pRecipient);

    }

    @Override
    public void sendMessage(String pMessage) {
        mChatInteractor.sendMessage(pMessage);
    }

    @Override
    @Subscribe
    public void onEventMainThread(ChatEvent pChatEvent) {
        if (mChatView != null){
            ChatMessage chatMessage = pChatEvent.getChatMessage();
            mChatView.messageReceives(chatMessage);
        }
    }
}
