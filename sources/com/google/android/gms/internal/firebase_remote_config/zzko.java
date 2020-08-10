package com.google.android.gms.internal.firebase_remote_config;

import com.google.android.exoplayer2.extractor.p040ts.PsExtractor;

final class zzko extends zzkl {
    zzko() {
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0035, code lost:
        if (com.google.android.gms.internal.firebase_remote_config.zzkh.zza(r1, r8) > -65) goto L_0x0037;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0061, code lost:
        if (com.google.android.gms.internal.firebase_remote_config.zzkh.zza(r1, r8) > -65) goto L_0x0063;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x0103, code lost:
        return -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x012a, code lost:
        return -1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int zzb(int r24, byte[] r25, int r26, int r27) {
        /*
            r23 = this;
            r0 = r24
            r1 = r25
            r2 = r26
            r3 = r27
            r4 = r2 | r3
            int r5 = r1.length
            int r5 = r5 - r3
            r4 = r4 | r5
            r7 = 0
            if (r4 < 0) goto L_0x015e
            long r8 = (long) r2
            long r2 = (long) r3
            r4 = -19
            r10 = -62
            r11 = -16
            r12 = 16
            r13 = -96
            r14 = -32
            r15 = -1
            r6 = -65
            r16 = 1
            if (r0 == 0) goto L_0x00ad
            int r18 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1))
            if (r18 < 0) goto L_0x002a
            return r0
        L_0x002a:
            byte r5 = (byte) r0
            if (r5 >= r14) goto L_0x0038
            if (r5 < r10) goto L_0x0037
            long r19 = r8 + r16
            byte r0 = com.google.android.gms.internal.firebase_remote_config.zzkh.zza(r1, r8)
            if (r0 <= r6) goto L_0x00af
        L_0x0037:
            return r15
        L_0x0038:
            if (r5 >= r11) goto L_0x0064
            int r0 = r0 >> 8
            r0 = r0 ^ r15
            byte r0 = (byte) r0
            if (r0 != 0) goto L_0x0051
            long r19 = r8 + r16
            byte r0 = com.google.android.gms.internal.firebase_remote_config.zzkh.zza(r1, r8)
            int r8 = (r19 > r2 ? 1 : (r19 == r2 ? 0 : -1))
            if (r8 < 0) goto L_0x004f
            int r0 = com.google.android.gms.internal.firebase_remote_config.zzkj.zzt(r5, r0)
            return r0
        L_0x004f:
            r8 = r19
        L_0x0051:
            if (r0 > r6) goto L_0x0063
            if (r5 != r14) goto L_0x0057
            if (r0 < r13) goto L_0x0063
        L_0x0057:
            if (r5 != r4) goto L_0x005b
            if (r0 >= r13) goto L_0x0063
        L_0x005b:
            long r19 = r8 + r16
            byte r0 = com.google.android.gms.internal.firebase_remote_config.zzkh.zza(r1, r8)
            if (r0 <= r6) goto L_0x00af
        L_0x0063:
            return r15
        L_0x0064:
            int r19 = r0 >> 8
            r4 = r19 ^ -1
            byte r4 = (byte) r4
            if (r4 != 0) goto L_0x007e
            long r19 = r8 + r16
            byte r4 = com.google.android.gms.internal.firebase_remote_config.zzkh.zza(r1, r8)
            int r0 = (r19 > r2 ? 1 : (r19 == r2 ? 0 : -1))
            if (r0 < 0) goto L_0x007a
            int r0 = com.google.android.gms.internal.firebase_remote_config.zzkj.zzt(r5, r4)
            return r0
        L_0x007a:
            r8 = r19
            r0 = 0
            goto L_0x0080
        L_0x007e:
            int r0 = r0 >> r12
            byte r0 = (byte) r0
        L_0x0080:
            if (r0 != 0) goto L_0x0093
            long r19 = r8 + r16
            byte r0 = com.google.android.gms.internal.firebase_remote_config.zzkh.zza(r1, r8)
            int r8 = (r19 > r2 ? 1 : (r19 == r2 ? 0 : -1))
            if (r8 < 0) goto L_0x0091
            int r0 = com.google.android.gms.internal.firebase_remote_config.zzkj.zzd(r5, r4, r0)
            return r0
        L_0x0091:
            r8 = r19
        L_0x0093:
            if (r4 > r6) goto L_0x00ac
            int r5 = r5 << 28
            int r4 = r4 + 112
            int r5 = r5 + r4
            int r4 = r5 >> 30
            if (r4 != 0) goto L_0x00ac
            if (r0 > r6) goto L_0x00ac
            long r4 = r8 + r16
            byte r0 = com.google.android.gms.internal.firebase_remote_config.zzkh.zza(r1, r8)
            if (r0 <= r6) goto L_0x00a9
            goto L_0x00ac
        L_0x00a9:
            r19 = r4
            goto L_0x00af
        L_0x00ac:
            return r15
        L_0x00ad:
            r19 = r8
        L_0x00af:
            long r2 = r2 - r19
            int r0 = (int) r2
            if (r0 >= r12) goto L_0x00b6
            r2 = 0
            goto L_0x00c9
        L_0x00b6:
            r3 = r19
            r2 = 0
        L_0x00b9:
            if (r2 >= r0) goto L_0x00c8
            long r8 = r3 + r16
            byte r3 = com.google.android.gms.internal.firebase_remote_config.zzkh.zza(r1, r3)
            if (r3 >= 0) goto L_0x00c4
            goto L_0x00c9
        L_0x00c4:
            int r2 = r2 + 1
            r3 = r8
            goto L_0x00b9
        L_0x00c8:
            r2 = r0
        L_0x00c9:
            int r0 = r0 - r2
            long r2 = (long) r2
            long r19 = r19 + r2
        L_0x00cd:
            r2 = r19
            r4 = 0
        L_0x00d0:
            if (r0 <= 0) goto L_0x00e2
            long r4 = r2 + r16
            byte r2 = com.google.android.gms.internal.firebase_remote_config.zzkh.zza(r1, r2)
            if (r2 < 0) goto L_0x00e7
            int r0 = r0 + -1
            r21 = r4
            r4 = r2
            r2 = r21
            goto L_0x00d0
        L_0x00e2:
            r21 = r2
            r2 = r4
            r4 = r21
        L_0x00e7:
            if (r0 != 0) goto L_0x00ea
            return r7
        L_0x00ea:
            int r0 = r0 + -1
            if (r2 >= r14) goto L_0x0104
            if (r0 != 0) goto L_0x00f1
            return r2
        L_0x00f1:
            int r0 = r0 + -1
            if (r2 < r10) goto L_0x0103
            long r2 = r4 + r16
            byte r4 = com.google.android.gms.internal.firebase_remote_config.zzkh.zza(r1, r4)
            if (r4 <= r6) goto L_0x00fe
            goto L_0x0103
        L_0x00fe:
            r19 = r2
            r12 = -19
            goto L_0x00cd
        L_0x0103:
            return r15
        L_0x0104:
            if (r2 >= r11) goto L_0x012b
            r3 = 2
            if (r0 >= r3) goto L_0x010e
            int r0 = zza(r1, r2, r4, r0)
            return r0
        L_0x010e:
            int r0 = r0 + -2
            long r8 = r4 + r16
            byte r3 = com.google.android.gms.internal.firebase_remote_config.zzkh.zza(r1, r4)
            if (r3 > r6) goto L_0x012a
            if (r2 != r14) goto L_0x011c
            if (r3 < r13) goto L_0x012a
        L_0x011c:
            r12 = -19
            if (r2 != r12) goto L_0x0122
            if (r3 >= r13) goto L_0x012a
        L_0x0122:
            long r19 = r8 + r16
            byte r2 = com.google.android.gms.internal.firebase_remote_config.zzkh.zza(r1, r8)
            if (r2 <= r6) goto L_0x00cd
        L_0x012a:
            return r15
        L_0x012b:
            r3 = 3
            r12 = -19
            if (r0 >= r3) goto L_0x0135
            int r0 = zza(r1, r2, r4, r0)
            return r0
        L_0x0135:
            int r0 = r0 + -3
            long r8 = r4 + r16
            byte r3 = com.google.android.gms.internal.firebase_remote_config.zzkh.zza(r1, r4)
            if (r3 > r6) goto L_0x015d
            int r2 = r2 << 28
            int r3 = r3 + 112
            int r2 = r2 + r3
            int r2 = r2 >> 30
            if (r2 != 0) goto L_0x015d
            long r2 = r8 + r16
            byte r4 = com.google.android.gms.internal.firebase_remote_config.zzkh.zza(r1, r8)
            if (r4 > r6) goto L_0x015d
            long r4 = r2 + r16
            byte r2 = com.google.android.gms.internal.firebase_remote_config.zzkh.zza(r1, r2)
            if (r2 <= r6) goto L_0x0159
            goto L_0x015d
        L_0x0159:
            r19 = r4
            goto L_0x00cd
        L_0x015d:
            return r15
        L_0x015e:
            java.lang.ArrayIndexOutOfBoundsException r0 = new java.lang.ArrayIndexOutOfBoundsException
            r4 = 3
            java.lang.Object[] r4 = new java.lang.Object[r4]
            int r1 = r1.length
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            r4[r7] = r1
            java.lang.Integer r1 = java.lang.Integer.valueOf(r26)
            r2 = 1
            r4[r2] = r1
            java.lang.Integer r1 = java.lang.Integer.valueOf(r27)
            r2 = 2
            r4[r2] = r1
            java.lang.String r1 = "Array length=%d, index=%d, limit=%d"
            java.lang.String r1 = java.lang.String.format(r1, r4)
            r0.<init>(r1)
            goto L_0x0183
        L_0x0182:
            throw r0
        L_0x0183:
            goto L_0x0182
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_remote_config.zzko.zzb(int, byte[], int, int):int");
    }

    /* access modifiers changed from: 0000 */
    public final String zzg(byte[] bArr, int i, int i2) throws zzho {
        if ((i | i2 | ((bArr.length - i) - i2)) >= 0) {
            int i3 = i + i2;
            char[] cArr = new char[i2];
            int i4 = 0;
            while (r13 < i3) {
                byte zza = zzkh.zza(bArr, (long) r13);
                if (!zzkk.zzd(zza)) {
                    break;
                }
                i = r13 + 1;
                int i5 = i4 + 1;
                zzkk.zza(zza, cArr, i4);
                i4 = i5;
            }
            int i6 = i4;
            while (r13 < i3) {
                int i7 = r13 + 1;
                byte zza2 = zzkh.zza(bArr, (long) r13);
                if (zzkk.zzd(zza2)) {
                    int i8 = i6 + 1;
                    zzkk.zza(zza2, cArr, i6);
                    while (i7 < i3) {
                        byte zza3 = zzkh.zza(bArr, (long) i7);
                        if (!zzkk.zzd(zza3)) {
                            break;
                        }
                        i7++;
                        int i9 = i8 + 1;
                        zzkk.zza(zza3, cArr, i8);
                        i8 = i9;
                    }
                    r13 = i7;
                    i6 = i8;
                } else if (zzkk.zze(zza2)) {
                    if (i7 < i3) {
                        int i10 = i7 + 1;
                        int i11 = i6 + 1;
                        zzkk.zza(zza2, zzkh.zza(bArr, (long) i7), cArr, i6);
                        r13 = i10;
                        i6 = i11;
                    } else {
                        throw zzho.zzho();
                    }
                } else if (zzkk.zzf(zza2)) {
                    if (i7 < i3 - 1) {
                        int i12 = i7 + 1;
                        int i13 = i12 + 1;
                        int i14 = i6 + 1;
                        zzkk.zza(zza2, zzkh.zza(bArr, (long) i7), zzkh.zza(bArr, (long) i12), cArr, i6);
                        r13 = i13;
                        i6 = i14;
                    } else {
                        throw zzho.zzho();
                    }
                } else if (i7 < i3 - 2) {
                    int i15 = i7 + 1;
                    byte zza4 = zzkh.zza(bArr, (long) i7);
                    int i16 = i15 + 1;
                    int i17 = i16 + 1;
                    int i18 = i6 + 1;
                    zzkk.zza(zza2, zza4, zzkh.zza(bArr, (long) i15), zzkh.zza(bArr, (long) i16), cArr, i6);
                    r13 = i17;
                    i6 = i18 + 1;
                } else {
                    throw zzho.zzho();
                }
            }
            return new String(cArr, 0, i6);
        }
        throw new ArrayIndexOutOfBoundsException(String.format("buffer length=%d, index=%d, size=%d", new Object[]{Integer.valueOf(bArr.length), Integer.valueOf(i), Integer.valueOf(i2)}));
    }

    /* access modifiers changed from: 0000 */
    public final int zzb(CharSequence charSequence, byte[] bArr, int i, int i2) {
        char c;
        long j;
        long j2;
        long j3;
        CharSequence charSequence2 = charSequence;
        byte[] bArr2 = bArr;
        int i3 = i;
        int i4 = i2;
        long j4 = (long) i3;
        long j5 = ((long) i4) + j4;
        int length = charSequence.length();
        String str = " at index ";
        String str2 = "Failed writing ";
        if (length > i4 || bArr2.length - i4 < i3) {
            char charAt = charSequence2.charAt(length - 1);
            int i5 = i3 + i4;
            StringBuilder sb = new StringBuilder(37);
            sb.append(str2);
            sb.append(charAt);
            sb.append(str);
            sb.append(i5);
            throw new ArrayIndexOutOfBoundsException(sb.toString());
        }
        int i6 = 0;
        while (true) {
            c = 128;
            j = 1;
            if (i6 >= length) {
                break;
            }
            char charAt2 = charSequence2.charAt(i6);
            if (charAt2 >= 128) {
                break;
            }
            long j6 = 1 + j4;
            zzkh.zza(bArr2, j4, (byte) charAt2);
            i6++;
            j4 = j6;
        }
        if (i6 == length) {
            return (int) j4;
        }
        while (i6 < length) {
            char charAt3 = charSequence2.charAt(i6);
            if (charAt3 < c && j4 < j5) {
                long j7 = j4 + j;
                zzkh.zza(bArr2, j4, (byte) charAt3);
                j3 = j;
                j2 = j7;
            } else if (charAt3 < 2048 && j4 <= j5 - 2) {
                long j8 = j4 + j;
                zzkh.zza(bArr2, j4, (byte) ((charAt3 >>> 6) | 960));
                long j9 = j8 + j;
                zzkh.zza(bArr2, j8, (byte) ((charAt3 & '?') | 128));
                j2 = j9;
                j3 = j;
                i6++;
                c = 128;
                long j10 = j3;
                j4 = j2;
                j = j10;
            } else if ((charAt3 < 55296 || 57343 < charAt3) && j4 <= j5 - 3) {
                long j11 = j4 + j;
                zzkh.zza(bArr2, j4, (byte) ((charAt3 >>> 12) | 480));
                long j12 = j11 + j;
                zzkh.zza(bArr2, j11, (byte) (((charAt3 >>> 6) & 63) | 128));
                long j13 = j12 + 1;
                zzkh.zza(bArr2, j12, (byte) ((charAt3 & '?') | 128));
                j2 = j13;
                j3 = 1;
            } else if (j4 <= j5 - 4) {
                int i7 = i6 + 1;
                if (i7 != length) {
                    char charAt4 = charSequence2.charAt(i7);
                    if (Character.isSurrogatePair(charAt3, charAt4)) {
                        int codePoint = Character.toCodePoint(charAt3, charAt4);
                        long j14 = j4 + 1;
                        zzkh.zza(bArr2, j4, (byte) ((codePoint >>> 18) | PsExtractor.VIDEO_STREAM_MASK));
                        long j15 = j14 + 1;
                        zzkh.zza(bArr2, j14, (byte) (((codePoint >>> 12) & 63) | 128));
                        long j16 = j15 + 1;
                        zzkh.zza(bArr2, j15, (byte) (((codePoint >>> 6) & 63) | 128));
                        j3 = 1;
                        j2 = j16 + 1;
                        zzkh.zza(bArr2, j16, (byte) ((codePoint & 63) | 128));
                        i6 = i7;
                        i6++;
                        c = 128;
                        long j102 = j3;
                        j4 = j2;
                        j = j102;
                    } else {
                        i6 = i7;
                    }
                }
                throw new zzkn(i6 - 1, length);
            } else {
                if (55296 <= charAt3 && charAt3 <= 57343) {
                    int i8 = i6 + 1;
                    if (i8 == length || !Character.isSurrogatePair(charAt3, charSequence2.charAt(i8))) {
                        throw new zzkn(i6, length);
                    }
                }
                StringBuilder sb2 = new StringBuilder(46);
                sb2.append(str2);
                sb2.append(charAt3);
                sb2.append(str);
                sb2.append(j4);
                throw new ArrayIndexOutOfBoundsException(sb2.toString());
            }
            i6++;
            c = 128;
            long j1022 = j3;
            j4 = j2;
            j = j1022;
        }
        return (int) j4;
    }

    private static int zza(byte[] bArr, int i, long j, int i2) {
        if (i2 == 0) {
            return zzkj.zzbq(i);
        }
        if (i2 == 1) {
            return zzkj.zzt(i, zzkh.zza(bArr, j));
        }
        if (i2 == 2) {
            return zzkj.zzd(i, zzkh.zza(bArr, j), zzkh.zza(bArr, j + 1));
        }
        throw new AssertionError();
    }
}
