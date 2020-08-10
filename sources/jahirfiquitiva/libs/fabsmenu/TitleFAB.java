package jahirfiquitiva.libs.fabsmenu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.animation.Interpolator;
import androidx.coordinatorlayout.widget.CoordinatorLayout.AttachedBehavior;
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TitleFAB extends FloatingActionButton implements AttachedBehavior {
    private static final int ANIM_STATE_HIDING = 1;
    private static final int ANIM_STATE_NONE = 0;
    private static final int ANIM_STATE_SHOWING = 2;
    private static final Interpolator FAST_OUT_LINEAR_IN_INTERPOLATOR = new FastOutLinearInInterpolator();
    private static final Interpolator LINEAR_OUT_SLOW_IN_INTERPOLATOR = new LinearOutSlowInInterpolator();
    private static final int MAX_CHARACTERS_COUNT = 25;
    private static final int SHOW_HIDE_ANIM_DURATION = 200;
    private static final String TAG = TitleFAB.class.getSimpleName();
    private OnClickListener clickListener;
    int mAnimState;
    private String title;
    private int titleBackgroundColor;
    private boolean titleClickEnabled;
    private float titleCornerRadius;
    private int titleTextColor;
    private int titleTextPadding;

    public TitleFAB(Context context) {
        this(context, null);
    }

    public TitleFAB(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mAnimState = 0;
        init(context, attributeSet);
    }

    public TitleFAB(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mAnimState = 0;
        init(context, attributeSet);
    }

    /* access modifiers changed from: 0000 */
    public void init(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C5959R.styleable.TitleFAB, 0, 0);
        int i = 1;
        try {
            this.title = obtainStyledAttributes.getString(C5959R.styleable.TitleFAB_fab_title);
            this.titleClickEnabled = obtainStyledAttributes.getBoolean(C5959R.styleable.TitleFAB_fab_enableTitleClick, true);
            this.titleBackgroundColor = obtainStyledAttributes.getInt(C5959R.styleable.TitleFAB_fab_title_backgroundColor, ContextCompat.getColor(context, 17170443));
            this.titleTextColor = obtainStyledAttributes.getInt(C5959R.styleable.TitleFAB_fab_title_textColor, ContextCompat.getColor(context, 17170444));
            this.titleCornerRadius = (float) obtainStyledAttributes.getDimensionPixelSize(C5959R.styleable.TitleFAB_fab_title_cornerRadius, -1);
            this.titleTextPadding = obtainStyledAttributes.getDimensionPixelSize(C5959R.styleable.TitleFAB_fab_title_textPadding, (int) DimensionUtils.convertDpToPixel(8.0f, context));
            i = obtainStyledAttributes.getInt(C5959R.styleable.FloatingActionButton_fabSize, 1);
        } catch (Exception e) {
            Log.w(TAG, "Failure reading attributes", e);
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
        obtainStyledAttributes.recycle();
        setOnClickListener(null);
        setSize(i);
    }

    public void setBackgroundColor(int i) {
        super.setBackgroundTintList(ColorStateList.valueOf(i));
    }

    public OnClickListener getOnClickListener() {
        return this.clickListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.clickListener = onClickListener;
        setClickable(onClickListener != null);
        super.setOnClickListener(onClickListener);
    }

    public void setClickable(boolean z) {
        super.setClickable(z);
        setFocusable(z);
        LabelView labelView = getLabelView();
        if (labelView != null) {
            labelView.setOnClickListener((!this.titleClickEnabled || !z) ? null : this.clickListener);
        }
    }

    /* access modifiers changed from: 0000 */
    public LabelView getLabelView() {
        return (LabelView) getTag(C5959R.C5962id.fab_label);
    }

    public String getTitle() {
        if (this.title == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        if (this.title.length() > 25) {
            sb.append(this.title.substring(0, 25));
            sb.append("...");
        } else {
            sb.append(this.title);
        }
        return sb.toString();
    }

    public void setTitle(String str) {
        this.title = str;
        LabelView labelView = getLabelView();
        if (labelView != null && labelView.getContent() != null) {
            labelView.getContent().setText(str);
        }
    }

    public boolean isTitleClickEnabled() {
        return this.titleClickEnabled;
    }

    public void setTitleClickEnabled(boolean z) {
        this.titleClickEnabled = z;
        LabelView labelView = getLabelView();
        if (labelView != null) {
            labelView.setClickable(z);
        }
    }

    public int getTitleBackgroundColor() {
        return this.titleBackgroundColor;
    }

    public void setTitleBackgroundColor(int i) {
        this.titleBackgroundColor = i;
        LabelView labelView = getLabelView();
        if (labelView != null) {
            labelView.setBackgroundColor(i);
        }
    }

    public int getTitleTextColor() {
        return this.titleTextColor;
    }

    public void setTitleTextColor(int i) {
        this.titleTextColor = i;
        LabelView labelView = getLabelView();
        if (labelView != null && labelView.getContent() != null) {
            labelView.getContent().setTextColor(i);
        }
    }

    public float getTitleCornerRadius() {
        return this.titleCornerRadius;
    }

    public void setTitleCornerRadius(float f) {
        this.titleCornerRadius = f;
        LabelView labelView = getLabelView();
        if (labelView != null) {
            labelView.setRadius(f);
        }
    }

    public int getTitleTextPadding() {
        return this.titleTextPadding;
    }

    public void setTitleTextPadding(int i) {
        this.titleTextPadding = i;
        LabelView labelView = getLabelView();
        if (labelView != null && labelView.getContent() != null) {
            int i2 = i / 2;
            labelView.getContent().setPadding(i, i2, i, i2);
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean isOrWillBeShown() {
        LabelView labelView = getLabelView();
        boolean z = false;
        if (labelView == null) {
            return false;
        }
        if (labelView.getVisibility() != 0) {
            if (this.mAnimState == 2) {
                z = true;
            }
            return z;
        }
        if (this.mAnimState != 1) {
            z = true;
        }
        return z;
    }

    /* access modifiers changed from: 0000 */
    public boolean isOrWillBeHidden() {
        LabelView labelView = getLabelView();
        boolean z = true;
        if (labelView == null) {
            return true;
        }
        if (labelView.getVisibility() == 0) {
            if (this.mAnimState != 1) {
                z = false;
            }
            return z;
        }
        if (this.mAnimState == 2) {
            z = false;
        }
        return z;
    }

    public void show() {
        final LabelView labelView = getLabelView();
        if (labelView == null) {
            super.show();
        } else if (!isOrWillBeShown()) {
            labelView.animate().cancel();
            if (shouldAnimateVisibilityChange()) {
                this.mAnimState = 2;
                if (labelView.getVisibility() != 0) {
                    labelView.setAlpha(0.0f);
                    labelView.setScaleY(0.0f);
                    labelView.setScaleX(0.0f);
                }
                labelView.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(200).setInterpolator(LINEAR_OUT_SLOW_IN_INTERPOLATOR).setListener(new AnimatorListenerAdapter() {
                    public void onAnimationStart(Animator animator) {
                        TitleFAB.super.show();
                        labelView.setVisibility(0);
                    }

                    public void onAnimationEnd(Animator animator) {
                        TitleFAB.this.mAnimState = 0;
                    }
                });
            } else {
                labelView.setVisibility(0);
                labelView.setAlpha(1.0f);
                labelView.setScaleY(1.0f);
                labelView.setScaleX(1.0f);
            }
        }
    }

    public void hide() {
        final LabelView labelView = getLabelView();
        if (labelView == null) {
            super.hide();
        } else if (!isOrWillBeHidden()) {
            labelView.animate().cancel();
            if (shouldAnimateVisibilityChange()) {
                this.mAnimState = 1;
                labelView.animate().scaleX(0.0f).scaleY(0.0f).alpha(0.0f).setDuration(200).setInterpolator(FAST_OUT_LINEAR_IN_INTERPOLATOR).setListener(new AnimatorListenerAdapter() {
                    private boolean mCancelled;

                    public void onAnimationStart(Animator animator) {
                        TitleFAB.super.hide();
                        labelView.setVisibility(0);
                        this.mCancelled = false;
                    }

                    public void onAnimationCancel(Animator animator) {
                        this.mCancelled = true;
                    }

                    public void onAnimationEnd(Animator animator) {
                        TitleFAB.this.mAnimState = 0;
                        if (!this.mCancelled) {
                            labelView.setVisibility(8);
                        }
                    }
                });
            } else {
                labelView.setVisibility(8);
            }
        }
    }

    private boolean shouldAnimateVisibilityChange() {
        LabelView labelView = getLabelView();
        boolean z = true;
        if (labelView != null) {
            if (!ViewCompat.isLaidOut(this) || !ViewCompat.isLaidOut(labelView) || isInEditMode()) {
                z = false;
            }
            return z;
        }
        if (!ViewCompat.isLaidOut(this) || isInEditMode()) {
            z = false;
        }
        return z;
    }

    public Behavior getBehavior() {
        return new FABSnackbarBehavior();
    }
}
