package com.google.android.gms.internal.ads;

import android.os.Bundle;
import java.util.Iterator;

final class zzakf extends zzakg {
    private final /* synthetic */ zzakd zzcrh;
    private final /* synthetic */ Bundle zzcri;

    zzakf(zzakd zzakd, Bundle bundle) {
        this.zzcrh = zzakd;
        this.zzcri = bundle;
        super(null);
    }

    public final void zzdn() {
        Iterator it = this.zzcrh.zzcqv.iterator();
        while (it.hasNext()) {
            ((zzakh) it.next()).zzd(this.zzcri);
        }
    }
}
