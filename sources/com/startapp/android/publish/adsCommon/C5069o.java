package com.startapp.android.publish.adsCommon;

import android.graphics.Rect;
import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewGroup;

/* renamed from: com.startapp.android.publish.adsCommon.o */
/* compiled from: StartAppSDK */
public class C5069o {

    /* renamed from: a */
    private final Rect f3368a = new Rect();

    /* renamed from: a */
    public boolean mo62441a(View view, int i) {
        boolean z = false;
        if (view != null && view.hasWindowFocus() && view.isShown() && !m3479a(view) && view.getRootView() != null && view.getRootView().getParent() != null) {
            if (!view.getGlobalVisibleRect(this.f3368a)) {
                return false;
            }
            if (view.getParent() instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view.getParent();
                for (int indexOfChild = viewGroup.indexOfChild(view) + 1; indexOfChild < viewGroup.getChildCount(); indexOfChild++) {
                    View childAt = viewGroup.getChildAt(indexOfChild);
                    if (childAt.getVisibility() == 0 && m3480a(view, childAt, 0)) {
                        return false;
                    }
                }
            }
            long height = ((long) this.f3368a.height()) * ((long) this.f3368a.width());
            long height2 = ((long) view.getHeight()) * ((long) view.getWidth());
            if (height2 > 0 && height * 100 >= ((long) i) * height2) {
                z = true;
            }
        }
        return z;
    }

    /* renamed from: a */
    private boolean m3479a(View view) {
        return VERSION.SDK_INT >= 11 && view.getAlpha() == 0.0f;
    }

    /* renamed from: a */
    private static boolean m3480a(View view, View view2, int i) {
        int[] iArr = new int[2];
        int[] iArr2 = new int[2];
        view.getLocationOnScreen(iArr);
        view2.getLocationOnScreen(iArr2);
        Rect rect = new Rect(iArr[0], iArr[1], iArr[0] + view.getWidth(), iArr[1] + view.getHeight());
        Rect rect2 = new Rect(iArr2[0], iArr2[1], iArr2[0] + view2.getWidth(), iArr2[1] + view2.getHeight());
        long max = ((long) Math.max(0, Math.min(rect.right, rect2.right) - Math.max(rect.left, rect2.left))) * ((long) Math.max(0, Math.min(rect.bottom, rect2.bottom) - Math.max(rect.top, rect2.top)));
        long height = ((long) view.getHeight()) * ((long) view.getWidth());
        if (max <= 0 || max * 100 <= ((long) i) * height) {
            return false;
        }
        return true;
    }
}
