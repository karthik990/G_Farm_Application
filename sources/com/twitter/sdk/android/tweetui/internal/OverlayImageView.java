package com.twitter.sdk.android.tweetui.internal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class OverlayImageView extends ImageView {
    Overlay overlay = new Overlay(null);

    protected static class Overlay {
        final Drawable drawable;

        Overlay(Drawable drawable2) {
            this.drawable = drawable2;
        }

        /* access modifiers changed from: protected */
        public void cleanupDrawable(ImageView imageView) {
            Drawable drawable2 = this.drawable;
            if (drawable2 != null) {
                drawable2.setCallback(null);
                imageView.unscheduleDrawable(this.drawable);
            }
        }

        /* access modifiers changed from: protected */
        public void setDrawableBounds(int i, int i2) {
            Drawable drawable2 = this.drawable;
            if (drawable2 != null) {
                drawable2.setBounds(0, 0, i, i2);
            }
        }

        /* access modifiers changed from: protected */
        public void setDrawableState(int[] iArr) {
            Drawable drawable2 = this.drawable;
            if (drawable2 != null && drawable2.isStateful()) {
                this.drawable.setState(iArr);
            }
        }

        /* access modifiers changed from: protected */
        public void draw(Canvas canvas) {
            Drawable drawable2 = this.drawable;
            if (drawable2 != null) {
                drawable2.draw(canvas);
            }
        }
    }

    public OverlayImageView(Context context) {
        super(context);
    }

    public OverlayImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.overlay.draw(canvas);
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        this.overlay.setDrawableState(getDrawableState());
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        this.overlay.setDrawableBounds(getMeasuredWidth(), getMeasuredHeight());
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.overlay.setDrawableBounds(i, i2);
    }

    public void invalidateDrawable(Drawable drawable) {
        if (drawable == this.overlay.drawable) {
            invalidate();
        } else {
            super.invalidateDrawable(drawable);
        }
    }

    public void setOverlayDrawable(Drawable drawable) {
        if (drawable != this.overlay.drawable) {
            this.overlay.cleanupDrawable(this);
            if (drawable != null) {
                drawable.setCallback(this);
            }
            this.overlay = new Overlay(drawable);
            this.overlay.setDrawableState(getDrawableState());
            requestLayout();
        }
    }
}
