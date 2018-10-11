package com.github.vasiliz.myapplication.addcontact;

import com.github.vasiliz.myapplication.addcontact.events.AddContactEvent;
import com.github.vasiliz.myapplication.addcontact.ui.AddContactView;
import com.github.vasiliz.myapplication.lib.EventBus;
import com.github.vasiliz.myapplication.lib.GreenRobotEventBus;

import org.greenrobot.eventbus.Subscribe;

public class AddContactPresenterImpl implements AddContactPresenter {

    private EventBus mEventBus;
    private AddContactView mAddContactView;
    private AddContactInteractor mAddContactInteractor;

    public AddContactPresenterImpl(AddContactView pAddContactView){
        mAddContactView = pAddContactView;
        mEventBus = GreenRobotEventBus.getInstance();
        mAddContactInteractor = new AddContactInteractorImpl(new AddContactRepositoryImpl());
    }

    @Override
    public void onShow() {
        mEventBus.register(this);
    }

    @Override
    public void onDestroy() {
        mAddContactView = null;
        mEventBus.unregister(this);
    }

    @Override
    public void addContact(String pEmail) {
        mAddContactView.hideInput();
        mAddContactView.showProgress();
        mAddContactInteractor.addContact(pEmail);
    }

    @Override
    @Subscribe
    public void onEventMainThread(AddContactEvent pAddContactEvent) {
            if (mAddContactView !=null){
                mAddContactView.hideProgress();
                mAddContactView.showInput();

                if (pAddContactEvent.isError()){
                    mAddContactView.contactNotAdded();
                }else {
                    mAddContactView.contactAdded();
                }
            }
    }
}
