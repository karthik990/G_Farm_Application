package com.mobiroller.viewholders.user;

import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;

public class PopupCountryViewHolder_ViewBinding implements Unbinder {
    private PopupCountryViewHolder target;

    public PopupCountryViewHolder_ViewBinding(PopupCountryViewHolder popupCountryViewHolder, View view) {
        this.target = popupCountryViewHolder;
        popupCountryViewHolder.text = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.text, "field 'text'", TextView.class);
    }

    public void unbind() {
        PopupCountryViewHolder popupCountryViewHolder = this.target;
        if (popupCountryViewHolder != null) {
            this.target = null;
            popupCountryViewHolder.text = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
