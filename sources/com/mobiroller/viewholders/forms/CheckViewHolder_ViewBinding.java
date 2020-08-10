package com.mobiroller.viewholders.forms;

import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;

public class CheckViewHolder_ViewBinding implements Unbinder {
    private CheckViewHolder target;

    public CheckViewHolder_ViewBinding(CheckViewHolder checkViewHolder, View view) {
        this.target = checkViewHolder;
        checkViewHolder.title = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.form_item_title, "field 'title'", TextView.class);
        checkViewHolder.checkBox = (Switch) C0812Utils.findRequiredViewAsType(view, R.id.form_item_check_box, "field 'checkBox'", Switch.class);
    }

    public void unbind() {
        CheckViewHolder checkViewHolder = this.target;
        if (checkViewHolder != null) {
            this.target = null;
            checkViewHolder.title = null;
            checkViewHolder.checkBox = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
