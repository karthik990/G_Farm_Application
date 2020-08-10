package com.mobiroller.viewholders.forms;

import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;
import com.rengwuxian.materialedittext.MaterialEditText;

public class DateViewHolder_ViewBinding implements Unbinder {
    private DateViewHolder target;

    public DateViewHolder_ViewBinding(DateViewHolder dateViewHolder, View view) {
        this.target = dateViewHolder;
        dateViewHolder.date = (MaterialEditText) C0812Utils.findRequiredViewAsType(view, R.id.form_item_date, "field 'date'", MaterialEditText.class);
    }

    public void unbind() {
        DateViewHolder dateViewHolder = this.target;
        if (dateViewHolder != null) {
            this.target = null;
            dateViewHolder.date = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
