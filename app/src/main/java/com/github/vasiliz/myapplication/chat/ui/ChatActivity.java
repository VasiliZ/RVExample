package com.github.vasiliz.myapplication.chat.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.github.vasiliz.myapplication.ChatApplication;
import com.github.vasiliz.myapplication.R;
import com.github.vasiliz.myapplication.chat.ChatPresenter;
import com.github.vasiliz.myapplication.chat.ChatPresenterImpl;
import com.github.vasiliz.myapplication.chat.adapters.ChatAdapter;
import com.github.vasiliz.myapplication.chat.entities.ChatMessage;
import com.github.vasiliz.myapplication.domain.AvatarHelper;
import com.github.vasiliz.myapplication.lib.ImageLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity implements ChatView {

    private ChatPresenter mChatPresenter;
    private ChatAdapter mChatAdapter;

    @BindView(R.id.chat_toolbar)
    Toolbar mChatToolBar;
    @BindView(R.id.txt_user)
    TextView mTextUser;
    @BindView(R.id.user_status)
    TextView mUserStatus;
    @BindView(R.id.text_send_message)
    EditText mTypeTextHere;
    @BindView(R.id.recycler_view_chat)
    RecyclerView mRecyclerViewChat;
    @BindView(R.id.circle_avatar)
    CircleImageView mCircleImageView;

    public static final String EMAIL_KEY = "email";
    public static final String ONLINE_KEY = "online";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ButterKnife.bind(this);
        mChatPresenter = new ChatPresenterImpl(this);
        mChatPresenter.onCreate();

        setSupportActionBar(mChatToolBar);
        Intent intent = getIntent();
        setToolBatData(intent);
        setUpAdapter();
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        mRecyclerViewChat.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewChat.setAdapter(mChatAdapter);
    }

    private void setUpAdapter() {
        mChatAdapter = new ChatAdapter(this, new ArrayList<ChatMessage>());
    }

    private void setToolBatData(Intent pIntent) {
        String recipient = pIntent.getStringExtra(EMAIL_KEY);
        Log.d("recipient", recipient);
        mChatPresenter.setChatRecipient(recipient);

        boolean online = getIntent().getBooleanExtra(ONLINE_KEY, false);
        String status = online ? "online" : "offline";
        int color = online?Color.GREEN: Color.RED;

        mTextUser.setText(recipient);
        mUserStatus.setText(status);
        mUserStatus.setTextColor(color);

        ChatApplication application = (ChatApplication) getApplication();
        ImageLoader imageLoader = application.getImageLoader();
        imageLoader.load(mCircleImageView, AvatarHelper.getAvatarUrl(recipient));
    }

    @Override
    @OnClick(R.id.button_send_message)
    public void sendMessage() {
            mChatPresenter.sendMessage(mTypeTextHere.getText().toString());
            mTypeTextHere.setText("");

    }

    @Override
    public void messageReceives(ChatMessage pMessage) {
        mChatAdapter.add(pMessage);
        mRecyclerViewChat.scrollToPosition(mChatAdapter.getItemCount()-1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mChatPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mChatPresenter.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mChatPresenter.onDestroy();
    }
}
