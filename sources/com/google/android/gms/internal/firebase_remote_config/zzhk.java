package com.google.android.gms.internal.firebase_remote_config;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public final class zzhk {
    private static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
    static final Charset UTF_8 = Charset.forName("UTF-8");
    public static final byte[] zztt;
    private static final ByteBuffer zztu;
    private static final zzgi zztv;

    static boolean zzf(zzio zzio) {
        return false;
    }

    public static int zzg(boolean z) {
        return z ? 1231 : 1237;
    }

    public static int zzo(long j) {
        return (int) (j ^ (j >>> 32));
    }

    static <T> T checkNotNull(T t) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException();
    }

    static <T> T zza(T t, String str) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(str);
    }

    public static boolean zzd(byte[] bArr) {
        return zzkj.zzd(bArr);
    }

    public static String zze(byte[] bArr) {
        return new String(bArr, UTF_8);
    }

    public static int hashCode(byte[] bArr) {
        int length = bArr.length;
        int zza = zza(length, bArr, 0, length);
        if (zza == 0) {
            return 1;
        }
        return zza;
    }

    static int zza(int i, byte[] bArr, int i2, int i3) {
        int i4 = i;
        for (int i5 = i2; i5 < i2 + i3; i5++) {
            i4 = (i4 * 31) + bArr[i5];
        }
        return i4;
    }

    static Object zzc(Object obj, Object obj2) {
        return ((zzio) obj).zzgx().zza((zzio) obj2).zzhc();
    }

    static {
        byte[] bArr = new byte[0];
        zztt = bArr;
        zztu = ByteBuffer.wrap(bArr);
        byte[] bArr2 = zztt;
        zztv = zzgi.zza(bArr2, 0, bArr2.length, false);
    }
}
