package com.github.vasiliz.myapplication;

import android.app.Application;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.vasiliz.myapplication.lib.GlideImageLoader;
import com.github.vasiliz.myapplication.lib.ImageLoader;
import com.google.firebase.database.FirebaseDatabase;

public class ChatApplication extends Application {
    private ImageLoader mImageLoader;



    @Override
    public void onCreate() {
        super.onCreate();
        setUpImageLoader();
        setUpFireBase();

    }

    private void setUpImageLoader(){
        mImageLoader = new GlideImageLoader(this);
    }

    public ImageLoader getImageLoader(){
        return mImageLoader;
    }

    private void setUpFireBase(){
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
