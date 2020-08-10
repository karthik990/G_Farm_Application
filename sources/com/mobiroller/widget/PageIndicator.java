package com.mobiroller.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.View.BaseSavedState;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.preview.C4290R;

public class PageIndicator extends View {
    private static final int MIN_DOT_COUNT = 1;
    public static final int NO_ACTIVE_DOT = -1;
    private static Rect sInRect = new Rect();
    private static Rect sOutRect = new Rect();
    private int mActiveDot;
    private int mDotCount;
    private Drawable mDotDrawable;
    private int mDotSpacing;
    private int mDotType;
    private int[] mExtraState;
    private int mGravity;
    private boolean mInitializing;

    public interface DotType {
        public static final int MULTIPLE = 1;
        public static final int SINGLE = 0;
    }

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        int activeDot;

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.activeDot = parcel.readInt();
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.activeDot);
        }
    }

    public PageIndicator(Context context) {
        this(context, null);
    }

    public PageIndicator(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.gdPageIndicatorStyle);
    }

    public PageIndicator(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initPageIndicator();
        this.mInitializing = true;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C4290R.styleable.PageIndicator, i, 0);
        setDotCount(obtainStyledAttributes.getInt(1, this.mDotCount));
        setActiveDot(obtainStyledAttributes.getInt(0, this.mActiveDot));
        setDotDrawable(obtainStyledAttributes.getDrawable(2));
        setDotSpacing(obtainStyledAttributes.getDimensionPixelSize(3, this.mDotSpacing));
        setGravity(obtainStyledAttributes.getInt(5, this.mGravity));
        setDotType(obtainStyledAttributes.getInt(4, this.mDotType));
        obtainStyledAttributes.recycle();
        this.mInitializing = false;
    }

    private void initPageIndicator() {
        this.mDotCount = 1;
        this.mGravity = 17;
        this.mActiveDot = 0;
        this.mDotSpacing = 0;
        this.mDotType = 0;
        this.mExtraState = onCreateDrawableState(1);
        mergeDrawableStates(this.mExtraState, SELECTED_STATE_SET);
    }

    public int getDotCount() {
        return this.mDotCount;
    }

    public void setDotCount(int i) {
        if (i < 1) {
            i = 1;
        }
        if (this.mDotCount != i) {
            this.mDotCount = i;
            requestLayout();
            invalidate();
        }
    }

    public int getActiveDot() {
        return this.mActiveDot;
    }

    public void setActiveDot(int i) {
        int i2 = -1;
        if (i < 0) {
            i = -1;
        }
        int i3 = this.mDotType;
        if (i3 == 0 ? i <= this.mDotCount - 1 : i3 != 1 || i <= this.mDotCount) {
            i2 = i;
        }
        this.mActiveDot = i2;
        invalidate();
    }

    public Drawable getDotDrawable() {
        return this.mDotDrawable;
    }

    public void setDotDrawable(Drawable drawable) {
        Drawable drawable2 = this.mDotDrawable;
        if (drawable != drawable2) {
            if (drawable2 != null) {
                drawable2.setCallback(null);
            }
            this.mDotDrawable = drawable;
            if (drawable != null) {
                if (drawable.getIntrinsicHeight() != -1 && drawable.getIntrinsicWidth() != -1) {
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    drawable.setCallback(this);
                    if (drawable.isStateful()) {
                        drawable.setState(getDrawableState());
                    }
                } else {
                    return;
                }
            }
            requestLayout();
            invalidate();
        }
    }

    public int getDotSpacing() {
        return this.mDotSpacing;
    }

    public void setDotSpacing(int i) {
        if (i != this.mDotSpacing) {
            this.mDotSpacing = i;
            requestLayout();
            invalidate();
        }
    }

    public int getGravity() {
        return this.mGravity;
    }

    public void setGravity(int i) {
        if (this.mGravity != i) {
            this.mGravity = i;
            invalidate();
        }
    }

    public int getDotType() {
        return this.mDotType;
    }

    public void setDotType(int i) {
        if ((i == 0 || i == 1) && this.mDotType != i) {
            this.mDotType = i;
            invalidate();
        }
    }

    public void requestLayout() {
        if (!this.mInitializing) {
            super.requestLayout();
        }
    }

    public void invalidate() {
        if (!this.mInitializing) {
            super.invalidate();
        }
    }

    /* access modifiers changed from: protected */
    public boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.mDotDrawable;
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        this.mExtraState = onCreateDrawableState(1);
        mergeDrawableStates(this.mExtraState, SELECTED_STATE_SET);
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int i3;
        Drawable drawable = this.mDotDrawable;
        int i4 = 0;
        if (drawable != null) {
            int i5 = this.mDotCount;
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int i6 = this.mDotSpacing;
            i4 = (i5 * (intrinsicWidth + i6)) - i6;
            i3 = drawable.getIntrinsicHeight();
        } else {
            i3 = 0;
        }
        setMeasuredDimension(resolveSize(i4 + getPaddingRight() + getPaddingLeft(), i), resolveSize(i3 + getPaddingBottom() + getPaddingTop(), i2));
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        Drawable drawable = this.mDotDrawable;
        if (drawable != null) {
            int i = this.mDotType == 0 ? this.mDotCount : this.mActiveDot;
            if (i > 0) {
                int intrinsicHeight = drawable.getIntrinsicHeight();
                int intrinsicWidth = drawable.getIntrinsicWidth();
                int i2 = this.mDotSpacing;
                int i3 = ((intrinsicWidth + i2) * i) - i2;
                int max = Math.max(0, i3);
                sInRect.set(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
                Gravity.apply(this.mGravity, max, intrinsicHeight, sInRect, sOutRect);
                canvas.save();
                canvas.translate((float) sOutRect.left, (float) sOutRect.top);
                for (int i4 = 0; i4 < i; i4++) {
                    if (drawable.isStateful()) {
                        int[] drawableState = getDrawableState();
                        if (this.mDotType == 1 || i4 == this.mActiveDot) {
                            drawableState = this.mExtraState;
                        }
                        drawable.setCallback(null);
                        drawable.setState(drawableState);
                        drawable.setCallback(this);
                    }
                    drawable.draw(canvas);
                    canvas.translate((float) (this.mDotSpacing + drawable.getIntrinsicWidth()), 0.0f);
                }
                canvas.restore();
            }
        }
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.activeDot = this.mActiveDot;
        return savedState;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mActiveDot = savedState.activeDot;
    }
}
