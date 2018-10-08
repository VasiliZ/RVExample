package com.github.vasiliz.myapplication.contactlist;

public class ContactListInteractorImpl implements ContactListInteractor{

    private ContactListRepository mContactListRepository;

    public ContactListInteractorImpl(){
        mContactListRepository = new ContactListRepositotyImpl();
    }

    @Override
    public void subscribeForContactEvents() {
        mContactListRepository.subscribeForContactListUpdates();
    }

    @Override
    public void unSubscribeForContactEvents() {
        mContactListRepository.unSubscribeForContactListUpdates();
    }

    @Override
    public void destroyContactListListener() {
            mContactListRepository.destroyContactListListener();
    }

    @Override
    public void removeContact(String pEmail) {
    }
}
