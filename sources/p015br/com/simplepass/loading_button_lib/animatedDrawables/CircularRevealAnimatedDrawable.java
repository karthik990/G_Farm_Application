package p015br.com.simplepass.loading_button_lib.animatedDrawables;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/* renamed from: br.com.simplepass.loading_button_lib.animatedDrawables.CircularRevealAnimatedDrawable */
public class CircularRevealAnimatedDrawable extends Drawable implements Animatable {
    private float bitMapXOffset;
    private float bitMapYOffset;
    private boolean isRunning = false;
    /* access modifiers changed from: private */
    public View mAnimatedView;
    private float mCenterHeith;
    private float mCenterWidth;
    private float mFinalRadius;
    /* access modifiers changed from: private */
    public int mImageReadyAlpha;
    /* access modifiers changed from: private */
    public boolean mIsFilled;
    private Paint mPaint = new Paint();
    private Paint mPaintImageReady;
    /* access modifiers changed from: private */
    public float mRadius;
    private Bitmap mReadyImage;
    private ValueAnimator mRevealInAnimation;

    public int getOpacity() {
        return -1;
    }

    public void setAlpha(int i) {
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }

    public CircularRevealAnimatedDrawable(View view, int i, Bitmap bitmap) {
        this.mAnimatedView = view;
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Style.FILL);
        this.mPaint.setColor(i);
        this.mPaintImageReady = new Paint();
        this.mPaintImageReady.setAntiAlias(true);
        this.mPaintImageReady.setStyle(Style.FILL);
        this.mPaintImageReady.setColor(0);
        this.mReadyImage = bitmap;
        this.mImageReadyAlpha = 0;
        this.mRadius = 0.0f;
    }

    public boolean isFilled() {
        return this.mIsFilled;
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        double d = (double) (rect.right - rect.left);
        Double.isNaN(d);
        int i = (int) (d * 0.6d);
        double d2 = (double) (rect.bottom - rect.top);
        Double.isNaN(d2);
        int i2 = (int) (d2 * 0.6d);
        this.bitMapXOffset = (float) (((rect.right - rect.left) - i) / 2);
        this.bitMapYOffset = (float) (((rect.bottom - rect.top) - i2) / 2);
        this.mReadyImage = Bitmap.createScaledBitmap(this.mReadyImage, i, i2, false);
        this.mFinalRadius = (float) ((rect.right - rect.left) / 2);
        this.mCenterWidth = (float) ((rect.right + rect.left) / 2);
        this.mCenterHeith = (float) ((rect.bottom + rect.top) / 2);
    }

    private void setupAnimations() {
        final ValueAnimator ofInt = ValueAnimator.ofInt(new int[]{0, 255});
        ofInt.setDuration(80);
        ofInt.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                CircularRevealAnimatedDrawable.this.mImageReadyAlpha = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                CircularRevealAnimatedDrawable.this.invalidateSelf();
                CircularRevealAnimatedDrawable.this.mAnimatedView.invalidate();
            }
        });
        this.mRevealInAnimation = ValueAnimator.ofFloat(new float[]{0.0f, this.mFinalRadius});
        this.mRevealInAnimation.setInterpolator(new DecelerateInterpolator());
        this.mRevealInAnimation.setDuration(120);
        this.mRevealInAnimation.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                CircularRevealAnimatedDrawable.this.mRadius = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                CircularRevealAnimatedDrawable.this.invalidateSelf();
                CircularRevealAnimatedDrawable.this.mAnimatedView.invalidate();
            }
        });
        this.mRevealInAnimation.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                CircularRevealAnimatedDrawable.this.mIsFilled = true;
                ofInt.start();
            }
        });
    }

    public void start() {
        if (!isRunning()) {
            setupAnimations();
            this.isRunning = true;
            this.mRevealInAnimation.start();
        }
    }

    public void stop() {
        if (isRunning()) {
            this.isRunning = false;
            this.mRevealInAnimation.cancel();
        }
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle(this.mCenterWidth, this.mCenterHeith, this.mRadius, this.mPaint);
        if (this.mIsFilled) {
            this.mPaintImageReady.setAlpha(this.mImageReadyAlpha);
            canvas.drawBitmap(this.mReadyImage, this.bitMapXOffset, this.bitMapYOffset, this.mPaintImageReady);
        }
    }

    public void dispose() {
        ValueAnimator valueAnimator = this.mRevealInAnimation;
        if (valueAnimator != null) {
            valueAnimator.end();
            this.mRevealInAnimation.removeAllUpdateListeners();
            this.mRevealInAnimation.cancel();
        }
        this.mRevealInAnimation = null;
    }
}
