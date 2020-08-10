package com.google.android.exoplayer2.p041ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* renamed from: com.google.android.exoplayer2.ui.AspectRatioFrameLayout */
public final class AspectRatioFrameLayout extends FrameLayout {
    private static final float MAX_ASPECT_RATIO_DEFORMATION_FRACTION = 0.01f;
    public static final int RESIZE_MODE_FILL = 3;
    public static final int RESIZE_MODE_FIT = 0;
    public static final int RESIZE_MODE_FIXED_HEIGHT = 2;
    public static final int RESIZE_MODE_FIXED_WIDTH = 1;
    public static final int RESIZE_MODE_ZOOM = 4;
    /* access modifiers changed from: private */
    public AspectRatioListener aspectRatioListener;
    private final AspectRatioUpdateDispatcher aspectRatioUpdateDispatcher;
    private int resizeMode;
    private float videoAspectRatio;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: com.google.android.exoplayer2.ui.AspectRatioFrameLayout$ResizeMode */
    public @interface ResizeMode {
    }

    /* renamed from: com.google.android.exoplayer2.ui.AspectRatioFrameLayout$AspectRatioListener */
    public interface AspectRatioListener {
        void onAspectRatioUpdated(float f, float f2, boolean z);
    }

    /* renamed from: com.google.android.exoplayer2.ui.AspectRatioFrameLayout$AspectRatioUpdateDispatcher */
    private final class AspectRatioUpdateDispatcher implements Runnable {
        private boolean aspectRatioMismatch;
        private boolean isScheduled;
        private float naturalAspectRatio;
        private float targetAspectRatio;

        private AspectRatioUpdateDispatcher() {
        }

        public void scheduleUpdate(float f, float f2, boolean z) {
            this.targetAspectRatio = f;
            this.naturalAspectRatio = f2;
            this.aspectRatioMismatch = z;
            if (!this.isScheduled) {
                this.isScheduled = true;
                AspectRatioFrameLayout.this.post(this);
            }
        }

        public void run() {
            this.isScheduled = false;
            if (AspectRatioFrameLayout.this.aspectRatioListener != null) {
                AspectRatioFrameLayout.this.aspectRatioListener.onAspectRatioUpdated(this.targetAspectRatio, this.naturalAspectRatio, this.aspectRatioMismatch);
            }
        }
    }

    public AspectRatioFrameLayout(Context context) {
        this(context, null);
    }

    public AspectRatioFrameLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.resizeMode = 0;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, C2489R.styleable.AspectRatioFrameLayout, 0, 0);
            try {
                this.resizeMode = obtainStyledAttributes.getInt(C2489R.styleable.AspectRatioFrameLayout_resize_mode, 0);
            } finally {
                obtainStyledAttributes.recycle();
            }
        }
        this.aspectRatioUpdateDispatcher = new AspectRatioUpdateDispatcher();
    }

    public void setAspectRatio(float f) {
        if (this.videoAspectRatio != f) {
            this.videoAspectRatio = f;
            requestLayout();
        }
    }

    public void setAspectRatioListener(AspectRatioListener aspectRatioListener2) {
        this.aspectRatioListener = aspectRatioListener2;
    }

    public int getResizeMode() {
        return this.resizeMode;
    }

    public void setResizeMode(int i) {
        if (this.resizeMode != i) {
            this.resizeMode = i;
            requestLayout();
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        float f;
        float f2;
        super.onMeasure(i, i2);
        if (this.videoAspectRatio > 0.0f) {
            int measuredWidth = getMeasuredWidth();
            int measuredHeight = getMeasuredHeight();
            float f3 = (float) measuredWidth;
            float f4 = (float) measuredHeight;
            float f5 = f3 / f4;
            float f6 = (this.videoAspectRatio / f5) - 1.0f;
            if (Math.abs(f6) <= MAX_ASPECT_RATIO_DEFORMATION_FRACTION) {
                this.aspectRatioUpdateDispatcher.scheduleUpdate(this.videoAspectRatio, f5, false);
                return;
            }
            int i3 = this.resizeMode;
            if (i3 != 0) {
                if (i3 == 1) {
                    f2 = this.videoAspectRatio;
                } else if (i3 != 2) {
                    if (i3 == 4) {
                        if (f6 > 0.0f) {
                            f = this.videoAspectRatio;
                        } else {
                            f2 = this.videoAspectRatio;
                        }
                    }
                    this.aspectRatioUpdateDispatcher.scheduleUpdate(this.videoAspectRatio, f5, true);
                    super.onMeasure(MeasureSpec.makeMeasureSpec(measuredWidth, 1073741824), MeasureSpec.makeMeasureSpec(measuredHeight, 1073741824));
                } else {
                    f = this.videoAspectRatio;
                }
                measuredHeight = (int) (f3 / f2);
                this.aspectRatioUpdateDispatcher.scheduleUpdate(this.videoAspectRatio, f5, true);
                super.onMeasure(MeasureSpec.makeMeasureSpec(measuredWidth, 1073741824), MeasureSpec.makeMeasureSpec(measuredHeight, 1073741824));
            } else if (f6 > 0.0f) {
                f2 = this.videoAspectRatio;
                measuredHeight = (int) (f3 / f2);
                this.aspectRatioUpdateDispatcher.scheduleUpdate(this.videoAspectRatio, f5, true);
                super.onMeasure(MeasureSpec.makeMeasureSpec(measuredWidth, 1073741824), MeasureSpec.makeMeasureSpec(measuredHeight, 1073741824));
            } else {
                f = this.videoAspectRatio;
            }
            measuredWidth = (int) (f4 * f);
            this.aspectRatioUpdateDispatcher.scheduleUpdate(this.videoAspectRatio, f5, true);
            super.onMeasure(MeasureSpec.makeMeasureSpec(measuredWidth, 1073741824), MeasureSpec.makeMeasureSpec(measuredHeight, 1073741824));
        }
    }
}
