package com.google.android.gms.internal.ads;

import com.google.android.gms.internal.ads.zzaxr.zzb;
import java.security.GeneralSecurityException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class zzauo {
    private static final Logger logger = Logger.getLogger(zzauo.class.getName());
    private static final ConcurrentMap<String, zzaug> zzdhq = new ConcurrentHashMap();
    private static final ConcurrentMap<String, Boolean> zzdhr = new ConcurrentHashMap();
    private static final ConcurrentMap<String, zzaua> zzdhs = new ConcurrentHashMap();

    public static <P> zzaum<P> zza(zzauh zzauh, zzaug<P> zzaug) throws GeneralSecurityException {
        zzaup.zzc(zzauh.zzwg());
        zzaum<P> zzaum = new zzaum<>();
        for (zzb zzb : zzauh.zzwg().zzzl()) {
            if (zzb.zzzq() == zzaxl.ENABLED) {
                zzaun zza = zzaum.zza(zza(zzb.zzzp().zzyw(), zzb.zzzp().zzyx()), zzb);
                if (zzb.zzzr() == zzauh.zzwg().zzzk()) {
                    zzaum.zza(zza);
                }
            }
        }
        return zzaum;
    }

    public static <P> zzaxi zza(zzaxn zzaxn) throws GeneralSecurityException {
        zzaug zzdz = zzdz(zzaxn.zzyw());
        if (((Boolean) zzdhr.get(zzaxn.zzyw())).booleanValue()) {
            return zzdz.zzc(zzaxn.zzyx());
        }
        String str = "newKey-operation not permitted for key type ";
        String valueOf = String.valueOf(zzaxn.zzyw());
        throw new GeneralSecurityException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
    }

    public static <P> zzbcu zza(String str, zzbcu zzbcu) throws GeneralSecurityException {
        zzaug zzdz = zzdz(str);
        if (((Boolean) zzdhr.get(str)).booleanValue()) {
            return zzdz.zzb(zzbcu);
        }
        String str2 = "newKey-operation not permitted for key type ";
        String valueOf = String.valueOf(str);
        throw new GeneralSecurityException(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
    }

    private static <P> P zza(String str, zzbah zzbah) throws GeneralSecurityException {
        return zzdz(str).zza(zzbah);
    }

    public static <P> P zza(String str, byte[] bArr) throws GeneralSecurityException {
        return zza(str, zzbah.zzo(bArr));
    }

    public static synchronized <P> void zza(String str, zzaua<P> zzaua) throws GeneralSecurityException {
        synchronized (zzauo.class) {
            if (zzdhs.containsKey(str.toLowerCase())) {
                if (!zzaua.getClass().equals(((zzaua) zzdhs.get(str.toLowerCase())).getClass())) {
                    Logger logger2 = logger;
                    Level level = Level.WARNING;
                    String str2 = "com.google.crypto.tink.Registry";
                    String str3 = "addCatalogue";
                    String str4 = "Attempted overwrite of a catalogueName catalogue for name ";
                    String valueOf = String.valueOf(str);
                    logger2.logp(level, str2, str3, valueOf.length() != 0 ? str4.concat(valueOf) : new String(str4));
                    StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 47);
                    sb.append("catalogue for name ");
                    sb.append(str);
                    sb.append(" has been already registered");
                    throw new GeneralSecurityException(sb.toString());
                }
            }
            zzdhs.put(str.toLowerCase(), zzaua);
        }
    }

    public static <P> void zza(String str, zzaug<P> zzaug) throws GeneralSecurityException {
        zza(str, zzaug, true);
    }

    public static synchronized <P> void zza(String str, zzaug<P> zzaug, boolean z) throws GeneralSecurityException {
        synchronized (zzauo.class) {
            if (zzaug != null) {
                if (zzdhq.containsKey(str)) {
                    zzaug zzdz = zzdz(str);
                    boolean booleanValue = ((Boolean) zzdhr.get(str)).booleanValue();
                    if (!zzaug.getClass().equals(zzdz.getClass()) || (!booleanValue && z)) {
                        Logger logger2 = logger;
                        Level level = Level.WARNING;
                        String str2 = "com.google.crypto.tink.Registry";
                        String str3 = "registerKeyManager";
                        String str4 = "Attempted overwrite of a registered key manager for key type ";
                        String valueOf = String.valueOf(str);
                        logger2.logp(level, str2, str3, valueOf.length() != 0 ? str4.concat(valueOf) : new String(str4));
                        throw new GeneralSecurityException(String.format("typeUrl (%s) is already registered with %s, cannot be re-registered with %s", new Object[]{str, zzdz.getClass().getName(), zzaug.getClass().getName()}));
                    }
                }
                zzdhq.put(str, zzaug);
                zzdhr.put(str, Boolean.valueOf(z));
            } else {
                throw new IllegalArgumentException("key manager must be non-null.");
            }
        }
    }

    public static <P> zzbcu zzb(zzaxn zzaxn) throws GeneralSecurityException {
        zzaug zzdz = zzdz(zzaxn.zzyw());
        if (((Boolean) zzdhr.get(zzaxn.zzyw())).booleanValue()) {
            return zzdz.zzb(zzaxn.zzyx());
        }
        String str = "newKey-operation not permitted for key type ";
        String valueOf = String.valueOf(zzaxn.zzyw());
        throw new GeneralSecurityException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
    }

    public static <P> P zzb(String str, zzbcu zzbcu) throws GeneralSecurityException {
        return zzdz(str).zza(zzbcu);
    }

    public static <P> zzaua<P> zzdy(String str) throws GeneralSecurityException {
        String str2;
        String str3;
        if (str != null) {
            zzaua<P> zzaua = (zzaua) zzdhs.get(str.toLowerCase());
            if (zzaua != null) {
                return zzaua;
            }
            String format = String.format("no catalogue found for %s. ", new Object[]{str});
            if (str.toLowerCase().startsWith("tinkaead")) {
                format = String.valueOf(format).concat("Maybe call AeadConfig.init().");
            }
            if (str.toLowerCase().startsWith("tinkdeterministicaead")) {
                str2 = String.valueOf(format);
                str3 = "Maybe call DeterministicAeadConfig.init().";
            } else if (str.toLowerCase().startsWith("tinkstreamingaead")) {
                str2 = String.valueOf(format);
                str3 = "Maybe call StreamingAeadConfig.init().";
            } else if (str.toLowerCase().startsWith("tinkhybriddecrypt") || str.toLowerCase().startsWith("tinkhybridencrypt")) {
                str2 = String.valueOf(format);
                str3 = "Maybe call HybridConfig.init().";
            } else if (str.toLowerCase().startsWith("tinkmac")) {
                str2 = String.valueOf(format);
                str3 = "Maybe call MacConfig.init().";
            } else if (str.toLowerCase().startsWith("tinkpublickeysign") || str.toLowerCase().startsWith("tinkpublickeyverify")) {
                str2 = String.valueOf(format);
                str3 = "Maybe call SignatureConfig.init().";
            } else {
                if (str.toLowerCase().startsWith("tink")) {
                    str2 = String.valueOf(format);
                    str3 = "Maybe call TinkConfig.init().";
                }
                throw new GeneralSecurityException(format);
            }
            format = str2.concat(str3);
            throw new GeneralSecurityException(format);
        }
        throw new IllegalArgumentException("catalogueName must be non-null.");
    }

    private static <P> zzaug<P> zzdz(String str) throws GeneralSecurityException {
        zzaug<P> zzaug = (zzaug) zzdhq.get(str);
        if (zzaug != null) {
            return zzaug;
        }
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 78);
        sb.append("No key manager found for key type: ");
        sb.append(str);
        sb.append(".  Check the configuration of the registry.");
        throw new GeneralSecurityException(sb.toString());
    }
}
