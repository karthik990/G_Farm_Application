package com.google.android.gms.internal.firebase_remote_config;

import com.google.api.client.json.Json;
import java.io.IOException;
import java.io.OutputStream;

public final class zzau extends zzo {
    private final Object data;
    private final zzax zzda;
    private String zzdb;

    public zzau(zzax zzax, Object obj) {
        super(Json.MEDIA_TYPE);
        this.zzda = (zzax) zzds.checkNotNull(zzax);
        this.data = zzds.checkNotNull(obj);
    }

    public final void writeTo(OutputStream outputStream) throws IOException {
        zzay zza = this.zzda.zza(outputStream, zzn());
        if (this.zzdb != null) {
            zza.zzau();
            zza.zzad(this.zzdb);
        }
        zza.zzd(this.data);
        if (this.zzdb != null) {
            zza.zzav();
        }
        zza.flush();
    }

    public final zzau zzab(String str) {
        this.zzdb = str;
        return this;
    }
}
