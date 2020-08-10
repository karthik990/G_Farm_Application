package com.startapp.android.publish.ads.video.p071a;

import java.util.List;

/* renamed from: com.startapp.android.publish.ads.video.a.a */
/* compiled from: StartAppSDK */
public class C4850a {

    /* renamed from: a */
    private List<String> f2740a;

    /* renamed from: b */
    private String f2741b;

    public C4850a(List<String> list, String str) {
        this.f2740a = list;
        this.f2741b = str;
    }

    /* renamed from: a */
    public List<String> mo61654a() {
        return this.f2740a;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[VideoEvent: tag=");
        sb.append(this.f2741b);
        sb.append(", fullUrls=");
        sb.append(this.f2740a.toString());
        sb.append("]");
        return sb.toString();
    }
}
