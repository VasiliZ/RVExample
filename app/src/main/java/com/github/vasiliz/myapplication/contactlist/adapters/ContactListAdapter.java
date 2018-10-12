package com.github.vasiliz.myapplication.contactlist.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.vasiliz.myapplication.R;
import com.github.vasiliz.myapplication.contactlist.entities.User;
import com.github.vasiliz.myapplication.contactlist.ui.OnItemClickListener;
import com.github.vasiliz.myapplication.domain.AvatarHelper;
import com.github.vasiliz.myapplication.lib.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

    private List<User> mUsers;
    private ImageLoader mImageLoader;
    private OnItemClickListener mOnItemClickListener;

    public ContactListAdapter(final List<User> pUsers, final ImageLoader pImageLoader, final OnItemClickListener pOnItemClickListener) {
        mUsers = pUsers;
        mImageLoader = pImageLoader;
        mOnItemClickListener = pOnItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        User user = mUsers.get(position);
        holder.setClickListener(user, mOnItemClickListener);

        String email = user.getEmail();
        boolean online = user.isOnline();
        String status = online ? "online" : "online";
        int color = online ? Color.GREEN:Color.RED;

        holder.mTextUser.setText(email);
        holder.mUserStatus.setText(status);
        holder.mUserStatus.setTextColor(color);

        mImageLoader.load(holder.mAvatar, AvatarHelper.getAvatarUrl(email));
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public int getPositionByUsername(String pUserName){
        int position = 0;
        for (User user:mUsers){
            if (user.getEmail().equals(pUserName)){
                break;
            }
            position++;
        }
        return position;
    }

    private boolean alreadyInAdapter(User pNewUser){
        boolean alreadyInAdapter = false;
        for (User user: mUsers){
            if (user.getEmail().equals(pNewUser.getEmail())){
                alreadyInAdapter = true;
                break;
            }
        }
        return alreadyInAdapter;
    }

    public void add(User pUser){
        if (!alreadyInAdapter(pUser)){
            Log.d("TAG", "add in adapter");
            this.mUsers.add(pUser);
            this.notifyDataSetChanged();
        }
    }

    public void update(User pUser){
        int pos = getPositionByUsername(pUser.getEmail());
        mUsers.set(pos, pUser);
        this.notifyDataSetChanged();
    }

    public void remove(User pUser){
        int pos = getPositionByUsername(pUser.getEmail());
        mUsers.remove(pos);
        this.notifyDataSetChanged();
    }




    //view holder class

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.circle_avatar)
        CircleImageView mAvatar;
        @BindView(R.id.txt_user)
        TextView mTextUser;
        @BindView(R.id.user_status)
        TextView mUserStatus;

        private View mView;

        public ViewHolder(final View itemView) {
            super(itemView);
            mView = itemView;
            ButterKnife.bind(this, itemView);
        }

        public void setClickListener(final User pUser, final OnItemClickListener pOnItemClickListener) {
            mView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(final View v) {
                    pOnItemClickListener.onItemClickListener(pUser);
                }
            });

            mView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(final View v) {
                    pOnItemClickListener.onItemLongClick(pUser);
                    return false;
                }
            });
        }
    }
}
