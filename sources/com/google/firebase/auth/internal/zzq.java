package com.google.firebase.auth.internal;

import com.google.android.gms.common.internal.Preconditions;
import com.google.firebase.auth.zzv;
import com.google.firebase.auth.zzx;
import java.util.List;

public final class zzq extends zzv {
    private final zzm zztu;

    public zzq(zzm zzm) {
        Preconditions.checkNotNull(zzm);
        this.zztu = zzm;
    }

    public final List<zzx> zzdc() {
        return this.zztu.zzdc();
    }
}
