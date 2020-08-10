package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.internal.firebase_auth.zzjc;
import java.io.InputStream;

public abstract class zzgc<MessageType extends zzjc> implements zzjm<MessageType> {
    private static final zzhf zzvq = zzhf.zzhq();

    private final MessageType zza(InputStream inputStream, zzhf zzhf) throws zzic {
        zzgr zzgr;
        if (inputStream == null) {
            byte[] bArr = zzht.EMPTY_BYTE_ARRAY;
            zzgr = zzgr.zza(bArr, 0, bArr.length, false);
        } else {
            zzgr = new zzgw(inputStream);
        }
        MessageType messagetype = (zzjc) zza(zzgr, zzhf);
        try {
            zzgr.zzs(0);
            return messagetype;
        } catch (zzic e) {
            throw e.zzh(messagetype);
        }
    }

    public final /* synthetic */ Object zzb(InputStream inputStream, zzhf zzhf) throws zzic {
        zzkl zzkl;
        zzjc zza = zza(inputStream, zzhf);
        if (zza == null || zza.isInitialized()) {
            return zza;
        }
        if (zza instanceof zzfx) {
            zzkl = new zzkl((zzfx) zza);
        } else if (zza instanceof zzfz) {
            zzkl = new zzkl((zzfz) zza);
        } else {
            zzkl = new zzkl(zza);
        }
        throw new zzic(zzkl.getMessage()).zzh(zza);
    }
}
