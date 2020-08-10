package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.gtm.zza;
import com.google.android.gms.internal.gtm.zzl;
import java.util.Map;

final class zzbl extends zzfz {

    /* renamed from: ID */
    private static final String f1552ID = zza.ENDS_WITH.toString();

    public zzbl() {
        super(f1552ID);
    }

    /* access modifiers changed from: protected */
    public final boolean zza(String str, String str2, Map<String, zzl> map) {
        return str.endsWith(str2);
    }
}
