package com.startapp.p054a.p055a.p056a;

import java.io.Serializable;

/* renamed from: com.startapp.a.a.a.c */
/* compiled from: StartAppSDK */
public class C4698c implements Serializable {

    /* renamed from: a */
    static final /* synthetic */ boolean f2371a = (!C4698c.class.desiredAssertionStatus());

    /* renamed from: d */
    private final long[][] f2372d;

    /* renamed from: e */
    private int f2373e;

    /* renamed from: f */
    private final int f2374f;

    /* renamed from: d */
    private int m2083d(long j) {
        return (int) (((j - 1) >>> 6) + 1);
    }

    public C4698c(long j) {
        this.f2373e = m2083d(j);
        int i = this.f2373e;
        int i2 = i % 4096;
        int i3 = i / 4096;
        this.f2374f = (i2 == 0 ? 0 : 1) + i3;
        int i4 = this.f2374f;
        if (i4 <= 100) {
            this.f2372d = new long[i4][];
            for (int i5 = 0; i5 < i3; i5++) {
                this.f2372d[i5] = new long[4096];
            }
            if (i2 != 0) {
                long[][] jArr = this.f2372d;
                jArr[jArr.length - 1] = new long[i2];
                return;
            }
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("HighPageCountException pageCount = ");
        sb.append(this.f2374f);
        throw new RuntimeException(sb.toString());
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public long mo61091a() {
        return ((long) this.f2373e) << 6;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public void mo61092a(long j) {
        int b = m2080b(j);
        long j2 = 1 << (((int) j) & 63);
        long[] jArr = this.f2372d[b / 4096];
        int i = b % 4096;
        jArr[i] = j2 | jArr[i];
    }

    /* renamed from: b */
    private int m2080b(long j) {
        int i = (int) (j >> 6);
        if (i >= this.f2373e) {
            m2082c(j + 1);
            this.f2373e = i + 1;
        }
        return i;
    }

    /* renamed from: c */
    private void m2082c(long j) {
        m2081b(m2083d(j));
    }

    /* renamed from: b */
    private void m2081b(int i) {
        if (!f2371a && i > this.f2373e) {
            throw new AssertionError("Growing of paged bitset is not supported");
        }
    }

    /* renamed from: b */
    public int mo61094b() {
        return this.f2373e;
    }

    /* renamed from: c */
    public int mo61095c() {
        return this.f2374f;
    }

    /* renamed from: a */
    public long[] mo61093a(int i) {
        return this.f2372d[i];
    }
}
