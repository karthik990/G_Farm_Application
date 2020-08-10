package p101me.zhanghai.android.materialratingbar.internal;

import android.content.Context;
import android.content.res.TypedArray;

/* renamed from: me.zhanghai.android.materialratingbar.internal.ThemeUtils */
public class ThemeUtils {
    private ThemeUtils() {
    }

    public static int getColorFromAttrRes(int i, Context context) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[]{i});
        try {
            return obtainStyledAttributes.getColor(0, 0);
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    public static float getFloatFromAttrRes(int i, Context context) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[]{i});
        try {
            return obtainStyledAttributes.getFloat(0, 0.0f);
        } finally {
            obtainStyledAttributes.recycle();
        }
    }
}
