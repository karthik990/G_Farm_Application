package com.google.android.gms.internal.ads;

import com.google.android.gms.internal.ads.zzaxr.zzb;
import java.security.GeneralSecurityException;

public final class zzaul {
    @Deprecated
    public static final zzauh zzh(byte[] bArr) throws GeneralSecurityException {
        try {
            zzaxr zzj = zzaxr.zzj(bArr);
            for (zzb zzb : zzj.zzzl()) {
                if (zzb.zzzp().zzyy() == zzaxi.zzb.UNKNOWN_KEYMATERIAL || zzb.zzzp().zzyy() == zzaxi.zzb.SYMMETRIC || zzb.zzzp().zzyy() == zzaxi.zzb.ASYMMETRIC_PRIVATE) {
                    throw new GeneralSecurityException("keyset contains secret key material");
                }
            }
            return zzauh.zza(zzj);
        } catch (zzbbu unused) {
            throw new GeneralSecurityException("invalid keyset");
        }
    }
}
