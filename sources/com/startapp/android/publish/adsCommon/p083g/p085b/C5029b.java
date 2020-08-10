package com.startapp.android.publish.adsCommon.p083g.p085b;

import android.content.Context;
import android.os.Build.VERSION;
import com.startapp.common.p092a.C5146c;
import com.startapp.common.p092a.C5155g;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.startapp.android.publish.adsCommon.g.b.b */
/* compiled from: StartAppSDK */
public class C5029b {

    /* renamed from: a */
    private Context f3267a;

    /* renamed from: b */
    private List<String> f3268b = new ArrayList();

    public C5029b(Context context) {
        this.f3267a = context.getApplicationContext();
    }

    /* renamed from: a */
    public boolean mo62328a() {
        boolean z = this.f3268b.contains("calendar") && VERSION.SDK_INT >= 14 && C5146c.m3760a(this.f3267a, "android.permission.WRITE_CALENDAR");
        StringBuilder sb = new StringBuilder();
        sb.append("isCalendarSupported ");
        sb.append(z);
        C5155g.m3807a("MraidNativeFeatureManager", 3, sb.toString());
        return z;
    }

    /* renamed from: b */
    public boolean mo62330b() {
        boolean contains = this.f3268b.contains("inlineVideo");
        StringBuilder sb = new StringBuilder();
        sb.append("isInlineVideoSupported ");
        sb.append(contains);
        C5155g.m3807a("MraidNativeFeatureManager", 3, sb.toString());
        return contains;
    }

    /* renamed from: c */
    public boolean mo62331c() {
        boolean z = this.f3268b.contains("sms") && C5146c.m3760a(this.f3267a, "android.permission.SEND_SMS");
        StringBuilder sb = new StringBuilder();
        sb.append("isSmsSupported ");
        sb.append(z);
        C5155g.m3807a("MraidNativeFeatureManager", 3, sb.toString());
        return z;
    }

    /* renamed from: d */
    public boolean mo62332d() {
        boolean contains = this.f3268b.contains("storePicture");
        StringBuilder sb = new StringBuilder();
        sb.append("isStorePictureSupported ");
        sb.append(contains);
        C5155g.m3807a("MraidNativeFeatureManager", 3, sb.toString());
        return contains;
    }

    /* renamed from: e */
    public boolean mo62333e() {
        boolean z = this.f3268b.contains("tel") && C5146c.m3760a(this.f3267a, "android.permission.CALL_PHONE");
        StringBuilder sb = new StringBuilder();
        sb.append("isTelSupported ");
        sb.append(z);
        C5155g.m3807a("MraidNativeFeatureManager", 3, sb.toString());
        return z;
    }

    /* renamed from: f */
    public List<String> mo62334f() {
        return this.f3268b;
    }

    /* renamed from: a */
    public boolean mo62329a(String str) {
        return mo62334f().contains(str);
    }
}
