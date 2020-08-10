package com.google.android.gms.internal.ads;

import com.google.android.gms.internal.ads.zzayf.zza;
import java.security.GeneralSecurityException;

public final class zzavc {
    public static final zzayf zzdht;
    private static final zzayf zzdhu = ((zzayf) ((zza) zzayf.zzaam().zza(zzdht)).zzej("TINK_HYBRID_1_1_0").zzadi());

    static {
        String str = "TinkHybridDecrypt";
        String str2 = "TinkHybridEncrypt";
        zzdht = (zzayf) ((zza) zzayf.zzaam().zza(zzaur.zzdht)).zzb(zzaub.zza(str, "HybridDecrypt", "EciesAeadHkdfPrivateKey", 0, true)).zzb(zzaub.zza(str2, "HybridEncrypt", "EciesAeadHkdfPublicKey", 0, true)).zzej("TINK_HYBRID_1_0_0").zzadi();
        try {
            zzauo.zza(str2, (zzaua<P>) new zzave<P>());
            zzauo.zza(str, (zzaua<P>) new zzavd<P>());
            zzaur.zzwk();
        } catch (GeneralSecurityException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
}
