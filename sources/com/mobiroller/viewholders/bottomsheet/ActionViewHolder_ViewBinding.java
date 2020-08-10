package com.mobiroller.viewholders.bottomsheet;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;

public class ActionViewHolder_ViewBinding implements Unbinder {
    private ActionViewHolder target;

    public ActionViewHolder_ViewBinding(ActionViewHolder actionViewHolder, View view) {
        this.target = actionViewHolder;
        actionViewHolder.image = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.image, "field 'image'", ImageView.class);
        actionViewHolder.title = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.title, "field 'title'", TextView.class);
    }

    public void unbind() {
        ActionViewHolder actionViewHolder = this.target;
        if (actionViewHolder != null) {
            this.target = null;
            actionViewHolder.image = null;
            actionViewHolder.title = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
