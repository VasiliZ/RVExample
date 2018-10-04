package com.github.vasiliz.myapplication.lib;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

public class GlideImageLoader implements ImageLoader {
    private RequestManager mRequestManager;

    public GlideImageLoader(final Context pContext){
        mRequestManager = Glide.with(pContext);
    }

    @Override
    public void load(final ImageView pImageView, final String URL) {
        mRequestManager.load(URL).into(pImageView);
    }
}
