package com.mobiroller.views.custom;

import android.view.View;
import android.widget.RelativeLayout;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;

public class MobirollerBadgeView_ViewBinding implements Unbinder {
    private MobirollerBadgeView target;

    public MobirollerBadgeView_ViewBinding(MobirollerBadgeView mobirollerBadgeView) {
        this(mobirollerBadgeView, mobirollerBadgeView);
    }

    public MobirollerBadgeView_ViewBinding(MobirollerBadgeView mobirollerBadgeView, View view) {
        this.target = mobirollerBadgeView;
        mobirollerBadgeView.textView = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.badge_text, "field 'textView'", MobirollerTextView.class);
        mobirollerBadgeView.mainLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.main_layout, "field 'mainLayout'", RelativeLayout.class);
    }

    public void unbind() {
        MobirollerBadgeView mobirollerBadgeView = this.target;
        if (mobirollerBadgeView != null) {
            this.target = null;
            mobirollerBadgeView.textView = null;
            mobirollerBadgeView.mainLayout = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
