package p101me.zhanghai.android.materialratingbar.internal;

import android.graphics.PorterDuff.Mode;

/* renamed from: me.zhanghai.android.materialratingbar.internal.DrawableCompat */
public class DrawableCompat {
    public static Mode parseTintMode(int i, Mode mode) {
        if (i == 3) {
            return Mode.SRC_OVER;
        }
        if (i == 5) {
            return Mode.SRC_IN;
        }
        if (i == 9) {
            return Mode.SRC_ATOP;
        }
        switch (i) {
            case 14:
                return Mode.MULTIPLY;
            case 15:
                return Mode.SCREEN;
            case 16:
                return Mode.ADD;
            default:
                return mode;
        }
    }
}
