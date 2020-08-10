package com.p021b.p022a.p023a.p024a.p026b;

import android.webkit.WebView;
import com.p021b.p022a.p023a.p024a.p030e.C0991e;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* renamed from: com.b.a.a.a.b.d */
public final class C0965d {

    /* renamed from: a */
    private final C0968g f104a;

    /* renamed from: b */
    private final WebView f105b;

    /* renamed from: c */
    private final List<C0969h> f106c = new ArrayList();

    /* renamed from: d */
    private final String f107d;

    /* renamed from: e */
    private final String f108e;

    /* renamed from: f */
    private final C0966e f109f;

    private C0965d(C0968g gVar, WebView webView, String str, List<C0969h> list, String str2) {
        C0966e eVar;
        this.f104a = gVar;
        this.f105b = webView;
        this.f107d = str;
        if (list != null) {
            this.f106c.addAll(list);
            eVar = C0966e.NATIVE;
        } else {
            eVar = C0966e.HTML;
        }
        this.f109f = eVar;
        this.f108e = str2;
    }

    /* renamed from: a */
    public static C0965d m127a(C0968g gVar, WebView webView, String str) {
        C0991e.m256a((Object) gVar, "Partner is null");
        C0991e.m256a((Object) webView, "WebView is null");
        if (str != null) {
            C0991e.m257a(str, 256, "CustomReferenceData is greater than 256 characters");
        }
        C0965d dVar = new C0965d(gVar, webView, null, null, str);
        return dVar;
    }

    /* renamed from: a */
    public static C0965d m128a(C0968g gVar, String str, List<C0969h> list, String str2) {
        C0991e.m256a((Object) gVar, "Partner is null");
        C0991e.m256a((Object) str, "OMID JS script content is null");
        C0991e.m256a((Object) list, "VerificationScriptResources is null");
        if (str2 != null) {
            C0991e.m257a(str2, 256, "CustomReferenceData is greater than 256 characters");
        }
        C0965d dVar = new C0965d(gVar, null, str, list, str2);
        return dVar;
    }

    /* renamed from: a */
    public C0968g mo11482a() {
        return this.f104a;
    }

    /* renamed from: b */
    public List<C0969h> mo11483b() {
        return Collections.unmodifiableList(this.f106c);
    }

    /* renamed from: c */
    public WebView mo11484c() {
        return this.f105b;
    }

    /* renamed from: d */
    public String mo11485d() {
        return this.f108e;
    }

    /* renamed from: e */
    public String mo11486e() {
        return this.f107d;
    }

    /* renamed from: f */
    public C0966e mo11487f() {
        return this.f109f;
    }
}
