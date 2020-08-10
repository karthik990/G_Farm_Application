package com.startapp.android.publish.adsCommon.p082f;

import com.startapp.android.publish.adsCommon.Utils.C4939c;
import com.startapp.android.publish.adsCommon.Utils.C4941e;
import com.startapp.common.p092a.C5138a;

/* renamed from: com.startapp.android.publish.adsCommon.f.b */
/* compiled from: StartAppSDK */
public class C5012b extends C5016e {

    /* renamed from: a */
    private String f3218a;

    /* renamed from: b */
    private String f3219b;

    /* renamed from: c */
    private boolean f3220c;

    /* renamed from: d */
    private String f3221d;

    public C5012b(C5015d dVar) {
        super(dVar);
    }

    public C4941e getNameValueJson() {
        C4941e nameValueJson = super.getNameValueJson();
        if (nameValueJson == null) {
            nameValueJson = new C4939c();
        }
        nameValueJson.mo62030a("sens", (Object) mo62271a(), false);
        nameValueJson.mo62030a("bt", (Object) mo62274b(), false);
        nameValueJson.mo62030a("isService", (Object) Boolean.valueOf(mo62277c()), false);
        nameValueJson.mo62030a("packagingType", (Object) mo62278d(), false);
        return nameValueJson;
    }

    /* renamed from: a */
    public String mo62271a() {
        return this.f3218a;
    }

    /* renamed from: b */
    public String mo62274b() {
        return this.f3219b;
    }

    /* renamed from: a */
    public void mo62272a(String str) {
        if (str != null) {
            this.f3218a = C5138a.m3717c(str);
        }
    }

    /* renamed from: b */
    public void mo62275b(String str) {
        if (str != null) {
            this.f3219b = C5138a.m3717c(str);
        }
    }

    /* renamed from: c */
    public boolean mo62277c() {
        return this.f3220c;
    }

    /* renamed from: a */
    public void mo62273a(boolean z) {
        this.f3220c = z;
    }

    /* renamed from: d */
    public String mo62278d() {
        return this.f3221d;
    }

    /* renamed from: c */
    public void mo62276c(String str) {
        this.f3221d = str;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(" DataEventRequest [");
        sb.append("sensors=");
        sb.append(this.f3218a);
        sb.append(", bluetooth=");
        sb.append(this.f3219b);
        sb.append(", isService=");
        sb.append(this.f3220c);
        sb.append(", packagingType=");
        sb.append(this.f3221d);
        sb.append("]");
        return sb.toString();
    }
}
