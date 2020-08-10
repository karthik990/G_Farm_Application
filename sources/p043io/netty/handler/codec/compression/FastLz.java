package p043io.netty.handler.codec.compression;

import com.google.common.base.Ascii;

/* renamed from: io.netty.handler.codec.compression.FastLz */
final class FastLz {
    static final byte BLOCK_TYPE_COMPRESSED = 1;
    static final byte BLOCK_TYPE_NON_COMPRESSED = 0;
    static final byte BLOCK_WITHOUT_CHECKSUM = 0;
    static final byte BLOCK_WITH_CHECKSUM = 16;
    static final int CHECKSUM_OFFSET = 4;
    private static final int HASH_LOG = 13;
    private static final int HASH_MASK = 8191;
    private static final int HASH_SIZE = 8192;
    static final int LEVEL_1 = 1;
    static final int LEVEL_2 = 2;
    static final int LEVEL_AUTO = 0;
    static final int MAGIC_NUMBER = 4607066;
    static final int MAX_CHUNK_LENGTH = 65535;
    private static final int MAX_COPY = 32;
    private static final int MAX_DISTANCE = 8191;
    private static final int MAX_FARDISTANCE = 73725;
    private static final int MAX_LEN = 264;
    static final int MIN_LENGTH_TO_COMPRESSION = 32;
    private static final int MIN_RECOMENDED_LENGTH_FOR_LEVEL_2 = 65536;
    static final int OPTIONS_OFFSET = 3;

    static int calculateOutputBufferLength(int i) {
        double d = (double) i;
        Double.isNaN(d);
        return Math.max((int) (d * 1.06d), 66);
    }

    /* JADX WARNING: Removed duplicated region for block: B:101:0x021d  */
    /* JADX WARNING: Removed duplicated region for block: B:118:0x02d2  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0092  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x014c  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x0158  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0170  */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x0204  */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x020f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static int compress(byte[] r22, int r23, int r24, byte[] r25, int r26, int r27) {
        /*
            r0 = r22
            r1 = r24
            r2 = 2
            r3 = 1
            if (r27 != 0) goto L_0x0010
            r4 = 65536(0x10000, float:9.18355E-41)
            if (r1 >= r4) goto L_0x000e
            r4 = 1
            goto L_0x0012
        L_0x000e:
            r4 = 2
            goto L_0x0012
        L_0x0010:
            r4 = r27
        L_0x0012:
            int r5 = r1 + 0
            int r6 = r5 + -2
            int r5 = r5 + -12
            r7 = 8192(0x2000, float:1.14794E-41)
            int[] r8 = new int[r7]
            r9 = 4
            r10 = 0
            if (r1 >= r9) goto L_0x0040
            if (r1 == 0) goto L_0x003f
            int r2 = r26 + 0
            int r4 = r1 + -1
            byte r4 = (byte) r4
            r25[r2] = r4
            int r6 = r6 + r3
            r2 = 1
        L_0x002b:
            if (r10 > r6) goto L_0x003c
            int r4 = r2 + 1
            int r2 = r26 + r2
            int r5 = r10 + 1
            int r7 = r23 + r10
            byte r7 = r0[r7]
            r25[r2] = r7
            r2 = r4
            r10 = r5
            goto L_0x002b
        L_0x003c:
            int r0 = r1 + 1
            return r0
        L_0x003f:
            return r10
        L_0x0040:
            r1 = 0
        L_0x0041:
            if (r1 >= r7) goto L_0x0048
            r8[r1] = r10
            int r1 = r1 + 1
            goto L_0x0041
        L_0x0048:
            int r1 = r26 + 0
            r7 = 31
            r25[r1] = r7
            int r1 = r26 + 1
            int r9 = r23 + 0
            byte r9 = r0[r9]
            r25[r1] = r9
            int r1 = r26 + 2
            int r9 = r23 + 1
            byte r9 = r0[r9]
            r25[r1] = r9
            r1 = 3
            r9 = 2
            r11 = 3
            r12 = 2
        L_0x0062:
            if (r9 >= r5) goto L_0x035f
            r14 = 0
            if (r4 != r2) goto L_0x0089
            int r16 = r23 + r9
            byte r10 = r0[r16]
            int r7 = r16 + -1
            byte r13 = r0[r7]
            if (r10 != r13) goto L_0x0089
            int r7 = readU16(r0, r7)
            int r10 = r16 + 1
            int r10 = readU16(r0, r10)
            if (r7 != r10) goto L_0x0089
            r18 = 1
            int r7 = r9 + 3
            int r10 = r9 + -1
            int r10 = r10 + r1
            r13 = r10
            r10 = r7
            r7 = 1
            goto L_0x008e
        L_0x0089:
            r10 = r9
            r18 = r14
            r7 = 0
            r13 = 0
        L_0x008e:
            r20 = 8191(0x1fff, double:4.047E-320)
            if (r7 != 0) goto L_0x014c
            int r7 = r23 + r10
            int r13 = hashFunction(r0, r7)
            r16 = r8[r13]
            int r1 = r9 - r16
            long r2 = (long) r1
            r8[r13] = r9
            int r1 = (r2 > r14 ? 1 : (r2 == r14 ? 0 : -1))
            if (r1 == 0) goto L_0x011e
            r1 = 1
            if (r4 != r1) goto L_0x00ac
            int r1 = (r2 > r20 ? 1 : (r2 == r20 ? 0 : -1))
            if (r1 < 0) goto L_0x00b3
            goto L_0x011e
        L_0x00ac:
            r18 = 73725(0x11ffd, double:3.6425E-319)
            int r1 = (r2 > r18 ? 1 : (r2 == r18 ? 0 : -1))
            if (r1 >= 0) goto L_0x011e
        L_0x00b3:
            int r1 = r16 + 1
            int r13 = r23 + r16
            byte r13 = r0[r13]
            int r10 = r10 + 1
            byte r7 = r0[r7]
            if (r13 != r7) goto L_0x011e
            int r7 = r1 + 1
            int r1 = r23 + r1
            byte r1 = r0[r1]
            int r13 = r10 + 1
            int r10 = r23 + r10
            byte r10 = r0[r10]
            if (r1 != r10) goto L_0x011e
            int r1 = r7 + 1
            int r7 = r23 + r7
            byte r7 = r0[r7]
            int r10 = r13 + 1
            int r13 = r23 + r13
            byte r13 = r0[r13]
            if (r7 == r13) goto L_0x00dc
            goto L_0x011e
        L_0x00dc:
            r7 = 2
            if (r4 != r7) goto L_0x011c
            int r7 = (r2 > r20 ? 1 : (r2 == r20 ? 0 : -1))
            if (r7 < 0) goto L_0x011c
            int r7 = r10 + 1
            int r10 = r23 + r10
            byte r10 = r0[r10]
            int r13 = r1 + 1
            int r1 = r23 + r1
            byte r1 = r0[r1]
            if (r10 != r1) goto L_0x0101
            int r1 = r23 + r7
            byte r1 = r0[r1]
            int r7 = r13 + 1
            int r10 = r23 + r13
            byte r10 = r0[r10]
            if (r1 == r10) goto L_0x00fe
            goto L_0x0101
        L_0x00fe:
            r1 = 5
            r13 = r7
            goto L_0x014f
        L_0x0101:
            int r1 = r11 + 1
            int r2 = r26 + r11
            int r3 = r9 + 1
            int r7 = r23 + r9
            byte r7 = r0[r7]
            r25[r2] = r7
            int r12 = r12 + 1
            r2 = 32
            if (r12 != r2) goto L_0x0142
            int r11 = r1 + 1
            int r1 = r26 + r1
            r2 = 31
            r25[r1] = r2
            goto L_0x0138
        L_0x011c:
            r13 = r1
            goto L_0x014e
        L_0x011e:
            int r1 = r11 + 1
            int r2 = r26 + r11
            int r3 = r9 + 1
            int r7 = r23 + r9
            byte r7 = r0[r7]
            r25[r2] = r7
            int r12 = r12 + 1
            r2 = 32
            if (r12 != r2) goto L_0x0142
            int r11 = r1 + 1
            int r1 = r26 + r1
            r2 = 31
            r25[r1] = r2
        L_0x0138:
            r9 = r3
        L_0x0139:
            r1 = 3
            r2 = 2
            r3 = 1
            r7 = 31
            r10 = 0
            r12 = 0
            goto L_0x0062
        L_0x0142:
            r11 = r1
            r9 = r3
            r1 = 3
            r2 = 2
            r3 = 1
            r7 = 31
            r10 = 0
            goto L_0x0062
        L_0x014c:
            r2 = r18
        L_0x014e:
            r1 = 3
        L_0x014f:
            int r1 = r1 + r9
            r16 = 1
            long r2 = r2 - r16
            int r7 = (r2 > r14 ? 1 : (r2 == r14 ? 0 : -1))
            if (r7 != 0) goto L_0x0170
            int r7 = r23 + r1
            r10 = 1
            int r7 = r7 - r10
            byte r7 = r0[r7]
        L_0x015e:
            if (r1 >= r6) goto L_0x016d
            int r10 = r13 + 1
            int r13 = r23 + r13
            byte r13 = r0[r13]
            if (r13 == r7) goto L_0x0169
            goto L_0x016d
        L_0x0169:
            int r1 = r1 + 1
            r13 = r10
            goto L_0x015e
        L_0x016d:
            r13 = r1
            goto L_0x0202
        L_0x0170:
            int r7 = r13 + 1
            int r10 = r23 + r13
            byte r10 = r0[r10]
            int r13 = r1 + 1
            int r1 = r23 + r1
            byte r1 = r0[r1]
            if (r10 == r1) goto L_0x0180
            goto L_0x0202
        L_0x0180:
            int r1 = r7 + 1
            int r7 = r23 + r7
            byte r7 = r0[r7]
            int r10 = r13 + 1
            int r13 = r23 + r13
            byte r13 = r0[r13]
            if (r7 == r13) goto L_0x0191
        L_0x018e:
            r13 = r10
            goto L_0x0202
        L_0x0191:
            int r7 = r1 + 1
            int r1 = r23 + r1
            byte r1 = r0[r1]
            int r13 = r10 + 1
            int r10 = r23 + r10
            byte r10 = r0[r10]
            if (r1 == r10) goto L_0x01a1
            goto L_0x0202
        L_0x01a1:
            int r1 = r7 + 1
            int r7 = r23 + r7
            byte r7 = r0[r7]
            int r10 = r13 + 1
            int r13 = r23 + r13
            byte r13 = r0[r13]
            if (r7 == r13) goto L_0x01b0
            goto L_0x018e
        L_0x01b0:
            int r7 = r1 + 1
            int r1 = r23 + r1
            byte r1 = r0[r1]
            int r13 = r10 + 1
            int r10 = r23 + r10
            byte r10 = r0[r10]
            if (r1 == r10) goto L_0x01bf
            goto L_0x0202
        L_0x01bf:
            int r1 = r7 + 1
            int r7 = r23 + r7
            byte r7 = r0[r7]
            int r10 = r13 + 1
            int r13 = r23 + r13
            byte r13 = r0[r13]
            if (r7 == r13) goto L_0x01ce
            goto L_0x018e
        L_0x01ce:
            int r7 = r1 + 1
            int r1 = r23 + r1
            byte r1 = r0[r1]
            int r13 = r10 + 1
            int r10 = r23 + r10
            byte r10 = r0[r10]
            if (r1 == r10) goto L_0x01dd
            goto L_0x0202
        L_0x01dd:
            int r1 = r7 + 1
            int r7 = r23 + r7
            byte r7 = r0[r7]
            int r10 = r13 + 1
            int r13 = r23 + r13
            byte r13 = r0[r13]
            if (r7 == r13) goto L_0x01ec
            goto L_0x018e
        L_0x01ec:
            r7 = r1
            r1 = r10
        L_0x01ee:
            if (r1 >= r6) goto L_0x016d
            int r10 = r7 + 1
            int r7 = r23 + r7
            byte r7 = r0[r7]
            int r13 = r1 + 1
            int r1 = r23 + r1
            byte r1 = r0[r1]
            if (r7 == r1) goto L_0x01ff
            goto L_0x0202
        L_0x01ff:
            r7 = r10
            r1 = r13
            goto L_0x01ee
        L_0x0202:
            if (r12 == 0) goto L_0x020f
            int r1 = r26 + r11
            int r1 = r1 - r12
            r7 = 1
            int r1 = r1 - r7
            int r12 = r12 + -1
            byte r7 = (byte) r12
            r25[r1] = r7
            goto L_0x0211
        L_0x020f:
            int r11 = r11 + -1
        L_0x0211:
            int r13 = r13 + -3
            int r1 = r13 - r9
            r7 = 7
            r14 = 255(0xff, double:1.26E-321)
            r12 = 8
            r9 = 2
            if (r4 != r9) goto L_0x02d2
            r9 = -1
            int r10 = (r2 > r20 ? 1 : (r2 == r20 ? 0 : -1))
            if (r10 >= 0) goto L_0x026c
            if (r1 >= r7) goto L_0x023d
            int r7 = r11 + 1
            int r9 = r26 + r11
            int r1 = r1 << 5
            long r10 = (long) r1
            long r16 = r2 >>> r12
            long r10 = r10 + r16
            int r1 = (int) r10
            byte r1 = (byte) r1
            r25[r9] = r1
            int r1 = r7 + 1
            int r7 = r26 + r7
            long r2 = r2 & r14
            int r3 = (int) r2
            byte r2 = (byte) r3
            r25[r7] = r2
            goto L_0x0295
        L_0x023d:
            int r7 = r11 + 1
            int r10 = r26 + r11
            long r11 = r2 >>> r12
            r16 = 224(0xe0, double:1.107E-321)
            long r11 = r11 + r16
            int r12 = (int) r11
            byte r11 = (byte) r12
            r25[r10] = r11
            int r1 = r1 + -7
        L_0x024d:
            r10 = 255(0xff, float:3.57E-43)
            if (r1 < r10) goto L_0x025b
            int r10 = r7 + 1
            int r7 = r26 + r7
            r25[r7] = r9
            int r1 = r1 + -255
            r7 = r10
            goto L_0x024d
        L_0x025b:
            int r9 = r7 + 1
            int r7 = r26 + r7
            byte r1 = (byte) r1
            r25[r7] = r1
            int r1 = r9 + 1
            int r7 = r26 + r9
            long r2 = r2 & r14
            int r3 = (int) r2
            byte r2 = (byte) r3
            r25[r7] = r2
            goto L_0x0295
        L_0x026c:
            if (r1 >= r7) goto L_0x0299
            long r2 = r2 - r20
            int r7 = r11 + 1
            int r10 = r26 + r11
            int r1 = r1 << 5
            r11 = 31
            int r1 = r1 + r11
            byte r1 = (byte) r1
            r25[r10] = r1
            int r1 = r7 + 1
            int r7 = r26 + r7
            r25[r7] = r9
            int r7 = r1 + 1
            int r1 = r26 + r1
            long r9 = r2 >>> r12
            int r10 = (int) r9
            byte r9 = (byte) r10
            r25[r1] = r9
            int r1 = r7 + 1
            int r7 = r26 + r7
            long r2 = r2 & r14
            int r3 = (int) r2
            byte r2 = (byte) r3
            r25[r7] = r2
        L_0x0295:
            r20 = r13
            goto L_0x0341
        L_0x0299:
            long r2 = r2 - r20
            int r7 = r11 + 1
            int r10 = r26 + r11
            r25[r10] = r9
            int r1 = r1 + -7
        L_0x02a3:
            r10 = 255(0xff, float:3.57E-43)
            if (r1 < r10) goto L_0x02b1
            int r10 = r7 + 1
            int r7 = r26 + r7
            r25[r7] = r9
            int r1 = r1 + -255
            r7 = r10
            goto L_0x02a3
        L_0x02b1:
            int r10 = r7 + 1
            int r7 = r26 + r7
            byte r1 = (byte) r1
            r25[r7] = r1
            int r1 = r10 + 1
            int r7 = r26 + r10
            r25[r7] = r9
            int r7 = r1 + 1
            int r1 = r26 + r1
            long r9 = r2 >>> r12
            int r10 = (int) r9
            byte r9 = (byte) r10
            r25[r1] = r9
            int r1 = r7 + 1
            int r7 = r26 + r7
            long r2 = r2 & r14
            int r3 = (int) r2
            byte r2 = (byte) r3
            r25[r7] = r2
            goto L_0x0295
        L_0x02d2:
            r9 = 262(0x106, float:3.67E-43)
            if (r1 <= r9) goto L_0x0302
        L_0x02d6:
            r9 = 262(0x106, float:3.67E-43)
            if (r1 <= r9) goto L_0x0302
            int r9 = r11 + 1
            int r10 = r26 + r11
            long r18 = r2 >>> r12
            r20 = r13
            r16 = 224(0xe0, double:1.107E-321)
            long r12 = r18 + r16
            int r11 = (int) r12
            byte r11 = (byte) r11
            r25[r10] = r11
            int r10 = r9 + 1
            int r9 = r26 + r9
            r11 = -3
            r25[r9] = r11
            int r11 = r10 + 1
            int r9 = r26 + r10
            long r12 = r2 & r14
            int r10 = (int) r12
            byte r10 = (byte) r10
            r25[r9] = r10
            int r1 = r1 + -262
            r13 = r20
            r12 = 8
            goto L_0x02d6
        L_0x0302:
            r20 = r13
            if (r1 >= r7) goto L_0x0320
            int r7 = r11 + 1
            int r9 = r26 + r11
            int r1 = r1 << 5
            long r10 = (long) r1
            r1 = 8
            long r12 = r2 >>> r1
            long r10 = r10 + r12
            int r1 = (int) r10
            byte r1 = (byte) r1
            r25[r9] = r1
            int r1 = r7 + 1
            int r7 = r26 + r7
            long r2 = r2 & r14
            int r3 = (int) r2
            byte r2 = (byte) r3
            r25[r7] = r2
            goto L_0x0341
        L_0x0320:
            int r7 = r11 + 1
            int r9 = r26 + r11
            r10 = 8
            long r10 = r2 >>> r10
            r12 = 224(0xe0, double:1.107E-321)
            long r10 = r10 + r12
            int r11 = (int) r10
            byte r10 = (byte) r11
            r25[r9] = r10
            int r9 = r7 + 1
            int r7 = r26 + r7
            int r1 = r1 + -7
            byte r1 = (byte) r1
            r25[r7] = r1
            int r1 = r9 + 1
            int r7 = r26 + r9
            long r2 = r2 & r14
            int r3 = (int) r2
            byte r2 = (byte) r3
            r25[r7] = r2
        L_0x0341:
            int r2 = r23 + r20
            int r2 = hashFunction(r0, r2)
            int r13 = r20 + 1
            r8[r2] = r20
            int r2 = r23 + r13
            int r2 = hashFunction(r0, r2)
            int r9 = r13 + 1
            r8[r2] = r13
            int r11 = r1 + 1
            int r1 = r26 + r1
            r2 = 31
            r25[r1] = r2
            goto L_0x0139
        L_0x035f:
            r1 = 1
            int r6 = r6 + r1
        L_0x0361:
            if (r9 > r6) goto L_0x0383
            int r1 = r11 + 1
            int r2 = r26 + r11
            int r3 = r9 + 1
            int r5 = r23 + r9
            byte r5 = r0[r5]
            r25[r2] = r5
            int r12 = r12 + 1
            r2 = 32
            if (r12 != r2) goto L_0x0380
            int r11 = r1 + 1
            int r1 = r26 + r1
            r2 = 31
            r25[r1] = r2
            r9 = r3
            r12 = 0
            goto L_0x0361
        L_0x0380:
            r11 = r1
            r9 = r3
            goto L_0x0361
        L_0x0383:
            if (r12 == 0) goto L_0x038f
            int r0 = r26 + r11
            int r0 = r0 - r12
            r1 = 1
            int r0 = r0 - r1
            int r12 = r12 - r1
            byte r1 = (byte) r12
            r25[r0] = r1
            goto L_0x0391
        L_0x038f:
            int r11 = r11 + -1
        L_0x0391:
            r0 = 2
            if (r4 != r0) goto L_0x039c
            byte r0 = r25[r26]
            r1 = 32
            r0 = r0 | r1
            byte r0 = (byte) r0
            r25[r26] = r0
        L_0x039c:
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.codec.compression.FastLz.compress(byte[], int, int, byte[], int, int):int");
    }

    static int decompress(byte[] bArr, int i, int i2, byte[] bArr2, int i3, int i4) {
        long j;
        int i5;
        long j2;
        int i6;
        boolean z;
        byte b;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12 = i2;
        int i13 = i4;
        char c = 5;
        int i14 = (bArr[i] >> 5) + 1;
        if (i14 == 1 || i14 == 2) {
            long j3 = (long) (bArr[i + 0] & Ascii.f1878US);
            int i15 = 0;
            int i16 = 1;
            boolean z2 = true;
            while (true) {
                long j4 = j3 >> c;
                long j5 = (31 & j3) << 8;
                if (j3 >= 32) {
                    long j6 = j4 - 1;
                    long j7 = (long) i15;
                    int i17 = i15;
                    int i18 = (int) (j7 - j5);
                    if (j6 != 6) {
                        j = j3;
                        z = z2;
                        b = 255;
                        i7 = i16;
                    } else if (i14 == 1) {
                        i7 = i16 + 1;
                        b = 255;
                        z = z2;
                        j6 += (long) (bArr[i + i16] & 255);
                        j = j3;
                    } else {
                        z = z2;
                        b = 255;
                        while (true) {
                            i7 = i16 + 1;
                            byte b2 = bArr[i + i16] & 255;
                            j = j3;
                            j6 += (long) b2;
                            if (b2 != 255) {
                                break;
                            }
                            i16 = i7;
                            j3 = j;
                        }
                    }
                    if (i14 == 1) {
                        i8 = i7 + 1;
                        i9 = i18 - (bArr[i + i7] & b);
                    } else {
                        i8 = i7 + 1;
                        byte b3 = bArr[i + i7] & b;
                        i9 = i18 - b3;
                        if (b3 == b && j5 == 7936) {
                            int i19 = i8 + 1;
                            i9 = (int) ((j7 - (((long) ((bArr[i + i8] & b) << 8)) + ((long) (bArr[i + i19] & b)))) - 8191);
                            i8 = i19 + 1;
                        }
                    }
                    if (j7 + j6 + 3 > ((long) i13)) {
                        return 0;
                    }
                    if (i9 - 1 < 0) {
                        return 0;
                    }
                    if (i8 < i12) {
                        i11 = i8 + 1;
                        j = (long) (bArr[i + i8] & 255);
                        i10 = i17;
                    } else {
                        i11 = i8;
                        i10 = i17;
                        z = false;
                    }
                    if (i9 == i10) {
                        byte b4 = bArr2[(i3 + i9) - 1];
                        int i20 = i10 + 1;
                        bArr2[i3 + i10] = b4;
                        int i21 = i20 + 1;
                        bArr2[i3 + i20] = b4;
                        int i22 = i21 + 1;
                        bArr2[i3 + i21] = b4;
                        while (j6 != 0) {
                            int i23 = i22 + 1;
                            bArr2[i3 + i22] = b4;
                            j6--;
                            i22 = i23;
                        }
                        i16 = i11;
                        i5 = i22;
                    } else {
                        int i24 = i9 - 1;
                        int i25 = i10 + 1;
                        int i26 = i24 + 1;
                        bArr2[i3 + i10] = bArr2[i3 + i24];
                        int i27 = i25 + 1;
                        int i28 = i26 + 1;
                        bArr2[i3 + i25] = bArr2[i3 + i26];
                        int i29 = i27 + 1;
                        int i30 = i28 + 1;
                        bArr2[i3 + i27] = bArr2[i3 + i28];
                        while (j6 != 0) {
                            int i31 = i29 + 1;
                            int i32 = i30 + 1;
                            bArr2[i3 + i29] = bArr2[i3 + i30];
                            j6--;
                            i29 = i31;
                            i30 = i32;
                        }
                        i16 = i11;
                        i5 = i29;
                    }
                    z2 = z;
                } else {
                    int i33 = i15;
                    long j8 = j3 + 1;
                    if (((long) i33) + j8 > ((long) i13)) {
                        return 0;
                    }
                    if (((long) i16) + j8 > ((long) i12)) {
                        return 0;
                    }
                    int i34 = i33 + 1;
                    int i35 = i16 + 1;
                    bArr2[i3 + i33] = bArr[i + i16];
                    long j9 = j8 - 1;
                    while (j9 != 0) {
                        int i36 = i5 + 1;
                        int i37 = i35 + 1;
                        bArr2[i3 + i5] = bArr[i + i35];
                        j9--;
                        i34 = i36;
                        i35 = i37;
                    }
                    boolean z3 = i35 < i12;
                    if (z3) {
                        j2 = (long) (bArr[i + i35] & 255);
                        i6 = i35 + 1;
                    } else {
                        j2 = j9;
                        i6 = i35;
                    }
                    z2 = z3;
                }
                if (!z2) {
                    return i5;
                }
                i15 = i5;
                j3 = j;
                c = 5;
            }
        } else {
            throw new DecompressionException(String.format("invalid level: %d (expected: %d or %d)", new Object[]{Integer.valueOf(i14), Integer.valueOf(1), Integer.valueOf(2)}));
        }
    }

    private static int hashFunction(byte[] bArr, int i) {
        int readU16 = readU16(bArr, i);
        return ((readU16(bArr, i + 1) ^ (readU16 >> 3)) ^ readU16) & 8191;
    }

    private static int readU16(byte[] bArr, int i) {
        int i2 = i + 1;
        if (i2 >= bArr.length) {
            return bArr[i] & 255;
        }
        return (bArr[i] & 255) | ((bArr[i2] & 255) << 8);
    }

    private FastLz() {
    }
}
