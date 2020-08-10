package com.p021b.p022a.p023a.p024a.p030e;

import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewParent;

/* renamed from: com.b.a.a.a.e.f */
public final class C0992f {
    /* renamed from: a */
    public static float m266a(View view) {
        if (VERSION.SDK_INT >= 21) {
            return view.getZ();
        }
        return 0.0f;
    }

    /* renamed from: b */
    public static View m267b(View view) {
        ViewParent parent = view.getParent();
        if (parent instanceof View) {
            return (View) parent;
        }
        return null;
    }

    /* renamed from: c */
    public static boolean m268c(View view) {
        if ((VERSION.SDK_INT >= 19 && !view.isAttachedToWindow()) || !view.isShown()) {
            return false;
        }
        while (view != null) {
            if (view.getAlpha() == 0.0f) {
                return false;
            }
            view = m267b(view);
        }
        return true;
    }

    /* renamed from: d */
    public static boolean m269d(View view) {
        return (VERSION.SDK_INT < 19 || view.isAttachedToWindow()) && view.getVisibility() == 0 && view.getAlpha() != 0.0f;
    }
}
