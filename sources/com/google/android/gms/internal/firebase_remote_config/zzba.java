package com.google.android.gms.internal.firebase_remote_config;

import java.util.Collection;
import java.util.HashSet;

public final class zzba {
    final zzax zzda;
    Collection<String> zzdd = new HashSet();

    public zzba(zzax zzax) {
        this.zzda = (zzax) zzds.checkNotNull(zzax);
    }

    public final zzaz zzaz() {
        return new zzaz(this);
    }

    public final zzba zza(Collection<String> collection) {
        this.zzdd = collection;
        return this;
    }
}
