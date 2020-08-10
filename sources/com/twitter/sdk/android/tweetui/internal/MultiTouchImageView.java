package com.twitter.sdk.android.tweetui.internal;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.ViewParent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.twitter.sdk.android.tweetui.internal.SwipeToDismissTouchListener.SwipeableViewProvider;

public class MultiTouchImageView extends ImageView implements SwipeableViewProvider {
    private static final float DOUBLE_TAP_SCALE_FACTOR = 2.0f;
    private static final float MINIMUM_SCALE_FACTOR = 1.0f;
    private static final long SCALE_ANIMATION_DURATION = 300;
    boolean allowIntercept;
    final Matrix baseMatrix;
    final Matrix drawMatrix;
    final RectF drawRect;
    final GestureDetector gestureDetector;
    final float[] matrixValues;
    final ScaleGestureDetector scaleGestureDetector;
    final Matrix updateMatrix;
    final RectF viewRect;

    public MultiTouchImageView(Context context) {
        this(context, null);
    }

    public MultiTouchImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MultiTouchImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.drawMatrix = new Matrix();
        this.baseMatrix = new Matrix();
        this.updateMatrix = new Matrix();
        this.viewRect = new RectF();
        this.drawRect = new RectF();
        this.matrixValues = new float[9];
        this.scaleGestureDetector = new ScaleGestureDetector(context, new SimpleOnScaleGestureListener() {
            public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
                MultiTouchImageView.this.setScale(scaleGestureDetector.getScaleFactor(), scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY());
                MultiTouchImageView.this.setImageMatrix();
                return true;
            }

            public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
                if (MultiTouchImageView.this.getScale() < 1.0f) {
                    MultiTouchImageView.this.reset();
                    MultiTouchImageView.this.setImageMatrix();
                }
            }
        });
        this.gestureDetector = new GestureDetector(context, new SimpleOnGestureListener() {
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                MultiTouchImageView.this.setTranslate(-f, -f2);
                MultiTouchImageView.this.setImageMatrix();
                if (MultiTouchImageView.this.allowIntercept && !MultiTouchImageView.this.scaleGestureDetector.isInProgress()) {
                    MultiTouchImageView.this.requestDisallowInterceptTouchEvent(false);
                }
                return true;
            }

            public boolean onDoubleTap(MotionEvent motionEvent) {
                if (MultiTouchImageView.this.getScale() > 1.0f) {
                    MultiTouchImageView multiTouchImageView = MultiTouchImageView.this;
                    multiTouchImageView.animateScale(multiTouchImageView.getScale(), 1.0f, motionEvent.getX(), motionEvent.getY());
                } else {
                    MultiTouchImageView multiTouchImageView2 = MultiTouchImageView.this;
                    multiTouchImageView2.animateScale(multiTouchImageView2.getScale(), MultiTouchImageView.DOUBLE_TAP_SCALE_FACTOR, motionEvent.getX(), motionEvent.getY());
                }
                return true;
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public boolean isInitializationComplete() {
        Drawable drawable = getDrawable();
        return drawable != null && drawable.getIntrinsicWidth() > 0;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (isInitializationComplete()) {
            initializeViewRect();
            initializeBaseMatrix(getDrawable());
            setImageMatrix();
        }
    }

    /* access modifiers changed from: 0000 */
    public void initializeViewRect() {
        this.viewRect.set((float) getPaddingLeft(), (float) getPaddingTop(), (float) (getWidth() - getPaddingRight()), (float) (getHeight() - getPaddingBottom()));
    }

    /* access modifiers changed from: 0000 */
    public void initializeBaseMatrix(Drawable drawable) {
        RectF rectF = new RectF(0.0f, 0.0f, (float) drawable.getIntrinsicWidth(), (float) drawable.getIntrinsicHeight());
        this.baseMatrix.reset();
        this.baseMatrix.setRectToRect(rectF, this.viewRect, ScaleToFit.CENTER);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!isInitializationComplete()) {
            return false;
        }
        boolean z = true;
        requestDisallowInterceptTouchEvent(true);
        if (!(this.gestureDetector.onTouchEvent(motionEvent) || this.scaleGestureDetector.onTouchEvent(motionEvent)) && !super.onTouchEvent(motionEvent)) {
            z = false;
        }
        return z;
    }

    /* access modifiers changed from: 0000 */
    public void requestDisallowInterceptTouchEvent(boolean z) {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(z);
        }
    }

    /* access modifiers changed from: 0000 */
    public void setScale(float f, float f2, float f3) {
        this.updateMatrix.postScale(f, f, f2, f3);
    }

    /* access modifiers changed from: 0000 */
    public float getScale() {
        this.updateMatrix.getValues(this.matrixValues);
        return this.matrixValues[0];
    }

    /* access modifiers changed from: 0000 */
    public void setTranslate(float f, float f2) {
        this.updateMatrix.postTranslate(f, f2);
    }

    /* access modifiers changed from: 0000 */
    public void reset() {
        this.updateMatrix.reset();
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0058  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x006b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateMatrixBounds() {
        /*
            r7 = this;
            android.graphics.Matrix r0 = r7.getDrawMatrix()
            android.graphics.RectF r0 = r7.getDrawRect(r0)
            float r1 = r0.height()
            android.graphics.RectF r2 = r7.viewRect
            float r2 = r2.height()
            r3 = 1073741824(0x40000000, float:2.0)
            r4 = 0
            int r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r1 > 0) goto L_0x0029
            android.graphics.RectF r1 = r7.viewRect
            float r1 = r1.height()
            float r2 = r0.height()
            float r1 = r1 - r2
            float r1 = r1 / r3
            float r2 = r0.top
        L_0x0027:
            float r1 = r1 - r2
            goto L_0x0049
        L_0x0029:
            float r1 = r0.top
            int r1 = (r1 > r4 ? 1 : (r1 == r4 ? 0 : -1))
            if (r1 <= 0) goto L_0x0033
            float r1 = r0.top
            float r1 = -r1
            goto L_0x0049
        L_0x0033:
            float r1 = r0.bottom
            android.graphics.RectF r2 = r7.viewRect
            float r2 = r2.height()
            int r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r1 >= 0) goto L_0x0048
            android.graphics.RectF r1 = r7.viewRect
            float r1 = r1.height()
            float r2 = r0.bottom
            goto L_0x0027
        L_0x0048:
            r1 = 0
        L_0x0049:
            float r2 = r0.width()
            android.graphics.RectF r5 = r7.viewRect
            float r5 = r5.width()
            r6 = 1
            int r2 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1))
            if (r2 > 0) goto L_0x006b
            r7.allowIntercept = r6
            android.graphics.RectF r2 = r7.viewRect
            float r2 = r2.width()
            float r4 = r0.width()
            float r2 = r2 - r4
            float r2 = r2 / r3
            float r0 = r0.left
        L_0x0068:
            float r4 = r2 - r0
            goto L_0x0091
        L_0x006b:
            float r2 = r0.left
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 <= 0) goto L_0x0077
            r7.allowIntercept = r6
            float r0 = r0.left
            float r4 = -r0
            goto L_0x0091
        L_0x0077:
            float r2 = r0.right
            android.graphics.RectF r3 = r7.viewRect
            float r3 = r3.width()
            int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r2 >= 0) goto L_0x008e
            r7.allowIntercept = r6
            android.graphics.RectF r2 = r7.viewRect
            float r2 = r2.width()
            float r0 = r0.right
            goto L_0x0068
        L_0x008e:
            r0 = 0
            r7.allowIntercept = r0
        L_0x0091:
            r7.setTranslate(r4, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.twitter.sdk.android.tweetui.internal.MultiTouchImageView.updateMatrixBounds():void");
    }

    /* access modifiers changed from: 0000 */
    public RectF getDrawRect(Matrix matrix) {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            this.drawRect.set(0.0f, 0.0f, (float) drawable.getIntrinsicWidth(), (float) drawable.getIntrinsicHeight());
            matrix.mapRect(this.drawRect);
        }
        return this.drawRect;
    }

    /* access modifiers changed from: 0000 */
    public Matrix getDrawMatrix() {
        this.drawMatrix.set(this.baseMatrix);
        this.drawMatrix.postConcat(this.updateMatrix);
        return this.drawMatrix;
    }

    /* access modifiers changed from: 0000 */
    public void setImageMatrix() {
        updateMatrixBounds();
        setScaleType(ScaleType.MATRIX);
        setImageMatrix(getDrawMatrix());
    }

    /* access modifiers changed from: 0000 */
    public void animateScale(float f, float f2, float f3, float f4) {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{f, f2});
        ofFloat.setDuration(300);
        ofFloat.setInterpolator(new AccelerateDecelerateInterpolator());
        ofFloat.addUpdateListener(new AnimatorUpdateListener(f3, f4) {
            private final /* synthetic */ float f$1;
            private final /* synthetic */ float f$2;

            {
                this.f$1 = r2;
                this.f$2 = r3;
            }

            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                MultiTouchImageView.this.lambda$animateScale$0$MultiTouchImageView(this.f$1, this.f$2, valueAnimator);
            }
        });
        ofFloat.start();
    }

    public /* synthetic */ void lambda$animateScale$0$MultiTouchImageView(float f, float f2, ValueAnimator valueAnimator) {
        setScale(((Float) valueAnimator.getAnimatedValue()).floatValue() / getScale(), f, f2);
        setImageMatrix();
    }

    public boolean canBeSwiped() {
        return getScale() == 1.0f;
    }
}
