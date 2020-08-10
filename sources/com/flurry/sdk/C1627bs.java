package com.flurry.sdk;

import android.text.TextUtils;

/* renamed from: com.flurry.sdk.bs */
public final class C1627bs extends C1603bn {
    public C1627bs() {
        super("Streaming", "FlurryStreamingWithFramesDataSender");
    }

    /* access modifiers changed from: protected */
    /* renamed from: d */
    public final String mo16299d() {
        String b = C1687d.m774b();
        if (TextUtils.isEmpty(b)) {
            return "https://data.flurry.com/v1/flr.do";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(b);
        sb.append("/v1/flr.do");
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo16293a(int i, String str, String str2) {
        if (C1948n.m1229a().f1425k.f618d.get()) {
            C1734dz.m870a(i, str, str2, true, true);
            return;
        }
        C1775fe.m935a("last_streaming_http_error_code", i);
        C1775fe.m937a("last_streaming_http_error_message", str);
        C1775fe.m937a("last_streaming_http_report_identifier", str2);
    }
}
