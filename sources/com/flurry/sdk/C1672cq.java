package com.flurry.sdk;

import android.content.Context;

/* renamed from: com.flurry.sdk.cq */
public final class C1672cq {
    /* renamed from: a */
    public static C1671cp m725a(Context context, String str) {
        if (str.startsWith("http://") || str.startsWith("https://")) {
            return new C1668cm(str);
        }
        return new C1669cn(context, str);
    }
}
