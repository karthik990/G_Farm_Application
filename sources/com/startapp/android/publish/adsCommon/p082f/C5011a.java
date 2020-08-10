package com.startapp.android.publish.adsCommon.p082f;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/* renamed from: com.startapp.android.publish.adsCommon.f.a */
/* compiled from: StartAppSDK */
public class C5011a implements Serializable {

    /* renamed from: a */
    private static final String f3217a = new String("https://imp.startappservice.com/tracking/infoEvent");
    private static final long serialVersionUID = 1;
    private boolean dns = false;
    public String hostPeriodic;
    public String hostSecured;
    private int retryNum = 3;
    private int retryTime = 10;
    private boolean sendHopsOnFirstSucceededSmartRedirect = false;
    private float succeededSmartRedirectInfoProbability = 0.01f;

    public C5011a() {
        String str = f3217a;
        this.hostSecured = str;
        this.hostPeriodic = str;
    }

    /* renamed from: a */
    public String mo62264a() {
        return this.hostSecured;
    }

    /* renamed from: b */
    public String mo62265b() {
        String str = this.hostPeriodic;
        return str != null ? str : f3217a;
    }

    /* renamed from: c */
    public boolean mo62266c() {
        return this.dns;
    }

    /* renamed from: d */
    public int mo62267d() {
        return this.retryNum;
    }

    /* renamed from: e */
    public long mo62268e() {
        return TimeUnit.SECONDS.toMillis((long) this.retryTime);
    }

    /* renamed from: f */
    public float mo62269f() {
        return this.succeededSmartRedirectInfoProbability;
    }

    /* renamed from: g */
    public boolean mo62270g() {
        return this.sendHopsOnFirstSucceededSmartRedirect;
    }
}
