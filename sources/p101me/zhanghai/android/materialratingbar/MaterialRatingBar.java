package p101me.zhanghai.android.materialratingbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RatingBar;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.view.ViewCompat;
import p101me.zhanghai.android.materialratingbar.internal.DrawableCompat;

/* renamed from: me.zhanghai.android.materialratingbar.MaterialRatingBar */
public class MaterialRatingBar extends RatingBar {
    private static final String TAG = MaterialRatingBar.class.getSimpleName();
    private MaterialRatingDrawable mDrawable;
    private float mLastKnownRating;
    private OnRatingChangeListener mOnRatingChangeListener;
    private TintInfo mProgressTintInfo = new TintInfo();

    /* renamed from: me.zhanghai.android.materialratingbar.MaterialRatingBar$OnRatingChangeListener */
    public interface OnRatingChangeListener {
        void onRatingChanged(MaterialRatingBar materialRatingBar, float f);
    }

    /* renamed from: me.zhanghai.android.materialratingbar.MaterialRatingBar$TintInfo */
    private static class TintInfo {
        public boolean mHasIndeterminateTintList;
        public boolean mHasIndeterminateTintMode;
        public boolean mHasProgressBackgroundTintList;
        public boolean mHasProgressBackgroundTintMode;
        public boolean mHasProgressTintList;
        public boolean mHasProgressTintMode;
        public boolean mHasSecondaryProgressTintList;
        public boolean mHasSecondaryProgressTintMode;
        public ColorStateList mIndeterminateTintList;
        public Mode mIndeterminateTintMode;
        public ColorStateList mProgressBackgroundTintList;
        public Mode mProgressBackgroundTintMode;
        public ColorStateList mProgressTintList;
        public Mode mProgressTintMode;
        public ColorStateList mSecondaryProgressTintList;
        public Mode mSecondaryProgressTintMode;

        private TintInfo() {
        }
    }

    public MaterialRatingBar(Context context) {
        super(context);
        init(null, 0);
    }

    public MaterialRatingBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(attributeSet, 0);
    }

    public MaterialRatingBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(attributeSet, i);
    }

    private void init(AttributeSet attributeSet, int i) {
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(getContext(), attributeSet, C6022R.styleable.MaterialRatingBar, i, 0);
        if (obtainStyledAttributes.hasValue(C6022R.styleable.MaterialRatingBar_mrb_progressTint)) {
            this.mProgressTintInfo.mProgressTintList = obtainStyledAttributes.getColorStateList(C6022R.styleable.MaterialRatingBar_mrb_progressTint);
            this.mProgressTintInfo.mHasProgressTintList = true;
        }
        if (obtainStyledAttributes.hasValue(C6022R.styleable.MaterialRatingBar_mrb_progressTintMode)) {
            this.mProgressTintInfo.mProgressTintMode = DrawableCompat.parseTintMode(obtainStyledAttributes.getInt(C6022R.styleable.MaterialRatingBar_mrb_progressTintMode, -1), null);
            this.mProgressTintInfo.mHasProgressTintMode = true;
        }
        if (obtainStyledAttributes.hasValue(C6022R.styleable.MaterialRatingBar_mrb_secondaryProgressTint)) {
            this.mProgressTintInfo.mSecondaryProgressTintList = obtainStyledAttributes.getColorStateList(C6022R.styleable.MaterialRatingBar_mrb_secondaryProgressTint);
            this.mProgressTintInfo.mHasSecondaryProgressTintList = true;
        }
        if (obtainStyledAttributes.hasValue(C6022R.styleable.MaterialRatingBar_mrb_secondaryProgressTintMode)) {
            this.mProgressTintInfo.mSecondaryProgressTintMode = DrawableCompat.parseTintMode(obtainStyledAttributes.getInt(C6022R.styleable.MaterialRatingBar_mrb_secondaryProgressTintMode, -1), null);
            this.mProgressTintInfo.mHasSecondaryProgressTintMode = true;
        }
        if (obtainStyledAttributes.hasValue(C6022R.styleable.MaterialRatingBar_mrb_progressBackgroundTint)) {
            this.mProgressTintInfo.mProgressBackgroundTintList = obtainStyledAttributes.getColorStateList(C6022R.styleable.MaterialRatingBar_mrb_progressBackgroundTint);
            this.mProgressTintInfo.mHasProgressBackgroundTintList = true;
        }
        if (obtainStyledAttributes.hasValue(C6022R.styleable.MaterialRatingBar_mrb_progressBackgroundTintMode)) {
            this.mProgressTintInfo.mProgressBackgroundTintMode = DrawableCompat.parseTintMode(obtainStyledAttributes.getInt(C6022R.styleable.MaterialRatingBar_mrb_progressBackgroundTintMode, -1), null);
            this.mProgressTintInfo.mHasProgressBackgroundTintMode = true;
        }
        if (obtainStyledAttributes.hasValue(C6022R.styleable.MaterialRatingBar_mrb_indeterminateTint)) {
            this.mProgressTintInfo.mIndeterminateTintList = obtainStyledAttributes.getColorStateList(C6022R.styleable.MaterialRatingBar_mrb_indeterminateTint);
            this.mProgressTintInfo.mHasIndeterminateTintList = true;
        }
        if (obtainStyledAttributes.hasValue(C6022R.styleable.MaterialRatingBar_mrb_indeterminateTintMode)) {
            this.mProgressTintInfo.mIndeterminateTintMode = DrawableCompat.parseTintMode(obtainStyledAttributes.getInt(C6022R.styleable.MaterialRatingBar_mrb_indeterminateTintMode, -1), null);
            this.mProgressTintInfo.mHasIndeterminateTintMode = true;
        }
        obtainStyledAttributes.recycle();
        this.mDrawable = new MaterialRatingDrawable(getContext());
        this.mDrawable.setStarCount(getNumStars());
        setProgressDrawable(this.mDrawable);
    }

    public void setNumStars(int i) {
        super.setNumStars(i);
        MaterialRatingDrawable materialRatingDrawable = this.mDrawable;
        if (materialRatingDrawable != null) {
            materialRatingDrawable.setStarCount(i);
        }
    }

    /* access modifiers changed from: protected */
    public synchronized void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int measuredHeight = getMeasuredHeight();
        setMeasuredDimension(ViewCompat.resolveSizeAndState(Math.round(((float) measuredHeight) * this.mDrawable.getTileRatio() * ((float) getNumStars())), i, 0), measuredHeight);
    }

    public void setProgressDrawable(Drawable drawable) {
        super.setProgressDrawable(drawable);
        if (this.mProgressTintInfo != null) {
            applyProgressTints();
        }
    }

    public void setIndeterminateDrawable(Drawable drawable) {
        super.setIndeterminateDrawable(drawable);
        if (this.mProgressTintInfo != null) {
            applyIndeterminateTint();
        }
    }

    public ColorStateList getProgressTintList() {
        return this.mProgressTintInfo.mProgressTintList;
    }

    public void setProgressTintList(ColorStateList colorStateList) {
        TintInfo tintInfo = this.mProgressTintInfo;
        tintInfo.mProgressTintList = colorStateList;
        tintInfo.mHasProgressTintList = true;
        applyPrimaryProgressTint();
    }

    public Mode getProgressTintMode() {
        return this.mProgressTintInfo.mProgressTintMode;
    }

    public void setProgressTintMode(Mode mode) {
        TintInfo tintInfo = this.mProgressTintInfo;
        tintInfo.mProgressTintMode = mode;
        tintInfo.mHasProgressTintMode = true;
        applyPrimaryProgressTint();
    }

    public ColorStateList getSecondaryProgressTintList() {
        return this.mProgressTintInfo.mSecondaryProgressTintList;
    }

    public void setSecondaryProgressTintList(ColorStateList colorStateList) {
        TintInfo tintInfo = this.mProgressTintInfo;
        tintInfo.mSecondaryProgressTintList = colorStateList;
        tintInfo.mHasSecondaryProgressTintList = true;
        applySecondaryProgressTint();
    }

    public Mode getSecondaryProgressTintMode() {
        return this.mProgressTintInfo.mSecondaryProgressTintMode;
    }

    public void setSecondaryProgressTintMode(Mode mode) {
        TintInfo tintInfo = this.mProgressTintInfo;
        tintInfo.mSecondaryProgressTintMode = mode;
        tintInfo.mHasSecondaryProgressTintMode = true;
        applySecondaryProgressTint();
    }

    public ColorStateList getProgressBackgroundTintList() {
        return this.mProgressTintInfo.mProgressBackgroundTintList;
    }

    public void setProgressBackgroundTintList(ColorStateList colorStateList) {
        TintInfo tintInfo = this.mProgressTintInfo;
        tintInfo.mProgressBackgroundTintList = colorStateList;
        tintInfo.mHasProgressBackgroundTintList = true;
        applyProgressBackgroundTint();
    }

    public Mode getProgressBackgroundTintMode() {
        return this.mProgressTintInfo.mProgressBackgroundTintMode;
    }

    public void setProgressBackgroundTintMode(Mode mode) {
        TintInfo tintInfo = this.mProgressTintInfo;
        tintInfo.mProgressBackgroundTintMode = mode;
        tintInfo.mHasProgressBackgroundTintMode = true;
        applyProgressBackgroundTint();
    }

    public ColorStateList getIndeterminateTintList() {
        return this.mProgressTintInfo.mIndeterminateTintList;
    }

    public void setIndeterminateTintList(ColorStateList colorStateList) {
        TintInfo tintInfo = this.mProgressTintInfo;
        tintInfo.mIndeterminateTintList = colorStateList;
        tintInfo.mHasIndeterminateTintList = true;
        applyIndeterminateTint();
    }

    public Mode getIndeterminateTintMode() {
        return this.mProgressTintInfo.mIndeterminateTintMode;
    }

    public void setIndeterminateTintMode(Mode mode) {
        TintInfo tintInfo = this.mProgressTintInfo;
        tintInfo.mIndeterminateTintMode = mode;
        tintInfo.mHasIndeterminateTintMode = true;
        applyIndeterminateTint();
    }

    private void applyProgressTints() {
        if (getProgressDrawable() != null) {
            applyPrimaryProgressTint();
            applyProgressBackgroundTint();
            applySecondaryProgressTint();
        }
    }

    private void applyPrimaryProgressTint() {
        if (getProgressDrawable() != null) {
            if (this.mProgressTintInfo.mHasProgressTintList || this.mProgressTintInfo.mHasProgressTintMode) {
                Drawable tintTargetFromProgressDrawable = getTintTargetFromProgressDrawable(16908301, true);
                if (tintTargetFromProgressDrawable != null) {
                    applyTintForDrawable(tintTargetFromProgressDrawable, this.mProgressTintInfo.mProgressTintList, this.mProgressTintInfo.mHasProgressTintList, this.mProgressTintInfo.mProgressTintMode, this.mProgressTintInfo.mHasProgressTintMode);
                }
            }
        }
    }

    private void applySecondaryProgressTint() {
        if (getProgressDrawable() != null) {
            if (this.mProgressTintInfo.mHasSecondaryProgressTintList || this.mProgressTintInfo.mHasSecondaryProgressTintMode) {
                Drawable tintTargetFromProgressDrawable = getTintTargetFromProgressDrawable(16908303, false);
                if (tintTargetFromProgressDrawable != null) {
                    applyTintForDrawable(tintTargetFromProgressDrawable, this.mProgressTintInfo.mSecondaryProgressTintList, this.mProgressTintInfo.mHasSecondaryProgressTintList, this.mProgressTintInfo.mSecondaryProgressTintMode, this.mProgressTintInfo.mHasSecondaryProgressTintMode);
                }
            }
        }
    }

    private void applyProgressBackgroundTint() {
        if (getProgressDrawable() != null) {
            if (this.mProgressTintInfo.mHasProgressBackgroundTintList || this.mProgressTintInfo.mHasProgressBackgroundTintMode) {
                Drawable tintTargetFromProgressDrawable = getTintTargetFromProgressDrawable(16908288, false);
                if (tintTargetFromProgressDrawable != null) {
                    applyTintForDrawable(tintTargetFromProgressDrawable, this.mProgressTintInfo.mProgressBackgroundTintList, this.mProgressTintInfo.mHasProgressBackgroundTintList, this.mProgressTintInfo.mProgressBackgroundTintMode, this.mProgressTintInfo.mHasProgressBackgroundTintMode);
                }
            }
        }
    }

    private Drawable getTintTargetFromProgressDrawable(int i, boolean z) {
        Drawable progressDrawable = getProgressDrawable();
        if (progressDrawable == null) {
            return null;
        }
        progressDrawable.mutate();
        Drawable findDrawableByLayerId = progressDrawable instanceof LayerDrawable ? ((LayerDrawable) progressDrawable).findDrawableByLayerId(i) : null;
        if (findDrawableByLayerId == null && z) {
            findDrawableByLayerId = progressDrawable;
        }
        return findDrawableByLayerId;
    }

    private void applyIndeterminateTint() {
        Drawable indeterminateDrawable = getIndeterminateDrawable();
        if (indeterminateDrawable != null) {
            if (this.mProgressTintInfo.mHasIndeterminateTintList || this.mProgressTintInfo.mHasIndeterminateTintMode) {
                indeterminateDrawable.mutate();
                applyTintForDrawable(indeterminateDrawable, this.mProgressTintInfo.mIndeterminateTintList, this.mProgressTintInfo.mHasIndeterminateTintList, this.mProgressTintInfo.mIndeterminateTintMode, this.mProgressTintInfo.mHasIndeterminateTintMode);
            }
        }
    }

    private void applyTintForDrawable(Drawable drawable, ColorStateList colorStateList, boolean z, Mode mode, boolean z2) {
        if (z || z2) {
            String str = "Drawable did not implement TintableDrawable, it won't be tinted below Lollipop";
            if (z) {
                if (drawable instanceof TintableDrawable) {
                    ((TintableDrawable) drawable).setTintList(colorStateList);
                } else {
                    Log.w(TAG, str);
                    if (VERSION.SDK_INT >= 21) {
                        drawable.setTintList(colorStateList);
                    }
                }
            }
            if (z2) {
                if (drawable instanceof TintableDrawable) {
                    ((TintableDrawable) drawable).setTintMode(mode);
                } else {
                    Log.w(TAG, str);
                    if (VERSION.SDK_INT >= 21) {
                        drawable.setTintMode(mode);
                    }
                }
            }
            if (drawable.isStateful()) {
                drawable.setState(getDrawableState());
            }
        }
    }

    public OnRatingChangeListener getOnRatingChangeListener() {
        return this.mOnRatingChangeListener;
    }

    public void setOnRatingChangeListener(OnRatingChangeListener onRatingChangeListener) {
        this.mOnRatingChangeListener = onRatingChangeListener;
    }

    public synchronized void setSecondaryProgress(int i) {
        super.setSecondaryProgress(i);
        float rating = getRating();
        if (!(this.mOnRatingChangeListener == null || rating == this.mLastKnownRating)) {
            this.mOnRatingChangeListener.onRatingChanged(this, rating);
        }
        this.mLastKnownRating = rating;
    }
}
