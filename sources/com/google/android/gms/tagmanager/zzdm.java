package com.google.android.gms.tagmanager;

import android.content.Context;
import android.provider.Settings.Secure;
import com.google.android.gms.internal.gtm.zza;
import com.google.android.gms.internal.gtm.zzl;
import java.util.Map;
import lib.android.paypal.com.magnessdk.p100a.C5985b;

final class zzdm extends zzbq {

    /* renamed from: ID */
    private static final String f1565ID = zza.MOBILE_ADWORDS_UNIQUE_ID.toString();
    private final Context zzrm;

    public zzdm(Context context) {
        super(f1565ID, new String[0]);
        this.zzrm = context;
    }

    public final boolean zzgw() {
        return true;
    }

    public final zzl zzb(Map<String, zzl> map) {
        String string = Secure.getString(this.zzrm.getContentResolver(), C5985b.f4233f);
        return string == null ? zzgj.zzkc() : zzgj.zzi(string);
    }
}
