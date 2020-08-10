package p101me.zhanghai.android.materialprogressbar;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;

/* renamed from: me.zhanghai.android.materialprogressbar.TintableDrawable */
public interface TintableDrawable {
    void setTint(int i);

    void setTintList(ColorStateList colorStateList);

    void setTintMode(Mode mode);
}
