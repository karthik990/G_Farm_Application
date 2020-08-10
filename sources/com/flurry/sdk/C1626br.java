package com.flurry.sdk;

/* renamed from: com.flurry.sdk.br */
public final class C1626br extends C1603bn {
    /* access modifiers changed from: protected */
    /* renamed from: d */
    public final String mo16299d() {
        return "https://data.flurry.com/aap.do";
    }

    public C1626br() {
        super("Analytics", "FlurryStreamingUpdateDataSender");
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo16293a(int i, String str, String str2) {
        if (C1948n.m1229a().f1425k.f618d.get()) {
            C1734dz.m870a(i, str, str2, false, true);
            return;
        }
        C1775fe.m935a("last_legacy_http_error_code", i);
        C1775fe.m937a("last_legacy_http_error_message", str);
        C1775fe.m937a("last_legacy_http_report_identifier", str2);
    }
}
