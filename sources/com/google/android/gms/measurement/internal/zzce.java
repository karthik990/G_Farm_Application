package com.google.android.gms.measurement.internal;

import java.util.List;
import java.util.concurrent.Callable;

final class zzce implements Callable<List<zzfw>> {
    private final /* synthetic */ String zzads;
    private final /* synthetic */ String zzadz;
    private final /* synthetic */ zzk zzaqn;
    private final /* synthetic */ zzby zzaqo;

    zzce(zzby zzby, zzk zzk, String str, String str2) {
        this.zzaqo = zzby;
        this.zzaqn = zzk;
        this.zzads = str;
        this.zzadz = str2;
    }

    public final /* synthetic */ Object call() throws Exception {
        this.zzaqo.zzamx.zzme();
        return this.zzaqo.zzamx.zzjt().zzb(this.zzaqn.packageName, this.zzads, this.zzadz);
    }
}
