package p101me.zhanghai.android.materialratingbar;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.util.Log;

/* renamed from: me.zhanghai.android.materialratingbar.ClipDrawableCompat */
class ClipDrawableCompat extends ClipDrawable implements TintableDrawable {
    private static final String TAG = ClipDrawableCompat.class.getSimpleName();
    private DummyConstantState mConstantState = new DummyConstantState();
    private Drawable mDrawable;

    /* renamed from: me.zhanghai.android.materialratingbar.ClipDrawableCompat$DummyConstantState */
    private class DummyConstantState extends ConstantState {
        public int getChangingConfigurations() {
            return 0;
        }

        private DummyConstantState() {
        }

        public Drawable newDrawable() {
            return ClipDrawableCompat.this;
        }
    }

    public ClipDrawableCompat(Drawable drawable, int i, int i2) {
        super(drawable, i, i2);
        this.mDrawable = drawable;
    }

    public Drawable getDrawable() {
        return this.mDrawable;
    }

    public void setTint(int i) {
        Drawable drawable = this.mDrawable;
        if (drawable instanceof TintableDrawable) {
            ((TintableDrawable) drawable).setTint(i);
            return;
        }
        Log.w(TAG, "Drawable did not implement TintableDrawable, it won't be tinted below Lollipop");
        super.setTint(i);
    }

    public void setTintList(ColorStateList colorStateList) {
        Drawable drawable = this.mDrawable;
        if (drawable instanceof TintableDrawable) {
            ((TintableDrawable) drawable).setTintList(colorStateList);
            return;
        }
        Log.w(TAG, "Drawable did not implement TintableDrawable, it won't be tinted below Lollipop");
        super.setTintList(colorStateList);
    }

    public void setTintMode(Mode mode) {
        Drawable drawable = this.mDrawable;
        if (drawable instanceof TintableDrawable) {
            ((TintableDrawable) drawable).setTintMode(mode);
            return;
        }
        Log.w(TAG, "Drawable did not implement TintableDrawable, it won't be tinted below Lollipop");
        super.setTintMode(mode);
    }

    public ConstantState getConstantState() {
        return this.mConstantState;
    }
}
