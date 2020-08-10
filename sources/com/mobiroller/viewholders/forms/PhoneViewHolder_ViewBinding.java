package com.mobiroller.viewholders.forms;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;

public class PhoneViewHolder_ViewBinding implements Unbinder {
    private PhoneViewHolder target;

    public PhoneViewHolder_ViewBinding(PhoneViewHolder phoneViewHolder, View view) {
        this.target = phoneViewHolder;
        phoneViewHolder.title = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.form_item_title, "field 'title'", TextView.class);
        phoneViewHolder.phone = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.form_item_phone, "field 'phone'", TextView.class);
        phoneViewHolder.phoneIcon = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.form_item_phone_icon, "field 'phoneIcon'", ImageView.class);
        phoneViewHolder.phoneMainLayout = (LinearLayout) C0812Utils.findRequiredViewAsType(view, R.id.form_item_phone_main_layout, "field 'phoneMainLayout'", LinearLayout.class);
    }

    public void unbind() {
        PhoneViewHolder phoneViewHolder = this.target;
        if (phoneViewHolder != null) {
            this.target = null;
            phoneViewHolder.title = null;
            phoneViewHolder.phone = null;
            phoneViewHolder.phoneIcon = null;
            phoneViewHolder.phoneMainLayout = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
