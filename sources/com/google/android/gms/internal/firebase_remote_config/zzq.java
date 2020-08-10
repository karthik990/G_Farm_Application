package com.google.android.gms.internal.firebase_remote_config;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;
import p043io.fabric.sdk.android.services.network.HttpRequest;

public final class zzq implements zzu {
    public final String getName() {
        return HttpRequest.ENCODING_GZIP;
    }

    public final void zza(zzcl zzcl, OutputStream outputStream) throws IOException {
        GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(new zzr(this, outputStream));
        zzcl.writeTo(gZIPOutputStream);
        gZIPOutputStream.close();
    }
}
