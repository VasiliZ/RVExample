package com.github.vasiliz.myapplication.contactlist;

import com.github.vasiliz.myapplication.contactlist.entities.User;
import com.github.vasiliz.myapplication.contactlist.events.ContactListEvents;
import com.github.vasiliz.myapplication.contactlist.ui.ContactListView;
import com.github.vasiliz.myapplication.lib.EventBus;
import com.github.vasiliz.myapplication.lib.GreenRobotEventBus;

import org.greenrobot.eventbus.Subscribe;

public class ContactListPresenterImpl implements ContactListPresenter {

    private ContactListView mContactListView;
    private ContactListSessionInteractor mContactListSessionInteractor;
    private ContactListInteractor mContactListInteractor;
    EventBus mEventBus;

    public ContactListPresenterImpl(ContactListView pContactListView) {
        mContactListView = pContactListView;
        mEventBus = GreenRobotEventBus.getInstance();
        mContactListSessionInteractor = new ContactListSessionInteractorImpl();
        mContactListInteractor = new ContactListInteractorImpl();

    }

    @Override
    public void onCreate() {
        mEventBus.register(this);
    }

    @Override
    public void signOff() {
        mContactListSessionInteractor.changeConnectionStatus(User.OFFLINE);
        mContactListInteractor.unSubscribeForContactEvents();
        mContactListInteractor.destroyContactListListener();
        mContactListSessionInteractor.signOff();
    }

    @Override
    public String getCurrentUserEmail() {
        return mContactListSessionInteractor.getCurrentUserEmail();
    }

    @Override
    @Subscribe
    public void onEventMainThread(ContactListEvents pContactListEvents) {
        User user = pContactListEvents.getUser();
        switch (pContactListEvents.getEventType()) {
            case ContactListEvents.ON_CONTACT_ADDED:
                break;
            case ContactListEvents.ON_CONTACT_CHANGED:
                break;
            case ContactListEvents.ON_CONTACT_REMOVED:
                break;
        }
    }
}
