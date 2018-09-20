package com.github.vasiliz.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private LayoutInflater layoutInflater;
    private List<Model> modelList;

    public DataAdapter(Context context, List<Model> models) {
        this.layoutInflater = LayoutInflater.from(context);
        this.modelList = models;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder holder, int position) {
        Model model = modelList.get(position);
        holder.id.setmText(String.valueOf(model.getId()));

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CustomView id;

        public ViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.item_text_id);
        }
    }
}
