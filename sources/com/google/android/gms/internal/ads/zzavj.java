package com.google.android.gms.internal.ads;

import com.google.android.gms.internal.ads.zzawe.zza;
import java.security.GeneralSecurityException;
import java.util.Arrays;

final class zzavj implements zzayn {
    private final String zzdic;
    private final int zzdid;
    private zzawe zzdie;
    private zzavo zzdif;
    private int zzdig;

    zzavj(zzaxn zzaxn) throws GeneralSecurityException {
        this.zzdic = zzaxn.zzyw();
        String str = "invalid KeyFormat protobuf, expected AesGcmKeyFormat";
        if (this.zzdic.equals("type.googleapis.com/google.crypto.tink.AesGcmKey")) {
            try {
                zzawg zzt = zzawg.zzt(zzaxn.zzyx());
                this.zzdie = (zzawe) zzauo.zzb(zzaxn);
                this.zzdid = zzt.getKeySize();
            } catch (zzbbu e) {
                throw new GeneralSecurityException(str, e);
            }
        } else if (this.zzdic.equals("type.googleapis.com/google.crypto.tink.AesCtrHmacAeadKey")) {
            try {
                zzavq zzj = zzavq.zzj(zzaxn.zzyx());
                this.zzdif = (zzavo) zzauo.zzb(zzaxn);
                this.zzdig = zzj.zzwr().getKeySize();
                this.zzdid = this.zzdig + zzj.zzws().getKeySize();
            } catch (zzbbu e2) {
                throw new GeneralSecurityException(str, e2);
            }
        } else {
            String str2 = "unsupported AEAD DEM key type: ";
            String valueOf = String.valueOf(this.zzdic);
            throw new GeneralSecurityException(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        }
    }

    public final zzatz zzi(byte[] bArr) throws GeneralSecurityException {
        zzbcu zzbcu;
        if (bArr.length == this.zzdid) {
            if (this.zzdic.equals("type.googleapis.com/google.crypto.tink.AesGcmKey")) {
                zzbcu = (zzawe) ((zza) zzawe.zzxk().zza(this.zzdie)).zzs(zzbah.zzc(bArr, 0, this.zzdid)).zzadi();
            } else if (this.zzdic.equals("type.googleapis.com/google.crypto.tink.AesCtrHmacAeadKey")) {
                byte[] copyOfRange = Arrays.copyOfRange(bArr, 0, this.zzdig);
                zzaxc zzaxc = (zzaxc) ((zzaxc.zza) zzaxc.zzyn().zza(this.zzdif.zzwo())).zzaf(zzbah.zzo(Arrays.copyOfRange(bArr, this.zzdig, this.zzdid))).zzadi();
                zzbcu = (zzavo) zzavo.zzwp().zzal(this.zzdif.getVersion()).zzb((zzavs) ((zzavs.zza) zzavs.zzww().zza(this.zzdif.zzwn())).zzm(zzbah.zzo(copyOfRange)).zzadi()).zzb(zzaxc).zzadi();
            } else {
                throw new GeneralSecurityException("unknown DEM key type");
            }
            return (zzatz) zzauo.zzb(this.zzdic, zzbcu);
        }
        throw new GeneralSecurityException("Symmetric key has incorrect length");
    }

    public final int zzwm() {
        return this.zzdid;
    }
}
