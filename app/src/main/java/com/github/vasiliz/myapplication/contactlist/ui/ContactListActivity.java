package com.github.vasiliz.myapplication.contactlist.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.github.vasiliz.myapplication.R;
import com.github.vasiliz.myapplication.contactlist.ContactListPresenterImpl;
import com.github.vasiliz.myapplication.contactlist.ContactListPresenter;
import com.github.vasiliz.myapplication.login.ui.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactListActivity extends AppCompatActivity implements ContactListView {

    private ContactListPresenter mContactListPresenter;
    @BindView(R.id.tool_bar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_list_layout);

        ButterKnife.bind(this);

        mContactListPresenter = new ContactListPresenterImpl(this);
        mContactListPresenter.onCreate();

        mToolbar.setSubtitle(mContactListPresenter.getCurrentUserEmail());
        setSupportActionBar(mToolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sign_out_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.sign_out_menu){
            mContactListPresenter.signOff();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    |Intent.FLAG_ACTIVITY_NEW_TASK
                    |Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
