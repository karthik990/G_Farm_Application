package com.mobiroller.activities;

import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import butterknife.internal.DebouncingOnClickListener;
import com.mobiroller.mobi942763453128.R;

public class MobiRollerBadgeActivity_ViewBinding implements Unbinder {
    private MobiRollerBadgeActivity target;
    private View view7f0a00f4;
    private View view7f0a0177;

    public MobiRollerBadgeActivity_ViewBinding(MobiRollerBadgeActivity mobiRollerBadgeActivity) {
        this(mobiRollerBadgeActivity, mobiRollerBadgeActivity.getWindow().getDecorView());
    }

    public MobiRollerBadgeActivity_ViewBinding(final MobiRollerBadgeActivity mobiRollerBadgeActivity, View view) {
        this.target = mobiRollerBadgeActivity;
        mobiRollerBadgeActivity.webViewLayout = (ConstraintLayout) C0812Utils.findRequiredViewAsType(view, R.id.web_view_layout, "field 'webViewLayout'", ConstraintLayout.class);
        mobiRollerBadgeActivity.designLayout = (ConstraintLayout) C0812Utils.findRequiredViewAsType(view, R.id.design_layout, "field 'designLayout'", ConstraintLayout.class);
        mobiRollerBadgeActivity.webView = (WebView) C0812Utils.findRequiredViewAsType(view, R.id.web_view, "field 'webView'", WebView.class);
        mobiRollerBadgeActivity.description = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.description, "field 'description'", TextView.class);
        mobiRollerBadgeActivity.buttonText = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.button_text, "field 'buttonText'", TextView.class);
        View findRequiredView = C0812Utils.findRequiredView(view, R.id.button, "method 'onClickGoButton'");
        this.view7f0a00f4 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                mobiRollerBadgeActivity.onClickGoButton();
            }
        });
        View findRequiredView2 = C0812Utils.findRequiredView(view, R.id.close_button, "method 'onClickCloseButton'");
        this.view7f0a0177 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                mobiRollerBadgeActivity.onClickCloseButton();
            }
        });
    }

    public void unbind() {
        MobiRollerBadgeActivity mobiRollerBadgeActivity = this.target;
        if (mobiRollerBadgeActivity != null) {
            this.target = null;
            mobiRollerBadgeActivity.webViewLayout = null;
            mobiRollerBadgeActivity.designLayout = null;
            mobiRollerBadgeActivity.webView = null;
            mobiRollerBadgeActivity.description = null;
            mobiRollerBadgeActivity.buttonText = null;
            this.view7f0a00f4.setOnClickListener(null);
            this.view7f0a00f4 = null;
            this.view7f0a0177.setOnClickListener(null);
            this.view7f0a0177 = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
