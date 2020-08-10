package com.google.android.gms.internal.firebase_remote_config;

import java.io.IOException;
import java.nio.charset.Charset;

public abstract class zzo implements zzt {
    private zzz zzag;
    private long zzah;

    protected zzo(String str) {
        this(str == null ? null : new zzz(str));
    }

    public final boolean zzo() {
        return true;
    }

    private zzo(zzz zzz) {
        this.zzah = -1;
        this.zzag = zzz;
    }

    public final long getLength() throws IOException {
        if (this.zzah == -1) {
            this.zzah = zzca.zzb(this);
        }
        return this.zzah;
    }

    /* access modifiers changed from: protected */
    public final Charset zzn() {
        zzz zzz = this.zzag;
        return (zzz == null || zzz.zzs() == null) ? zzbp.UTF_8 : this.zzag.zzs();
    }

    public final String getType() {
        zzz zzz = this.zzag;
        if (zzz == null) {
            return null;
        }
        return zzz.zzp();
    }
}
