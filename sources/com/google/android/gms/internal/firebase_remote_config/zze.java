package com.google.android.gms.internal.firebase_remote_config;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class zze {
    private static final Logger logger = Logger.getLogger(zze.class.getName());
    private final zzab zzf;
    private final zzj zzg;
    private final String zzh;
    private final String zzi;
    private final String zzj;
    private final String zzk;
    private final zzch zzl;
    private final boolean zzm;
    private final boolean zzn;

    public static abstract class zza {
        zzj zzg;
        String zzh;
        String zzi;
        String zzj;
        final zzch zzl;
        final zzah zzo;
        zzac zzp;

        protected zza(zzah zzah, String str, String str2, zzch zzch, zzac zzac) {
            this.zzo = (zzah) zzds.checkNotNull(zzah);
            this.zzl = zzch;
            zzc(str);
            zzd(str2);
            this.zzp = zzac;
        }

        public zza zzc(String str) {
            this.zzh = zze.zza(str);
            return this;
        }

        public zza zzd(String str) {
            this.zzi = zze.zzb(str);
            return this;
        }

        public zza zze(String str) {
            this.zzj = str;
            return this;
        }

        public zza zza(zzj zzj2) {
            this.zzg = zzj2;
            return this;
        }
    }

    protected zze(zza zza2) {
        zzab zzab;
        this.zzg = zza2.zzg;
        this.zzh = zza(zza2.zzh);
        this.zzi = zzb(zza2.zzi);
        this.zzj = zza2.zzj;
        if (zzdz.zzbc(null)) {
            logger.logp(Level.WARNING, "com.google.api.client.googleapis.services.AbstractGoogleClient", "<init>", "Application name is not set. Call Builder#setApplicationName.");
        }
        this.zzk = null;
        if (zza2.zzp == null) {
            zzab = zza2.zzo.zza(null);
        } else {
            zzab = zza2.zzo.zza(zza2.zzp);
        }
        this.zzf = zzab;
        this.zzl = zza2.zzl;
        this.zzm = false;
        this.zzn = false;
    }

    public final String zzc() {
        String valueOf = String.valueOf(this.zzh);
        String valueOf2 = String.valueOf(this.zzi);
        return valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
    }

    public final zzab zzd() {
        return this.zzf;
    }

    public zzch zze() {
        return this.zzl;
    }

    /* access modifiers changed from: protected */
    public void zza(zzf<?> zzf2) throws IOException {
        zzj zzj2 = this.zzg;
        if (zzj2 != null) {
            zzj2.zza(zzf2);
        }
    }

    static String zza(String str) {
        if (str != null) {
            String str2 = "/";
            return !str.endsWith(str2) ? String.valueOf(str).concat(str2) : str;
        }
        throw new NullPointerException("root URL cannot be null.");
    }

    static String zzb(String str) {
        if (str != null) {
            String str2 = "/";
            if (str.length() == 1) {
                if (str2.equals(str)) {
                    return "";
                }
                throw new IllegalArgumentException("service path must equal \"/\" if it is of length 1.");
            } else if (str.length() <= 0) {
                return str;
            } else {
                if (!str.endsWith(str2)) {
                    str = String.valueOf(str).concat(str2);
                }
                if (str.startsWith(str2)) {
                    return str.substring(1);
                }
                return str;
            }
        } else {
            throw new NullPointerException("service path cannot be null");
        }
    }
}
