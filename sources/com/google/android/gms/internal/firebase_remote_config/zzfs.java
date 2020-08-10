package com.google.android.gms.internal.firebase_remote_config;

import com.google.common.base.Ascii;
import java.io.IOException;
import org.objectweb.asm.Opcodes;

final class zzfs {
    static int zza(byte[] bArr, int i, zzft zzft) {
        int i2 = i + 1;
        byte b = bArr[i];
        if (b < 0) {
            return zza((int) b, bArr, i2, zzft);
        }
        zzft.zzoj = b;
        return i2;
    }

    static int zza(int i, byte[] bArr, int i2, zzft zzft) {
        int i3 = i & Opcodes.LAND;
        int i4 = i2 + 1;
        byte b = bArr[i2];
        if (b >= 0) {
            zzft.zzoj = i3 | (b << 7);
            return i4;
        }
        int i5 = i3 | ((b & Byte.MAX_VALUE) << 7);
        int i6 = i4 + 1;
        byte b2 = bArr[i4];
        if (b2 >= 0) {
            zzft.zzoj = i5 | (b2 << Ascii.f1876SO);
            return i6;
        }
        int i7 = i5 | ((b2 & Byte.MAX_VALUE) << Ascii.f1876SO);
        int i8 = i6 + 1;
        byte b3 = bArr[i6];
        if (b3 >= 0) {
            zzft.zzoj = i7 | (b3 << Ascii.NAK);
            return i8;
        }
        int i9 = i7 | ((b3 & Byte.MAX_VALUE) << Ascii.NAK);
        int i10 = i8 + 1;
        byte b4 = bArr[i8];
        if (b4 >= 0) {
            zzft.zzoj = i9 | (b4 << Ascii.f1869FS);
            return i10;
        }
        int i11 = i9 | ((b4 & Byte.MAX_VALUE) << Ascii.f1869FS);
        while (true) {
            int i12 = i10 + 1;
            if (bArr[i10] >= 0) {
                zzft.zzoj = i11;
                return i12;
            }
            i10 = i12;
        }
    }

    static int zzb(byte[] bArr, int i, zzft zzft) {
        int i2 = i + 1;
        long j = (long) bArr[i];
        if (j >= 0) {
            zzft.zzok = j;
            return i2;
        }
        long j2 = j & 127;
        int i3 = i2 + 1;
        byte b = bArr[i2];
        long j3 = j2 | (((long) (b & Byte.MAX_VALUE)) << 7);
        int i4 = 7;
        while (b < 0) {
            int i5 = i3 + 1;
            byte b2 = bArr[i3];
            i4 += 7;
            j3 |= ((long) (b2 & Byte.MAX_VALUE)) << i4;
            int i6 = i5;
            b = b2;
            i3 = i6;
        }
        zzft.zzok = j3;
        return i3;
    }

    static int zza(byte[] bArr, int i) {
        return ((bArr[i + 3] & 255) << Ascii.CAN) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << Ascii.DLE);
    }

    static long zzb(byte[] bArr, int i) {
        return ((((long) bArr[i + 7]) & 255) << 56) | (((long) bArr[i]) & 255) | ((((long) bArr[i + 1]) & 255) << 8) | ((((long) bArr[i + 2]) & 255) << 16) | ((((long) bArr[i + 3]) & 255) << 24) | ((((long) bArr[i + 4]) & 255) << 32) | ((((long) bArr[i + 5]) & 255) << 40) | ((((long) bArr[i + 6]) & 255) << 48);
    }

    static double zzc(byte[] bArr, int i) {
        return Double.longBitsToDouble(zzb(bArr, i));
    }

    static float zzd(byte[] bArr, int i) {
        return Float.intBitsToFloat(zza(bArr, i));
    }

    static int zzc(byte[] bArr, int i, zzft zzft) throws zzho {
        int zza = zza(bArr, i, zzft);
        int i2 = zzft.zzoj;
        if (i2 < 0) {
            throw zzho.zzhh();
        } else if (i2 == 0) {
            zzft.zzol = "";
            return zza;
        } else {
            zzft.zzol = new String(bArr, zza, i2, zzhk.UTF_8);
            return zza + i2;
        }
    }

    static int zzd(byte[] bArr, int i, zzft zzft) throws zzho {
        int zza = zza(bArr, i, zzft);
        int i2 = zzft.zzoj;
        if (i2 < 0) {
            throw zzho.zzhh();
        } else if (i2 == 0) {
            zzft.zzol = "";
            return zza;
        } else {
            zzft.zzol = zzkj.zzg(bArr, zza, i2);
            return zza + i2;
        }
    }

    static int zze(byte[] bArr, int i, zzft zzft) throws zzho {
        int zza = zza(bArr, i, zzft);
        int i2 = zzft.zzoj;
        if (i2 < 0) {
            throw zzho.zzhh();
        } else if (i2 > bArr.length - zza) {
            throw zzho.zzhg();
        } else if (i2 == 0) {
            zzft.zzol = zzfw.zzop;
            return zza;
        } else {
            zzft.zzol = zzfw.zzb(bArr, zza, i2);
            return zza + i2;
        }
    }

    /* JADX WARNING: type inference failed for: r8v2, types: [int] */
    /* JADX WARNING: type inference failed for: r8v5 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static int zza(com.google.android.gms.internal.firebase_remote_config.zzjj r6, byte[] r7, int r8, int r9, com.google.android.gms.internal.firebase_remote_config.zzft r10) throws java.io.IOException {
        /*
            int r0 = r8 + 1
            byte r8 = r7[r8]
            if (r8 >= 0) goto L_0x000c
            int r0 = zza(r8, r7, r0, r10)
            int r8 = r10.zzoj
        L_0x000c:
            r3 = r0
            if (r8 < 0) goto L_0x0025
            int r9 = r9 - r3
            if (r8 > r9) goto L_0x0025
            java.lang.Object r9 = r6.newInstance()
            int r8 = r8 + r3
            r0 = r6
            r1 = r9
            r2 = r7
            r4 = r8
            r5 = r10
            r0.zza(r1, r2, r3, r4, r5)
            r6.zzm(r9)
            r10.zzol = r9
            return r8
        L_0x0025:
            com.google.android.gms.internal.firebase_remote_config.zzho r6 = com.google.android.gms.internal.firebase_remote_config.zzho.zzhg()
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_remote_config.zzfs.zza(com.google.android.gms.internal.firebase_remote_config.zzjj, byte[], int, int, com.google.android.gms.internal.firebase_remote_config.zzft):int");
    }

    static int zza(zzjj zzjj, byte[] bArr, int i, int i2, int i3, zzft zzft) throws IOException {
        zzis zzis = (zzis) zzjj;
        Object newInstance = zzis.newInstance();
        int zza = zzis.zza(newInstance, bArr, i, i2, i3, zzft);
        zzis.zzm(newInstance);
        zzft.zzol = newInstance;
        return zza;
    }

    static int zza(int i, byte[] bArr, int i2, int i3, zzhn<?> zzhn, zzft zzft) {
        zzhj zzhj = (zzhj) zzhn;
        int zza = zza(bArr, i2, zzft);
        zzhj.zzbc(zzft.zzoj);
        while (zza < i3) {
            int zza2 = zza(bArr, zza, zzft);
            if (i != zzft.zzoj) {
                break;
            }
            zza = zza(bArr, zza2, zzft);
            zzhj.zzbc(zzft.zzoj);
        }
        return zza;
    }

    static int zza(byte[] bArr, int i, zzhn<?> zzhn, zzft zzft) throws IOException {
        zzhj zzhj = (zzhj) zzhn;
        int zza = zza(bArr, i, zzft);
        int i2 = zzft.zzoj + zza;
        while (zza < i2) {
            zza = zza(bArr, zza, zzft);
            zzhj.zzbc(zzft.zzoj);
        }
        if (zza == i2) {
            return zza;
        }
        throw zzho.zzhg();
    }

    static int zza(zzjj<?> zzjj, int i, byte[] bArr, int i2, int i3, zzhn<?> zzhn, zzft zzft) throws IOException {
        int zza = zza((zzjj) zzjj, bArr, i2, i3, zzft);
        zzhn.add(zzft.zzol);
        while (zza < i3) {
            int zza2 = zza(bArr, zza, zzft);
            if (i != zzft.zzoj) {
                break;
            }
            zza = zza((zzjj) zzjj, bArr, zza2, i3, zzft);
            zzhn.add(zzft.zzol);
        }
        return zza;
    }

    static int zza(int i, byte[] bArr, int i2, int i3, zzkc zzkc, zzft zzft) throws zzho {
        if ((i >>> 3) != 0) {
            int i4 = i & 7;
            if (i4 == 0) {
                int zzb = zzb(bArr, i2, zzft);
                zzkc.zzb(i, (Object) Long.valueOf(zzft.zzok));
                return zzb;
            } else if (i4 == 1) {
                zzkc.zzb(i, (Object) Long.valueOf(zzb(bArr, i2)));
                return i2 + 8;
            } else if (i4 == 2) {
                int zza = zza(bArr, i2, zzft);
                int i5 = zzft.zzoj;
                if (i5 < 0) {
                    throw zzho.zzhh();
                } else if (i5 <= bArr.length - zza) {
                    if (i5 == 0) {
                        zzkc.zzb(i, (Object) zzfw.zzop);
                    } else {
                        zzkc.zzb(i, (Object) zzfw.zzb(bArr, zza, i5));
                    }
                    return zza + i5;
                } else {
                    throw zzho.zzhg();
                }
            } else if (i4 == 3) {
                zzkc zzjf = zzkc.zzjf();
                int i6 = (i & -8) | 4;
                int i7 = 0;
                while (true) {
                    if (i2 >= i3) {
                        break;
                    }
                    int zza2 = zza(bArr, i2, zzft);
                    int i8 = zzft.zzoj;
                    i7 = i8;
                    if (i8 == i6) {
                        i2 = zza2;
                        break;
                    }
                    int zza3 = zza(i7, bArr, zza2, i3, zzjf, zzft);
                    i7 = i8;
                    i2 = zza3;
                }
                if (i2 > i3 || i7 != i6) {
                    throw zzho.zzhn();
                }
                zzkc.zzb(i, (Object) zzjf);
                return i2;
            } else if (i4 == 5) {
                zzkc.zzb(i, (Object) Integer.valueOf(zza(bArr, i2)));
                return i2 + 4;
            } else {
                throw zzho.zzhj();
            }
        } else {
            throw zzho.zzhj();
        }
    }

    static int zza(int i, byte[] bArr, int i2, int i3, zzft zzft) throws zzho {
        if ((i >>> 3) != 0) {
            int i4 = i & 7;
            if (i4 == 0) {
                return zzb(bArr, i2, zzft);
            }
            if (i4 == 1) {
                return i2 + 8;
            }
            if (i4 == 2) {
                return zza(bArr, i2, zzft) + zzft.zzoj;
            }
            if (i4 == 3) {
                int i5 = (i & -8) | 4;
                int i6 = 0;
                while (i2 < i3) {
                    i2 = zza(bArr, i2, zzft);
                    i6 = zzft.zzoj;
                    if (i6 == i5) {
                        break;
                    }
                    i2 = zza(i6, bArr, i2, i3, zzft);
                }
                if (i2 <= i3 && i6 == i5) {
                    return i2;
                }
                throw zzho.zzhn();
            } else if (i4 == 5) {
                return i2 + 4;
            } else {
                throw zzho.zzhj();
            }
        } else {
            throw zzho.zzhj();
        }
    }
}
