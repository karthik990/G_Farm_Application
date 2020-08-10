package com.startapp.android.publish.inappbrowser;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ProgressBar;

/* compiled from: StartAppSDK */
public class AnimatingProgressBar extends ProgressBar {

    /* renamed from: a */
    private static final Interpolator f3491a = new AccelerateDecelerateInterpolator();

    /* renamed from: b */
    private ValueAnimator f3492b;

    /* renamed from: c */
    private boolean f3493c = false;

    public AnimatingProgressBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        boolean z = false;
        if (VERSION.SDK_INT >= 11) {
            z = true;
        }
        this.f3493c = z;
    }

    public void setProgress(int i) {
        if (!this.f3493c) {
            super.setProgress(i);
            return;
        }
        ValueAnimator valueAnimator = this.f3492b;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            if (getProgress() >= i) {
                return;
            }
        } else {
            this.f3492b = ValueAnimator.ofInt(new int[]{getProgress(), i});
            this.f3492b.setInterpolator(f3491a);
            this.f3492b.addUpdateListener(new AnimatorUpdateListener() {

                /* renamed from: a */
                Integer f3494a;

                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    this.f3494a = (Integer) valueAnimator.getAnimatedValue();
                    AnimatingProgressBar.super.setProgress(this.f3494a.intValue());
                }
            });
        }
        this.f3492b.setIntValues(new int[]{getProgress(), i});
        this.f3492b.start();
    }

    /* access modifiers changed from: protected */
    public ValueAnimator getAnimator() {
        return this.f3492b;
    }

    /* renamed from: a */
    public void mo62807a() {
        super.setProgress(0);
        ValueAnimator valueAnimator = this.f3492b;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ValueAnimator valueAnimator = this.f3492b;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
    }
}
