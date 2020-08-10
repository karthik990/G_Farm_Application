package com.startapp.android.publish.ads.video.p073c.p074a.p075a;

import android.text.TextUtils;
import com.startapp.common.p092a.C5155g;

/* renamed from: com.startapp.android.publish.ads.video.c.a.a.b */
/* compiled from: StartAppSDK */
public class C4876b {

    /* renamed from: a */
    private String f2842a;

    /* renamed from: b */
    private String f2843b;

    /* renamed from: c */
    private String f2844c;

    /* renamed from: d */
    private String f2845d;

    /* renamed from: e */
    private Integer f2846e;

    /* renamed from: f */
    private Integer f2847f;

    /* renamed from: g */
    private Integer f2848g;

    /* renamed from: h */
    private Boolean f2849h;

    /* renamed from: i */
    private Boolean f2850i;

    /* renamed from: j */
    private String f2851j;

    /* renamed from: a */
    private boolean m2658a(int i) {
        return i > 0 && i < 5000;
    }

    /* renamed from: a */
    public String mo61717a() {
        return this.f2842a;
    }

    /* renamed from: a */
    public void mo61720a(String str) {
        this.f2842a = str;
    }

    /* renamed from: b */
    public void mo61724b(String str) {
        this.f2843b = str;
    }

    /* renamed from: c */
    public void mo61727c(String str) {
        this.f2844c = str;
    }

    /* renamed from: b */
    public String mo61721b() {
        return this.f2845d;
    }

    /* renamed from: d */
    public void mo61729d(String str) {
        this.f2845d = str;
    }

    /* renamed from: c */
    public Integer mo61725c() {
        return this.f2846e;
    }

    /* renamed from: a */
    public void mo61719a(Integer num) {
        this.f2846e = num;
    }

    /* renamed from: d */
    public Integer mo61728d() {
        return this.f2847f;
    }

    /* renamed from: b */
    public void mo61723b(Integer num) {
        this.f2847f = num;
    }

    /* renamed from: e */
    public Integer mo61730e() {
        return this.f2848g;
    }

    /* renamed from: c */
    public void mo61726c(Integer num) {
        this.f2848g = num;
    }

    /* renamed from: a */
    public void mo61718a(Boolean bool) {
        this.f2849h = bool;
    }

    /* renamed from: b */
    public void mo61722b(Boolean bool) {
        this.f2850i = bool;
    }

    /* renamed from: e */
    public void mo61731e(String str) {
        this.f2851j = str;
    }

    /* renamed from: f */
    public boolean mo61732f() {
        String str = "VASTMediaFile";
        if (TextUtils.isEmpty(mo61721b())) {
            C5155g.m3807a(str, 3, "Validator error: mediaFile type empty");
            return false;
        }
        Integer e = mo61730e();
        Integer d = mo61728d();
        if (e == null || d == null || !m2658a(e.intValue()) || !m2658a(d.intValue())) {
            C5155g.m3807a(str, 3, "Validator error: mediaFile invalid size");
            return false;
        } else if (!TextUtils.isEmpty(mo61717a())) {
            return true;
        } else {
            C5155g.m3807a(str, 3, "Validator error: mediaFile url empty");
            return false;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MediaFile [url=");
        sb.append(this.f2842a);
        sb.append(", id=");
        sb.append(this.f2843b);
        sb.append(", delivery=");
        sb.append(this.f2844c);
        sb.append(", type=");
        sb.append(this.f2845d);
        sb.append(", bitrate=");
        sb.append(this.f2846e);
        sb.append(", width=");
        sb.append(this.f2847f);
        sb.append(", height=");
        sb.append(this.f2848g);
        sb.append(", scalable=");
        sb.append(this.f2849h);
        sb.append(", maintainAspectRatio=");
        sb.append(this.f2850i);
        sb.append(", apiFramework=");
        sb.append(this.f2851j);
        sb.append("]");
        return sb.toString();
    }
}
