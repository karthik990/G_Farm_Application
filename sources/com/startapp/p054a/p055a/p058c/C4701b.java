package com.startapp.p054a.p055a.p058c;

import java.util.Arrays;

/* renamed from: com.startapp.a.a.c.b */
/* compiled from: StartAppSDK */
public abstract class C4701b {

    /* renamed from: a */
    private final int f2388a;

    /* renamed from: b */
    protected final byte f2389b = 61;

    /* renamed from: c */
    protected final int f2390c;

    /* renamed from: d */
    private final int f2391d;

    /* renamed from: e */
    private final int f2392e;

    /* renamed from: com.startapp.a.a.c.b$a */
    /* compiled from: StartAppSDK */
    static class C4702a {

        /* renamed from: a */
        int f2393a;

        /* renamed from: b */
        long f2394b;

        /* renamed from: c */
        byte[] f2395c;

        /* renamed from: d */
        int f2396d;

        /* renamed from: e */
        int f2397e;

        /* renamed from: f */
        boolean f2398f;

        /* renamed from: g */
        int f2399g;

        /* renamed from: h */
        int f2400h;

        C4702a() {
        }

        public String toString() {
            return String.format("%s[buffer=%s, currentLinePos=%s, eof=%s, ibitWorkArea=%s, lbitWorkArea=%s, modulus=%s, pos=%s, readPos=%s]", new Object[]{getClass().getSimpleName(), Arrays.toString(this.f2395c), Integer.valueOf(this.f2399g), Boolean.valueOf(this.f2398f), Integer.valueOf(this.f2393a), Long.valueOf(this.f2394b), Integer.valueOf(this.f2400h), Integer.valueOf(this.f2396d), Integer.valueOf(this.f2397e)});
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public int mo61100a() {
        return 8192;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public abstract void mo61098a(byte[] bArr, int i, int i2, C4702a aVar);

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public abstract boolean mo61099a(byte b);

    protected C4701b(int i, int i2, int i3, int i4) {
        this.f2388a = i;
        this.f2391d = i2;
        int i5 = 0;
        if (i3 > 0 && i4 > 0) {
            i5 = (i3 / i2) * i2;
        }
        this.f2390c = i5;
        this.f2392e = i4;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public int mo61101a(C4702a aVar) {
        if (aVar.f2395c != null) {
            return aVar.f2396d - aVar.f2397e;
        }
        return 0;
    }

    /* renamed from: b */
    private byte[] m2097b(C4702a aVar) {
        if (aVar.f2395c == null) {
            aVar.f2395c = new byte[mo61100a()];
            aVar.f2396d = 0;
            aVar.f2397e = 0;
        } else {
            byte[] bArr = new byte[(aVar.f2395c.length * 2)];
            System.arraycopy(aVar.f2395c, 0, bArr, 0, aVar.f2395c.length);
            aVar.f2395c = bArr;
        }
        return aVar.f2395c;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public byte[] mo61102a(int i, C4702a aVar) {
        if (aVar.f2395c == null || aVar.f2395c.length < aVar.f2396d + i) {
            return m2097b(aVar);
        }
        return aVar.f2395c;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: b */
    public int mo61103b(byte[] bArr, int i, int i2, C4702a aVar) {
        if (aVar.f2395c != null) {
            int min = Math.min(mo61101a(aVar), i2);
            System.arraycopy(aVar.f2395c, aVar.f2397e, bArr, i, min);
            aVar.f2397e += min;
            if (aVar.f2397e >= aVar.f2396d) {
                aVar.f2395c = null;
            }
            return min;
        }
        return aVar.f2398f ? -1 : 0;
    }

    /* renamed from: b */
    public byte[] mo61104b(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return bArr;
        }
        C4702a aVar = new C4702a();
        mo61098a(bArr, 0, bArr.length, aVar);
        mo61098a(bArr, 0, -1, aVar);
        byte[] bArr2 = new byte[(aVar.f2396d - aVar.f2397e)];
        mo61103b(bArr2, 0, bArr2.length, aVar);
        return bArr2;
    }

    /* access modifiers changed from: protected */
    /* renamed from: c */
    public boolean mo61105c(byte[] bArr) {
        if (bArr == null) {
            return false;
        }
        for (byte b : bArr) {
            if (61 == b || mo61099a(b)) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: d */
    public long mo61106d(byte[] bArr) {
        int length = bArr.length;
        int i = this.f2388a;
        long j = ((long) (((length + i) - 1) / i)) * ((long) this.f2391d);
        int i2 = this.f2390c;
        return i2 > 0 ? j + ((((((long) i2) + j) - 1) / ((long) i2)) * ((long) this.f2392e)) : j;
    }
}
