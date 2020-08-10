package p101me.zhanghai.android.materialprogressbar;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Animatable;
import androidx.core.view.ViewCompat;
import p101me.zhanghai.android.materialprogressbar.internal.ThemeUtils;

/* renamed from: me.zhanghai.android.materialprogressbar.BaseIndeterminateProgressDrawable */
abstract class BaseIndeterminateProgressDrawable extends BaseProgressDrawable implements Animatable {
    protected Animator[] mAnimators;

    public BaseIndeterminateProgressDrawable(Context context) {
        setTint(ThemeUtils.getColorFromAttrRes(C6013R.attr.colorControlActivated, ViewCompat.MEASURED_STATE_MASK, context));
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (isStarted()) {
            invalidateSelf();
        }
    }

    public void start() {
        if (!isStarted()) {
            for (Animator start : this.mAnimators) {
                start.start();
            }
            invalidateSelf();
        }
    }

    private boolean isStarted() {
        for (Animator isStarted : this.mAnimators) {
            if (isStarted.isStarted()) {
                return true;
            }
        }
        return false;
    }

    public void stop() {
        for (Animator end : this.mAnimators) {
            end.end();
        }
    }

    public boolean isRunning() {
        for (Animator isRunning : this.mAnimators) {
            if (isRunning.isRunning()) {
                return true;
            }
        }
        return false;
    }
}
