package com.startapp.android.publish.ads.banner;

import android.content.Context;
import android.util.AttributeSet;

/* renamed from: com.startapp.android.publish.ads.banner.b */
/* compiled from: StartAppSDK */
class C4766b {

    /* renamed from: a */
    private Context f2529a;

    /* renamed from: b */
    private String f2530b;

    C4766b(Context context, AttributeSet attributeSet) {
        this.f2529a = context;
        this.f2530b = m2318a(attributeSet, "adTag");
    }

    /* renamed from: a */
    private String m2318a(AttributeSet attributeSet, String str) {
        String str2;
        try {
            int attributeResourceValue = attributeSet.getAttributeResourceValue(null, str, -1);
            if (attributeResourceValue != -1) {
                str2 = this.f2529a.getResources().getString(attributeResourceValue);
            } else {
                str2 = attributeSet.getAttributeValue(null, str);
            }
            return str2;
        } catch (Exception unused) {
            return null;
        }
    }

    /* renamed from: a */
    public String mo61298a() {
        return this.f2530b;
    }
}
