package com.mobiroller.viewholders.forms;

import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import butterknife.internal.DebouncingOnClickListener;
import com.mobiroller.mobi942763453128.R;
import p015br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class SubmitViewHolder_ViewBinding implements Unbinder {
    private SubmitViewHolder target;
    private View view7f0a028e;

    public SubmitViewHolder_ViewBinding(final SubmitViewHolder submitViewHolder, View view) {
        this.target = submitViewHolder;
        View findRequiredView = C0812Utils.findRequiredView(view, R.id.form_item_submit, "field 'submit' and method 'submit'");
        submitViewHolder.submit = (CircularProgressButton) C0812Utils.castView(findRequiredView, R.id.form_item_submit, "field 'submit'", CircularProgressButton.class);
        this.view7f0a028e = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                submitViewHolder.submit();
            }
        });
    }

    public void unbind() {
        SubmitViewHolder submitViewHolder = this.target;
        if (submitViewHolder != null) {
            this.target = null;
            submitViewHolder.submit = null;
            this.view7f0a028e.setOnClickListener(null);
            this.view7f0a028e = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
