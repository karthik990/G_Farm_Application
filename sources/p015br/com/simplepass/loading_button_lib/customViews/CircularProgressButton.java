package p015br.com.simplepass.loading_button_lib.customViews;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import p015br.com.simplepass.loading_button_lib.C0769R;
import p015br.com.simplepass.loading_button_lib.C0773Utils;
import p015br.com.simplepass.loading_button_lib.UtilsJava;
import p015br.com.simplepass.loading_button_lib.animatedDrawables.CircularAnimatedDrawable;
import p015br.com.simplepass.loading_button_lib.animatedDrawables.CircularRevealAnimatedDrawable;
import p015br.com.simplepass.loading_button_lib.interfaces.AnimatedButton;
import p015br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;

/* renamed from: br.com.simplepass.loading_button_lib.customViews.CircularProgressButton */
public class CircularProgressButton extends Button implements AnimatedButton {
    /* access modifiers changed from: private */
    public boolean doneWhileMorphing;
    private CircularAnimatedDrawable mAnimatedDrawable;
    private AnimatorSet mAnimatorSet;
    /* access modifiers changed from: private */
    public Bitmap mBitmapDone;
    /* access modifiers changed from: private */
    public int mFillColorDone;
    private GradientDrawable mGradientDrawable;
    /* access modifiers changed from: private */
    public boolean mIsMorphingInProgress;
    /* access modifiers changed from: private */
    public Params mParams;
    private CircularRevealAnimatedDrawable mRevealDrawable;
    private State mState;

    /* renamed from: br.com.simplepass.loading_button_lib.customViews.CircularProgressButton$Params */
    private class Params {
        private int mDoneColor;
        /* access modifiers changed from: private */
        public float mFinalCornerRadius;
        /* access modifiers changed from: private */
        public float mInitialCornerRadius;
        /* access modifiers changed from: private */
        public Integer mInitialHeight;
        /* access modifiers changed from: private */
        public int mInitialWidth;
        /* access modifiers changed from: private */
        public Float mPaddingProgress;
        /* access modifiers changed from: private */
        public int mSpinningBarColor;
        /* access modifiers changed from: private */
        public float mSpinningBarWidth;
        /* access modifiers changed from: private */
        public String mText;

        private Params() {
        }
    }

    /* renamed from: br.com.simplepass.loading_button_lib.customViews.CircularProgressButton$State */
    private enum State {
        PROGRESS,
        IDLE,
        DONE,
        STOPED
    }

    public CircularProgressButton(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public CircularProgressButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet, 0, 0);
    }

    public CircularProgressButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet, i, 0);
    }

    public CircularProgressButton(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        init(context, attributeSet, i, i2);
    }

    private void init(Context context, AttributeSet attributeSet, int i, int i2) {
        this.mParams = new Params();
        this.mParams.mPaddingProgress = Float.valueOf(0.0f);
        if (attributeSet == null) {
            this.mGradientDrawable = (GradientDrawable) UtilsJava.getDrawable(getContext(), C0769R.C0771drawable.shape_default);
        } else {
            int[] iArr = {16842964};
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C0769R.styleable.CircularProgressButton, i, i2);
            TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, iArr, i, i2);
            try {
                this.mGradientDrawable = (GradientDrawable) obtainStyledAttributes2.getDrawable(0);
            } catch (ClassCastException unused) {
                Drawable drawable = obtainStyledAttributes2.getDrawable(0);
                if (drawable instanceof ColorDrawable) {
                    ColorDrawable colorDrawable = (ColorDrawable) drawable;
                    this.mGradientDrawable = new GradientDrawable();
                    this.mGradientDrawable.setColor(colorDrawable.getColor());
                } else if (drawable instanceof StateListDrawable) {
                    try {
                        this.mGradientDrawable = (GradientDrawable) ((StateListDrawable) drawable).getCurrent();
                    } catch (ClassCastException e) {
                        throw new RuntimeException("Error reading background... Use a shape or a color in xml!", e.getCause());
                    }
                }
            }
            this.mParams.mInitialCornerRadius = obtainStyledAttributes.getDimension(C0769R.styleable.CircularProgressButton_initialCornerAngle, 0.0f);
            this.mParams.mFinalCornerRadius = obtainStyledAttributes.getDimension(C0769R.styleable.CircularProgressButton_finalCornerAngle, 100.0f);
            this.mParams.mSpinningBarWidth = obtainStyledAttributes.getDimension(C0769R.styleable.CircularProgressButton_spinning_bar_width, 10.0f);
            this.mParams.mSpinningBarColor = obtainStyledAttributes.getColor(C0769R.styleable.CircularProgressButton_spinning_bar_color, C0773Utils.Companion.getColorWrapper(context, 17170444));
            this.mParams.mPaddingProgress = Float.valueOf(obtainStyledAttributes.getDimension(C0769R.styleable.CircularProgressButton_spinning_bar_padding, 0.0f));
            obtainStyledAttributes.recycle();
            obtainStyledAttributes2.recycle();
        }
        this.mState = State.IDLE;
        this.mParams.mText = getText().toString();
        setBackground(this.mGradientDrawable);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mState == State.PROGRESS && !this.mIsMorphingInProgress) {
            drawIndeterminateProgress(canvas);
        } else if (this.mState == State.DONE) {
            drawDoneAnimation(canvas);
        }
    }

    private void drawIndeterminateProgress(Canvas canvas) {
        CircularAnimatedDrawable circularAnimatedDrawable = this.mAnimatedDrawable;
        if (circularAnimatedDrawable == null || !circularAnimatedDrawable.isRunning()) {
            this.mAnimatedDrawable = new CircularAnimatedDrawable(this, this.mParams.mSpinningBarWidth, this.mParams.mSpinningBarColor);
            int width = (getWidth() - getHeight()) / 2;
            int intValue = this.mParams.mPaddingProgress.intValue() + width;
            int width2 = (getWidth() - width) - this.mParams.mPaddingProgress.intValue();
            int height = getHeight() - this.mParams.mPaddingProgress.intValue();
            this.mAnimatedDrawable.setBounds(intValue, this.mParams.mPaddingProgress.intValue(), width2, height);
            this.mAnimatedDrawable.setCallback(this);
            this.mAnimatedDrawable.start();
            return;
        }
        this.mAnimatedDrawable.draw(canvas);
    }

    public void stopAnimation() {
        if (this.mState == State.PROGRESS && !this.mIsMorphingInProgress) {
            this.mState = State.STOPED;
            this.mAnimatedDrawable.stop();
        }
    }

    public void doneLoadingAnimation(int i, Bitmap bitmap) {
        if (this.mState == State.PROGRESS) {
            if (this.mIsMorphingInProgress) {
                this.doneWhileMorphing = true;
                this.mFillColorDone = i;
                this.mBitmapDone = bitmap;
                return;
            }
            this.mState = State.DONE;
            this.mAnimatedDrawable.stop();
            this.mRevealDrawable = new CircularRevealAnimatedDrawable(this, i, bitmap);
            this.mRevealDrawable.setBounds(0, 0, getWidth(), getHeight());
            this.mRevealDrawable.setCallback(this);
            this.mRevealDrawable.start();
        }
    }

    private void drawDoneAnimation(Canvas canvas) {
        this.mRevealDrawable.draw(canvas);
    }

    public void revertAnimation() {
        if (this.mState != State.IDLE) {
            this.mState = State.IDLE;
            CircularAnimatedDrawable circularAnimatedDrawable = this.mAnimatedDrawable;
            if (circularAnimatedDrawable != null && circularAnimatedDrawable.isRunning()) {
                stopAnimation();
            }
            if (this.mIsMorphingInProgress) {
                this.mAnimatorSet.cancel();
            }
            setClickable(false);
            int width = getWidth();
            int height = getHeight();
            int intValue = this.mParams.mInitialHeight.intValue();
            int access$800 = this.mParams.mInitialWidth;
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mGradientDrawable, "cornerRadius", new float[]{this.mParams.mFinalCornerRadius, this.mParams.mInitialCornerRadius});
            ValueAnimator ofInt = ValueAnimator.ofInt(new int[]{width, access$800});
            ofInt.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                    LayoutParams layoutParams = CircularProgressButton.this.getLayoutParams();
                    layoutParams.width = intValue;
                    CircularProgressButton.this.setLayoutParams(layoutParams);
                }
            });
            ValueAnimator ofInt2 = ValueAnimator.ofInt(new int[]{height, intValue});
            ofInt2.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                    LayoutParams layoutParams = CircularProgressButton.this.getLayoutParams();
                    layoutParams.height = intValue;
                    CircularProgressButton.this.setLayoutParams(layoutParams);
                }
            });
            this.mAnimatorSet = new AnimatorSet();
            this.mAnimatorSet.setDuration(300);
            this.mAnimatorSet.playTogether(new Animator[]{ofFloat, ofInt, ofInt2});
            this.mAnimatorSet.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animator) {
                    CircularProgressButton.this.setClickable(true);
                    CircularProgressButton.this.mIsMorphingInProgress = false;
                    CircularProgressButton circularProgressButton = CircularProgressButton.this;
                    circularProgressButton.setText(circularProgressButton.mParams.mText);
                }
            });
            this.mIsMorphingInProgress = true;
            this.mAnimatorSet.start();
        }
    }

    public void revertAnimation(final OnAnimationEndListener onAnimationEndListener) {
        if (this.mState != State.IDLE) {
            this.mState = State.IDLE;
            CircularAnimatedDrawable circularAnimatedDrawable = this.mAnimatedDrawable;
            if (circularAnimatedDrawable != null && circularAnimatedDrawable.isRunning()) {
                stopAnimation();
            }
            if (this.mIsMorphingInProgress) {
                this.mAnimatorSet.cancel();
            }
            setClickable(false);
            int width = getWidth();
            int height = getHeight();
            int intValue = this.mParams.mInitialHeight.intValue();
            int access$800 = this.mParams.mInitialWidth;
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mGradientDrawable, "cornerRadius", new float[]{this.mParams.mFinalCornerRadius, this.mParams.mInitialCornerRadius});
            ValueAnimator ofInt = ValueAnimator.ofInt(new int[]{width, access$800});
            ofInt.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                    LayoutParams layoutParams = CircularProgressButton.this.getLayoutParams();
                    layoutParams.width = intValue;
                    CircularProgressButton.this.setLayoutParams(layoutParams);
                }
            });
            ValueAnimator ofInt2 = ValueAnimator.ofInt(new int[]{height, intValue});
            ofInt2.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                    LayoutParams layoutParams = CircularProgressButton.this.getLayoutParams();
                    layoutParams.height = intValue;
                    CircularProgressButton.this.setLayoutParams(layoutParams);
                }
            });
            this.mAnimatorSet = new AnimatorSet();
            this.mAnimatorSet.setDuration(300);
            this.mAnimatorSet.playTogether(new Animator[]{ofFloat, ofInt, ofInt2});
            this.mAnimatorSet.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animator) {
                    CircularProgressButton.this.setClickable(true);
                    CircularProgressButton.this.mIsMorphingInProgress = false;
                    CircularProgressButton circularProgressButton = CircularProgressButton.this;
                    circularProgressButton.setText(circularProgressButton.mParams.mText);
                    onAnimationEndListener.onAnimationEnd();
                }
            });
            this.mIsMorphingInProgress = true;
            this.mAnimatorSet.start();
        }
    }

    public void dispose() {
        CircularAnimatedDrawable circularAnimatedDrawable = this.mAnimatedDrawable;
        if (circularAnimatedDrawable != null) {
            circularAnimatedDrawable.dispose();
        }
        CircularRevealAnimatedDrawable circularRevealAnimatedDrawable = this.mRevealDrawable;
        if (circularRevealAnimatedDrawable != null) {
            circularRevealAnimatedDrawable.dispose();
        }
    }

    public void startAnimation() {
        if (this.mState == State.IDLE) {
            if (this.mIsMorphingInProgress) {
                this.mAnimatorSet.cancel();
            } else {
                this.mParams.mInitialWidth = getWidth();
                this.mParams.mInitialHeight = Integer.valueOf(getHeight());
            }
            this.mState = State.PROGRESS;
            setCompoundDrawables(null, null, null, null);
            setText(null);
            setClickable(false);
            int intValue = this.mParams.mInitialHeight.intValue();
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mGradientDrawable, "cornerRadius", new float[]{this.mParams.mInitialCornerRadius, this.mParams.mFinalCornerRadius});
            ValueAnimator ofInt = ValueAnimator.ofInt(new int[]{this.mParams.mInitialWidth, intValue});
            ofInt.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                    LayoutParams layoutParams = CircularProgressButton.this.getLayoutParams();
                    layoutParams.width = intValue;
                    CircularProgressButton.this.setLayoutParams(layoutParams);
                }
            });
            ValueAnimator ofInt2 = ValueAnimator.ofInt(new int[]{this.mParams.mInitialHeight.intValue(), intValue});
            ofInt2.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                    LayoutParams layoutParams = CircularProgressButton.this.getLayoutParams();
                    layoutParams.height = intValue;
                    CircularProgressButton.this.setLayoutParams(layoutParams);
                }
            });
            this.mAnimatorSet = new AnimatorSet();
            this.mAnimatorSet.setDuration(300);
            this.mAnimatorSet.playTogether(new Animator[]{ofFloat, ofInt, ofInt2});
            this.mAnimatorSet.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animator) {
                    CircularProgressButton.this.mIsMorphingInProgress = false;
                    if (CircularProgressButton.this.doneWhileMorphing) {
                        CircularProgressButton.this.doneWhileMorphing = false;
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                CircularProgressButton.this.doneLoadingAnimation(CircularProgressButton.this.mFillColorDone, CircularProgressButton.this.mBitmapDone);
                            }
                        }, 50);
                    }
                }
            });
            this.mIsMorphingInProgress = true;
            this.mAnimatorSet.start();
        }
    }
}
