package com.mobiroller.adapters;

import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

class BaseAdapter extends Adapter {
    public int getItemCount() {
        return 0;
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return null;
    }

    BaseAdapter() {
    }
}
