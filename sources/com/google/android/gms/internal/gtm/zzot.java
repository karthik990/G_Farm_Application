package com.google.android.gms.internal.gtm;

import java.util.Collections;
import java.util.Map;

public final class zzot {
    private final zzl zzakg;
    private final Map<String, zzl> zzats;

    private zzot(Map<String, zzl> map, zzl zzl) {
        this.zzats = map;
        this.zzakg = zzl;
    }

    public static zzou zzml() {
        return new zzou();
    }

    public final void zza(String str, zzl zzl) {
        this.zzats.put(str, zzl);
    }

    public final Map<String, zzl> zzlu() {
        return Collections.unmodifiableMap(this.zzats);
    }

    public final zzl zzji() {
        return this.zzakg;
    }

    public final String toString() {
        String valueOf = String.valueOf(Collections.unmodifiableMap(this.zzats));
        String valueOf2 = String.valueOf(this.zzakg);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 32 + String.valueOf(valueOf2).length());
        sb.append("Properties: ");
        sb.append(valueOf);
        sb.append(" pushAfterEvaluate: ");
        sb.append(valueOf2);
        return sb.toString();
    }
}
