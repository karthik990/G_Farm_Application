package p015br.com.simplepass.loading_button_lib.animatedDrawables;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;

/* renamed from: br.com.simplepass.loading_button_lib.animatedDrawables.CircularAnimatedDrawable */
public class CircularAnimatedDrawable extends Drawable implements Animatable {
    private static final int ANGLE_ANIMATOR_DURATION = 2000;
    private static final Interpolator ANGLE_INTERPOLATOR = new LinearInterpolator();
    private static final Float MIN_SWEEP_ANGLE = Float.valueOf(30.0f);
    private static final int SWEEP_ANIMATOR_DURATION = 900;
    private static final Interpolator SWEEP_INTERPOLATOR = new DecelerateInterpolator();
    private final RectF fBounds = new RectF();
    /* access modifiers changed from: private */
    public View mAnimatedView;
    private float mBorderWidth;
    private float mCurrentGlobalAngle;
    private float mCurrentGlobalAngleOffset;
    private float mCurrentSweepAngle;
    private boolean mModeAppearing;
    private Paint mPaint;
    private boolean mRunning;
    private ValueAnimator mValueAnimatorAngle;
    private ValueAnimator mValueAnimatorSweep;

    public int getOpacity() {
        return -2;
    }

    public CircularAnimatedDrawable(View view, float f, int i) {
        this.mAnimatedView = view;
        this.mBorderWidth = f;
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Style.STROKE);
        this.mPaint.setStrokeWidth(f);
        this.mPaint.setColor(i);
        setupAnimations();
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.fBounds.left = ((float) rect.left) + (this.mBorderWidth / 2.0f) + 0.5f;
        this.fBounds.right = (((float) rect.right) - (this.mBorderWidth / 2.0f)) - 0.5f;
        this.fBounds.top = ((float) rect.top) + (this.mBorderWidth / 2.0f) + 0.5f;
        this.fBounds.bottom = (((float) rect.bottom) - (this.mBorderWidth / 2.0f)) - 0.5f;
    }

    public void start() {
        if (!isRunning()) {
            this.mRunning = true;
            this.mValueAnimatorAngle.start();
            this.mValueAnimatorSweep.start();
        }
    }

    public void stop() {
        if (isRunning()) {
            this.mRunning = false;
            this.mValueAnimatorAngle.cancel();
            this.mValueAnimatorSweep.cancel();
        }
    }

    public boolean isRunning() {
        return this.mRunning;
    }

    public void draw(Canvas canvas) {
        float f;
        float f2 = this.mCurrentGlobalAngle - this.mCurrentGlobalAngleOffset;
        float f3 = this.mCurrentSweepAngle;
        if (!this.mModeAppearing) {
            f2 += f3;
            f = (360.0f - f3) - MIN_SWEEP_ANGLE.floatValue();
        } else {
            f = MIN_SWEEP_ANGLE.floatValue() + f3;
        }
        Canvas canvas2 = canvas;
        canvas2.drawArc(this.fBounds, f2, f, false, this.mPaint);
    }

    public void setAlpha(int i) {
        this.mPaint.setAlpha(i);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }

    public void setCurrentGlobalAngle(float f) {
        this.mCurrentGlobalAngle = f;
        invalidateSelf();
    }

    public void setCurrentSweepAngle(float f) {
        this.mCurrentSweepAngle = f;
        invalidateSelf();
    }

    private void setupAnimations() {
        this.mValueAnimatorAngle = ValueAnimator.ofFloat(new float[]{0.0f, 360.0f});
        this.mValueAnimatorAngle.setInterpolator(ANGLE_INTERPOLATOR);
        this.mValueAnimatorAngle.setDuration(AdaptiveTrackSelection.DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS);
        this.mValueAnimatorAngle.setRepeatCount(-1);
        this.mValueAnimatorAngle.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                CircularAnimatedDrawable.this.setCurrentGlobalAngle(((Float) valueAnimator.getAnimatedValue()).floatValue());
                CircularAnimatedDrawable.this.mAnimatedView.invalidate();
            }
        });
        this.mValueAnimatorSweep = ValueAnimator.ofFloat(new float[]{0.0f, 360.0f - (MIN_SWEEP_ANGLE.floatValue() * 2.0f)});
        this.mValueAnimatorSweep.setInterpolator(SWEEP_INTERPOLATOR);
        this.mValueAnimatorSweep.setDuration(900);
        this.mValueAnimatorSweep.setRepeatCount(-1);
        this.mValueAnimatorSweep.addListener(new AnimatorListenerAdapter() {
            public void onAnimationRepeat(Animator animator) {
                CircularAnimatedDrawable.this.toggleAppearingMode();
            }
        });
        this.mValueAnimatorSweep.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                CircularAnimatedDrawable.this.setCurrentSweepAngle(((Float) valueAnimator.getAnimatedValue()).floatValue());
                CircularAnimatedDrawable.this.mAnimatedView.invalidate();
            }
        });
    }

    /* access modifiers changed from: private */
    public void toggleAppearingMode() {
        this.mModeAppearing = !this.mModeAppearing;
        if (this.mModeAppearing) {
            this.mCurrentGlobalAngleOffset = (this.mCurrentGlobalAngleOffset + (MIN_SWEEP_ANGLE.floatValue() * 2.0f)) % 360.0f;
        }
    }

    public void dispose() {
        ValueAnimator valueAnimator = this.mValueAnimatorAngle;
        if (valueAnimator != null) {
            valueAnimator.end();
            this.mValueAnimatorAngle.removeAllUpdateListeners();
            this.mValueAnimatorAngle.cancel();
        }
        this.mValueAnimatorAngle = null;
        ValueAnimator valueAnimator2 = this.mValueAnimatorSweep;
        if (valueAnimator2 != null) {
            valueAnimator2.end();
            this.mValueAnimatorSweep.removeAllUpdateListeners();
            this.mValueAnimatorSweep.cancel();
        }
        this.mValueAnimatorSweep = null;
    }
}
