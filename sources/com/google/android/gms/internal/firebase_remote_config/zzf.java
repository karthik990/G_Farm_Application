package com.google.android.gms.internal.firebase_remote_config;

import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import java.io.IOException;

public class zzf<T> extends zzbz {
    private final zze zzq;
    private final String zzr;
    private final String zzs;
    private final zzt zzt;
    private zzx zzu = new zzx();
    private zzx zzv;
    private int zzw = -1;
    private String zzx;
    private Class<T> zzy;

    protected zzf(zze zze, String str, String str2, zzt zzt2, Class<T> cls) {
        this.zzy = (Class) zzds.checkNotNull(cls);
        this.zzq = (zze) zzds.checkNotNull(zze);
        this.zzr = (String) zzds.checkNotNull(str);
        this.zzs = (String) zzds.checkNotNull(str2);
        this.zzt = zzt2;
        this.zzu.zzu(AbstractGoogleClientRequest.USER_AGENT_SUFFIX);
        this.zzu.zzb("X-Goog-Api-Client", zzh.zzj().zzf(zze.getClass().getSimpleName()));
    }

    public zze zzf() {
        return this.zzq;
    }

    public final zzx zzg() {
        return this.zzu;
    }

    public final zzx zzh() {
        return this.zzv;
    }

    /* access modifiers changed from: protected */
    public IOException zza(zzad zzad) {
        return new zzae(zzad);
    }

    public final T zzi() throws IOException {
        zzaa zza = zzf().zzd().zza(this.zzr, new zzs(zzak.zza(this.zzq.zzc(), this.zzs, (Object) this, true)), this.zzt);
        new zzb().zzb(zza);
        zza.zza(zzf().zze());
        if (this.zzt == null && (this.zzr.equals("POST") || this.zzr.equals("PUT") || this.zzr.equals("PATCH"))) {
            zza.zza((zzt) new zzp());
        }
        zza.zzy().putAll(this.zzu);
        zza.zza((zzu) new zzq());
        zza.zza((zzag) new zzg(this, zza.zzaa(), zza));
        zzad zzad = zza.zzad();
        this.zzv = zzad.zzy();
        this.zzw = zzad.getStatusCode();
        this.zzx = zzad.getStatusMessage();
        return zzad.zza(this.zzy);
    }

    /* renamed from: zzc */
    public zzf<T> zzb(String str, Object obj) {
        return (zzf) super.zzb(str, obj);
    }
}
