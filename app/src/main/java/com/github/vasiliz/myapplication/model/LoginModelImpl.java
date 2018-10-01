package com.github.vasiliz.myapplication.model;

import android.os.AsyncTask;

import com.github.vasiliz.myapplication.events.CanceledEvent;
import com.github.vasiliz.myapplication.events.PasswordErrorEvent;
import com.github.vasiliz.myapplication.events.SuccessEvent;

import org.greenrobot.eventbus.EventBus;

public class LoginModelImpl implements ILoginModel {

    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "test@galileo.edu:asdfasdf", "test2@galileo.edu:asdfasdf"
    };

    @Override
    public void login(final String userName, final String password) {

        new UserLoginTask(userName, password).execute();

    }

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        public static final int MILLIS = 2000;
        private String mEmail;
        private String mPassword;

        public UserLoginTask(final String pEmail, final String pPassword) {
            mEmail = pEmail;
            mPassword = pPassword;
        }

        @Override
        protected Boolean doInBackground(final Void... pVoids) {

            try {
                Thread.sleep(MILLIS);
            } catch (final InterruptedException pE) {
                return false;
            }

            for (final String credential : DUMMY_CREDENTIALS) {
                final String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    return pieces[1].equals(mPassword);
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean pBoolean) {
            if (pBoolean) {
                EventBus.getDefault().post(new SuccessEvent());

            } else {
                EventBus.getDefault().post(new PasswordErrorEvent());
            }
        }

        @Override
        protected void onCancelled() {
            EventBus.getDefault().post(new CanceledEvent());
        }
    }
}
