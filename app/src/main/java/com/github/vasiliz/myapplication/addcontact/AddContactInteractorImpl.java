package com.github.vasiliz.myapplication.addcontact;

public class AddContactInteractorImpl implements AddContactInteractor {

    private AddContactRepository mAddContactRepository;

    public AddContactInteractorImpl(AddContactRepository pAddContactRepository){
        mAddContactRepository = pAddContactRepository;
    }

    @Override
    public void addContact(String pEmail) {
        mAddContactRepository.addContact(pEmail);

    }
}
