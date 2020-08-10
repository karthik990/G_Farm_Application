package com.google.android.gms.tagmanager;

import android.content.Context;
import android.provider.Settings.Secure;
import com.google.android.gms.internal.gtm.zza;
import com.google.android.gms.internal.gtm.zzl;
import java.util.Map;
import lib.android.paypal.com.magnessdk.p100a.C5985b;

final class zzbc extends zzbq {

    /* renamed from: ID */
    private static final String f1549ID = zza.DEVICE_ID.toString();
    private final Context zzrm;

    public zzbc(Context context) {
        super(f1549ID, new String[0]);
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
