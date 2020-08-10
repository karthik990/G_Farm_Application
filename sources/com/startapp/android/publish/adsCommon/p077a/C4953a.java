package com.startapp.android.publish.adsCommon.p077a;

import com.startapp.android.publish.common.model.AdPreferences.Placement;

/* renamed from: com.startapp.android.publish.adsCommon.a.a */
/* compiled from: StartAppSDK */
public class C4953a implements Comparable<C4953a> {

    /* renamed from: a */
    private long f3066a = System.currentTimeMillis();

    /* renamed from: b */
    private Placement f3067b;

    /* renamed from: c */
    private String f3068c;

    public C4953a(Placement placement, String str) {
        this.f3067b = placement;
        if (str == null) {
            str = "";
        }
        this.f3068c = str;
    }

    /* renamed from: a */
    public Placement mo62049a() {
        return this.f3067b;
    }

    /* renamed from: b */
    public String mo62050b() {
        return this.f3068c;
    }

    /* renamed from: a */
    public int compareTo(C4953a aVar) {
        long j = this.f3066a - aVar.f3066a;
        if (j > 0) {
            return 1;
        }
        return j == 0 ? 0 : -1;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AdDisplayEvent [displayTime=");
        sb.append(this.f3066a);
        sb.append(", placement=");
        sb.append(this.f3067b);
        sb.append(", adTag=");
        sb.append(this.f3068c);
        sb.append("]");
        return sb.toString();
    }
}
