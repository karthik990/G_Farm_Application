package com.google.android.gms.internal.ads;

import com.google.android.gms.internal.ads.zzayf.zza;
import java.security.GeneralSecurityException;

public final class zzaur {
    public static final zzayf zzdht;
    private static final zzayf zzdhu = ((zzayf) ((zza) zzayf.zzaam().zza(zzdht)).zzej("TINK_AEAD_1_1_0").zzadi());

    static {
        String str = "Aead";
        String str2 = "TinkAead";
        zzdht = (zzayf) ((zza) zzayf.zzaam().zza(zzavn.zzdht)).zzb(zzaub.zza(str2, str, "AesCtrHmacAeadKey", 0, true)).zzb(zzaub.zza(str2, str, "AesEaxKey", 0, true)).zzb(zzaub.zza(str2, str, "AesGcmKey", 0, true)).zzb(zzaub.zza(str2, str, "ChaCha20Poly1305Key", 0, true)).zzb(zzaub.zza(str2, str, "KmsAeadKey", 0, true)).zzb(zzaub.zza(str2, str, "KmsEnvelopeAeadKey", 0, true)).zzej("TINK_AEAD_1_0_0").zzadi();
        try {
            zzwk();
        } catch (GeneralSecurityException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static void zzwk() throws GeneralSecurityException {
        zzauo.zza("TinkAead", (zzaua<P>) new zzauq<P>());
        zzavn.zzwk();
    }
}
