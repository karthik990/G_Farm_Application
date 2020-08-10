package com.google.android.gms.measurement.internal;

import java.util.List;
import java.util.concurrent.Callable;

final class zzco implements Callable<List<zzfw>> {
    private final /* synthetic */ zzk zzaqn;
    private final /* synthetic */ zzby zzaqo;

    zzco(zzby zzby, zzk zzk) {
        this.zzaqo = zzby;
        this.zzaqn = zzk;
    }

    public final /* synthetic */ Object call() throws Exception {
        this.zzaqo.zzamx.zzme();
        return this.zzaqo.zzamx.zzjt().zzbl(this.zzaqn.packageName);
    }
}
