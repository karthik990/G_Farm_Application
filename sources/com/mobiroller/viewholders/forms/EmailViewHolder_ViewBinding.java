package com.mobiroller.viewholders.forms;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;

public class EmailViewHolder_ViewBinding implements Unbinder {
    private EmailViewHolder target;

    public EmailViewHolder_ViewBinding(EmailViewHolder emailViewHolder, View view) {
        this.target = emailViewHolder;
        emailViewHolder.title = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.form_item_title, "field 'title'", TextView.class);
        emailViewHolder.emailMainLayout = (LinearLayout) C0812Utils.findRequiredViewAsType(view, R.id.form_item_email_main_layout, "field 'emailMainLayout'", LinearLayout.class);
        emailViewHolder.email = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.form_item_email, "field 'email'", TextView.class);
        emailViewHolder.emailIcon = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.form_item_email_icon, "field 'emailIcon'", ImageView.class);
    }

    public void unbind() {
        EmailViewHolder emailViewHolder = this.target;
        if (emailViewHolder != null) {
            this.target = null;
            emailViewHolder.title = null;
            emailViewHolder.emailMainLayout = null;
            emailViewHolder.email = null;
            emailViewHolder.emailIcon = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
