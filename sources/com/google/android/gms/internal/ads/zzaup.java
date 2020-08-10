package com.google.android.gms.internal.ads;

import com.google.android.gms.internal.ads.zzaxr.zzb;
import com.google.android.gms.internal.ads.zzaxt.zza;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;

final class zzaup {
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    public static zzaxt zzb(zzaxr zzaxr) {
        zza zzbb = zzaxt.zzzu().zzbb(zzaxr.zzzk());
        for (zzb zzb : zzaxr.zzzl()) {
            zzbb.zzb((zzaxt.zzb) zzaxt.zzb.zzzw().zzeh(zzb.zzzp().zzyw()).zzb(zzb.zzzq()).zzb(zzb.zzzs()).zzbd(zzb.zzzr()).zzadi());
        }
        return (zzaxt) zzbb.zzadi();
    }

    public static void zzc(zzaxr zzaxr) throws GeneralSecurityException {
        if (zzaxr.zzzm() != 0) {
            int zzzk = zzaxr.zzzk();
            boolean z = false;
            boolean z2 = true;
            for (zzb zzb : zzaxr.zzzl()) {
                if (!zzb.zzzo()) {
                    throw new GeneralSecurityException(String.format("key %d has no key data", new Object[]{Integer.valueOf(zzb.zzzr())}));
                } else if (zzb.zzzs() == zzayd.UNKNOWN_PREFIX) {
                    throw new GeneralSecurityException(String.format("key %d has unknown prefix", new Object[]{Integer.valueOf(zzb.zzzr())}));
                } else if (zzb.zzzq() != zzaxl.UNKNOWN_STATUS) {
                    if (zzb.zzzq() == zzaxl.ENABLED && zzb.zzzr() == zzzk) {
                        if (!z) {
                            z = true;
                        } else {
                            throw new GeneralSecurityException("keyset contains multiple primary keys");
                        }
                    }
                    if (zzb.zzzp().zzyy() != zzaxi.zzb.ASYMMETRIC_PUBLIC) {
                        z2 = false;
                    }
                } else {
                    throw new GeneralSecurityException(String.format("key %d has unknown status", new Object[]{Integer.valueOf(zzb.zzzr())}));
                }
            }
            if (!z && !z2) {
                throw new GeneralSecurityException("keyset doesn't contain a valid primary key");
            }
            return;
        }
        throw new GeneralSecurityException("empty keyset");
    }
}
