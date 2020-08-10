package com.mobiroller.viewholders.forms;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;

public class LabelViewHolder_ViewBinding implements Unbinder {
    private LabelViewHolder target;

    public LabelViewHolder_ViewBinding(LabelViewHolder labelViewHolder, View view) {
        this.target = labelViewHolder;
        labelViewHolder.title = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.form_item_title, "field 'title'", TextView.class);
        labelViewHolder.label = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.form_item_label, "field 'label'", TextView.class);
        labelViewHolder.labelIcon = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.form_item_label_icon, "field 'labelIcon'", ImageView.class);
        labelViewHolder.labelMainLayout = (LinearLayout) C0812Utils.findRequiredViewAsType(view, R.id.form_item_label_main_layout, "field 'labelMainLayout'", LinearLayout.class);
    }

    public void unbind() {
        LabelViewHolder labelViewHolder = this.target;
        if (labelViewHolder != null) {
            this.target = null;
            labelViewHolder.title = null;
            labelViewHolder.label = null;
            labelViewHolder.labelIcon = null;
            labelViewHolder.labelMainLayout = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
