package com.github.vasiliz.myapplication.contactlist;

public class ContactListSessionInteractorImpl implements ContactListSessionInteractor {

    private ContactListRepositoryImpl mContactListRepositoty;

    public ContactListSessionInteractorImpl() {
        mContactListRepositoty = new ContactListRepositoryImpl();
    }

    @Override
    public void signOff() {
        mContactListRepositoty.signOff();
    }

    @Override
    public String getCurrentUserEmail() {
        return mContactListRepositoty.getCurrentEmail();
    }

    @Override
    public void changeConnectionStatus(boolean pStatus) {
        mContactListRepositoty.changeUserConnectionStatus(pStatus);
    }
}
