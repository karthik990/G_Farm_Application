package com.google.android.gms.measurement.internal;

import java.util.List;
import java.util.concurrent.Callable;

final class zzch implements Callable<List<zzo>> {
    private final /* synthetic */ String zzads;
    private final /* synthetic */ String zzadz;
    private final /* synthetic */ String zzagj;
    private final /* synthetic */ zzby zzaqo;

    zzch(zzby zzby, String str, String str2, String str3) {
        this.zzaqo = zzby;
        this.zzagj = str;
        this.zzads = str2;
        this.zzadz = str3;
    }

    public final /* synthetic */ Object call() throws Exception {
        this.zzaqo.zzamx.zzme();
        return this.zzaqo.zzamx.zzjt().zzc(this.zzagj, this.zzads, this.zzadz);
    }
}
