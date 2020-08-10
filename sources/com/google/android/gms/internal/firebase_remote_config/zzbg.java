package com.google.android.gms.internal.firebase_remote_config;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;

public final class zzbg extends zzax {
    public static zzbg zzbr() {
        return zzbh.zzdu;
    }

    public final zzbb zza(InputStream inputStream) {
        return zza((Reader) new InputStreamReader(inputStream, zzbp.UTF_8));
    }

    public final zzbb zza(InputStream inputStream, Charset charset) {
        if (charset == null) {
            return zza(inputStream);
        }
        return zza((Reader) new InputStreamReader(inputStream, charset));
    }

    public final zzbb zzac(String str) {
        return zza((Reader) new StringReader(str));
    }

    private final zzbb zza(Reader reader) {
        return new zzbj(this, new zzfi(reader));
    }

    public final zzay zza(OutputStream outputStream, Charset charset) {
        return new zzbi(this, new zzfl(new OutputStreamWriter(outputStream, charset)));
    }
}
