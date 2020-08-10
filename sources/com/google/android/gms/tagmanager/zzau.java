package com.google.android.gms.tagmanager;

import java.util.List;

final class zzau implements Runnable {
    private final /* synthetic */ List zzagb;
    private final /* synthetic */ long zzagc;
    private final /* synthetic */ zzat zzagd;

    zzau(zzat zzat, List list, long j) {
        this.zzagd = zzat;
        this.zzagb = list;
        this.zzagc = j;
    }

    public final void run() {
        this.zzagd.zzb(this.zzagb, this.zzagc);
    }
}
