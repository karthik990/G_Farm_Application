package com.stfalcon.chatkit.commons;

import android.view.View;

public abstract class ViewHolder<DATA> extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
    public abstract void onBind(DATA data);

    public ViewHolder(View view) {
        super(view);
    }
}
