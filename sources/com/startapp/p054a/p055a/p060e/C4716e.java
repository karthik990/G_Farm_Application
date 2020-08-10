package com.startapp.p054a.p055a.p060e;

import com.startapp.p054a.p055a.p056a.C4698c;
import java.io.DataInput;
import java.io.IOException;

/* renamed from: com.startapp.a.a.e.e */
/* compiled from: StartAppSDK */
public class C4716e extends C4715d {
    /* access modifiers changed from: protected */
    /* renamed from: a */
    public DataInput mo61123a(byte[] bArr) {
        DataInput a = super.mo61123a(bArr);
        m2125b(a);
        return a;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public C4698c mo61118a(DataInput dataInput) {
        long readInt = (long) dataInput.readInt();
        C4698c cVar = new C4698c(readInt << 6);
        mo61124a(dataInput, cVar, readInt);
        return cVar;
    }

    /* renamed from: b */
    private void m2125b(DataInput dataInput) {
        try {
            dataInput.readInt();
        } catch (IOException e) {
            throw new RuntimeException("problem incrementInputStreamForBackwordCompatability", e);
        }
    }
}
