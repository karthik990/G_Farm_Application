package com.flurry.sdk;

import com.flurry.sdk.C1696df.C1700b;
import java.io.InputStream;
import java.io.OutputStream;

/* renamed from: com.flurry.sdk.dd */
public final class C1691dd<RequestObjectType, ResponseObjectType> extends C1696df {

    /* renamed from: a */
    public C1693a<RequestObjectType, ResponseObjectType> f882a;

    /* renamed from: b */
    public RequestObjectType f883b;

    /* renamed from: c */
    public C1724ds<RequestObjectType> f884c;

    /* renamed from: d */
    public C1724ds<ResponseObjectType> f885d;
    /* access modifiers changed from: private */

    /* renamed from: q */
    public ResponseObjectType f886q;

    /* renamed from: com.flurry.sdk.dd$a */
    public interface C1693a<RequestObjectType, ResponseObjectType> {
        /* renamed from: a */
        void mo16300a(C1691dd<RequestObjectType, ResponseObjectType> ddVar, ResponseObjectType responseobjecttype);
    }

    /* renamed from: a */
    public final void mo16236a() {
        this.f899h = new C1700b() {
            /* renamed from: a */
            public final void mo16399a(OutputStream outputStream) throws Exception {
                if (C1691dd.this.f883b != null && C1691dd.this.f884c != null) {
                    C1691dd.this.f884c.mo16251a(outputStream, C1691dd.this.f883b);
                }
            }

            /* renamed from: a */
            public final void mo16397a() {
                C1691dd.m792d(C1691dd.this);
            }

            /* renamed from: a */
            public final void mo16398a(C1696df dfVar, InputStream inputStream) throws Exception {
                if ((dfVar.f904m >= 200 && dfVar.f904m < 400 && !dfVar.f906o) && C1691dd.this.f885d != null) {
                    C1691dd ddVar = C1691dd.this;
                    ddVar.f886q = ddVar.f885d.mo16250a(inputStream);
                }
            }
        };
        super.mo16236a();
    }

    /* renamed from: d */
    static /* synthetic */ void m792d(C1691dd ddVar) {
        if (ddVar.f882a != null && !ddVar.mo16405c()) {
            ddVar.f882a.mo16300a(ddVar, ddVar.f886q);
        }
    }
}
