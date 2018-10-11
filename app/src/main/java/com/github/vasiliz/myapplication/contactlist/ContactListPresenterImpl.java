package com.github.vasiliz.myapplication.contactlist;

import com.github.vasiliz.myapplication.R;
import com.github.vasiliz.myapplication.contactlist.entities.User;
import com.github.vasiliz.myapplication.contactlist.events.ContactListEvents;
import com.github.vasiliz.myapplication.contactlist.ui.ContactListView;
import com.github.vasiliz.myapplication.lib.EventBus;
import com.github.vasiliz.myapplication.lib.GreenRobotEventBus;

import org.greenrobot.eventbus.Subscribe;

import butterknife.OnClick;

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
    public void onPause() {
        mContactListSessionInteractor.changeConnectionStatus(User.OFFLINE);
        mContactListInteractor.unSubscribeForContactEvents();
    }

    @Override
    public void onResume() {
        mContactListSessionInteractor.changeConnectionStatus(User.ONLINE);
        mContactListInteractor.subscribeForContactEvents();
    }

    @Override
    public void onDestroy() {
        mEventBus.unregister(this);
        mContactListInteractor.destroyContactListListener();
        mContactListView = null;
    }

    @Override
    public void removeContact(String pEmail) {
        mContactListInteractor.removeContact(pEmail);
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
                onContactAdded(user);
                break;
            case ContactListEvents.ON_CONTACT_CHANGED:
                onContactChanged(user);
                break;
            case ContactListEvents.ON_CONTACT_REMOVED:
                onContactRemoved(user);
                break;
        }
    }

    public void onContactAdded(User pUser){
        mContactListView.onContactAdded(pUser);
    }

    public void onContactChanged(User pUser){
        mContactListView.onContactChanged(pUser);
    }

    public void onContactRemoved(User pUser){
        mContactListView.onContactRemoved(pUser);
    }

    @OnClick(R.id.floating_button)
    public void addContact(){

    }
}
