package p015br.com.simplepass.loading_button_lib.customViews;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import p015br.com.simplepass.loading_button_lib.C0769R;
import p015br.com.simplepass.loading_button_lib.animatedDrawables.CircularAnimatedDrawable;

/* renamed from: br.com.simplepass.loading_button_lib.customViews.CircularProgressEditText */
public class CircularProgressEditText extends EditText {
    private CircularAnimatedDrawable mAnimatedDrawable;
    private AnimatorSet mAnimatorSet;
    /* access modifiers changed from: private */
    public String mButtonText;
    Drawable mDrawable;
    private Integer mInitialHeight;
    private int mInitialWidth;
    /* access modifiers changed from: private */
    public boolean mIsMorphingInProgress;
    private int mPaddingProgress;
    private int mSpinningBarColor;
    private float mSpinningBarWidth;
    private State mState;

    /* renamed from: br.com.simplepass.loading_button_lib.customViews.CircularProgressEditText$State */
    private enum State {
        PROGRESS,
        IDLE
    }

    public CircularProgressEditText(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public CircularProgressEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet, 0, 0);
    }

    public CircularProgressEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet, i, 0);
    }

    public CircularProgressEditText(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i);
        init(context, attributeSet, i, i2);
    }

    private void init(Context context, AttributeSet attributeSet, int i, int i2) {
        this.mPaddingProgress = 0;
        if (attributeSet != null) {
            int[] iArr = {16842964};
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C0769R.styleable.CircularProgressButton, i, i2);
            TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, iArr, i, i2);
            this.mDrawable = obtainStyledAttributes2.getDrawable(0);
            this.mSpinningBarWidth = obtainStyledAttributes.getDimension(C0769R.styleable.CircularProgressButton_spinning_bar_width, 10.0f);
            this.mSpinningBarColor = obtainStyledAttributes.getColor(C0769R.styleable.CircularProgressButton_spinning_bar_color, getResources().getColor(17170444));
            obtainStyledAttributes.recycle();
            obtainStyledAttributes2.recycle();
        }
        this.mState = State.IDLE;
        this.mButtonText = getText().toString();
        setBackground(this.mDrawable);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mState == State.PROGRESS && !this.mIsMorphingInProgress) {
            drawIndeterminateProgress(canvas);
        }
    }

    private void drawIndeterminateProgress(Canvas canvas) {
        CircularAnimatedDrawable circularAnimatedDrawable = this.mAnimatedDrawable;
        if (circularAnimatedDrawable == null || !circularAnimatedDrawable.isRunning()) {
            int width = (getWidth() - getHeight()) / 2;
            this.mAnimatedDrawable = new CircularAnimatedDrawable(this, this.mSpinningBarWidth, this.mSpinningBarColor);
            int i = this.mPaddingProgress + width;
            int width2 = (getWidth() - width) - this.mPaddingProgress;
            int height = getHeight();
            int i2 = this.mPaddingProgress;
            this.mAnimatedDrawable.setBounds(i, i2, width2, height - i2);
            this.mAnimatedDrawable.setCallback(this);
            this.mAnimatedDrawable.start();
            return;
        }
        this.mAnimatedDrawable.draw(canvas);
    }

    public void stopAnimation() {
        if (this.mState == State.PROGRESS && !this.mIsMorphingInProgress) {
            this.mAnimatedDrawable.stop();
        }
    }

    public void revertAnimation() {
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
        int intValue = this.mInitialHeight.intValue();
        int i = this.mInitialWidth;
        this.mState = State.IDLE;
        ValueAnimator ofInt = ValueAnimator.ofInt(new int[]{width, i});
        ofInt.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                LayoutParams layoutParams = CircularProgressEditText.this.getLayoutParams();
                layoutParams.width = intValue;
                CircularProgressEditText.this.setLayoutParams(layoutParams);
            }
        });
        ValueAnimator ofInt2 = ValueAnimator.ofInt(new int[]{height, intValue});
        ofInt2.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                LayoutParams layoutParams = CircularProgressEditText.this.getLayoutParams();
                layoutParams.height = intValue;
                CircularProgressEditText.this.setLayoutParams(layoutParams);
            }
        });
        this.mAnimatorSet = new AnimatorSet();
        this.mAnimatorSet.setDuration(300);
        this.mAnimatorSet.playTogether(new Animator[]{ofInt, ofInt2});
        this.mAnimatorSet.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                CircularProgressEditText.this.setClickable(true);
                CircularProgressEditText.this.mIsMorphingInProgress = false;
                CircularProgressEditText circularProgressEditText = CircularProgressEditText.this;
                circularProgressEditText.setText(circularProgressEditText.mButtonText);
            }
        });
        this.mIsMorphingInProgress = true;
        this.mAnimatorSet.start();
    }

    public void startAnimation() {
        if (this.mState == State.IDLE) {
            setText(null);
            setClickable(false);
            if (this.mIsMorphingInProgress) {
                this.mAnimatorSet.cancel();
            }
            this.mInitialWidth = getWidth();
            this.mInitialHeight = Integer.valueOf(getHeight());
            double intValue = (double) this.mInitialHeight.intValue();
            Double.isNaN(intValue);
            int i = (int) (intValue * 1.2d);
            this.mState = State.PROGRESS;
            ValueAnimator ofInt = ValueAnimator.ofInt(new int[]{this.mInitialWidth, i});
            ofInt.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                    LayoutParams layoutParams = CircularProgressEditText.this.getLayoutParams();
                    layoutParams.width = intValue;
                    CircularProgressEditText.this.setLayoutParams(layoutParams);
                }
            });
            ValueAnimator ofInt2 = ValueAnimator.ofInt(new int[]{this.mInitialHeight.intValue(), i});
            ofInt2.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                    LayoutParams layoutParams = CircularProgressEditText.this.getLayoutParams();
                    layoutParams.height = intValue;
                    CircularProgressEditText.this.setLayoutParams(layoutParams);
                }
            });
            this.mAnimatorSet = new AnimatorSet();
            this.mAnimatorSet.setDuration(300);
            this.mAnimatorSet.playTogether(new Animator[]{ofInt, ofInt2});
            this.mAnimatorSet.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animator) {
                    CircularProgressEditText.this.mIsMorphingInProgress = false;
                    CircularProgressEditText.this.setClickable(true);
                }
            });
            this.mIsMorphingInProgress = true;
            this.mAnimatorSet.start();
        }
    }
}
