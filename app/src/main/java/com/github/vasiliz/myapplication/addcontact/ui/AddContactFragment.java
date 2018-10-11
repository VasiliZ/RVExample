package com.github.vasiliz.myapplication.addcontact.ui;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.vasiliz.myapplication.R;
import com.github.vasiliz.myapplication.addcontact.AddContactPresenter;
import com.github.vasiliz.myapplication.addcontact.AddContactPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddContactFragment extends DialogFragment implements AddContactView, DialogInterface.OnShowListener {

    @BindView(R.id.new_user_email)
    EditText mNewUserEmail;
    @BindView(R.id.fragment_progressbar)
    ProgressBar mProgressBar;

    private AddContactPresenter mAddContactPresenter;

    public AddContactFragment() {
        mAddContactPresenter = new AddContactPresenterImpl(this);
    }

    @Override
    public void onShow(DialogInterface dialog) {
        final AlertDialog alertDialog = (AlertDialog) getDialog();
        if (alertDialog != null) {
            Button positiveButton = alertDialog.getButton(Dialog.BUTTON_POSITIVE);
            Button negativeButton = alertDialog.getButton(Dialog.BUTTON_NEGATIVE);

            positiveButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mAddContactPresenter.addContact(mNewUserEmail.getText().toString());
                }
            });

            negativeButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }

        mAddContactPresenter.onShow();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mAddContactPresenter.onDestroy();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.add_contact_title)
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.add_contact_fragment_dialog, null);
        ButterKnife.bind(this, view);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(this);

        return alertDialog;
    }

    @Override
    public void showInput() {
        mNewUserEmail.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideInput() {
        mNewUserEmail.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
            mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void contactAdded() {
        Toast.makeText(getActivity(), R.string.added_contact,Toast.LENGTH_SHORT).show();
        dismiss();
    }

    @Override
    public void contactNotAdded() {
        mNewUserEmail.setText("");
        mNewUserEmail.setError(getString(R.string.error_added_contact));
    }
}
