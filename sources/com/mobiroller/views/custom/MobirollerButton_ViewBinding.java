package com.mobiroller.views.custom;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;

public class MobirollerButton_ViewBinding implements Unbinder {
    private MobirollerButton target;

    public MobirollerButton_ViewBinding(MobirollerButton mobirollerButton) {
        this(mobirollerButton, mobirollerButton);
    }

    public MobirollerButton_ViewBinding(MobirollerButton mobirollerButton, View view) {
        this.target = mobirollerButton;
        mobirollerButton.mainLayout = (ConstraintLayout) C0812Utils.findRequiredViewAsType(view, R.id.main_layout, "field 'mainLayout'", ConstraintLayout.class);
        mobirollerButton.textView = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.text_view, "field 'textView'", TextView.class);
        mobirollerButton.leftImageView = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.left_image_view, "field 'leftImageView'", ImageView.class);
        mobirollerButton.successIcon = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.success_icon, "field 'successIcon'", ImageView.class);
        mobirollerButton.layout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.layout, "field 'layout'", RelativeLayout.class);
    }

    public void unbind() {
        MobirollerButton mobirollerButton = this.target;
        if (mobirollerButton != null) {
            this.target = null;
            mobirollerButton.mainLayout = null;
            mobirollerButton.textView = null;
            mobirollerButton.leftImageView = null;
            mobirollerButton.successIcon = null;
            mobirollerButton.layout = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
