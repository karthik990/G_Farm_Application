package com.startapp.android.publish.adsCommon.p077a;

import com.startapp.android.publish.adsCommon.AdsConstants;
import com.startapp.common.p042c.C2362f;
import java.io.Serializable;

/* renamed from: com.startapp.android.publish.adsCommon.a.g */
/* compiled from: StartAppSDK */
public class C4959g implements Serializable {

    /* renamed from: a */
    private static transient C4959g f3079a = new C4959g();
    @C2362f(mo20785a = true)
    private C4957e adRules = new C4957e();
    private String adaptMetaDataUpdateVersion = AdsConstants.f3024h;

    private C4959g() {
    }

    /* renamed from: a */
    public static C4959g m2962a() {
        return f3079a;
    }

    /* renamed from: b */
    public C4957e mo62067b() {
        return this.adRules;
    }
}
