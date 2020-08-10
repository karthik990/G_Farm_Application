package com.mobiroller.viewholders.forms;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;

public class AddressViewHolder_ViewBinding implements Unbinder {
    private AddressViewHolder target;

    public AddressViewHolder_ViewBinding(AddressViewHolder addressViewHolder, View view) {
        this.target = addressViewHolder;
        addressViewHolder.address = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.form_item_address, "field 'address'", TextView.class);
        addressViewHolder.addressIcon = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.form_item_address_icon, "field 'addressIcon'", ImageView.class);
        addressViewHolder.addressMainLayout = (LinearLayout) C0812Utils.findRequiredViewAsType(view, R.id.form_item_address_main_layout, "field 'addressMainLayout'", LinearLayout.class);
    }

    public void unbind() {
        AddressViewHolder addressViewHolder = this.target;
        if (addressViewHolder != null) {
            this.target = null;
            addressViewHolder.address = null;
            addressViewHolder.addressIcon = null;
            addressViewHolder.addressMainLayout = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
