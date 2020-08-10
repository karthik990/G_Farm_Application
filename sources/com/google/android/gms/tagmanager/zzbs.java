package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.gtm.zza;
import com.google.android.gms.internal.gtm.zzl;
import java.util.Map;

final class zzbs extends zzdy {

    /* renamed from: ID */
    private static final String f1555ID = zza.GREATER_EQUALS.toString();

    public zzbs() {
        super(f1555ID);
    }

    /* access modifiers changed from: protected */
    public final boolean zza(zzgi zzgi, zzgi zzgi2, Map<String, zzl> map) {
        return zzgi.compareTo(zzgi2) >= 0;
    }
}
