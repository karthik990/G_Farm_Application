package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.gtm.zza;
import com.google.android.gms.internal.gtm.zzb;
import com.google.android.gms.internal.gtm.zzl;
import java.util.Map;

final class zzcv extends zzbq {

    /* renamed from: ID */
    private static final String f1559ID = zza.INSTALL_REFERRER.toString();
    private static final String zzadu = zzb.COMPONENT.toString();
    private final Context zzrm;

    public zzcv(Context context) {
        super(f1559ID, new String[0]);
        this.zzrm = context;
    }

    public final boolean zzgw() {
        return true;
    }

    public final zzl zzb(Map<String, zzl> map) {
        String zze = zzcw.zze(this.zzrm, ((zzl) map.get(zzadu)) != null ? zzgj.zzc((zzl) map.get(zzadu)) : null);
        return zze != null ? zzgj.zzi(zze) : zzgj.zzkc();
    }
}
