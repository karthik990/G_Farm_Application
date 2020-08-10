package com.facebook.shimmer;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;

public final class ShimmerDrawable extends Drawable {
    private final Rect mDrawRect = new Rect();
    private final Matrix mShaderMatrix = new Matrix();
    private Shimmer mShimmer;
    private final Paint mShimmerPaint = new Paint();
    private final AnimatorUpdateListener mUpdateListener = new AnimatorUpdateListener() {
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            ShimmerDrawable.this.invalidateSelf();
        }
    };
    private ValueAnimator mValueAnimator;

    private float offset(float f, float f2, float f3) {
        return f + ((f2 - f) * f3);
    }

    public void setAlpha(int i) {
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }

    public ShimmerDrawable() {
        this.mShimmerPaint.setAntiAlias(true);
    }

    public void setShimmer(Shimmer shimmer) {
        this.mShimmer = shimmer;
        Shimmer shimmer2 = this.mShimmer;
        if (shimmer2 != null) {
            this.mShimmerPaint.setXfermode(new PorterDuffXfermode(shimmer2.alphaShimmer ? Mode.DST_IN : Mode.SRC_IN));
        }
        updateShader();
        updateValueAnimator();
        invalidateSelf();
    }

    public void startShimmer() {
        if (this.mValueAnimator != null && !isShimmerStarted() && getCallback() != null) {
            this.mValueAnimator.start();
        }
    }

    public void stopShimmer() {
        if (this.mValueAnimator != null && isShimmerStarted()) {
            this.mValueAnimator.cancel();
        }
    }

    public boolean isShimmerStarted() {
        ValueAnimator valueAnimator = this.mValueAnimator;
        return valueAnimator != null && valueAnimator.isStarted();
    }

    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.mDrawRect.set(0, 0, rect.width(), rect.height());
        updateShader();
        maybeStartShimmer();
    }

    public void draw(Canvas canvas) {
        float f;
        float f2;
        if (this.mShimmer != null && this.mShimmerPaint.getShader() != null) {
            float tan = (float) Math.tan(Math.toRadians((double) this.mShimmer.tilt));
            float height = ((float) this.mDrawRect.height()) + (((float) this.mDrawRect.width()) * tan);
            float width = ((float) this.mDrawRect.width()) + (tan * ((float) this.mDrawRect.height()));
            ValueAnimator valueAnimator = this.mValueAnimator;
            float f3 = 0.0f;
            float animatedFraction = valueAnimator != null ? valueAnimator.getAnimatedFraction() : 0.0f;
            int i = this.mShimmer.direction;
            if (i != 1) {
                if (i == 2) {
                    f = offset(width, -width, animatedFraction);
                } else if (i != 3) {
                    f = offset(-width, width, animatedFraction);
                } else {
                    f2 = offset(height, -height, animatedFraction);
                }
                this.mShaderMatrix.reset();
                this.mShaderMatrix.setRotate(this.mShimmer.tilt, ((float) this.mDrawRect.width()) / 2.0f, ((float) this.mDrawRect.height()) / 2.0f);
                this.mShaderMatrix.postTranslate(f, f3);
                this.mShimmerPaint.getShader().setLocalMatrix(this.mShaderMatrix);
                canvas.drawRect(this.mDrawRect, this.mShimmerPaint);
            }
            f2 = offset(-height, height, animatedFraction);
            f3 = f2;
            f = 0.0f;
            this.mShaderMatrix.reset();
            this.mShaderMatrix.setRotate(this.mShimmer.tilt, ((float) this.mDrawRect.width()) / 2.0f, ((float) this.mDrawRect.height()) / 2.0f);
            this.mShaderMatrix.postTranslate(f, f3);
            this.mShimmerPaint.getShader().setLocalMatrix(this.mShaderMatrix);
            canvas.drawRect(this.mDrawRect, this.mShimmerPaint);
        }
    }

    public int getOpacity() {
        Shimmer shimmer = this.mShimmer;
        return (shimmer == null || (!shimmer.clipToChildren && !this.mShimmer.alphaShimmer)) ? -1 : -3;
    }

    private void updateValueAnimator() {
        boolean z;
        if (this.mShimmer != null) {
            ValueAnimator valueAnimator = this.mValueAnimator;
            if (valueAnimator != null) {
                z = valueAnimator.isStarted();
                this.mValueAnimator.cancel();
                this.mValueAnimator.removeAllUpdateListeners();
            } else {
                z = false;
            }
            this.mValueAnimator = ValueAnimator.ofFloat(new float[]{0.0f, ((float) (this.mShimmer.repeatDelay / this.mShimmer.animationDuration)) + 1.0f});
            this.mValueAnimator.setRepeatMode(this.mShimmer.repeatMode);
            this.mValueAnimator.setRepeatCount(this.mShimmer.repeatCount);
            this.mValueAnimator.setDuration(this.mShimmer.animationDuration + this.mShimmer.repeatDelay);
            this.mValueAnimator.addUpdateListener(this.mUpdateListener);
            if (z) {
                this.mValueAnimator.start();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void maybeStartShimmer() {
        ValueAnimator valueAnimator = this.mValueAnimator;
        if (valueAnimator != null && !valueAnimator.isStarted()) {
            Shimmer shimmer = this.mShimmer;
            if (shimmer != null && shimmer.autoStart && getCallback() != null) {
                this.mValueAnimator.start();
            }
        }
    }

    private void updateShader() {
        RadialGradient radialGradient;
        Rect bounds = getBounds();
        int width = bounds.width();
        int height = bounds.height();
        if (width != 0 && height != 0) {
            Shimmer shimmer = this.mShimmer;
            if (shimmer != null) {
                int width2 = shimmer.width(width);
                int height2 = this.mShimmer.height(height);
                boolean z = true;
                if (this.mShimmer.shape != 1) {
                    if (!(this.mShimmer.direction == 1 || this.mShimmer.direction == 3)) {
                        z = false;
                    }
                    if (z) {
                        width2 = 0;
                    }
                    if (!z) {
                        height2 = 0;
                    }
                    LinearGradient linearGradient = new LinearGradient(0.0f, 0.0f, (float) width2, (float) height2, this.mShimmer.colors, this.mShimmer.positions, TileMode.CLAMP);
                    radialGradient = linearGradient;
                } else {
                    float f = ((float) width2) / 2.0f;
                    float f2 = ((float) height2) / 2.0f;
                    double max = (double) Math.max(width2, height2);
                    double sqrt = Math.sqrt(2.0d);
                    Double.isNaN(max);
                    float f3 = (float) (max / sqrt);
                    int[] iArr = this.mShimmer.colors;
                    RadialGradient radialGradient2 = new RadialGradient(f, f2, f3, iArr, this.mShimmer.positions, TileMode.CLAMP);
                    radialGradient = radialGradient2;
                }
                this.mShimmerPaint.setShader(radialGradient);
            }
        }
    }
}
