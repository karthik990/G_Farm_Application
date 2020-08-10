package com.rengwuxian.materialedittext;

import android.content.Context;
import android.util.TypedValue;

class Density {
    Density() {
    }

    public static int dp2px(Context context, float f) {
        return Math.round(TypedValue.applyDimension(1, f, context.getResources().getDisplayMetrics()));
    }
}
