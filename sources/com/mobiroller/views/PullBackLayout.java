package com.mobiroller.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

public class PullBackLayout extends FrameLayout {
    /* access modifiers changed from: private */
    public Callback callback;
    /* access modifiers changed from: private */
    public final ViewDragHelper dragger;
    private boolean enableSwipe;
    /* access modifiers changed from: private */
    public final int minimumFlingVelocity;

    public interface Callback {
        void onPull(float f);

        void onPullCancel();

        void onPullComplete();

        void onPullStart();
    }

    private class ViewDragCallback extends androidx.customview.widget.ViewDragHelper.Callback {
        public int clampViewPositionHorizontal(View view, int i, int i2) {
            return 0;
        }

        public int getViewHorizontalDragRange(View view) {
            return 0;
        }

        public boolean tryCaptureView(View view, int i) {
            return true;
        }

        private ViewDragCallback() {
        }

        public int clampViewPositionVertical(View view, int i, int i2) {
            return Math.max(0, i);
        }

        public int getViewVerticalDragRange(View view) {
            return PullBackLayout.this.getHeight();
        }

        public void onViewCaptured(View view, int i) {
            if (PullBackLayout.this.callback != null) {
                PullBackLayout.this.callback.onPullStart();
            }
        }

        public void onViewPositionChanged(View view, int i, int i2, int i3, int i4) {
            if (PullBackLayout.this.callback != null) {
                PullBackLayout.this.callback.onPull(((float) i2) / ((float) PullBackLayout.this.getHeight()));
            }
        }

        public void onViewReleased(View view, float f, float f2) {
            if (view.getTop() <= (f2 > ((float) PullBackLayout.this.minimumFlingVelocity) ? PullBackLayout.this.getHeight() / 6 : PullBackLayout.this.getHeight() / 3)) {
                if (PullBackLayout.this.callback != null) {
                    PullBackLayout.this.callback.onPullCancel();
                }
                PullBackLayout.this.dragger.settleCapturedViewAt(0, 0);
                PullBackLayout.this.invalidate();
            } else if (PullBackLayout.this.callback != null) {
                PullBackLayout.this.callback.onPullComplete();
            }
        }
    }

    public PullBackLayout(Context context) {
        this(context, null);
    }

    public PullBackLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PullBackLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.enableSwipe = true;
        this.dragger = ViewDragHelper.create(this, 0.125f, new ViewDragCallback());
        this.minimumFlingVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
    }

    public void setCallback(Callback callback2) {
        this.callback = callback2;
    }

    public void setEnableSwipe(boolean z) {
        this.enableSwipe = z;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return this.enableSwipe && this.dragger.shouldInterceptTouchEvent(motionEvent);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.dragger.processTouchEvent(motionEvent);
        return true;
    }

    public void computeScroll() {
        if (this.dragger.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }
}
