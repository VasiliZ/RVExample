package com.github.vasiliz.myapplication.contactlist.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.github.vasiliz.myapplication.ChatApplication;
import com.github.vasiliz.myapplication.R;
import com.github.vasiliz.myapplication.chat.ui.ChatActivity;
import com.github.vasiliz.myapplication.contactlist.ContactListPresenterImpl;
import com.github.vasiliz.myapplication.contactlist.ContactListPresenter;
import com.github.vasiliz.myapplication.contactlist.adapters.ContactListAdapter;
import com.github.vasiliz.myapplication.addcontact.ui.AddContactFragment;
import com.github.vasiliz.myapplication.contactlist.entities.User;
import com.github.vasiliz.myapplication.lib.ImageLoader;
import com.github.vasiliz.myapplication.login.ui.LoginActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactListActivity extends AppCompatActivity
        implements ContactListView, OnItemClickListener {

    private ContactListPresenter mContactListPresenter;
    @BindView(R.id.tool_bar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view_contacts)
    RecyclerView mRecyclerViewContactList;

    private ContactListAdapter mContactListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_list_layout);

        ButterKnife.bind(this);

        mContactListPresenter = new ContactListPresenterImpl(this);
        mContactListPresenter.onCreate();

        mToolbar.setSubtitle(mContactListPresenter.getCurrentUserEmail());
        setSupportActionBar(mToolbar);

        setUpAdapter();
        setUpRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sign_out_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.sign_out_menu) {
            mContactListPresenter.signOff();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mContactListPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mContactListPresenter.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mContactListPresenter.onDestroy();
    }

    private void setUpAdapter() {
        ChatApplication application = (ChatApplication) getApplication();
        ImageLoader imageLoader = application.getImageLoader();
        mContactListAdapter = new ContactListAdapter(new ArrayList<User>(), imageLoader, this);
    }

    private void setUpRecyclerView() {
        mRecyclerViewContactList.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewContactList.setAdapter(mContactListAdapter);
    }

    @Override
    public void onItemClickListener(User pUser) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra(ChatActivity.ONLINE_KEY, pUser.isOnline());
        intent.putExtra(ChatActivity.EMAIL_KEY, pUser.getEmail());
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(User pUser) {
        mContactListPresenter.removeContact(pUser.getEmail());
    }

    @Override
    public void onContactAdded(User pUser) {
        mContactListAdapter.add(pUser);
        Log.d("TAG", "added in activity");
    }

    @Override
    public void onContactChanged(User pUser) {
        mContactListAdapter.update(pUser);
    }

    @Override
    public void onContactRemoved(User pUser) {
        mContactListAdapter.remove(pUser);
    }

    @OnClick(R.id.floating_button)
    public void addContact() {
        AddContactFragment addContactFragment = new AddContactFragment();
        addContactFragment.show(getSupportFragmentManager(), "");
    }
}
