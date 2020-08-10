package com.google.android.gms.internal.gtm;

import java.util.HashMap;
import java.util.Map;

public final class zzou {
    private zzl zzakg;
    private final Map<String, zzl> zzats;

    private zzou() {
        this.zzats = new HashMap();
    }

    public final zzou zzb(String str, zzl zzl) {
        this.zzats.put(str, zzl);
        return this;
    }

    public final zzou zzm(zzl zzl) {
        this.zzakg = zzl;
        return this;
    }

    public final zzot zzmm() {
        return new zzot(this.zzats, this.zzakg);
    }
}
