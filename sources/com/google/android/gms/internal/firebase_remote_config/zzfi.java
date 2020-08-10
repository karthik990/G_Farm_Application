package com.google.android.gms.internal.firebase_remote_config;

import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;

public final class zzfi implements Closeable {
    private static final char[] zzna = ")]}'\n".toCharArray();

    /* renamed from: in */
    private final Reader f1540in;
    private int limit = 0;
    private int pos = 0;
    private boolean zznb = false;
    private final char[] zznc = new char[1024];
    private int zznd = 0;
    private int zzne = 0;
    private int zznf = 0;
    private long zzng;
    private int zznh;
    private String zzni;
    private int[] zznj = new int[32];
    private int zznk = 0;
    private String[] zznl;
    private int[] zznm;

    public zzfi(Reader reader) {
        int[] iArr = this.zznj;
        int i = this.zznk;
        this.zznk = i + 1;
        iArr[i] = 6;
        this.zznl = new String[32];
        this.zznm = new int[32];
        if (reader != null) {
            this.f1540in = reader;
            return;
        }
        throw new NullPointerException("in == null");
    }

    public final void setLenient(boolean z) {
        this.zznb = true;
    }

    public final void beginArray() throws IOException {
        int i = this.zznf;
        if (i == 0) {
            i = zzdy();
        }
        if (i == 3) {
            zzo(1);
            this.zznm[this.zznk - 1] = 0;
            this.zznf = 0;
            return;
        }
        StringBuilder sb = new StringBuilder("Expected BEGIN_ARRAY but was ");
        sb.append(zzdx());
        sb.append(zzec());
        throw new IllegalStateException(sb.toString());
    }

    public final void endArray() throws IOException {
        int i = this.zznf;
        if (i == 0) {
            i = zzdy();
        }
        if (i == 4) {
            this.zznk--;
            int[] iArr = this.zznm;
            int i2 = this.zznk - 1;
            iArr[i2] = iArr[i2] + 1;
            this.zznf = 0;
            return;
        }
        StringBuilder sb = new StringBuilder("Expected END_ARRAY but was ");
        sb.append(zzdx());
        sb.append(zzec());
        throw new IllegalStateException(sb.toString());
    }

    public final void beginObject() throws IOException {
        int i = this.zznf;
        if (i == 0) {
            i = zzdy();
        }
        if (i == 1) {
            zzo(3);
            this.zznf = 0;
            return;
        }
        StringBuilder sb = new StringBuilder("Expected BEGIN_OBJECT but was ");
        sb.append(zzdx());
        sb.append(zzec());
        throw new IllegalStateException(sb.toString());
    }

    public final void endObject() throws IOException {
        int i = this.zznf;
        if (i == 0) {
            i = zzdy();
        }
        if (i == 2) {
            this.zznk--;
            String[] strArr = this.zznl;
            int i2 = this.zznk;
            strArr[i2] = null;
            int[] iArr = this.zznm;
            int i3 = i2 - 1;
            iArr[i3] = iArr[i3] + 1;
            this.zznf = 0;
            return;
        }
        StringBuilder sb = new StringBuilder("Expected END_OBJECT but was ");
        sb.append(zzdx());
        sb.append(zzec());
        throw new IllegalStateException(sb.toString());
    }

    public final zzfk zzdx() throws IOException {
        int i = this.zznf;
        if (i == 0) {
            i = zzdy();
        }
        switch (i) {
            case 1:
                return zzfk.BEGIN_OBJECT;
            case 2:
                return zzfk.END_OBJECT;
            case 3:
                return zzfk.BEGIN_ARRAY;
            case 4:
                return zzfk.END_ARRAY;
            case 5:
            case 6:
                return zzfk.BOOLEAN;
            case 7:
                return zzfk.NULL;
            case 8:
            case 9:
            case 10:
            case 11:
                return zzfk.STRING;
            case 12:
            case 13:
            case 14:
                return zzfk.NAME;
            case 15:
            case 16:
                return zzfk.NUMBER;
            case 17:
                return zzfk.END_DOCUMENT;
            default:
                throw new AssertionError();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:106:0x019d, code lost:
        r1 = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:144:0x0212, code lost:
        if (zze(r5) == false) goto L_0x019d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:145:0x0215, code lost:
        if (r7 != 2) goto L_0x0234;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:146:0x0217, code lost:
        if (r8 == false) goto L_0x0233;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:148:0x021d, code lost:
        if (r12 != Long.MIN_VALUE) goto L_0x0221;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:149:0x021f, code lost:
        if (r9 == false) goto L_0x0233;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:150:0x0221, code lost:
        if (r9 == false) goto L_0x0224;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:151:0x0224, code lost:
        r12 = -r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:152:0x0225, code lost:
        r0.zzng = r12;
        r0.pos += r3;
        r0.zznf = 15;
        r16 = 15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:153:0x0233, code lost:
        r1 = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:154:0x0234, code lost:
        if (r7 == r1) goto L_0x0240;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:156:0x0237, code lost:
        if (r7 == 4) goto L_0x0240;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:158:0x023a, code lost:
        if (r7 != 7) goto L_0x023d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:160:0x0240, code lost:
        r0.zznh = r3;
        r0.zznf = 16;
        r16 = 16;
     */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x017a A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x017b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final int zzdy() throws java.io.IOException {
        /*
            r19 = this;
            r0 = r19
            int[] r1 = r0.zznj
            int r2 = r0.zznk
            int r3 = r2 + -1
            r3 = r1[r3]
            r4 = 8
            r7 = 93
            r8 = 59
            r9 = 44
            r10 = 3
            r11 = 6
            r12 = 7
            r13 = 4
            r14 = 5
            r15 = 2
            r5 = 0
            r6 = 1
            if (r3 != r6) goto L_0x0021
            int r2 = r2 - r6
            r1[r2] = r15
            goto L_0x00d2
        L_0x0021:
            if (r3 != r15) goto L_0x003c
            int r1 = r0.zzc(r6)
            if (r1 == r9) goto L_0x00d2
            if (r1 == r8) goto L_0x0037
            if (r1 != r7) goto L_0x0030
            r0.zznf = r13
            return r13
        L_0x0030:
            java.lang.String r1 = "Unterminated array"
            java.io.IOException r1 = r0.zzbg(r1)
            throw r1
        L_0x0037:
            r19.zzea()
            goto L_0x00d2
        L_0x003c:
            if (r3 == r10) goto L_0x02c5
            if (r3 != r14) goto L_0x0042
            goto L_0x02c5
        L_0x0042:
            if (r3 != r13) goto L_0x0077
            int r2 = r2 - r6
            r1[r2] = r14
            int r1 = r0.zzc(r6)
            r2 = 58
            if (r1 == r2) goto L_0x00d2
            r2 = 61
            if (r1 != r2) goto L_0x0070
            r19.zzea()
            int r1 = r0.pos
            int r2 = r0.limit
            if (r1 < r2) goto L_0x0062
            boolean r1 = r0.zzp(r6)
            if (r1 == 0) goto L_0x00d2
        L_0x0062:
            char[] r1 = r0.zznc
            int r2 = r0.pos
            char r1 = r1[r2]
            r13 = 62
            if (r1 != r13) goto L_0x00d2
            int r2 = r2 + r6
            r0.pos = r2
            goto L_0x00d2
        L_0x0070:
            java.lang.String r1 = "Expected ':'"
            java.io.IOException r1 = r0.zzbg(r1)
            throw r1
        L_0x0077:
            if (r3 != r11) goto L_0x00b9
            boolean r1 = r0.zznb
            if (r1 == 0) goto L_0x00b1
            r0.zzc(r6)
            int r1 = r0.pos
            int r1 = r1 - r6
            r0.pos = r1
            int r1 = r0.pos
            char[] r2 = zzna
            int r13 = r2.length
            int r1 = r1 + r13
            int r13 = r0.limit
            if (r1 <= r13) goto L_0x0096
            int r1 = r2.length
            boolean r1 = r0.zzp(r1)
            if (r1 == 0) goto L_0x00b1
        L_0x0096:
            r1 = 0
        L_0x0097:
            char[] r2 = zzna
            int r13 = r2.length
            if (r1 >= r13) goto L_0x00ab
            char[] r13 = r0.zznc
            int r11 = r0.pos
            int r11 = r11 + r1
            char r11 = r13[r11]
            char r2 = r2[r1]
            if (r11 != r2) goto L_0x00b1
            int r1 = r1 + 1
            r11 = 6
            goto L_0x0097
        L_0x00ab:
            int r1 = r0.pos
            int r2 = r2.length
            int r1 = r1 + r2
            r0.pos = r1
        L_0x00b1:
            int[] r1 = r0.zznj
            int r2 = r0.zznk
            int r2 = r2 - r6
            r1[r2] = r12
            goto L_0x00d2
        L_0x00b9:
            if (r3 != r12) goto L_0x00d0
            int r1 = r0.zzc(r5)
            r2 = -1
            if (r1 != r2) goto L_0x00c7
            r1 = 17
            r0.zznf = r1
            return r1
        L_0x00c7:
            r19.zzea()
            int r1 = r0.pos
            int r1 = r1 - r6
            r0.pos = r1
            goto L_0x00d2
        L_0x00d0:
            if (r3 == r4) goto L_0x02bd
        L_0x00d2:
            int r1 = r0.zzc(r6)
            r2 = 34
            if (r1 == r2) goto L_0x02b8
            r2 = 39
            if (r1 == r2) goto L_0x02b2
            if (r1 == r9) goto L_0x0299
            if (r1 == r8) goto L_0x0299
            r2 = 91
            if (r1 == r2) goto L_0x0296
            if (r1 == r7) goto L_0x0290
            r2 = 123(0x7b, float:1.72E-43)
            if (r1 == r2) goto L_0x028d
            int r1 = r0.pos
            int r1 = r1 - r6
            r0.pos = r1
            char[] r1 = r0.zznc
            int r2 = r0.pos
            char r1 = r1[r2]
            r2 = 116(0x74, float:1.63E-43)
            if (r1 == r2) goto L_0x011f
            r2 = 84
            if (r1 != r2) goto L_0x0100
            goto L_0x011f
        L_0x0100:
            r2 = 102(0x66, float:1.43E-43)
            if (r1 == r2) goto L_0x0118
            r2 = 70
            if (r1 != r2) goto L_0x0109
            goto L_0x0118
        L_0x0109:
            r2 = 110(0x6e, float:1.54E-43)
            if (r1 == r2) goto L_0x0111
            r2 = 78
            if (r1 != r2) goto L_0x016f
        L_0x0111:
            java.lang.String r1 = "null"
            java.lang.String r2 = "NULL"
            r3 = r2
            r2 = 7
            goto L_0x0125
        L_0x0118:
            java.lang.String r1 = "false"
            java.lang.String r2 = "FALSE"
            r3 = r2
            r2 = 6
            goto L_0x0125
        L_0x011f:
            java.lang.String r1 = "true"
            java.lang.String r2 = "TRUE"
            r3 = r2
            r2 = 5
        L_0x0125:
            int r4 = r1.length()
            r7 = 1
        L_0x012a:
            if (r7 >= r4) goto L_0x0153
            int r8 = r0.pos
            int r8 = r8 + r7
            int r9 = r0.limit
            if (r8 < r9) goto L_0x013c
            int r8 = r7 + 1
            boolean r8 = r0.zzp(r8)
            if (r8 != 0) goto L_0x013c
            goto L_0x016f
        L_0x013c:
            char[] r8 = r0.zznc
            int r9 = r0.pos
            int r9 = r9 + r7
            char r8 = r8[r9]
            char r9 = r1.charAt(r7)
            if (r8 == r9) goto L_0x0150
            char r9 = r3.charAt(r7)
            if (r8 == r9) goto L_0x0150
            goto L_0x016f
        L_0x0150:
            int r7 = r7 + 1
            goto L_0x012a
        L_0x0153:
            int r1 = r0.pos
            int r1 = r1 + r4
            int r3 = r0.limit
            if (r1 < r3) goto L_0x0162
            int r1 = r4 + 1
            boolean r1 = r0.zzp(r1)
            if (r1 == 0) goto L_0x0171
        L_0x0162:
            char[] r1 = r0.zznc
            int r3 = r0.pos
            int r3 = r3 + r4
            char r1 = r1[r3]
            boolean r1 = r0.zze(r1)
            if (r1 == 0) goto L_0x0171
        L_0x016f:
            r2 = 0
            goto L_0x0178
        L_0x0171:
            int r1 = r0.pos
            int r1 = r1 + r4
            r0.pos = r1
            r0.zznf = r2
        L_0x0178:
            if (r2 == 0) goto L_0x017b
            return r2
        L_0x017b:
            char[] r1 = r0.zznc
            int r2 = r0.pos
            int r3 = r0.limit
            r7 = 0
            r4 = r3
            r12 = r7
            r3 = 0
            r7 = 0
            r8 = 1
            r9 = 0
        L_0x0189:
            int r5 = r2 + r3
            if (r5 != r4) goto L_0x01a0
            int r2 = r1.length
            if (r3 == r2) goto L_0x023d
            int r2 = r3 + 1
            boolean r2 = r0.zzp(r2)
            if (r2 == 0) goto L_0x019d
            int r2 = r0.pos
            int r4 = r0.limit
            goto L_0x01a0
        L_0x019d:
            r1 = 2
            goto L_0x0215
        L_0x01a0:
            int r5 = r2 + r3
            char r5 = r1[r5]
            r11 = 43
            if (r5 == r11) goto L_0x0264
            r11 = 69
            if (r5 == r11) goto L_0x025a
            r11 = 101(0x65, float:1.42E-43)
            if (r5 == r11) goto L_0x025a
            r11 = 45
            if (r5 == r11) goto L_0x024f
            r11 = 46
            if (r5 == r11) goto L_0x0249
            r11 = 48
            if (r5 < r11) goto L_0x020e
            r11 = 57
            if (r5 <= r11) goto L_0x01c1
            goto L_0x020e
        L_0x01c1:
            if (r7 == r6) goto L_0x0205
            if (r7 != 0) goto L_0x01c6
            goto L_0x0205
        L_0x01c6:
            if (r7 != r15) goto L_0x01f6
            r17 = 0
            int r11 = (r12 > r17 ? 1 : (r12 == r17 ? 0 : -1))
            if (r11 == 0) goto L_0x023d
            r17 = 10
            long r17 = r17 * r12
            int r5 = r5 + -48
            long r14 = (long) r5
            long r17 = r17 - r14
            r14 = -922337203685477580(0xf333333333333334, double:-8.390303882365713E246)
            int r5 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r5 > 0) goto L_0x01f0
            r14 = -922337203685477580(0xf333333333333334, double:-8.390303882365713E246)
            int r5 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r5 != 0) goto L_0x01ee
            int r5 = (r17 > r12 ? 1 : (r17 == r12 ? 0 : -1))
            if (r5 >= 0) goto L_0x01ee
            goto L_0x01f0
        L_0x01ee:
            r5 = 0
            goto L_0x01f1
        L_0x01f0:
            r5 = 1
        L_0x01f1:
            r5 = r5 & r8
            r8 = r5
            r12 = r17
            goto L_0x01f9
        L_0x01f6:
            if (r7 != r10) goto L_0x01fc
            r7 = 4
        L_0x01f9:
            r14 = 6
            goto L_0x0269
        L_0x01fc:
            r5 = 5
            r14 = 6
            if (r7 == r5) goto L_0x0202
            if (r7 != r14) goto L_0x0269
        L_0x0202:
            r7 = 7
            goto L_0x0269
        L_0x0205:
            r14 = 6
            int r5 = r5 + -48
            int r5 = -r5
            long r11 = (long) r5
            r12 = r11
            r7 = 2
            goto L_0x0269
        L_0x020e:
            boolean r1 = r0.zze(r5)
            if (r1 == 0) goto L_0x019d
            goto L_0x023d
        L_0x0215:
            if (r7 != r1) goto L_0x0234
            if (r8 == 0) goto L_0x0233
            r1 = -9223372036854775808
            int r4 = (r12 > r1 ? 1 : (r12 == r1 ? 0 : -1))
            if (r4 != 0) goto L_0x0221
            if (r9 == 0) goto L_0x0233
        L_0x0221:
            if (r9 == 0) goto L_0x0224
            goto L_0x0225
        L_0x0224:
            long r12 = -r12
        L_0x0225:
            r0.zzng = r12
            int r1 = r0.pos
            int r1 = r1 + r3
            r0.pos = r1
            r5 = 15
            r0.zznf = r5
            r16 = 15
            goto L_0x026f
        L_0x0233:
            r1 = 2
        L_0x0234:
            if (r7 == r1) goto L_0x0240
            r1 = 4
            if (r7 == r1) goto L_0x0240
            r1 = 7
            if (r7 != r1) goto L_0x023d
            goto L_0x0240
        L_0x023d:
            r16 = 0
            goto L_0x026f
        L_0x0240:
            r0.zznh = r3
            r5 = 16
            r0.zznf = r5
            r16 = 16
            goto L_0x026f
        L_0x0249:
            r5 = 2
            r14 = 6
            if (r7 != r5) goto L_0x023d
            r7 = 3
            goto L_0x0269
        L_0x024f:
            r5 = 2
            r14 = 6
            if (r7 != 0) goto L_0x0256
            r7 = 1
            r9 = 1
            goto L_0x0269
        L_0x0256:
            r15 = 5
            if (r7 != r15) goto L_0x023d
            goto L_0x0268
        L_0x025a:
            r5 = 2
            r14 = 6
            r15 = 5
            if (r7 == r5) goto L_0x0262
            r5 = 4
            if (r7 != r5) goto L_0x023d
        L_0x0262:
            r7 = 5
            goto L_0x0269
        L_0x0264:
            r14 = 6
            r15 = 5
            if (r7 != r15) goto L_0x023d
        L_0x0268:
            r7 = 6
        L_0x0269:
            int r3 = r3 + 1
            r14 = 5
            r15 = 2
            goto L_0x0189
        L_0x026f:
            if (r16 == 0) goto L_0x0272
            return r16
        L_0x0272:
            char[] r1 = r0.zznc
            int r2 = r0.pos
            char r1 = r1[r2]
            boolean r1 = r0.zze(r1)
            if (r1 == 0) goto L_0x0286
            r19.zzea()
            r1 = 10
            r0.zznf = r1
            return r1
        L_0x0286:
            java.lang.String r1 = "Expected value"
            java.io.IOException r1 = r0.zzbg(r1)
            throw r1
        L_0x028d:
            r0.zznf = r6
            return r6
        L_0x0290:
            if (r3 != r6) goto L_0x0299
            r1 = 4
            r0.zznf = r1
            return r1
        L_0x0296:
            r0.zznf = r10
            return r10
        L_0x0299:
            if (r3 == r6) goto L_0x02a6
            r1 = 2
            if (r3 != r1) goto L_0x029f
            goto L_0x02a6
        L_0x029f:
            java.lang.String r1 = "Unexpected value"
            java.io.IOException r1 = r0.zzbg(r1)
            throw r1
        L_0x02a6:
            r19.zzea()
            int r1 = r0.pos
            int r1 = r1 - r6
            r0.pos = r1
            r1 = 7
            r0.zznf = r1
            return r1
        L_0x02b2:
            r19.zzea()
            r0.zznf = r4
            return r4
        L_0x02b8:
            r1 = 9
            r0.zznf = r1
            return r1
        L_0x02bd:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "JsonReader is closed"
            r1.<init>(r2)
            throw r1
        L_0x02c5:
            int[] r1 = r0.zznj
            int r2 = r0.zznk
            int r2 = r2 - r6
            r4 = 4
            r1[r2] = r4
            r1 = 5
            if (r3 != r1) goto L_0x02ea
            int r1 = r0.zzc(r6)
            if (r1 == r9) goto L_0x02ea
            if (r1 == r8) goto L_0x02e7
            r2 = 125(0x7d, float:1.75E-43)
            if (r1 != r2) goto L_0x02e0
            r1 = 2
            r0.zznf = r1
            return r1
        L_0x02e0:
            java.lang.String r1 = "Unterminated object"
            java.io.IOException r1 = r0.zzbg(r1)
            throw r1
        L_0x02e7:
            r19.zzea()
        L_0x02ea:
            int r1 = r0.zzc(r6)
            r2 = 34
            if (r1 == r2) goto L_0x032b
            r2 = 39
            if (r1 == r2) goto L_0x0323
            r2 = 125(0x7d, float:1.75E-43)
            if (r1 == r2) goto L_0x0315
            r19.zzea()
            int r2 = r0.pos
            int r2 = r2 - r6
            r0.pos = r2
            char r1 = (char) r1
            boolean r1 = r0.zze(r1)
            if (r1 == 0) goto L_0x030e
            r1 = 14
            r0.zznf = r1
            return r1
        L_0x030e:
            java.lang.String r1 = "Expected name"
            java.io.IOException r1 = r0.zzbg(r1)
            throw r1
        L_0x0315:
            r1 = 5
            if (r3 == r1) goto L_0x031c
            r1 = 2
            r0.zznf = r1
            return r1
        L_0x031c:
            java.lang.String r1 = "Expected name"
            java.io.IOException r1 = r0.zzbg(r1)
            throw r1
        L_0x0323:
            r19.zzea()
            r1 = 12
            r0.zznf = r1
            return r1
        L_0x032b:
            r1 = 13
            r0.zznf = r1
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_remote_config.zzfi.zzdy():int");
    }

    private final boolean zze(char c) throws IOException {
        if (!(c == 9 || c == 10 || c == 12 || c == 13 || c == ' ')) {
            if (c != '#') {
                if (c != ',') {
                    if (!(c == '/' || c == '=')) {
                        if (!(c == '{' || c == '}' || c == ':')) {
                            if (c != ';') {
                                switch (c) {
                                    case '[':
                                    case ']':
                                        break;
                                    case '\\':
                                        break;
                                    default:
                                        return true;
                                }
                            }
                        }
                    }
                }
            }
            zzea();
        }
        return false;
    }

    public final String nextName() throws IOException {
        String str;
        int i = this.zznf;
        if (i == 0) {
            i = zzdy();
        }
        if (i == 14) {
            str = zzdz();
        } else if (i == 12) {
            str = zzf('\'');
        } else if (i == 13) {
            str = zzf('\"');
        } else {
            StringBuilder sb = new StringBuilder("Expected a name but was ");
            sb.append(zzdx());
            sb.append(zzec());
            throw new IllegalStateException(sb.toString());
        }
        this.zznf = 0;
        this.zznl[this.zznk - 1] = str;
        return str;
    }

    public final String nextString() throws IOException {
        int i = this.zznf;
        if (i == 0) {
            i = zzdy();
        }
        String str = null;
        if (i == 10) {
            str = zzdz();
        } else if (i == 8) {
            str = zzf('\'');
        } else if (i == 9) {
            str = zzf('\"');
        } else if (i == 11) {
            this.zzni = null;
        } else if (i == 15) {
            str = Long.toString(this.zzng);
        } else if (i == 16) {
            str = new String(this.zznc, this.pos, this.zznh);
            this.pos += this.zznh;
        } else {
            StringBuilder sb = new StringBuilder("Expected a string but was ");
            sb.append(zzdx());
            sb.append(zzec());
            throw new IllegalStateException(sb.toString());
        }
        this.zznf = 0;
        int[] iArr = this.zznm;
        int i2 = this.zznk - 1;
        iArr[i2] = iArr[i2] + 1;
        return str;
    }

    public final boolean nextBoolean() throws IOException {
        int i = this.zznf;
        if (i == 0) {
            i = zzdy();
        }
        if (i == 5) {
            this.zznf = 0;
            int[] iArr = this.zznm;
            int i2 = this.zznk - 1;
            iArr[i2] = iArr[i2] + 1;
            return true;
        } else if (i == 6) {
            this.zznf = 0;
            int[] iArr2 = this.zznm;
            int i3 = this.zznk - 1;
            iArr2[i3] = iArr2[i3] + 1;
            return false;
        } else {
            StringBuilder sb = new StringBuilder("Expected a boolean but was ");
            sb.append(zzdx());
            sb.append(zzec());
            throw new IllegalStateException(sb.toString());
        }
    }

    public final void nextNull() throws IOException {
        int i = this.zznf;
        if (i == 0) {
            i = zzdy();
        }
        if (i == 7) {
            this.zznf = 0;
            int[] iArr = this.zznm;
            int i2 = this.zznk - 1;
            iArr[i2] = iArr[i2] + 1;
            return;
        }
        StringBuilder sb = new StringBuilder("Expected null but was ");
        sb.append(zzdx());
        sb.append(zzec());
        throw new IllegalStateException(sb.toString());
    }

    private final String zzf(char c) throws IOException {
        char[] cArr = this.zznc;
        StringBuilder sb = new StringBuilder();
        while (true) {
            int i = this.pos;
            int i2 = this.limit;
            int i3 = i;
            while (true) {
                if (i3 < i2) {
                    int i4 = i3 + 1;
                    char c2 = cArr[i3];
                    if (c2 == c) {
                        this.pos = i4;
                        sb.append(cArr, i, (i4 - i) - 1);
                        return sb.toString();
                    } else if (c2 == '\\') {
                        this.pos = i4;
                        sb.append(cArr, i, (i4 - i) - 1);
                        sb.append(zzed());
                        break;
                    } else {
                        if (c2 == 10) {
                            this.zznd++;
                            this.zzne = i4;
                        }
                        i3 = i4;
                    }
                } else {
                    sb.append(cArr, i, i3 - i);
                    this.pos = i3;
                    if (!zzp(1)) {
                        throw zzbg("Unterminated string");
                    }
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x004b, code lost:
        zzea();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.lang.String zzdz() throws java.io.IOException {
        /*
            r6 = this;
            r0 = 0
            r1 = 0
            r2 = r1
        L_0x0003:
            r1 = 0
        L_0x0004:
            int r3 = r6.pos
            int r4 = r3 + r1
            int r5 = r6.limit
            if (r4 >= r5) goto L_0x004f
            char[] r4 = r6.zznc
            int r3 = r3 + r1
            char r3 = r4[r3]
            r4 = 9
            if (r3 == r4) goto L_0x005d
            r4 = 10
            if (r3 == r4) goto L_0x005d
            r4 = 12
            if (r3 == r4) goto L_0x005d
            r4 = 13
            if (r3 == r4) goto L_0x005d
            r4 = 32
            if (r3 == r4) goto L_0x005d
            r4 = 35
            if (r3 == r4) goto L_0x004b
            r4 = 44
            if (r3 == r4) goto L_0x005d
            r4 = 47
            if (r3 == r4) goto L_0x004b
            r4 = 61
            if (r3 == r4) goto L_0x004b
            r4 = 123(0x7b, float:1.72E-43)
            if (r3 == r4) goto L_0x005d
            r4 = 125(0x7d, float:1.75E-43)
            if (r3 == r4) goto L_0x005d
            r4 = 58
            if (r3 == r4) goto L_0x005d
            r4 = 59
            if (r3 == r4) goto L_0x004b
            switch(r3) {
                case 91: goto L_0x005d;
                case 92: goto L_0x004b;
                case 93: goto L_0x005d;
                default: goto L_0x0048;
            }
        L_0x0048:
            int r1 = r1 + 1
            goto L_0x0004
        L_0x004b:
            r6.zzea()
            goto L_0x005d
        L_0x004f:
            char[] r3 = r6.zznc
            int r3 = r3.length
            if (r1 >= r3) goto L_0x005f
            int r3 = r1 + 1
            boolean r3 = r6.zzp(r3)
            if (r3 == 0) goto L_0x005d
            goto L_0x0004
        L_0x005d:
            r0 = r1
            goto L_0x0079
        L_0x005f:
            if (r2 != 0) goto L_0x0066
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
        L_0x0066:
            char[] r3 = r6.zznc
            int r4 = r6.pos
            r2.append(r3, r4, r1)
            int r3 = r6.pos
            int r3 = r3 + r1
            r6.pos = r3
            r1 = 1
            boolean r1 = r6.zzp(r1)
            if (r1 != 0) goto L_0x0003
        L_0x0079:
            if (r2 != 0) goto L_0x0085
            java.lang.String r1 = new java.lang.String
            char[] r2 = r6.zznc
            int r3 = r6.pos
            r1.<init>(r2, r3, r0)
            goto L_0x0090
        L_0x0085:
            char[] r1 = r6.zznc
            int r3 = r6.pos
            r2.append(r1, r3, r0)
            java.lang.String r1 = r2.toString()
        L_0x0090:
            int r2 = r6.pos
            int r2 = r2 + r0
            r6.pos = r2
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_remote_config.zzfi.zzdz():java.lang.String");
    }

    private final void zzg(char c) throws IOException {
        char[] cArr = this.zznc;
        while (true) {
            int i = this.pos;
            int i2 = this.limit;
            while (true) {
                if (i < i2) {
                    int i3 = i + 1;
                    char c2 = cArr[i];
                    if (c2 == c) {
                        this.pos = i3;
                        return;
                    } else if (c2 == '\\') {
                        this.pos = i3;
                        zzed();
                        break;
                    } else {
                        if (c2 == 10) {
                            this.zznd++;
                            this.zzne = i3;
                        }
                        i = i3;
                    }
                } else {
                    this.pos = i;
                    if (!zzp(1)) {
                        throw zzbg("Unterminated string");
                    }
                }
            }
        }
    }

    public final void close() throws IOException {
        this.zznf = 0;
        this.zznj[0] = 8;
        this.zznk = 1;
        this.f1540in.close();
    }

    public final void skipValue() throws IOException {
        int i;
        int i2 = 0;
        do {
            int i3 = this.zznf;
            if (i3 == 0) {
                i3 = zzdy();
            }
            if (i3 == 3) {
                zzo(1);
            } else if (i3 == 1) {
                zzo(3);
            } else {
                if (i3 == 4) {
                    this.zznk--;
                } else if (i3 == 2) {
                    this.zznk--;
                } else if (i3 == 14 || i3 == 10) {
                    while (true) {
                        i = 0;
                        while (true) {
                            int i4 = this.pos;
                            if (i4 + i < this.limit) {
                                char c = this.zznc[i4 + i];
                                if (!(c == 9 || c == 10 || c == 12 || c == 13 || c == ' ')) {
                                    if (c != '#') {
                                        if (c != ',') {
                                            if (!(c == '/' || c == '=')) {
                                                if (!(c == '{' || c == '}' || c == ':')) {
                                                    if (c != ';') {
                                                        switch (c) {
                                                            case '[':
                                                            case ']':
                                                                break;
                                                            case '\\':
                                                                break;
                                                            default:
                                                                i++;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                this.pos = i4 + i;
                                if (!zzp(1)) {
                                }
                            }
                        }
                    }
                    zzea();
                    this.pos += i;
                    this.zznf = 0;
                } else if (i3 == 8 || i3 == 12) {
                    zzg('\'');
                    this.zznf = 0;
                } else if (i3 == 9 || i3 == 13) {
                    zzg('\"');
                    this.zznf = 0;
                } else {
                    if (i3 == 16) {
                        this.pos += this.zznh;
                    }
                    this.zznf = 0;
                }
                i2--;
                this.zznf = 0;
            }
            i2++;
            this.zznf = 0;
        } while (i2 != 0);
        int[] iArr = this.zznm;
        int i5 = this.zznk;
        int i6 = i5 - 1;
        iArr[i6] = iArr[i6] + 1;
        this.zznl[i5 - 1] = "null";
    }

    private final void zzo(int i) {
        int i2 = this.zznk;
        int[] iArr = this.zznj;
        if (i2 == iArr.length) {
            int[] iArr2 = new int[(i2 << 1)];
            int[] iArr3 = new int[(i2 << 1)];
            String[] strArr = new String[(i2 << 1)];
            System.arraycopy(iArr, 0, iArr2, 0, i2);
            System.arraycopy(this.zznm, 0, iArr3, 0, this.zznk);
            System.arraycopy(this.zznl, 0, strArr, 0, this.zznk);
            this.zznj = iArr2;
            this.zznm = iArr3;
            this.zznl = strArr;
        }
        int[] iArr4 = this.zznj;
        int i3 = this.zznk;
        this.zznk = i3 + 1;
        iArr4[i3] = i;
    }

    private final boolean zzp(int i) throws IOException {
        char[] cArr = this.zznc;
        int i2 = this.zzne;
        int i3 = this.pos;
        this.zzne = i2 - i3;
        int i4 = this.limit;
        if (i4 != i3) {
            this.limit = i4 - i3;
            System.arraycopy(cArr, i3, cArr, 0, this.limit);
        } else {
            this.limit = 0;
        }
        this.pos = 0;
        do {
            Reader reader = this.f1540in;
            int i5 = this.limit;
            int read = reader.read(cArr, i5, cArr.length - i5);
            if (read == -1) {
                return false;
            }
            this.limit += read;
            if (this.zznd == 0) {
                int i6 = this.zzne;
                if (i6 == 0 && this.limit > 0 && cArr[0] == 65279) {
                    this.pos++;
                    this.zzne = i6 + 1;
                    i++;
                }
            }
        } while (this.limit < i);
        return true;
    }

    private final int zzc(boolean z) throws IOException {
        char[] cArr = this.zznc;
        int i = this.pos;
        int i2 = this.limit;
        while (true) {
            boolean z2 = true;
            if (i == i2) {
                this.pos = i;
                if (zzp(1)) {
                    i = this.pos;
                    i2 = this.limit;
                } else if (!z) {
                    return -1;
                } else {
                    StringBuilder sb = new StringBuilder("End of input");
                    sb.append(zzec());
                    throw new EOFException(sb.toString());
                }
            }
            int i3 = i + 1;
            char c = cArr[i];
            if (c == 10) {
                this.zznd++;
                this.zzne = i3;
            } else if (!(c == ' ' || c == 13 || c == 9)) {
                if (c == '/') {
                    this.pos = i3;
                    if (i3 == i2) {
                        this.pos--;
                        boolean zzp = zzp(2);
                        this.pos++;
                        if (!zzp) {
                            return c;
                        }
                    }
                    zzea();
                    int i4 = this.pos;
                    char c2 = cArr[i4];
                    if (c2 == '*') {
                        this.pos = i4 + 1;
                        while (true) {
                            int i5 = 0;
                            if (this.pos + 2 > this.limit && !zzp(2)) {
                                z2 = false;
                                break;
                            }
                            char[] cArr2 = this.zznc;
                            int i6 = this.pos;
                            if (cArr2[i6] != 10) {
                                while (i5 < 2) {
                                    if (this.zznc[this.pos + i5] == "*/".charAt(i5)) {
                                        i5++;
                                    }
                                }
                                break;
                            }
                            this.zznd++;
                            this.zzne = i6 + 1;
                            this.pos++;
                        }
                        if (z2) {
                            i = this.pos + 2;
                            i2 = this.limit;
                        } else {
                            throw zzbg("Unterminated comment");
                        }
                    } else if (c2 != '/') {
                        return c;
                    } else {
                        this.pos = i4 + 1;
                        zzeb();
                        i = this.pos;
                        i2 = this.limit;
                    }
                } else if (c == '#') {
                    this.pos = i3;
                    zzea();
                    zzeb();
                    i = this.pos;
                    i2 = this.limit;
                } else {
                    this.pos = i3;
                    return c;
                }
            }
            i = i3;
        }
    }

    private final void zzea() throws IOException {
        if (!this.zznb) {
            throw zzbg("Use JsonReader.setLenient(true) to accept malformed JSON");
        }
    }

    private final void zzeb() throws IOException {
        char c;
        do {
            if (this.pos >= this.limit && !zzp(1)) {
                break;
            }
            char[] cArr = this.zznc;
            int i = this.pos;
            this.pos = i + 1;
            c = cArr[i];
            if (c == 10) {
                this.zznd++;
                this.zzne = this.pos;
                return;
            }
        } while (c != 13);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(zzec());
        return sb.toString();
    }

    private final String zzec() {
        int i = this.zznd + 1;
        int i2 = (this.pos - this.zzne) + 1;
        StringBuilder sb = new StringBuilder(" at line ");
        sb.append(i);
        sb.append(" column ");
        sb.append(i2);
        sb.append(" path ");
        StringBuilder sb2 = new StringBuilder("$");
        int i3 = this.zznk;
        for (int i4 = 0; i4 < i3; i4++) {
            int i5 = this.zznj[i4];
            if (i5 == 1 || i5 == 2) {
                sb2.append('[');
                sb2.append(this.zznm[i4]);
                sb2.append(']');
            } else if (i5 == 3 || i5 == 4 || i5 == 5) {
                sb2.append('.');
                String[] strArr = this.zznl;
                if (strArr[i4] != null) {
                    sb2.append(strArr[i4]);
                }
            }
        }
        sb.append(sb2.toString());
        return sb.toString();
    }

    private final char zzed() throws IOException {
        int i;
        int i2;
        String str = "Unterminated escape sequence";
        if (this.pos != this.limit || zzp(1)) {
            char[] cArr = this.zznc;
            int i3 = this.pos;
            this.pos = i3 + 1;
            char c = cArr[i3];
            if (c == 10) {
                this.zznd++;
                this.zzne = this.pos;
            } else if (!(c == '\"' || c == '\'' || c == '/' || c == '\\')) {
                if (c == 'b') {
                    return 8;
                }
                if (c == 'f') {
                    return 12;
                }
                if (c == 'n') {
                    return 10;
                }
                if (c == 'r') {
                    return 13;
                }
                if (c == 't') {
                    return 9;
                }
                if (c != 'u') {
                    throw zzbg("Invalid escape sequence");
                } else if (this.pos + 4 <= this.limit || zzp(4)) {
                    char c2 = 0;
                    int i4 = this.pos;
                    int i5 = i4 + 4;
                    while (i4 < i5) {
                        char c3 = this.zznc[i4];
                        char c4 = (char) (c2 << 4);
                        if (c3 < '0' || c3 > '9') {
                            if (c3 >= 'a' && c3 <= 'f') {
                                i = c3 - 'a';
                            } else if (c3 < 'A' || c3 > 'F') {
                                StringBuilder sb = new StringBuilder("\\u");
                                sb.append(new String(this.zznc, this.pos, 4));
                                throw new NumberFormatException(sb.toString());
                            } else {
                                i = c3 - 'A';
                            }
                            i2 = i + 10;
                        } else {
                            i2 = c3 - '0';
                        }
                        c2 = (char) (c4 + i2);
                        i4++;
                    }
                    this.pos += 4;
                    return c2;
                } else {
                    throw zzbg(str);
                }
            }
            return c;
        }
        throw zzbg(str);
    }

    private final IOException zzbg(String str) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(zzec());
        throw new zzfm(sb.toString());
    }

    static {
        zzfh.zzmz = new zzfj();
    }
}
