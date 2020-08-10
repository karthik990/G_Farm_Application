package com.paypal.android.sdk.onetouch.core.fpti;

import p043io.reactivex.annotations.SchedulerSupport;

public enum TrackingPoint {
    WalletIsPresent(r1, "present"),
    WalletIsAbsent(r1, "absent"),
    PreflightBrowser(r5, r1),
    PreflightWallet(r5, r6),
    PreflightNone(r5, SchedulerSupport.NONE),
    SwitchToBrowser(r5, r1),
    SwitchToWallet(r5, r6),
    Cancel(r5, "cancel"),
    Return(r5, "return"),
    Error("switchback", "cancel", true);
    

    /* renamed from: mC */
    private final String f2255mC;

    /* renamed from: mD */
    private final String f2256mD;
    private final boolean mHasError;

    private TrackingPoint(String str, String str2, boolean z) {
        this.f2255mC = str;
        this.f2256mD = str2;
        this.mHasError = z;
    }

    private TrackingPoint(String str, String str2) {
        this(r7, r8, str, str2, false);
    }

    public String getCd() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.f2255mC);
        sb.append(":");
        sb.append(this.f2256mD);
        return sb.toString();
    }

    public boolean hasError() {
        return this.mHasError;
    }
}
