package com.github.vasiliz.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Model> modelList = new ArrayList<>();
    private int res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setData();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        res = R.anim.layout_anim;
        LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(recyclerView.getContext(), res);
        recyclerView.setLayoutAnimation(animationController);
        DataAdapter dataAdapter = new DataAdapter(this, modelList);
        recyclerView.setAdapter(dataAdapter);
    }

    private void setData(){
        for (int i =0; i<10;i++){
            modelList.add(new Model(i));
        }
    }
}
