package com.google.android.gms.internal.firebase_remote_config;

import java.io.IOException;

public final class zzaf {
    String message;
    int statusCode;
    zzx zzbb;
    String zzbv;
    String zzby;

    public zzaf(int i, String str, zzx zzx) {
        if (i >= 0) {
            this.statusCode = i;
            this.zzbv = str;
            this.zzbb = (zzx) zzds.checkNotNull(zzx);
            return;
        }
        throw new IllegalArgumentException();
    }

    public zzaf(zzad zzad) {
        this(zzad.getStatusCode(), zzad.getStatusMessage(), zzad.zzy());
        try {
            this.zzby = zzad.zzaf();
            if (this.zzby.length() == 0) {
                this.zzby = null;
            }
        } catch (IOException e) {
            zzeb.zzb(e);
        }
        StringBuilder zzc = zzae.zzc(zzad);
        if (this.zzby != null) {
            zzc.append(zzcm.zzgh);
            zzc.append(this.zzby);
        }
        this.message = zzc.toString();
    }

    public final zzaf zzx(String str) {
        this.message = str;
        return this;
    }

    public final zzaf zzy(String str) {
        this.zzby = str;
        return this;
    }
}
