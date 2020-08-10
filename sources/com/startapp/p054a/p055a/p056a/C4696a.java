package com.startapp.p054a.p055a.p056a;

import java.nio.ByteBuffer;
import java.util.List;

/* renamed from: com.startapp.a.a.a.a */
/* compiled from: StartAppSDK */
public class C4696a {

    /* renamed from: a */
    private final int f2369a;

    /* renamed from: b */
    private final int f2370b;

    public C4696a(int i, int i2) {
        this.f2369a = i;
        this.f2370b = i2;
    }

    /* renamed from: a */
    public C4698c mo61089a(List<String> list) {
        C4698c cVar = new C4698c((long) (this.f2369a * this.f2370b));
        mo61090a(list, cVar);
        return cVar;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public void mo61090a(List<String> list, C4698c cVar) {
        for (String bytes : list) {
            m2075a(ByteBuffer.wrap(bytes.getBytes()), cVar);
        }
    }

    /* renamed from: a */
    private void m2075a(ByteBuffer byteBuffer, C4698c cVar) {
        for (long a : m2076a(byteBuffer, cVar.mo61091a())) {
            cVar.mo61092a(a);
        }
    }

    /* renamed from: a */
    private long[] m2076a(ByteBuffer byteBuffer, long j) {
        int i = this.f2369a;
        long[] jArr = new long[i];
        long j2 = j / ((long) i);
        long a = C4697b.m2079a(byteBuffer, byteBuffer.position(), byteBuffer.remaining(), 0);
        long a2 = C4697b.m2079a(byteBuffer, byteBuffer.position(), byteBuffer.remaining(), a);
        for (int i2 = 0; i2 < this.f2369a; i2++) {
            long j3 = (long) i2;
            jArr[i2] = (j3 * j2) + Math.abs(((j3 * a2) + a) % j2);
        }
        return jArr;
    }
}
