package com.google.android.gms.internal.firebase_remote_config;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class zzcf implements zzcl {
    private final Logger logger;
    private final zzcl zzan;
    private final int zzbe;
    private final Level zzge;

    public zzcf(zzcl zzcl, Logger logger2, Level level, int i) {
        this.zzan = zzcl;
        this.logger = logger2;
        this.zzge = level;
        this.zzbe = i;
    }

    /* JADX INFO: finally extract failed */
    public final void writeTo(OutputStream outputStream) throws IOException {
        zzce zzce = new zzce(outputStream, this.logger, this.zzge, this.zzbe);
        try {
            this.zzan.writeTo(zzce);
            zzce.zzcd().close();
            outputStream.flush();
        } catch (Throwable th) {
            zzce.zzcd().close();
            throw th;
        }
    }
}
