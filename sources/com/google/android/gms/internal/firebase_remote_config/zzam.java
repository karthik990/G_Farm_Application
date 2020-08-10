package com.google.android.gms.internal.firebase_remote_config;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map.Entry;

public final class zzam extends zzo {
    private Object data;

    public zzam(Object obj) {
        super(zzan.MEDIA_TYPE);
        this.data = zzds.checkNotNull(obj);
    }

    public final void writeTo(OutputStream outputStream) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, zzn()));
        boolean z = true;
        for (Entry entry : zzbs.zzf(this.data).entrySet()) {
            Object value = entry.getValue();
            if (value != null) {
                String zzag = zzcr.zzag((String) entry.getKey());
                Class cls = value.getClass();
                if ((value instanceof Iterable) || cls.isArray()) {
                    for (Object zza : zzcn.zzi(value)) {
                        z = zza(z, bufferedWriter, zzag, zza);
                    }
                } else {
                    z = zza(z, bufferedWriter, zzag, value);
                }
            }
        }
        bufferedWriter.flush();
    }

    private static boolean zza(boolean z, Writer writer, String str, Object obj) throws IOException {
        if (obj != null && !zzbs.isNull(obj)) {
            if (z) {
                z = false;
            } else {
                writer.write("&");
            }
            writer.write(str);
            String zzag = zzcr.zzag(obj instanceof Enum ? zzby.zza((Enum) obj).getName() : obj.toString());
            if (zzag.length() != 0) {
                writer.write("=");
                writer.write(zzag);
            }
        }
        return z;
    }
}
