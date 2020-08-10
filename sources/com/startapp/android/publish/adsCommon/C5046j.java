package com.startapp.android.publish.adsCommon;

import android.content.Context;
import com.startapp.android.publish.adsCommon.Utils.C4940d;
import com.startapp.android.publish.adsCommon.Utils.C4941e;
import com.startapp.android.publish.adsCommon.p089i.C5042a;
import com.startapp.android.publish.adsCommon.p089i.C5045b;
import com.startapp.common.p092a.C5146c;

/* renamed from: com.startapp.android.publish.adsCommon.j */
/* compiled from: StartAppSDK */
public class C5046j extends BaseRequest {

    /* renamed from: a */
    private C5045b f3298a;

    /* renamed from: b */
    private String f3299b;

    public C5046j(Context context) {
        this.f3298a = C5042a.m3317a(context);
        this.f3299b = C5146c.m3778j(context);
    }

    public C4941e getNameValueMap() {
        C4941e nameValueMap = super.getNameValueMap();
        if (nameValueMap == null) {
            nameValueMap = new C4940d();
        }
        nameValueMap.mo62030a("placement", (Object) "INAPP_DOWNLOAD", true);
        C5045b bVar = this.f3298a;
        if (bVar != null) {
            nameValueMap.mo62030a("install_referrer", (Object) bVar.mo62352a(), true);
            nameValueMap.mo62030a("referrer_click_timestamp_seconds", (Object) Long.valueOf(this.f3298a.mo62353b()), true);
            nameValueMap.mo62030a("install_begin_timestamp_seconds", (Object) Long.valueOf(this.f3298a.mo62354c()), true);
        }
        nameValueMap.mo62030a("apkSig", (Object) this.f3299b, true);
        return nameValueMap;
    }
}
