package com.alexkaz.task2.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexkaz.task2.R;

import ru.alexbykov.nopaginate.item.LoadingItem;

public class CustomLoadingItem implements LoadingItem {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_loading_item, parent, false);
        return new RecyclerView.ViewHolder(view) { };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) { }

}
