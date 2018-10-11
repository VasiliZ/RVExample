package com.github.vasiliz.myapplication.chat.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.vasiliz.myapplication.R;
import com.github.vasiliz.myapplication.chat.entities.ChatMessage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private Context mContext;
    private List<ChatMessage> mChatMessages;

    public ChatAdapter(Context pContext, List<ChatMessage> pChatMessages) {
        mContext = pContext;
        mChatMessages = pChatMessages;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_chat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChatMessage chatMessage = mChatMessages.get(position);
        String msg = chatMessage.getpMessage();
        holder.mTextView.setText(msg);

        int color = fetchColor(R.attr.colorPrimary);
        int gravity = Gravity.LEFT;

        if (!chatMessage.ispSentByMe()){
            gravity = Gravity.RIGHT;
            color = fetchColor(R.attr.colorAccent);
        }

        holder.mTextView.setBackgroundColor(color);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.mTextView.getLayoutParams();
        layoutParams.gravity = gravity;
        holder.mTextView.setLayoutParams(layoutParams);
    }

    @Override
    public int getItemCount() {
        return mChatMessages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_message)
        TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void add(ChatMessage pChatMessage) {
        if (!alreadyInAdapter(pChatMessage)) {
            mChatMessages.add(pChatMessage);
            notifyDataSetChanged();
        }
    }

    private boolean alreadyInAdapter(ChatMessage pChatMessage) {
        boolean alreadyInAdapter = false;
        for (ChatMessage chatMessage : mChatMessages) {
            if (chatMessage.getpMessage()
                    .equals(pChatMessage.getpMessage())
                    && chatMessage.getpSender()
                    .equals(pChatMessage.getpSender())) {
                alreadyInAdapter = true;
                break;
            }
        }
        return alreadyInAdapter;
    }

    private int fetchColor(int color){
        TypedValue typedValue = new TypedValue();
        TypedArray typedArray = mContext.obtainStyledAttributes(typedValue.data, new int[]{color});
        int returnColor = typedArray.getColor(0,0);
        typedArray.recycle();
        return returnColor;

    }
}
