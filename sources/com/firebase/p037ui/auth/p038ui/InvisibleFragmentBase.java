package com.firebase.p037ui.auth.p038ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.firebase.p037ui.auth.C1330R;
import p101me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/* renamed from: com.firebase.ui.auth.ui.InvisibleFragmentBase */
public class InvisibleFragmentBase extends FragmentBase {
    private static final long MIN_SPINNER_MS = 750;
    protected FrameLayout mFrameLayout;
    private Handler mHandler = new Handler();
    /* access modifiers changed from: private */
    public long mLastShownTime = 0;
    /* access modifiers changed from: private */
    public MaterialProgressBar mProgressBar;
    protected View mTopLevelView;

    public void onViewCreated(View view, Bundle bundle) {
        this.mProgressBar = new MaterialProgressBar(new ContextThemeWrapper(getContext(), getFlowParams().themeId));
        this.mProgressBar.setIndeterminate(true);
        this.mProgressBar.setVisibility(8);
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.gravity = 17;
        this.mFrameLayout = (FrameLayout) view.findViewById(C1330R.C1333id.invisible_frame);
        this.mFrameLayout.addView(this.mProgressBar, layoutParams);
    }

    public void showProgress(int i) {
        if (this.mProgressBar.getVisibility() == 0) {
            this.mHandler.removeCallbacksAndMessages(null);
            return;
        }
        this.mLastShownTime = System.currentTimeMillis();
        this.mProgressBar.setVisibility(0);
    }

    public void hideProgress() {
        doAfterTimeout(new Runnable() {
            public void run() {
                InvisibleFragmentBase.this.mLastShownTime = 0;
                InvisibleFragmentBase.this.mProgressBar.setVisibility(8);
                InvisibleFragmentBase.this.mFrameLayout.setVisibility(8);
            }
        });
    }

    /* access modifiers changed from: protected */
    public void doAfterTimeout(Runnable runnable) {
        this.mHandler.postDelayed(runnable, Math.max(MIN_SPINNER_MS - (System.currentTimeMillis() - this.mLastShownTime), 0));
    }
}
