package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.gtm.zza;
import com.google.android.gms.internal.gtm.zzl;
import java.util.Map;

final class zzdf extends zzdy {

    /* renamed from: ID */
    private static final String f1563ID = zza.LESS_THAN.toString();

    public zzdf() {
        super(f1563ID);
    }

    /* access modifiers changed from: protected */
    public final boolean zza(zzgi zzgi, zzgi zzgi2, Map<String, zzl> map) {
        return zzgi.compareTo(zzgi2) < 0;
    }
}
