package com.google.android.gms.internal.firebase_remote_config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class zzdt {
    /* access modifiers changed from: private */
    public final int limit;
    /* access modifiers changed from: private */
    public final zzdk zzhh;
    private final boolean zzhi;
    private final zzdx zzhj;

    private zzdt(zzdx zzdx) {
        this(zzdx, false, zzdo.zzhf, Integer.MAX_VALUE);
    }

    private zzdt(zzdx zzdx, boolean z, zzdk zzdk, int i) {
        this.zzhj = zzdx;
        this.zzhi = false;
        this.zzhh = zzdk;
        this.limit = Integer.MAX_VALUE;
    }

    public static zzdt zza(zzdk zzdk) {
        zzds.checkNotNull(zzdk);
        return new zzdt(new zzdu(zzdk));
    }

    public final List<String> zza(CharSequence charSequence) {
        zzds.checkNotNull(charSequence);
        Iterator zza = this.zzhj.zza(this, charSequence);
        ArrayList arrayList = new ArrayList();
        while (zza.hasNext()) {
            arrayList.add((String) zza.next());
        }
        return Collections.unmodifiableList(arrayList);
    }
}
