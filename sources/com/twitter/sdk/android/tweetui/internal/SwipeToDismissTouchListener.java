package com.twitter.sdk.android.tweetui.internal;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;

public class SwipeToDismissTouchListener implements OnTouchListener {
    private Callback callback;
    private final float closeThreshold;
    private float initialY;
    private boolean isMoving;
    private float lastX;
    private float lastY;
    private final float maxTranslate;
    private int pointerIndex;
    private int touchSlop;

    public interface Callback {
        void onDismiss();

        void onMove(float f);
    }

    public interface SwipeableViewProvider {
        boolean canBeSwiped();
    }

    public static SwipeToDismissTouchListener createFromView(View view, Callback callback2) {
        return new SwipeToDismissTouchListener(callback2, ViewConfiguration.get(view.getContext()).getScaledTouchSlop(), ((float) view.getContext().getResources().getDisplayMetrics().heightPixels) * 0.5f);
    }

    SwipeToDismissTouchListener(Callback callback2, int i, float f) {
        this(callback2, i, f, 0.2f * f);
    }

    SwipeToDismissTouchListener(Callback callback2, int i, float f, float f2) {
        setCallback(callback2);
        this.touchSlop = i;
        this.maxTranslate = f;
        this.closeThreshold = f2;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        boolean z;
        if (!(view instanceof SwipeableViewProvider) || ((SwipeableViewProvider) view).canBeSwiped() || isMoving()) {
            z = handleTouchEvent(view, motionEvent);
        } else {
            z = false;
        }
        if (z || view.onTouchEvent(motionEvent)) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public boolean handleTouchEvent(View view, MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 0) {
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    float rawX = motionEvent.getRawX();
                    float rawY = motionEvent.getRawY();
                    float f = rawY - this.initialY;
                    float f2 = rawX - this.lastX;
                    float f3 = rawY - this.lastY;
                    this.lastX = rawX;
                    this.lastY = rawY;
                    if (isValidPointer(motionEvent) && (this.isMoving || (hasMovedEnoughInProperYDirection(f) && hasMovedMoreInYDirectionThanX(f2, f3)))) {
                        this.isMoving = true;
                        moveView(view, f3);
                    }
                } else if (actionMasked != 3) {
                    if (actionMasked == 5) {
                        settleView(view);
                        this.isMoving = false;
                        this.pointerIndex = -1;
                    }
                }
            }
            boolean z = (!isValidPointer(motionEvent) || !this.isMoving) ? false : settleOrCloseView(view);
            this.isMoving = false;
            return z;
        }
        this.lastX = motionEvent.getRawX();
        float rawY2 = motionEvent.getRawY();
        this.lastY = rawY2;
        this.initialY = rawY2;
        this.isMoving = false;
        this.pointerIndex = motionEvent.getPointerId(motionEvent.getPointerCount() - 1);
        return false;
    }

    /* access modifiers changed from: 0000 */
    public boolean hasMovedEnoughInProperYDirection(float f) {
        return Math.abs(f) > ((float) this.touchSlop);
    }

    /* access modifiers changed from: 0000 */
    public boolean hasMovedMoreInYDirectionThanX(float f, float f2) {
        return Math.abs(f2) > Math.abs(f);
    }

    /* access modifiers changed from: 0000 */
    public boolean isMoving() {
        return this.isMoving;
    }

    /* access modifiers changed from: 0000 */
    public boolean isValidPointer(MotionEvent motionEvent) {
        return this.pointerIndex >= 0 && motionEvent.getPointerCount() == 1;
    }

    /* access modifiers changed from: 0000 */
    public boolean settleOrCloseView(View view) {
        float translationY = view.getTranslationY();
        float f = this.closeThreshold;
        if (translationY > f || translationY < (-f)) {
            Callback callback2 = this.callback;
            if (callback2 != null) {
                callback2.onDismiss();
            }
            return true;
        }
        settleView(view);
        return false;
    }

    /* access modifiers changed from: 0000 */
    public void settleView(View view) {
        if (view.getTranslationY() != 0.0f) {
            ObjectAnimator duration = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, new float[]{0.0f}).setDuration(100);
            duration.addUpdateListener(new AnimatorUpdateListener() {
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    SwipeToDismissTouchListener.this.lambda$settleView$0$SwipeToDismissTouchListener(valueAnimator);
                }
            });
            duration.start();
        }
    }

    public /* synthetic */ void lambda$settleView$0$SwipeToDismissTouchListener(ValueAnimator valueAnimator) {
        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        Callback callback2 = this.callback;
        if (callback2 != null) {
            callback2.onMove(floatValue);
        }
    }

    /* access modifiers changed from: 0000 */
    public void moveView(View view, float f) {
        float translationY = view.getTranslationY();
        double d = (double) f;
        double calculateTension = calculateTension(translationY);
        Double.isNaN(d);
        float bound = bound(translationY + ((float) (d * calculateTension)));
        view.setTranslationY(bound);
        Callback callback2 = this.callback;
        if (callback2 != null) {
            callback2.onMove(bound);
        }
    }

    /* access modifiers changed from: 0000 */
    public double calculateTension(float f) {
        return 1.0d - (Math.pow((double) Math.abs(f), 2.0d) / Math.pow((double) (this.closeThreshold * 2.0f), 2.0d));
    }

    /* access modifiers changed from: 0000 */
    public float bound(float f) {
        float f2 = this.maxTranslate;
        if (f < (-f2)) {
            return -f2;
        }
        return f > f2 ? f2 : f;
    }

    public void setCallback(Callback callback2) {
        this.callback = callback2;
    }
}
