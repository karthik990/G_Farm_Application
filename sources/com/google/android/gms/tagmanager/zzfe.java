package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.gtm.zzot;
import com.google.android.gms.internal.gtm.zzox;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class zzfe implements zzfg {
    private final /* synthetic */ Map zzakb;
    private final /* synthetic */ Map zzakc;
    private final /* synthetic */ Map zzakd;
    private final /* synthetic */ Map zzake;

    zzfe(zzfb zzfb, Map map, Map map2, Map map3, Map map4) {
        this.zzakb = map;
        this.zzakc = map2;
        this.zzakd = map3;
        this.zzake = map4;
    }

    public final void zza(zzox zzox, Set<zzot> set, Set<zzot> set2, zzeq zzeq) {
        List list = (List) this.zzakb.get(zzox);
        this.zzakc.get(zzox);
        if (list != null) {
            set.addAll(list);
            zzeq.zzio();
        }
        List list2 = (List) this.zzakd.get(zzox);
        this.zzake.get(zzox);
        if (list2 != null) {
            set2.addAll(list2);
            zzeq.zzip();
        }
    }
}
