package com.firebase.p037ui.auth.p038ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextThemeWrapper;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.firebase.p037ui.auth.C1330R;
import p101me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/* renamed from: com.firebase.ui.auth.ui.InvisibleActivityBase */
public class InvisibleActivityBase extends HelperActivityBase {
    private static final long MIN_SPINNER_MS = 750;
    private Handler mHandler = new Handler();
    /* access modifiers changed from: private */
    public long mLastShownTime = 0;
    /* access modifiers changed from: private */
    public MaterialProgressBar mProgressBar;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(C1330R.layout.fui_activity_invisible);
        this.mProgressBar = new MaterialProgressBar(new ContextThemeWrapper(this, getFlowParams().themeId));
        this.mProgressBar.setIndeterminate(true);
        this.mProgressBar.setVisibility(8);
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.gravity = 17;
        ((FrameLayout) findViewById(C1330R.C1333id.invisible_frame)).addView(this.mProgressBar, layoutParams);
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
                InvisibleActivityBase.this.mLastShownTime = 0;
                InvisibleActivityBase.this.mProgressBar.setVisibility(8);
            }
        });
    }

    public void finish(int i, Intent intent) {
        setResult(i, intent);
        doAfterTimeout(new Runnable() {
            public void run() {
                InvisibleActivityBase.this.finish();
            }
        });
    }

    private void doAfterTimeout(Runnable runnable) {
        this.mHandler.postDelayed(runnable, Math.max(MIN_SPINNER_MS - (System.currentTimeMillis() - this.mLastShownTime), 0));
    }
}
