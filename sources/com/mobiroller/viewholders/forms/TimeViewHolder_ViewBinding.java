package com.mobiroller.viewholders.forms;

import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;
import com.rengwuxian.materialedittext.MaterialEditText;

public class TimeViewHolder_ViewBinding implements Unbinder {
    private TimeViewHolder target;

    public TimeViewHolder_ViewBinding(TimeViewHolder timeViewHolder, View view) {
        this.target = timeViewHolder;
        timeViewHolder.time = (MaterialEditText) C0812Utils.findRequiredViewAsType(view, R.id.form_item_time, "field 'time'", MaterialEditText.class);
    }

    public void unbind() {
        TimeViewHolder timeViewHolder = this.target;
        if (timeViewHolder != null) {
            this.target = null;
            timeViewHolder.time = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
