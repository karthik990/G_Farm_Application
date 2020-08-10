package com.ogaclejapan.smarttablayout;

import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.view.ViewCompat;

/* renamed from: com.ogaclejapan.smarttablayout.Utils */
final class C4560Utils {
    static int getMeasuredWidth(View view) {
        if (view == null) {
            return 0;
        }
        return view.getMeasuredWidth();
    }

    static int getWidth(View view) {
        if (view == null) {
            return 0;
        }
        return view.getWidth();
    }

    static int getWidthWithMargin(View view) {
        return getWidth(view) + getMarginHorizontally(view);
    }

    static int getStart(View view) {
        return getStart(view, false);
    }

    static int getStart(View view, boolean z) {
        if (view == null) {
            return 0;
        }
        if (isLayoutRtl(view)) {
            return z ? view.getRight() - getPaddingStart(view) : view.getRight();
        }
        return z ? view.getLeft() + getPaddingStart(view) : view.getLeft();
    }

    static int getEnd(View view) {
        return getEnd(view, false);
    }

    static int getEnd(View view, boolean z) {
        if (view == null) {
            return 0;
        }
        if (isLayoutRtl(view)) {
            return z ? view.getLeft() + getPaddingEnd(view) : view.getLeft();
        }
        return z ? view.getRight() - getPaddingEnd(view) : view.getRight();
    }

    static int getPaddingStart(View view) {
        if (view == null) {
            return 0;
        }
        return ViewCompat.getPaddingStart(view);
    }

    static int getPaddingEnd(View view) {
        if (view == null) {
            return 0;
        }
        return ViewCompat.getPaddingEnd(view);
    }

    static int getPaddingHorizontally(View view) {
        if (view == null) {
            return 0;
        }
        return view.getPaddingLeft() + view.getPaddingRight();
    }

    static int getMarginStart(View view) {
        if (view == null) {
            return 0;
        }
        return MarginLayoutParamsCompat.getMarginStart((MarginLayoutParams) view.getLayoutParams());
    }

    static int getMarginEnd(View view) {
        if (view == null) {
            return 0;
        }
        return MarginLayoutParamsCompat.getMarginEnd((MarginLayoutParams) view.getLayoutParams());
    }

    static int getMarginHorizontally(View view) {
        if (view == null) {
            return 0;
        }
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) view.getLayoutParams();
        return MarginLayoutParamsCompat.getMarginStart(marginLayoutParams) + MarginLayoutParamsCompat.getMarginEnd(marginLayoutParams);
    }

    static boolean isLayoutRtl(View view) {
        return ViewCompat.getLayoutDirection(view) == 1;
    }

    private C4560Utils() {
    }
}
