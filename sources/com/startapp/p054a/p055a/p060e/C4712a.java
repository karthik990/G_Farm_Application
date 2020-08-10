package com.startapp.p054a.p055a.p060e;

import com.startapp.p054a.p055a.p056a.C4698c;
import java.io.DataInput;

/* renamed from: com.startapp.a.a.e.a */
/* compiled from: StartAppSDK */
public class C4712a extends C4715d {

    /* renamed from: a */
    private final int f2417a;

    /* renamed from: b */
    private final int f2418b;

    public C4712a(int i, int i2) {
        this.f2417a = i;
        this.f2418b = i2;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public C4698c mo61118a(DataInput dataInput) {
        C4698c cVar = new C4698c((long) (this.f2417a * this.f2418b));
        mo61124a(dataInput, cVar, (long) cVar.mo61094b());
        return cVar;
    }
}
