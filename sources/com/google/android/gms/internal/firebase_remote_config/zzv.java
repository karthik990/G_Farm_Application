package com.google.android.gms.internal.firebase_remote_config;

import java.io.IOException;
import java.io.OutputStream;

public final class zzv implements zzcl {
    private final zzcl zzan;
    private final zzu zzao;

    public zzv(zzcl zzcl, zzu zzu) {
        this.zzan = (zzcl) zzds.checkNotNull(zzcl);
        this.zzao = (zzu) zzds.checkNotNull(zzu);
    }

    public final void writeTo(OutputStream outputStream) throws IOException {
        this.zzao.zza(this.zzan, outputStream);
    }
}
