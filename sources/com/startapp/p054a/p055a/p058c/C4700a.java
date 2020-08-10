package com.startapp.p054a.p055a.p058c;

import com.google.common.base.Ascii;
import org.objenesis.instantiator.basic.ClassDefinitionUtils;

/* renamed from: com.startapp.a.a.c.a */
/* compiled from: StartAppSDK */
public class C4700a extends C4701b {

    /* renamed from: a */
    static final byte[] f2379a = {Ascii.f1866CR, 10};

    /* renamed from: d */
    private static final byte[] f2380d = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, ClassDefinitionUtils.OPS_dup, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};

    /* renamed from: e */
    private static final byte[] f2381e = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, ClassDefinitionUtils.OPS_dup, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};

    /* renamed from: f */
    private static final byte[] f2382f = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, 62, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, Ascii.f1879VT, Ascii.f1868FF, Ascii.f1866CR, Ascii.f1876SO, Ascii.f1875SI, Ascii.DLE, 17, Ascii.DC2, 19, Ascii.DC4, Ascii.NAK, Ascii.SYN, Ascii.ETB, Ascii.CAN, Ascii.f1867EM, -1, -1, -1, -1, 63, -1, Ascii.SUB, Ascii.ESC, Ascii.f1869FS, Ascii.f1870GS, Ascii.f1874RS, Ascii.f1878US, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, ClassDefinitionUtils.OPS_aload_0, 43, 44, 45, 46, 47, 48, 49, 50, 51};

    /* renamed from: g */
    private final byte[] f2383g;

    /* renamed from: h */
    private final byte[] f2384h;

    /* renamed from: i */
    private final byte[] f2385i;

    /* renamed from: j */
    private final int f2386j;

    /* renamed from: k */
    private final int f2387k;

    public C4700a() {
        this(0);
    }

    public C4700a(boolean z) {
        this(76, f2379a, z);
    }

    public C4700a(int i) {
        this(i, f2379a);
    }

    public C4700a(int i, byte[] bArr) {
        this(i, bArr, false);
    }

    public C4700a(int i, byte[] bArr, boolean z) {
        super(3, 4, i, bArr == null ? 0 : bArr.length);
        this.f2384h = f2382f;
        if (bArr == null) {
            this.f2387k = 4;
            this.f2385i = null;
        } else if (mo61105c(bArr)) {
            String a = C4706f.m2109a(bArr);
            StringBuilder sb = new StringBuilder();
            sb.append("lineSeparator must not contain base64 characters: [");
            sb.append(a);
            sb.append("]");
            throw new IllegalArgumentException(sb.toString());
        } else if (i > 0) {
            this.f2387k = bArr.length + 4;
            this.f2385i = new byte[bArr.length];
            System.arraycopy(bArr, 0, this.f2385i, 0, bArr.length);
        } else {
            this.f2387k = 4;
            this.f2385i = null;
        }
        this.f2386j = this.f2387k - 1;
        this.f2383g = z ? f2381e : f2380d;
    }

    /* JADX WARNING: type inference failed for: r2v18 */
    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r2v2, types: [int, byte] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void mo61098a(byte[] r8, int r9, int r10, com.startapp.p054a.p055a.p058c.C4701b.C4702a r11) {
        /*
            r7 = this;
            boolean r0 = r11.f2398f
            if (r0 == 0) goto L_0x0005
            return
        L_0x0005:
            r0 = 0
            r1 = 1
            if (r10 >= 0) goto L_0x00e3
            r11.f2398f = r1
            int r8 = r11.f2400h
            if (r8 != 0) goto L_0x0014
            int r8 = r7.f2390c
            if (r8 != 0) goto L_0x0014
            return
        L_0x0014:
            int r8 = r7.f2387k
            byte[] r8 = r7.mo61102a(r8, r11)
            int r9 = r11.f2396d
            int r10 = r11.f2400h
            if (r10 == 0) goto L_0x00bf
            r2 = 61
            r3 = 2
            if (r10 == r1) goto L_0x0085
            if (r10 != r3) goto L_0x006c
            int r10 = r11.f2396d
            int r1 = r10 + 1
            r11.f2396d = r1
            byte[] r1 = r7.f2383g
            int r4 = r11.f2393a
            int r4 = r4 >> 10
            r4 = r4 & 63
            byte r1 = r1[r4]
            r8[r10] = r1
            int r10 = r11.f2396d
            int r1 = r10 + 1
            r11.f2396d = r1
            byte[] r1 = r7.f2383g
            int r4 = r11.f2393a
            int r4 = r4 >> 4
            r4 = r4 & 63
            byte r1 = r1[r4]
            r8[r10] = r1
            int r10 = r11.f2396d
            int r1 = r10 + 1
            r11.f2396d = r1
            byte[] r1 = r7.f2383g
            int r4 = r11.f2393a
            int r3 = r4 << 2
            r3 = r3 & 63
            byte r1 = r1[r3]
            r8[r10] = r1
            byte[] r10 = r7.f2383g
            byte[] r1 = f2380d
            if (r10 != r1) goto L_0x00bf
            int r10 = r11.f2396d
            int r1 = r10 + 1
            r11.f2396d = r1
            r8[r10] = r2
            goto L_0x00bf
        L_0x006c:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "Impossible modulus "
            r9.append(r10)
            int r10 = r11.f2400h
            r9.append(r10)
            java.lang.String r9 = r9.toString()
            r8.<init>(r9)
            throw r8
        L_0x0085:
            int r10 = r11.f2396d
            int r1 = r10 + 1
            r11.f2396d = r1
            byte[] r1 = r7.f2383g
            int r4 = r11.f2393a
            int r3 = r4 >> 2
            r3 = r3 & 63
            byte r1 = r1[r3]
            r8[r10] = r1
            int r10 = r11.f2396d
            int r1 = r10 + 1
            r11.f2396d = r1
            byte[] r1 = r7.f2383g
            int r3 = r11.f2393a
            int r3 = r3 << 4
            r3 = r3 & 63
            byte r1 = r1[r3]
            r8[r10] = r1
            byte[] r10 = r7.f2383g
            byte[] r1 = f2380d
            if (r10 != r1) goto L_0x00bf
            int r10 = r11.f2396d
            int r1 = r10 + 1
            r11.f2396d = r1
            r8[r10] = r2
            int r10 = r11.f2396d
            int r1 = r10 + 1
            r11.f2396d = r1
            r8[r10] = r2
        L_0x00bf:
            int r10 = r11.f2399g
            int r1 = r11.f2396d
            int r1 = r1 - r9
            int r10 = r10 + r1
            r11.f2399g = r10
            int r9 = r7.f2390c
            if (r9 <= 0) goto L_0x0176
            int r9 = r11.f2399g
            if (r9 <= 0) goto L_0x0176
            byte[] r9 = r7.f2385i
            int r10 = r11.f2396d
            byte[] r1 = r7.f2385i
            int r1 = r1.length
            java.lang.System.arraycopy(r9, r0, r8, r10, r1)
            int r8 = r11.f2396d
            byte[] r9 = r7.f2385i
            int r9 = r9.length
            int r8 = r8 + r9
            r11.f2396d = r8
            goto L_0x0176
        L_0x00e3:
            r2 = r9
            r9 = 0
        L_0x00e5:
            if (r9 >= r10) goto L_0x0176
            int r3 = r7.f2387k
            byte[] r3 = r7.mo61102a(r3, r11)
            int r4 = r11.f2400h
            int r4 = r4 + r1
            int r4 = r4 % 3
            r11.f2400h = r4
            int r4 = r2 + 1
            byte r2 = r8[r2]
            if (r2 >= 0) goto L_0x00fc
            int r2 = r2 + 256
        L_0x00fc:
            int r5 = r11.f2393a
            int r5 = r5 << 8
            int r5 = r5 + r2
            r11.f2393a = r5
            int r2 = r11.f2400h
            if (r2 != 0) goto L_0x0171
            int r2 = r11.f2396d
            int r5 = r2 + 1
            r11.f2396d = r5
            byte[] r5 = r7.f2383g
            int r6 = r11.f2393a
            int r6 = r6 >> 18
            r6 = r6 & 63
            byte r5 = r5[r6]
            r3[r2] = r5
            int r2 = r11.f2396d
            int r5 = r2 + 1
            r11.f2396d = r5
            byte[] r5 = r7.f2383g
            int r6 = r11.f2393a
            int r6 = r6 >> 12
            r6 = r6 & 63
            byte r5 = r5[r6]
            r3[r2] = r5
            int r2 = r11.f2396d
            int r5 = r2 + 1
            r11.f2396d = r5
            byte[] r5 = r7.f2383g
            int r6 = r11.f2393a
            int r6 = r6 >> 6
            r6 = r6 & 63
            byte r5 = r5[r6]
            r3[r2] = r5
            int r2 = r11.f2396d
            int r5 = r2 + 1
            r11.f2396d = r5
            byte[] r5 = r7.f2383g
            int r6 = r11.f2393a
            r6 = r6 & 63
            byte r5 = r5[r6]
            r3[r2] = r5
            int r2 = r11.f2399g
            int r2 = r2 + 4
            r11.f2399g = r2
            int r2 = r7.f2390c
            if (r2 <= 0) goto L_0x0171
            int r2 = r7.f2390c
            int r5 = r11.f2399g
            if (r2 > r5) goto L_0x0171
            byte[] r2 = r7.f2385i
            int r5 = r11.f2396d
            byte[] r6 = r7.f2385i
            int r6 = r6.length
            java.lang.System.arraycopy(r2, r0, r3, r5, r6)
            int r2 = r11.f2396d
            byte[] r3 = r7.f2385i
            int r3 = r3.length
            int r2 = r2 + r3
            r11.f2396d = r2
            r11.f2399g = r0
        L_0x0171:
            int r9 = r9 + 1
            r2 = r4
            goto L_0x00e5
        L_0x0176:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.startapp.p054a.p055a.p058c.C4700a.mo61098a(byte[], int, int, com.startapp.a.a.c.b$a):void");
    }

    /* renamed from: a */
    public static String m2091a(byte[] bArr) {
        return C4706f.m2109a(m2092a(bArr, false));
    }

    /* renamed from: a */
    public static byte[] m2092a(byte[] bArr, boolean z) {
        return m2093a(bArr, z, false);
    }

    /* renamed from: a */
    public static byte[] m2093a(byte[] bArr, boolean z, boolean z2) {
        return m2094a(bArr, z, z2, Integer.MAX_VALUE);
    }

    /* renamed from: a */
    public static byte[] m2094a(byte[] bArr, boolean z, boolean z2, int i) {
        if (bArr == null || bArr.length == 0) {
            return bArr;
        }
        C4700a aVar = z ? new C4700a(z2) : new C4700a(0, f2379a, z2);
        long d = aVar.mo61106d(bArr);
        if (d <= ((long) i)) {
            return aVar.mo61104b(bArr);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Input array too big, the output array would be bigger (");
        sb.append(d);
        sb.append(") than the specified maximum size of ");
        sb.append(i);
        throw new IllegalArgumentException(sb.toString());
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public boolean mo61099a(byte b) {
        if (b >= 0) {
            byte[] bArr = this.f2384h;
            if (b < bArr.length && bArr[b] != -1) {
                return true;
            }
        }
        return false;
    }
}
