package com.google.android.gms.internal.firebase_remote_config;

import java.io.IOException;

public class zzaw extends zzbz implements Cloneable {
    private zzax zzda;

    public final void zza(zzax zzax) {
        this.zzda = zzax;
    }

    public String toString() {
        zzax zzax = this.zzda;
        if (zzax == null) {
            return super.toString();
        }
        try {
            return zzax.toString(this);
        } catch (IOException e) {
            throw zzea.zza(e);
        }
    }

    public final String zzar() throws IOException {
        zzax zzax = this.zzda;
        if (zzax != null) {
            return zzax.zzc(this);
        }
        return super.toString();
    }

    /* renamed from: zza */
    public zzaw clone() {
        return (zzaw) super.clone();
    }

    /* renamed from: zza */
    public zzaw zzb(String str, Object obj) {
        return (zzaw) super.zzb(str, obj);
    }

    public /* synthetic */ zzbz zzb() {
        return (zzaw) clone();
    }
}
