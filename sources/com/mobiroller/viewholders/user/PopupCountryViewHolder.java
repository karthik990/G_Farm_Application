package com.mobiroller.viewholders.user;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PopupCountryViewHolder extends ViewHolder {
    @BindView(2131363230)
    TextView text;

    public PopupCountryViewHolder(View view) {
        super(view);
        ButterKnife.bind((Object) this, view);
    }

    public void bindText(String str) {
        this.text.setText(str);
    }
}
