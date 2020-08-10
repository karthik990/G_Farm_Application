package com.startapp.android.publish.adsCommon.p089i;

import android.os.Bundle;

/* renamed from: com.startapp.android.publish.adsCommon.i.b */
/* compiled from: StartAppSDK */
public class C5045b {

    /* renamed from: a */
    private final Bundle f3297a;

    public C5045b(Bundle bundle) {
        this.f3297a = bundle;
    }

    /* renamed from: a */
    public String mo62352a() {
        return this.f3297a.getString("install_referrer");
    }

    /* renamed from: b */
    public long mo62353b() {
        return this.f3297a.getLong("referrer_click_timestamp_seconds");
    }

    /* renamed from: c */
    public long mo62354c() {
        return this.f3297a.getLong("install_begin_timestamp_seconds");
    }
}
