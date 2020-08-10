package com.startapp.android.publish.ads.video.p073c.p074a.p075a;

import java.util.ArrayList;
import java.util.List;

/* renamed from: com.startapp.android.publish.ads.video.c.a.a.e */
/* compiled from: StartAppSDK */
public class C4879e {

    /* renamed from: a */
    private String f2856a;

    /* renamed from: b */
    private List<String> f2857b;

    /* renamed from: c */
    private List<String> f2858c;

    /* renamed from: a */
    public String mo61739a() {
        return this.f2856a;
    }

    /* renamed from: a */
    public void mo61740a(String str) {
        this.f2856a = str;
    }

    /* renamed from: b */
    public List<String> mo61741b() {
        if (this.f2857b == null) {
            this.f2857b = new ArrayList();
        }
        return this.f2857b;
    }

    /* renamed from: c */
    public List<String> mo61742c() {
        if (this.f2858c == null) {
            this.f2858c = new ArrayList();
        }
        return this.f2858c;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("VASTVideoClicks [clickThrough=");
        sb.append(this.f2856a);
        sb.append(", clickTracking=[");
        sb.append(this.f2857b);
        sb.append("], customClick=[");
        sb.append(this.f2858c);
        sb.append("] ]");
        return sb.toString();
    }
}
