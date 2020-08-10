package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.gtm.zzi;
import com.google.android.gms.internal.gtm.zzk;
import com.google.android.gms.internal.gtm.zzop;

final class zzad implements zzdh<zzop> {
    private final /* synthetic */ zzy zzafg;

    private zzad(zzy zzy) {
        this.zzafg = zzy;
    }

    public final void zzhj() {
    }

    public final void zzs(int i) {
        if (!this.zzafg.zzafb) {
            this.zzafg.zzk(0);
        }
    }

    public final /* synthetic */ void zze(Object obj) {
        zzk zzk;
        zzop zzop = (zzop) obj;
        if (zzop.zzauy != null) {
            zzk = zzop.zzauy;
        } else {
            zzi zzi = zzop.zzqk;
            zzk zzk2 = new zzk();
            zzk2.zzqk = zzi;
            zzk2.zzqj = null;
            zzk2.zzql = zzi.version;
            zzk = zzk2;
        }
        this.zzafg.zza(zzk, zzop.zzaux, true);
    }

    /* synthetic */ zzad(zzy zzy, zzz zzz) {
        this(zzy);
    }
}
