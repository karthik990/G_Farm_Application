package com.google.android.gms.internal.gtm;

import com.google.android.gms.tagmanager.zzgj;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class zzow {
    private String version;
    private final List<zzox> zzatq;
    private final Map<String, List<zzot>> zzatr;
    private int zzpw;

    private zzow() {
        this.zzatq = new ArrayList();
        this.zzatr = new HashMap();
        this.version = "";
        this.zzpw = 0;
    }

    public final zzow zzb(zzox zzox) {
        this.zzatq.add(zzox);
        return this;
    }

    public final zzow zzc(zzot zzot) {
        String zzc = zzgj.zzc((zzl) zzot.zzlu().get(zzb.INSTANCE_NAME.toString()));
        List list = (List) this.zzatr.get(zzc);
        if (list == null) {
            list = new ArrayList();
            this.zzatr.put(zzc, list);
        }
        list.add(zzot);
        return this;
    }

    public final zzow zzcs(String str) {
        this.version = str;
        return this;
    }

    public final zzow zzaf(int i) {
        this.zzpw = i;
        return this;
    }

    public final zzov zzmp() {
        zzov zzov = new zzov(this.zzatq, this.zzatr, this.version, this.zzpw);
        return zzov;
    }
}
