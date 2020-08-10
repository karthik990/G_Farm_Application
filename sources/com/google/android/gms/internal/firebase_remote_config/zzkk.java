package com.google.android.gms.internal.firebase_remote_config;

import com.fasterxml.jackson.core.base.GeneratorBase;
import com.google.common.base.Ascii;

final class zzkk {
    /* access modifiers changed from: private */
    public static boolean zzd(byte b) {
        return b >= 0;
    }

    /* access modifiers changed from: private */
    public static boolean zze(byte b) {
        return b < -32;
    }

    /* access modifiers changed from: private */
    public static boolean zzf(byte b) {
        return b < -16;
    }

    private static boolean zzg(byte b) {
        return b > -65;
    }

    /* access modifiers changed from: private */
    public static void zza(byte b, char[] cArr, int i) {
        cArr[i] = (char) b;
    }

    /* access modifiers changed from: private */
    public static void zza(byte b, byte b2, char[] cArr, int i) throws zzho {
        if (b < -62 || zzg(b2)) {
            throw zzho.zzho();
        }
        cArr[i] = (char) (((b & Ascii.f1878US) << 6) | (b2 & 63));
    }

    /* access modifiers changed from: private */
    public static void zza(byte b, byte b2, byte b3, char[] cArr, int i) throws zzho {
        if (zzg(b2) || ((b == -32 && b2 < -96) || ((b == -19 && b2 >= -96) || zzg(b3)))) {
            throw zzho.zzho();
        }
        cArr[i] = (char) (((b & Ascii.f1875SI) << Ascii.f1868FF) | ((b2 & 63) << 6) | (b3 & 63));
    }

    /* access modifiers changed from: private */
    public static void zza(byte b, byte b2, byte b3, byte b4, char[] cArr, int i) throws zzho {
        if (zzg(b2) || (((b << Ascii.f1869FS) + (b2 + 112)) >> 30) != 0 || zzg(b3) || zzg(b4)) {
            throw zzho.zzho();
        }
        byte b5 = ((b & 7) << Ascii.DC2) | ((b2 & 63) << Ascii.f1868FF) | ((b3 & 63) << 6) | (b4 & 63);
        cArr[i] = (char) ((b5 >>> 10) + 55232);
        cArr[i + 1] = (char) ((b5 & 1023) + GeneratorBase.SURR2_FIRST);
    }
}
