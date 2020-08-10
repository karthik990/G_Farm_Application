package com.startapp.android.publish.adsCommon.p078b;

import java.io.Serializable;

/* renamed from: com.startapp.android.publish.adsCommon.b.a */
/* compiled from: StartAppSDK */
public class C4984a implements Serializable {
    private static final long serialVersionUID = 1;
    private int adAttempt = 0;
    private boolean appPresence = false;
    private boolean isShown = true;
    private int minAppVersion = 0;
    private String packageName;
    private String trackingUrl;

    public C4984a(String str, String str2, int i, int i2) {
        this.trackingUrl = str;
        this.packageName = str2;
        this.adAttempt = i;
        this.minAppVersion = i2;
    }

    /* renamed from: a */
    public String mo62187a() {
        return this.trackingUrl;
    }

    /* renamed from: a */
    public void mo62188a(String str) {
        this.trackingUrl = str;
    }

    /* renamed from: b */
    public String mo62190b() {
        return this.packageName;
    }

    /* renamed from: c */
    public boolean mo62192c() {
        return this.isShown;
    }

    /* renamed from: a */
    public void mo62189a(boolean z) {
        this.isShown = z;
    }

    /* renamed from: d */
    public boolean mo62193d() {
        return this.appPresence;
    }

    /* renamed from: b */
    public void mo62191b(boolean z) {
        this.appPresence = z;
    }

    /* renamed from: e */
    public int mo62194e() {
        return this.minAppVersion;
    }
}
