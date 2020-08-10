package com.google.android.gms.internal.firebase_remote_config;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

public abstract class zzax {
    public abstract zzay zza(OutputStream outputStream, Charset charset) throws IOException;

    public abstract zzbb zza(InputStream inputStream) throws IOException;

    public abstract zzbb zza(InputStream inputStream, Charset charset) throws IOException;

    public abstract zzbb zzac(String str) throws IOException;

    public final String toString(Object obj) throws IOException {
        return zza(obj, false);
    }

    public final String zzc(Object obj) throws IOException {
        return zza(obj, true);
    }

    private final String zza(Object obj, boolean z) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        zzay zza = zza((OutputStream) byteArrayOutputStream, zzbp.UTF_8);
        if (z) {
            zza.zzax();
        }
        zza.zzd(obj);
        zza.flush();
        return byteArrayOutputStream.toString("UTF-8");
    }
}
