package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.gtm.zza;
import com.google.android.gms.internal.gtm.zzl;
import java.util.Map;

final class zzde extends zzdy {

    /* renamed from: ID */
    private static final String f1562ID = zza.LESS_EQUALS.toString();

    public zzde() {
        super(f1562ID);
    }

    /* access modifiers changed from: protected */
    public final boolean zza(zzgi zzgi, zzgi zzgi2, Map<String, zzl> map) {
        return zzgi.compareTo(zzgi2) <= 0;
    }
}