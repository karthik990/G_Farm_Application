package com.startapp.android.publish.adsCommon.p077a;

import java.io.Serializable;

/* renamed from: com.startapp.android.publish.adsCommon.a.f */
/* compiled from: StartAppSDK */
public class C4958f implements Serializable {
    private static final long serialVersionUID = 1;
    private String reason;
    private boolean shouldDisplayAd;

    public C4958f(boolean z, String str) {
        this.shouldDisplayAd = z;
        this.reason = str;
    }

    public C4958f(boolean z) {
        this(z, "");
    }

    /* renamed from: a */
    public boolean mo62064a() {
        return this.shouldDisplayAd;
    }

    /* renamed from: b */
    public String mo62065b() {
        return this.reason;
    }

    /* renamed from: c */
    public String mo62066c() {
        String str = this.reason;
        return str != null ? str.split(" ")[0] : "";
    }
}
