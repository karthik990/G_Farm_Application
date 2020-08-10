package com.google.android.gms.internal.firebase_remote_config;

import com.google.android.exoplayer2.extractor.p040ts.PsExtractor;

final class zzkm extends zzkl {
    zzkm() {
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0043, code lost:
        if (r13[r14] > -65) goto L_0x0045;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x007e, code lost:
        if (r13[r14] > -65) goto L_0x0080;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x001c, code lost:
        if (r13[r14] > -65) goto L_0x001e;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int zzb(int r12, byte[] r13, int r14, int r15) {
        /*
            r11 = this;
            r0 = -19
            r1 = -62
            r2 = -16
            r3 = 0
            r4 = -96
            r5 = -32
            r6 = -1
            r7 = -65
            if (r12 == 0) goto L_0x0081
            if (r14 < r15) goto L_0x0013
            return r12
        L_0x0013:
            byte r8 = (byte) r12
            if (r8 >= r5) goto L_0x001f
            if (r8 < r1) goto L_0x001e
            int r12 = r14 + 1
            byte r14 = r13[r14]
            if (r14 <= r7) goto L_0x0082
        L_0x001e:
            return r6
        L_0x001f:
            if (r8 >= r2) goto L_0x0046
            int r12 = r12 >> 8
            r12 = r12 ^ r6
            byte r12 = (byte) r12
            if (r12 != 0) goto L_0x0035
            int r12 = r14 + 1
            byte r14 = r13[r14]
            if (r12 < r15) goto L_0x0032
            int r12 = com.google.android.gms.internal.firebase_remote_config.zzkj.zzt(r8, r14)
            return r12
        L_0x0032:
            r10 = r14
            r14 = r12
            r12 = r10
        L_0x0035:
            if (r12 > r7) goto L_0x0045
            if (r8 != r5) goto L_0x003b
            if (r12 < r4) goto L_0x0045
        L_0x003b:
            if (r8 != r0) goto L_0x003f
            if (r12 >= r4) goto L_0x0045
        L_0x003f:
            int r12 = r14 + 1
            byte r14 = r13[r14]
            if (r14 <= r7) goto L_0x0082
        L_0x0045:
            return r6
        L_0x0046:
            int r9 = r12 >> 8
            r9 = r9 ^ r6
            byte r9 = (byte) r9
            if (r9 != 0) goto L_0x005a
            int r12 = r14 + 1
            byte r9 = r13[r14]
            if (r12 < r15) goto L_0x0057
            int r12 = com.google.android.gms.internal.firebase_remote_config.zzkj.zzt(r8, r9)
            return r12
        L_0x0057:
            r14 = r12
            r12 = 0
            goto L_0x005d
        L_0x005a:
            int r12 = r12 >> 16
            byte r12 = (byte) r12
        L_0x005d:
            if (r12 != 0) goto L_0x006d
            int r12 = r14 + 1
            byte r14 = r13[r14]
            if (r12 < r15) goto L_0x006a
            int r12 = com.google.android.gms.internal.firebase_remote_config.zzkj.zzd(r8, r9, r14)
            return r12
        L_0x006a:
            r10 = r14
            r14 = r12
            r12 = r10
        L_0x006d:
            if (r9 > r7) goto L_0x0080
            int r8 = r8 << 28
            int r9 = r9 + 112
            int r8 = r8 + r9
            int r8 = r8 >> 30
            if (r8 != 0) goto L_0x0080
            if (r12 > r7) goto L_0x0080
            int r12 = r14 + 1
            byte r14 = r13[r14]
            if (r14 <= r7) goto L_0x0082
        L_0x0080:
            return r6
        L_0x0081:
            r12 = r14
        L_0x0082:
            if (r12 >= r15) goto L_0x008b
            byte r14 = r13[r12]
            if (r14 < 0) goto L_0x008b
            int r12 = r12 + 1
            goto L_0x0082
        L_0x008b:
            if (r12 < r15) goto L_0x008e
            return r3
        L_0x008e:
            if (r12 < r15) goto L_0x0091
            return r3
        L_0x0091:
            int r14 = r12 + 1
            byte r12 = r13[r12]
            if (r12 >= 0) goto L_0x00ea
            if (r12 >= r5) goto L_0x00a5
            if (r14 < r15) goto L_0x009c
            return r12
        L_0x009c:
            if (r12 < r1) goto L_0x00a4
            int r12 = r14 + 1
            byte r14 = r13[r14]
            if (r14 <= r7) goto L_0x008e
        L_0x00a4:
            return r6
        L_0x00a5:
            if (r12 >= r2) goto L_0x00c5
            int r8 = r15 + -1
            if (r14 < r8) goto L_0x00b0
            int r12 = com.google.android.gms.internal.firebase_remote_config.zzkj.zzf(r13, r14, r15)
            return r12
        L_0x00b0:
            int r8 = r14 + 1
            byte r14 = r13[r14]
            if (r14 > r7) goto L_0x00c4
            if (r12 != r5) goto L_0x00ba
            if (r14 < r4) goto L_0x00c4
        L_0x00ba:
            if (r12 != r0) goto L_0x00be
            if (r14 >= r4) goto L_0x00c4
        L_0x00be:
            int r12 = r8 + 1
            byte r14 = r13[r8]
            if (r14 <= r7) goto L_0x008e
        L_0x00c4:
            return r6
        L_0x00c5:
            int r8 = r15 + -2
            if (r14 < r8) goto L_0x00ce
            int r12 = com.google.android.gms.internal.firebase_remote_config.zzkj.zzf(r13, r14, r15)
            return r12
        L_0x00ce:
            int r8 = r14 + 1
            byte r14 = r13[r14]
            if (r14 > r7) goto L_0x00e9
            int r12 = r12 << 28
            int r14 = r14 + 112
            int r12 = r12 + r14
            int r12 = r12 >> 30
            if (r12 != 0) goto L_0x00e9
            int r12 = r8 + 1
            byte r14 = r13[r8]
            if (r14 > r7) goto L_0x00e9
            int r14 = r12 + 1
            byte r12 = r13[r12]
            if (r12 <= r7) goto L_0x00ea
        L_0x00e9:
            return r6
        L_0x00ea:
            r12 = r14
            goto L_0x008e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_remote_config.zzkm.zzb(int, byte[], int, int):int");
    }

    /* access modifiers changed from: 0000 */
    public final String zzg(byte[] bArr, int i, int i2) throws zzho {
        if ((i | i2 | ((bArr.length - i) - i2)) >= 0) {
            int i3 = i + i2;
            char[] cArr = new char[i2];
            int i4 = 0;
            while (r13 < i3) {
                byte b = bArr[r13];
                if (!zzkk.zzd(b)) {
                    break;
                }
                i = r13 + 1;
                int i5 = i4 + 1;
                zzkk.zza(b, cArr, i4);
                i4 = i5;
            }
            int i6 = i4;
            while (r13 < i3) {
                int i7 = r13 + 1;
                byte b2 = bArr[r13];
                if (zzkk.zzd(b2)) {
                    int i8 = i6 + 1;
                    zzkk.zza(b2, cArr, i6);
                    while (i7 < i3) {
                        byte b3 = bArr[i7];
                        if (!zzkk.zzd(b3)) {
                            break;
                        }
                        i7++;
                        int i9 = i8 + 1;
                        zzkk.zza(b3, cArr, i8);
                        i8 = i9;
                    }
                    r13 = i7;
                    i6 = i8;
                } else if (zzkk.zze(b2)) {
                    if (i7 < i3) {
                        int i10 = i7 + 1;
                        int i11 = i6 + 1;
                        zzkk.zza(b2, bArr[i7], cArr, i6);
                        r13 = i10;
                        i6 = i11;
                    } else {
                        throw zzho.zzho();
                    }
                } else if (zzkk.zzf(b2)) {
                    if (i7 < i3 - 1) {
                        int i12 = i7 + 1;
                        int i13 = i12 + 1;
                        int i14 = i6 + 1;
                        zzkk.zza(b2, bArr[i7], bArr[i12], cArr, i6);
                        r13 = i13;
                        i6 = i14;
                    } else {
                        throw zzho.zzho();
                    }
                } else if (i7 < i3 - 2) {
                    int i15 = i7 + 1;
                    byte b4 = bArr[i7];
                    int i16 = i15 + 1;
                    int i17 = i16 + 1;
                    int i18 = i6 + 1;
                    zzkk.zza(b2, b4, bArr[i15], bArr[i16], cArr, i6);
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
        int i3;
        int length = charSequence.length();
        int i4 = i2 + i;
        int i5 = 0;
        while (i5 < length) {
            int i6 = i5 + i;
            if (i6 >= i4) {
                break;
            }
            char charAt = charSequence.charAt(i5);
            if (charAt >= 128) {
                break;
            }
            bArr[i6] = (byte) charAt;
            i5++;
        }
        if (i5 == length) {
            return i + length;
        }
        int i7 = i + i5;
        while (i5 < length) {
            char charAt2 = charSequence.charAt(i5);
            if (charAt2 < 128 && i7 < i4) {
                i3 = i7 + 1;
                bArr[i7] = (byte) charAt2;
            } else if (charAt2 < 2048 && i7 <= i4 - 2) {
                int i8 = i7 + 1;
                bArr[i7] = (byte) ((charAt2 >>> 6) | 960);
                i7 = i8 + 1;
                bArr[i8] = (byte) ((charAt2 & '?') | 128);
                i5++;
            } else if ((charAt2 < 55296 || 57343 < charAt2) && i7 <= i4 - 3) {
                int i9 = i7 + 1;
                bArr[i7] = (byte) ((charAt2 >>> 12) | 480);
                int i10 = i9 + 1;
                bArr[i9] = (byte) (((charAt2 >>> 6) & 63) | 128);
                i3 = i10 + 1;
                bArr[i10] = (byte) ((charAt2 & '?') | 128);
            } else if (i7 <= i4 - 4) {
                int i11 = i5 + 1;
                if (i11 != charSequence.length()) {
                    char charAt3 = charSequence.charAt(i11);
                    if (Character.isSurrogatePair(charAt2, charAt3)) {
                        int codePoint = Character.toCodePoint(charAt2, charAt3);
                        int i12 = i7 + 1;
                        bArr[i7] = (byte) ((codePoint >>> 18) | PsExtractor.VIDEO_STREAM_MASK);
                        int i13 = i12 + 1;
                        bArr[i12] = (byte) (((codePoint >>> 12) & 63) | 128);
                        int i14 = i13 + 1;
                        bArr[i13] = (byte) (((codePoint >>> 6) & 63) | 128);
                        i7 = i14 + 1;
                        bArr[i14] = (byte) ((codePoint & 63) | 128);
                        i5 = i11;
                        i5++;
                    } else {
                        i5 = i11;
                    }
                }
                throw new zzkn(i5 - 1, length);
            } else {
                if (55296 <= charAt2 && charAt2 <= 57343) {
                    int i15 = i5 + 1;
                    if (i15 == charSequence.length() || !Character.isSurrogatePair(charAt2, charSequence.charAt(i15))) {
                        throw new zzkn(i5, length);
                    }
                }
                StringBuilder sb = new StringBuilder(37);
                sb.append("Failed writing ");
                sb.append(charAt2);
                sb.append(" at index ");
                sb.append(i7);
                throw new ArrayIndexOutOfBoundsException(sb.toString());
            }
            i7 = i3;
            i5++;
        }
        return i7;
    }
}
