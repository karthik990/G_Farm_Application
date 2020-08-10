package jahirfiquitiva.libs.fabsmenu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;
import androidx.coordinatorlayout.widget.CoordinatorLayout.AttachedBehavior;
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.OnScrollListener;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class FABsMenu extends ViewGroup implements AttachedBehavior {
    private static final float COLLAPSED_PLUS_ROTATION = 0.0f;
    private static final float EXPANDED_PLUS_ROTATION = 135.0f;
    public static final int EXPAND_DOWN = 1;
    public static final int EXPAND_LEFT = 2;
    public static final int EXPAND_RIGHT = 3;
    public static final int EXPAND_UP = 0;
    public static final int LABELS_ON_LEFT_SIDE = 0;
    public static final int LABELS_ON_RIGHT_SIDE = 1;
    private static final String TAG = FABsMenu.class.getSimpleName();
    /* access modifiers changed from: private */
    public static Interpolator alphaExpandInterpolator = new DecelerateInterpolator();
    /* access modifiers changed from: private */
    public static Interpolator collapseInterpolator = new DecelerateInterpolator(3.0f);
    /* access modifiers changed from: private */
    public static Interpolator expandInterpolator = new OvershootInterpolator();
    /* access modifiers changed from: private */
    public boolean animating;
    private int animationDuration;
    private int buttonSpacing;
    private int buttonsCount;
    /* access modifiers changed from: private */
    public AnimatorSet collapseAnimation;
    private AnimatorListenerAdapter collapseListener;
    /* access modifiers changed from: private */
    public AnimatorSet expandAnimation;
    /* access modifiers changed from: private */
    public int expandDirection;
    private AnimatorListenerAdapter expandListener;
    /* access modifiers changed from: private */
    public boolean expanded;
    private int labelsMargin;
    private int labelsPosition;
    private int labelsVerticalOffset;
    private int maxButtonHeight;
    private int maxButtonWidth;
    private int menuBottomMargin;
    /* access modifiers changed from: private */
    public MenuFAB menuButton;
    private int menuButtonColor;
    private Drawable menuButtonIcon;
    private int menuButtonRippleColor;
    private int menuButtonSize;
    private int menuLeftMargin;
    /* access modifiers changed from: private */
    public FABsMenuListener menuListener;
    private int menuMargins;
    private int menuRightMargin;
    private int menuTopMargin;
    private RotatingDrawable rotatingDrawable;
    private TouchDelegateGroup touchDelegateGroup;

    @Retention(RetentionPolicy.SOURCE)
    public @interface EXPAND_DIRECTION {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface LABELS_POSITION {
    }

    private class LayoutParams extends android.view.ViewGroup.LayoutParams {
        private boolean animationsSetToPlay;
        private ObjectAnimator mCollapseAlpha = new ObjectAnimator();
        /* access modifiers changed from: private */
        public ObjectAnimator mCollapseDir = new ObjectAnimator();
        private ObjectAnimator mExpandAlpha = new ObjectAnimator();
        /* access modifiers changed from: private */
        public ObjectAnimator mExpandDir = new ObjectAnimator();

        LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.mExpandDir.setInterpolator(FABsMenu.expandInterpolator);
            this.mExpandAlpha.setInterpolator(FABsMenu.alphaExpandInterpolator);
            this.mCollapseDir.setInterpolator(FABsMenu.collapseInterpolator);
            this.mCollapseAlpha.setInterpolator(FABsMenu.collapseInterpolator);
            this.mCollapseAlpha.setProperty(View.ALPHA);
            this.mCollapseAlpha.setFloatValues(new float[]{1.0f, 0.0f});
            this.mExpandAlpha.setProperty(View.ALPHA);
            this.mExpandAlpha.setFloatValues(new float[]{0.0f, 1.0f});
            int access$1100 = FABsMenu.this.expandDirection;
            if (access$1100 == 0 || access$1100 == 1) {
                this.mCollapseDir.setProperty(View.TRANSLATION_Y);
                this.mExpandDir.setProperty(View.TRANSLATION_Y);
            } else if (access$1100 == 2 || access$1100 == 3) {
                this.mCollapseDir.setProperty(View.TRANSLATION_X);
                this.mExpandDir.setProperty(View.TRANSLATION_X);
            }
        }

        /* access modifiers changed from: 0000 */
        public void setAnimationsTarget(View view) {
            this.mCollapseAlpha.setTarget(view);
            this.mCollapseDir.setTarget(view);
            this.mExpandAlpha.setTarget(view);
            this.mExpandDir.setTarget(view);
            if (!this.animationsSetToPlay) {
                addLayerTypeListener(this.mExpandDir, view);
                addLayerTypeListener(this.mCollapseDir, view);
                FABsMenu.this.collapseAnimation.play(this.mCollapseAlpha);
                FABsMenu.this.collapseAnimation.play(this.mCollapseDir);
                FABsMenu.this.expandAnimation.play(this.mExpandAlpha);
                FABsMenu.this.expandAnimation.play(this.mExpandDir);
                this.animationsSetToPlay = true;
            }
        }

        private void addLayerTypeListener(Animator animator, final View view) {
            animator.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animator) {
                    view.setLayerType(0, null);
                }

                public void onAnimationStart(Animator animator) {
                    view.setLayerType(2, null);
                }
            });
        }
    }

    private static class RotatingDrawable extends LayerDrawable {
        private float mRotation;

        RotatingDrawable(Drawable drawable) {
            super(new Drawable[]{drawable});
        }

        public float getRotation() {
            return this.mRotation;
        }

        /* access modifiers changed from: 0000 */
        public void setRotation(float f) {
            this.mRotation = f;
            invalidateSelf();
        }

        public void draw(Canvas canvas) {
            canvas.save();
            canvas.rotate(this.mRotation, (float) getBounds().centerX(), (float) getBounds().centerY());
            super.draw(canvas);
            canvas.restore();
        }
    }

    public static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public boolean expanded;

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            boolean z = true;
            if (parcel.readInt() != 1) {
                z = false;
            }
            this.expanded = z;
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.expanded ? 1 : 0);
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    public FABsMenu(Context context) {
        this(context, null);
    }

    public FABsMenu(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.animationDuration = 500;
        this.animating = false;
        this.expandAnimation = new AnimatorSet().setDuration((long) this.animationDuration);
        this.collapseAnimation = new AnimatorSet().setDuration((long) this.animationDuration);
        this.collapseListener = new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                FABsMenu.this.setMenuButtonsClickable(false);
            }

            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                FABsMenu.this.setMenuButtonsVisibility(false);
                FABsMenu.this.animating = false;
                FABsMenu.this.expanded = false;
            }
        };
        this.expandListener = new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                FABsMenu.this.setMenuButtonsVisibility(true);
            }

            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                FABsMenu.this.setMenuButtonsClickable(true);
                FABsMenu.this.animating = false;
                FABsMenu.this.expanded = true;
            }
        };
        init(context, attributeSet);
    }

    public FABsMenu(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.animationDuration = 500;
        this.animating = false;
        this.expandAnimation = new AnimatorSet().setDuration((long) this.animationDuration);
        this.collapseAnimation = new AnimatorSet().setDuration((long) this.animationDuration);
        this.collapseListener = new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                FABsMenu.this.setMenuButtonsClickable(false);
            }

            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                FABsMenu.this.setMenuButtonsVisibility(false);
                FABsMenu.this.animating = false;
                FABsMenu.this.expanded = false;
            }
        };
        this.expandListener = new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                FABsMenu.this.setMenuButtonsVisibility(true);
            }

            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                FABsMenu.this.setMenuButtonsClickable(true);
                FABsMenu.this.animating = false;
                FABsMenu.this.expanded = true;
            }
        };
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        int i;
        int i2;
        int i3;
        int i4;
        this.buttonSpacing = (int) DimensionUtils.convertDpToPixel(16.0f, context);
        this.labelsMargin = getResources().getDimensionPixelSize(C5959R.dimen.fab_labels_margin);
        this.labelsVerticalOffset = (int) DimensionUtils.convertDpToPixel(-1.5f, context);
        this.touchDelegateGroup = new TouchDelegateGroup(this);
        setTouchDelegate(this.touchDelegateGroup);
        int i5 = 0;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C5959R.styleable.FABsMenu, 0, 0);
        try {
            this.menuMargins = obtainStyledAttributes.getDimensionPixelSize(C5959R.styleable.FABsMenu_fab_menuMargins, 0);
            int i6 = C5959R.styleable.FABsMenu_fab_menuTopMargin;
            if (this.menuMargins != 0) {
                i = this.menuMargins;
            } else {
                i = (int) DimensionUtils.convertDpToPixel(16.0f, context);
            }
            this.menuTopMargin = obtainStyledAttributes.getDimensionPixelSize(i6, i);
            int i7 = C5959R.styleable.FABsMenu_fab_menuBottomMargin;
            if (this.menuMargins != 0) {
                i2 = this.menuMargins;
            } else {
                i2 = (int) DimensionUtils.convertDpToPixel(16.0f, context);
            }
            this.menuBottomMargin = obtainStyledAttributes.getDimensionPixelSize(i7, i2);
            int i8 = C5959R.styleable.FABsMenu_fab_menuRightMargin;
            if (this.menuMargins != 0) {
                i3 = this.menuMargins;
            } else {
                i3 = (int) DimensionUtils.convertDpToPixel(16.0f, context);
            }
            this.menuRightMargin = obtainStyledAttributes.getDimensionPixelSize(i8, i3);
            int i9 = C5959R.styleable.FABsMenu_fab_menuLeftMargin;
            if (this.menuMargins != 0) {
                i4 = this.menuMargins;
            } else {
                i4 = (int) DimensionUtils.convertDpToPixel(16.0f, context);
            }
            this.menuLeftMargin = obtainStyledAttributes.getDimensionPixelSize(i9, i4);
            int resourceId = obtainStyledAttributes.getResourceId(C5959R.styleable.FABsMenu_fab_moreButtonPlusIcon, 0);
            if (resourceId != 0) {
                this.menuButtonIcon = VectorDrawableCompat.create(getResources(), resourceId, context.getTheme());
            }
            this.menuButtonColor = obtainStyledAttributes.getColor(C5959R.styleable.FABsMenu_fab_moreButtonBackgroundColor, getColor(17170451));
            this.menuButtonRippleColor = obtainStyledAttributes.getColor(C5959R.styleable.FABsMenu_fab_moreButtonRippleColor, getColor(17170450));
            this.menuButtonSize = obtainStyledAttributes.getInt(C5959R.styleable.FABsMenu_fab_moreButtonSize, 0);
            this.expandDirection = obtainStyledAttributes.getInt(C5959R.styleable.FABsMenu_fab_expandDirection, 0);
            int i10 = C5959R.styleable.FABsMenu_fab_labelsPosition;
            if (isRtl()) {
                i5 = 1;
            }
            this.labelsPosition = obtainStyledAttributes.getInt(i10, i5);
        } catch (Exception e) {
            Log.w(TAG, "Failure reading attributes", e);
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
        obtainStyledAttributes.recycle();
        if (this.menuListener == null) {
            setMenuListener(new FABsMenuListener() {
            });
        }
        createMenuButton(context);
    }

    private boolean expandsHorizontally() {
        int i = this.expandDirection;
        return i == 2 || i == 3;
    }

    private void createMenuButton(Context context) {
        this.menuButton = new MenuFAB(context);
        if (this.menuButtonIcon != null) {
            createRotatingDrawable();
        }
        this.menuButton.setBackgroundTintList(ColorStateList.valueOf(this.menuButtonColor));
        this.menuButton.setRippleColor(this.menuButtonRippleColor);
        this.menuButton.setId(C5959R.C5962id.fab_expand_menu_button);
        this.menuButton.setSize(this.menuButtonSize);
        this.menuButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (FABsMenu.this.menuListener != null) {
                    FABsMenu.this.menuListener.onMenuClicked(FABsMenu.this);
                }
            }
        });
        addView(this.menuButton, super.generateDefaultLayoutParams());
        this.buttonsCount++;
    }

    private void createRotatingDrawable() {
        RotatingDrawable rotatingDrawable2 = new RotatingDrawable(this.menuButtonIcon);
        OvershootInterpolator overshootInterpolator = new OvershootInterpolator();
        String str = "rotation";
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(rotatingDrawable2, str, new float[]{EXPANDED_PLUS_ROTATION, 0.0f});
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(rotatingDrawable2, str, new float[]{0.0f, EXPANDED_PLUS_ROTATION});
        ofFloat.setInterpolator(overshootInterpolator);
        ofFloat2.setInterpolator(overshootInterpolator);
        this.expandAnimation.play(ofFloat2);
        this.collapseAnimation.play(ofFloat);
        this.menuButton.setImageDrawable(rotatingDrawable2);
        this.rotatingDrawable = rotatingDrawable2;
    }

    public void addAllButtons(TitleFAB... titleFABArr) throws IllegalArgumentException {
        for (TitleFAB addButton : titleFABArr) {
            addButton(addButton);
        }
    }

    public void addButton(TitleFAB titleFAB, int i) throws IllegalArgumentException {
        if (this.buttonsCount < 6) {
            addView(titleFAB, i);
            this.buttonsCount++;
            createLabels();
            if (this.buttonsCount > 1 && getVisibility() != 0) {
                show(false);
            }
            if (this.buttonsCount < 3) {
                Log.w(TAG, "A floating action buttons menu should have at least three options");
                return;
            }
            return;
        }
        throw new IllegalArgumentException("A floating action buttons menu should have no more than six options.");
    }

    public void addButton(TitleFAB titleFAB) throws IllegalArgumentException {
        addButton(titleFAB, this.buttonsCount - 1);
    }

    private void removeButtonInternal(int i, boolean z) throws NullPointerException, IllegalArgumentException {
        View childAt = getChildAt(i);
        if (childAt != null) {
            if (!(childAt instanceof MenuFAB)) {
                if (childAt instanceof TitleFAB) {
                    removeButton((TitleFAB) childAt);
                } else if (z) {
                    throw new IllegalArgumentException("The view you want to remove is not an instance of TitleFAB");
                }
            }
        } else if (z) {
            throw new NullPointerException("The button you want to remove does not exists");
        }
    }

    public void removeButton(int i) throws IndexOutOfBoundsException {
        removeButtonInternal(i, false);
    }

    public void removeButton(TitleFAB titleFAB) {
        try {
            titleFAB.hide();
            removeView(titleFAB.getLabelView());
            removeView(titleFAB);
            titleFAB.setTag(C5959R.C5962id.fab_label, null);
            this.buttonsCount--;
            if (this.buttonsCount <= 1) {
                hide();
            }
        } catch (Exception e) {
            Log.e(TAG, "Failure removing Button", e);
        }
    }

    public void removeAllButtons() {
        for (int i = 0; i <= getChildCount(); i++) {
            removeButton(0);
        }
    }

    private int getColor(int i) {
        return ContextCompat.getColor(getContext(), i);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        measureChildren(i, i2);
        int i3 = this.buttonSpacing;
        int i4 = 0;
        this.maxButtonWidth = 0;
        this.maxButtonHeight = 0;
        int i5 = i3;
        int i6 = i5;
        int i7 = 0;
        for (int i8 = 0; i8 < this.buttonsCount; i8++) {
            View childAt = getChildAt(i8);
            if (childAt.getVisibility() != 8) {
                int i9 = this.expandDirection;
                if (i9 == 0 || i9 == 1) {
                    this.maxButtonWidth = Math.max(this.maxButtonWidth, childAt.getMeasuredWidth());
                    i5 += childAt.getMeasuredHeight();
                } else if (i9 == 2 || i9 == 3) {
                    i6 += childAt.getMeasuredWidth();
                    this.maxButtonHeight = Math.max(this.maxButtonHeight, childAt.getMeasuredHeight());
                }
                if (!expandsHorizontally()) {
                    LabelView labelView = (LabelView) childAt.getTag(C5959R.C5962id.fab_label);
                    if (labelView != null) {
                        i7 = Math.max(i7, labelView.getMeasuredWidth());
                    }
                }
            }
        }
        if (!expandsHorizontally()) {
            int i10 = this.maxButtonWidth;
            if (i7 > 0) {
                i4 = this.labelsMargin + i7;
            }
            i6 = i10 + i4;
        } else {
            i5 = this.maxButtonHeight;
        }
        int i11 = this.expandDirection;
        if (i11 == 0 || i11 == 1) {
            i5 = adjustForOvershoot(i5 + (this.buttonSpacing * (this.buttonsCount - 1)));
        } else if (i11 == 2 || i11 == 3) {
            i6 = adjustForOvershoot(i6 + (this.buttonSpacing * (this.buttonsCount - 1)));
        }
        setMeasuredDimension(i6 + (Math.max(this.menuLeftMargin, this.menuRightMargin) * 2), i5 + (Math.max(this.menuTopMargin, this.menuBottomMargin) * 2));
    }

    private int adjustForOvershoot(int i) {
        return (i * 12) / 10;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11 = this.expandDirection;
        int i12 = 8;
        float f = 0.0f;
        char c = 0;
        char c2 = 1;
        if (i11 == 0 || i11 == 1) {
            boolean z2 = this.expandDirection == 0;
            this.touchDelegateGroup.clearTouchDelegates();
            int measuredHeight = z2 ? (i4 - i2) - this.menuButton.getMeasuredHeight() : 0;
            if (z2) {
                i5 = measuredHeight - this.menuBottomMargin;
            } else {
                i5 = measuredHeight + this.menuTopMargin;
            }
            int i13 = (this.labelsPosition == 0 ? (i3 - i) - (this.maxButtonWidth / 2) : this.maxButtonWidth / 2) - (this.labelsPosition == 0 ? this.menuRightMargin : -this.menuLeftMargin);
            int measuredWidth = i13 - (this.menuButton.getMeasuredWidth() / 2);
            MenuFAB menuFAB = this.menuButton;
            menuFAB.layout(measuredWidth, i5, menuFAB.getMeasuredWidth() + measuredWidth, this.menuButton.getMeasuredHeight() + i5);
            int i14 = (this.maxButtonWidth / 2) + this.labelsMargin;
            int i15 = this.labelsPosition == 0 ? i13 - i14 : i14 + i13;
            if (z2) {
                i6 = i5 - this.buttonSpacing;
            } else {
                i6 = this.menuButton.getMeasuredHeight() + i5 + this.buttonSpacing;
            }
            int i16 = this.buttonsCount - 1;
            while (i16 >= 0) {
                View childAt = getChildAt(i16);
                if (childAt.equals(this.menuButton) || childAt.getVisibility() == i12) {
                    i7 = i5;
                } else {
                    int measuredWidth2 = i13 - (childAt.getMeasuredWidth() / 2);
                    if (z2) {
                        i6 -= childAt.getMeasuredHeight();
                    }
                    childAt.layout(measuredWidth2, i6, childAt.getMeasuredWidth() + measuredWidth2, childAt.getMeasuredHeight() + i6);
                    float f2 = (float) (i5 - i6);
                    childAt.setTranslationY(this.expanded ? 0.0f : f2);
                    childAt.setAlpha(this.expanded ? 1.0f : 0.0f);
                    LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                    ObjectAnimator access$300 = layoutParams.mCollapseDir;
                    i7 = i5;
                    float[] fArr = new float[2];
                    fArr[c] = f;
                    fArr[c2] = f2;
                    access$300.setFloatValues(fArr);
                    ObjectAnimator access$400 = layoutParams.mExpandDir;
                    float[] fArr2 = new float[2];
                    fArr2[c] = f2;
                    fArr2[c2] = f;
                    access$400.setFloatValues(fArr2);
                    layoutParams.setAnimationsTarget(childAt);
                    View view = (View) childAt.getTag(C5959R.C5962id.fab_label);
                    if (view != null) {
                        if (this.labelsPosition == 0) {
                            i8 = i15 - view.getMeasuredWidth();
                        } else {
                            i8 = view.getMeasuredWidth() + i15;
                        }
                        int i17 = this.labelsPosition == 0 ? i8 : i15;
                        if (this.labelsPosition == 0) {
                            i8 = i15;
                        }
                        int measuredHeight2 = (i6 - this.labelsVerticalOffset) + ((childAt.getMeasuredHeight() - view.getMeasuredHeight()) / 2);
                        view.layout(i17, measuredHeight2, i8, measuredHeight2 + view.getMeasuredHeight());
                        this.touchDelegateGroup.addTouchDelegate(new TouchDelegate(new Rect(Math.min(measuredWidth2, i17), i6 - (this.buttonSpacing / 2), Math.max(measuredWidth2 + childAt.getMeasuredWidth(), i8), childAt.getMeasuredHeight() + i6 + (this.buttonSpacing / 2)), childAt));
                        view.setTranslationY(this.expanded ? 0.0f : f2);
                        view.setAlpha(this.expanded ? 1.0f : 0.0f);
                        LayoutParams layoutParams2 = (LayoutParams) view.getLayoutParams();
                        layoutParams2.mCollapseDir.setFloatValues(new float[]{0.0f, f2});
                        layoutParams2.mExpandDir.setFloatValues(new float[]{f2, 0.0f});
                        layoutParams2.setAnimationsTarget(view);
                    }
                    if (z2) {
                        i6 -= this.buttonSpacing;
                    } else {
                        i6 = i6 + childAt.getMeasuredHeight() + this.buttonSpacing;
                    }
                }
                i16--;
                i5 = i7;
                i12 = 8;
                f = 0.0f;
                c = 0;
                c2 = 1;
            }
        } else if (i11 == 2 || i11 == 3) {
            boolean z3 = this.expandDirection == 2;
            int measuredWidth3 = z3 ? (i3 - i) - this.menuButton.getMeasuredWidth() : 0;
            if (z3) {
                i9 = measuredWidth3 - this.menuRightMargin;
            } else {
                i9 = measuredWidth3 + this.menuLeftMargin;
            }
            int i18 = i4 - i2;
            int i19 = this.maxButtonHeight;
            int measuredHeight3 = ((i18 - i19) + ((i19 - this.menuButton.getMeasuredHeight()) / 2)) - this.menuBottomMargin;
            MenuFAB menuFAB2 = this.menuButton;
            menuFAB2.layout(i9, measuredHeight3, menuFAB2.getMeasuredWidth() + i9, this.menuButton.getMeasuredHeight() + measuredHeight3);
            if (z3) {
                i10 = i9 - this.buttonSpacing;
            } else {
                i10 = this.menuButton.getMeasuredWidth() + i9 + this.buttonSpacing;
            }
            for (int i20 = this.buttonsCount - 1; i20 >= 0; i20--) {
                View childAt2 = getChildAt(i20);
                if (!childAt2.equals(this.menuButton) && childAt2.getVisibility() != 8) {
                    if (z3) {
                        i10 -= childAt2.getMeasuredWidth();
                    }
                    int measuredHeight4 = ((this.menuButton.getMeasuredHeight() - childAt2.getMeasuredHeight()) / 2) + measuredHeight3;
                    childAt2.layout(i10, measuredHeight4, childAt2.getMeasuredWidth() + i10, childAt2.getMeasuredHeight() + measuredHeight4);
                    float f3 = (float) (i9 - i10);
                    childAt2.setTranslationX(this.expanded ? 0.0f : f3);
                    childAt2.setAlpha(this.expanded ? 1.0f : 0.0f);
                    LayoutParams layoutParams3 = (LayoutParams) childAt2.getLayoutParams();
                    layoutParams3.mCollapseDir.setFloatValues(new float[]{0.0f, f3});
                    layoutParams3.mExpandDir.setFloatValues(new float[]{f3, 0.0f});
                    layoutParams3.setAnimationsTarget(childAt2);
                    if (z3) {
                        i10 -= this.buttonSpacing;
                    } else {
                        i10 = i10 + childAt2.getMeasuredWidth() + this.buttonSpacing;
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(super.generateDefaultLayoutParams());
    }

    public android.view.ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(super.generateLayoutParams(attributeSet));
    }

    /* access modifiers changed from: protected */
    public android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(super.generateLayoutParams(layoutParams));
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return super.checkLayoutParams(layoutParams);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        bringChildToFront(this.menuButton);
        this.buttonsCount = getChildCount();
        createLabels();
    }

    private void createLabels() {
        if (!expandsHorizontally()) {
            for (int i = 0; i < this.buttonsCount; i++) {
                TitleFAB titleFAB = (TitleFAB) getChildAt(i);
                String title = titleFAB.getTitle();
                if (!titleFAB.equals(this.menuButton) && title != null && title.length() > 0 && titleFAB.getTag(C5959R.C5962id.fab_label) == null) {
                    LabelView labelView = new LabelView(getContext(), titleFAB.getTitleBackgroundColor());
                    labelView.setId(i + 1);
                    if (titleFAB.getTitleCornerRadius() != -1.0f) {
                        labelView.setRadius(titleFAB.getTitleCornerRadius());
                    }
                    TextView textView = new TextView(getContext());
                    textView.setText(titleFAB.getTitle());
                    textView.setTextColor(titleFAB.getTitleTextColor());
                    int titleTextPadding = titleFAB.getTitleTextPadding();
                    int i2 = titleTextPadding / 2;
                    textView.setPadding(titleTextPadding, i2, titleTextPadding, i2);
                    labelView.addView(textView);
                    labelView.setContent(textView);
                    addView(labelView);
                    titleFAB.setTag(C5959R.C5962id.fab_label, labelView);
                }
            }
            return;
        }
        Log.e("FABs Menu", "FABs menu items can't have labels when the menu expands horizontally");
    }

    private void toggleOverlay(boolean z, boolean z2) {
        ViewParent parent = getParent();
        if (parent != null && (parent instanceof FABsMenuLayout)) {
            ((FABsMenuLayout) parent).toggle(z, z2, new OnClickListener() {
                public void onClick(View view) {
                    FABsMenu.this.collapse();
                }
            });
        }
    }

    public void collapse() {
        collapse(false);
    }

    public void collapseImmediately() {
        collapse(true);
    }

    /* access modifiers changed from: private */
    public void collapse(boolean z) {
        if (!this.animating && this.expanded) {
            this.animating = true;
            this.touchDelegateGroup.setEnabled(false);
            toggleOverlay(false, z);
            this.collapseAnimation.setDuration(z ? 0 : (long) this.animationDuration);
            this.collapseAnimation.removeListener(this.collapseListener);
            this.collapseAnimation.addListener(this.collapseListener);
            this.collapseAnimation.start();
            this.expandAnimation.cancel();
            FABsMenuListener fABsMenuListener = this.menuListener;
            if (fABsMenuListener != null) {
                fABsMenuListener.onMenuCollapsed(this);
            }
        }
    }

    public void toggle() {
        if (this.expanded) {
            collapse();
        } else {
            expand();
        }
    }

    public void expand() {
        if (!this.animating && !this.expanded) {
            this.animating = true;
            this.touchDelegateGroup.setEnabled(true);
            toggleOverlay(true, false);
            this.collapseAnimation.cancel();
            this.expandAnimation.removeListener(this.expandListener);
            this.expandAnimation.addListener(this.expandListener);
            this.expandAnimation.start();
            FABsMenuListener fABsMenuListener = this.menuListener;
            if (fABsMenuListener != null) {
                fABsMenuListener.onMenuExpanded(this);
            }
        }
    }

    public boolean isExpanded() {
        return this.expanded;
    }

    private boolean isRtl() {
        return getResources().getBoolean(C5959R.bool.is_right_to_left);
    }

    public void setEnabled(boolean z) {
        super.setEnabled(z);
        this.menuButton.setEnabled(z);
    }

    public void setMenuButtonsVisibility(boolean z) {
        for (int i = 0; i < this.buttonsCount; i++) {
            View childAt = getChildAt(i);
            if ((childAt instanceof TitleFAB) && !(childAt instanceof MenuFAB)) {
                if (z) {
                    ((TitleFAB) childAt).show();
                } else {
                    ((TitleFAB) childAt).hide();
                }
            }
        }
    }

    public void setMenuButtonsClickable(boolean z) {
        for (int i = 0; i < this.buttonsCount; i++) {
            View childAt = getChildAt(i);
            if ((childAt instanceof TitleFAB) && !(childAt instanceof MenuFAB)) {
                childAt.setClickable(z);
            }
        }
    }

    public void attachToRecyclerView(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new OnScrollListener() {
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                super.onScrolled(recyclerView, i, i2);
                if (i2 > 0) {
                    if (FABsMenu.this.isExpanded()) {
                        FABsMenu.this.collapse(true);
                    }
                    FABsMenu.this.menuButton.hide();
                    return;
                }
                FABsMenu.this.menuButton.show();
            }
        });
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.expanded = this.expanded;
        return savedState;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            SavedState savedState = (SavedState) parcelable;
            this.expanded = savedState.expanded;
            this.touchDelegateGroup.setEnabled(this.expanded);
            RotatingDrawable rotatingDrawable2 = this.rotatingDrawable;
            if (rotatingDrawable2 != null) {
                rotatingDrawable2.setRotation(this.expanded ? EXPANDED_PLUS_ROTATION : 0.0f);
            }
            super.onRestoreInstanceState(savedState.getSuperState());
            return;
        }
        super.onRestoreInstanceState(parcelable);
    }

    public void show() {
        show(false);
    }

    public void show(boolean z) {
        setVisibility(0);
        this.menuButton.show();
        if (z) {
            expand();
        }
    }

    public void hide() {
        hide(true);
    }

    public void hide(boolean z) {
        if (z) {
            collapse();
        }
        this.menuButton.hide();
        setVisibility(8);
    }

    public void setOverlayColor(int i) {
        ViewParent parent = getParent();
        if (parent != null && (parent instanceof FABsMenuLayout)) {
            ((FABsMenuLayout) parent).setOverlayColor(i);
        }
    }

    public int getMenuMargins() {
        return this.menuMargins;
    }

    public void setMenuMargins(int i) {
        this.menuMargins = i;
        setMenuTopMargin(i);
        setMenuBottomMargin(i);
        setMenuLeftMargin(i);
        setMenuRightMargin(i);
        requestLayout();
    }

    public int getMenuTopMargin() {
        return this.menuTopMargin;
    }

    public void setMenuTopMargin(int i) {
        this.menuTopMargin = i;
        requestLayout();
    }

    public int getMenuBottomMargin() {
        return this.menuBottomMargin;
    }

    public void setMenuBottomMargin(int i) {
        this.menuBottomMargin = i;
        requestLayout();
    }

    public int getMenuRightMargin() {
        return this.menuRightMargin;
    }

    public void setMenuRightMargin(int i) {
        this.menuRightMargin = i;
        requestLayout();
    }

    public int getMenuLeftMargin() {
        return this.menuLeftMargin;
    }

    public void setMenuLeftMargin(int i) {
        this.menuLeftMargin = i;
        requestLayout();
    }

    public int getMenuButtonColor() {
        return this.menuButtonColor;
    }

    public void setMenuButtonColor(int i) {
        this.menuButton.setBackgroundColor(i);
        this.menuButtonColor = i;
    }

    public int getMenuButtonRippleColor() {
        return this.menuButtonRippleColor;
    }

    public void setMenuButtonRippleColor(int i) {
        this.menuButton.setRippleColor(i);
        this.menuButtonRippleColor = i;
    }

    public int getMenuButtonSize() {
        return this.menuButtonSize;
    }

    public void setMenuButtonSize(int i) {
        this.menuButton.setSize(i);
        this.menuButtonSize = i;
        requestLayout();
    }

    public int getExpandDirection() {
        return this.expandDirection;
    }

    public void setExpandDirection(int i) {
        this.expandDirection = i;
        requestLayout();
    }

    public MenuFAB getMenuButton() {
        return this.menuButton;
    }

    public void setMenuButton(MenuFAB menuFAB) {
        this.menuButton = menuFAB;
        requestLayout();
    }

    public Drawable getMenuButtonIcon() {
        return this.menuButtonIcon;
    }

    public void setMenuButtonIcon(Bitmap bitmap) {
        try {
            setMenuButtonIcon((Drawable) new BitmapDrawable(getResources(), bitmap));
        } catch (Exception e) {
            Log.e(TAG, "Failure setting MenuButton icon", e);
        }
    }

    public void setMenuButtonIcon(Uri uri) {
        try {
            Drawable createFromStream = Drawable.createFromStream(getContext().getContentResolver().openInputStream(uri), uri.toString());
            if (createFromStream != null) {
                setMenuButtonIcon(createFromStream);
            }
        } catch (Exception e) {
            Log.e(TAG, "Failure setting MenuButton icon", e);
        }
    }

    public void setMenuButtonIcon(int i) {
        try {
            Drawable drawable = ContextCompat.getDrawable(getContext(), i);
            if (drawable != null) {
                setMenuButtonIcon(drawable);
                return;
            }
            throw new NullPointerException("The icon you try to assign to FABsMenu does not exist");
        } catch (Exception e) {
            Log.e(TAG, "Failure setting MenuButton icon", e);
        }
    }

    public void setMenuButtonIcon(Drawable drawable) {
        this.menuButton.setImageDrawable(drawable);
        this.menuButtonIcon = drawable;
        createRotatingDrawable();
    }

    public RotatingDrawable getRotatingDrawable() {
        return this.rotatingDrawable;
    }

    public int getButtonSpacing() {
        return this.buttonSpacing;
    }

    public void setButtonSpacing(int i) {
        this.buttonSpacing = i;
        requestLayout();
    }

    public int getLabelsMargin() {
        return this.labelsMargin;
    }

    public void setLabelsMargin(int i) {
        this.labelsMargin = i;
        requestLayout();
    }

    public int getLabelsPosition() {
        return this.labelsPosition;
    }

    public void setLabelsPosition(int i) {
        this.labelsPosition = i;
        requestLayout();
    }

    public int getButtonsCount() {
        return this.buttonsCount;
    }

    public int getMaxButtonWidth() {
        return this.maxButtonWidth;
    }

    public void setMaxButtonWidth(int i) {
        this.maxButtonWidth = i;
        requestLayout();
    }

    public int getMaxButtonHeight() {
        return this.maxButtonHeight;
    }

    public void setMaxButtonHeight(int i) {
        this.maxButtonHeight = i;
        requestLayout();
    }

    public FABsMenuListener getMenuListener() {
        return this.menuListener;
    }

    public void setMenuListener(FABsMenuListener fABsMenuListener) {
        this.menuListener = fABsMenuListener;
    }

    @Deprecated
    public void setMenuUpdateListener(FABsMenuListener fABsMenuListener) {
        setMenuListener(fABsMenuListener);
    }

    public void setLabelsVerticalOffset(int i) {
        this.labelsVerticalOffset = i;
    }

    public int getAnimationDuration() {
        return this.animationDuration;
    }

    public void setAnimationDuration(int i) {
        setAnimationDuration(i, true);
    }

    public void setAnimationDuration(int i, boolean z) {
        if (z) {
            ViewParent parent = getParent();
            if (parent != null && (parent instanceof FABsMenuLayout)) {
                ((FABsMenuLayout) parent).setAnimationDuration(i);
            }
        }
        this.animationDuration = i;
    }

    public Behavior getBehavior() {
        return new FABSnackbarBehavior();
    }
}
