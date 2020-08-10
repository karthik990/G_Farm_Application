package com.google.android.gms.analytics;

import android.net.Uri;
import android.net.Uri.Builder;
import android.text.TextUtils;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.gtm.zzaa;
import com.google.android.gms.internal.gtm.zzab;
import com.google.android.gms.internal.gtm.zzac;
import com.google.android.gms.internal.gtm.zzam;
import com.google.android.gms.internal.gtm.zzao;
import com.google.android.gms.internal.gtm.zzap;
import com.google.android.gms.internal.gtm.zzas;
import com.google.android.gms.internal.gtm.zzcd;
import com.google.android.gms.internal.gtm.zzcz;
import com.google.android.gms.internal.gtm.zzq;
import com.google.android.gms.internal.gtm.zzr;
import com.google.android.gms.internal.gtm.zzs;
import com.google.android.gms.internal.gtm.zzt;
import com.google.android.gms.internal.gtm.zzu;
import com.google.android.gms.internal.gtm.zzv;
import com.google.android.gms.internal.gtm.zzw;
import com.google.android.gms.internal.gtm.zzx;
import com.google.android.gms.internal.gtm.zzy;
import com.google.android.gms.internal.gtm.zzz;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.mobiroller.constants.ChatConstants;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class zzb extends zzam implements zzo {
    private static DecimalFormat zzrf;
    private final zzap zzrb;
    private final String zzrg;
    private final Uri zzrh;

    public zzb(zzap zzap, String str) {
        this(zzap, str, true, false);
    }

    private zzb(zzap zzap, String str, boolean z, boolean z2) {
        super(zzap);
        Preconditions.checkNotEmpty(str);
        this.zzrb = zzap;
        this.zzrg = str;
        this.zzrh = zzb(this.zzrg);
    }

    static Uri zzb(String str) {
        Preconditions.checkNotEmpty(str);
        Builder builder = new Builder();
        builder.scheme("uri");
        builder.authority("google-analytics.com");
        builder.path(str);
        return builder.build();
    }

    public final Uri zzae() {
        return this.zzrh;
    }

    public final void zzb(zzg zzg) {
        Preconditions.checkNotNull(zzg);
        Preconditions.checkArgument(zzg.zzan(), "Can't deliver not submitted measurement");
        Preconditions.checkNotMainThread("deliver should be called on worker thread");
        zzg zzai = zzg.zzai();
        zzz zzz = (zzz) zzai.zzb(zzz.class);
        if (TextUtils.isEmpty(zzz.zzbs())) {
            zzco().zza(zzc(zzai), "Ignoring measurement without type");
        } else if (TextUtils.isEmpty(zzz.zzbt())) {
            zzco().zza(zzc(zzai), "Ignoring measurement without client id");
        } else if (!this.zzrb.zzde().getAppOptOut()) {
            double zzbz = zzz.zzbz();
            if (zzcz.zza(zzbz, zzz.zzbt())) {
                zzb("Sampling enabled. Hit sampled out. sampling rate", Double.valueOf(zzbz));
                return;
            }
            Map zzc = zzc(zzai);
            zzc.put("v", IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE);
            zzc.put("_v", zzao.zzwe);
            zzc.put("tid", this.zzrg);
            if (this.zzrb.zzde().isDryRunEnabled()) {
                StringBuilder sb = new StringBuilder();
                for (Entry entry : zzc.entrySet()) {
                    if (sb.length() != 0) {
                        sb.append(", ");
                    }
                    sb.append((String) entry.getKey());
                    sb.append("=");
                    sb.append((String) entry.getValue());
                }
                zzc("Dry run is enabled. GoogleAnalytics would have sent", sb.toString());
                return;
            }
            HashMap hashMap = new HashMap();
            zzcz.zzb((Map<String, String>) hashMap, ChatConstants.ARG_USER_INFO_UID, zzz.zzbu());
            zzq zzq = (zzq) zzg.zza(zzq.class);
            if (zzq != null) {
                zzcz.zzb((Map<String, String>) hashMap, "an", zzq.zzaz());
                zzcz.zzb((Map<String, String>) hashMap, "aid", zzq.zzbb());
                zzcz.zzb((Map<String, String>) hashMap, "av", zzq.zzba());
                zzcz.zzb((Map<String, String>) hashMap, "aiid", zzq.zzbc());
            }
            zzas zzas = new zzas(0, zzz.zzbt(), this.zzrg, !TextUtils.isEmpty(zzz.zzbv()), 0, hashMap);
            zzc.put("_s", String.valueOf(zzcs().zza(zzas)));
            zzcd zzcd = new zzcd(zzco(), zzc, zzg.zzal(), true);
            zzcs().zza(zzcd);
        }
    }

    private static Map<String, String> zzc(zzg zzg) {
        HashMap hashMap = new HashMap();
        zzu zzu = (zzu) zzg.zza(zzu.class);
        if (zzu != null) {
            for (Entry entry : zzu.zzbm().entrySet()) {
                Object value = entry.getValue();
                String str = null;
                if (value != null) {
                    if (value instanceof String) {
                        String str2 = (String) value;
                        if (!TextUtils.isEmpty(str2)) {
                            str = str2;
                        }
                    } else if (value instanceof Double) {
                        Double d = (Double) value;
                        if (d.doubleValue() != FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
                            str = zza(d.doubleValue());
                        }
                    } else if (!(value instanceof Boolean)) {
                        str = String.valueOf(value);
                    } else if (value != Boolean.FALSE) {
                        str = IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE;
                    }
                }
                if (str != null) {
                    hashMap.put((String) entry.getKey(), str);
                }
            }
        }
        zzz zzz = (zzz) zzg.zza(zzz.class);
        if (zzz != null) {
            zza((Map<String, String>) hashMap, "t", zzz.zzbs());
            zza((Map<String, String>) hashMap, "cid", zzz.zzbt());
            zza((Map<String, String>) hashMap, ChatConstants.ARG_USER_INFO_UID, zzz.zzbu());
            zza((Map<String, String>) hashMap, "sc", zzz.zzbx());
            zza((Map<String, String>) hashMap, "sf", zzz.zzbz());
            zza((Map<String, String>) hashMap, "ni", zzz.zzby());
            zza((Map<String, String>) hashMap, "adid", zzz.zzbv());
            zza((Map<String, String>) hashMap, "ate", zzz.zzbw());
        }
        zzaa zzaa = (zzaa) zzg.zza(zzaa.class);
        if (zzaa != null) {
            zza((Map<String, String>) hashMap, "cd", zzaa.zzca());
            zza((Map<String, String>) hashMap, "a", (double) zzaa.zzcb());
            zza((Map<String, String>) hashMap, "dr", zzaa.zzcc());
        }
        zzx zzx = (zzx) zzg.zza(zzx.class);
        if (zzx != null) {
            zza((Map<String, String>) hashMap, "ec", zzx.zzbr());
            zza((Map<String, String>) hashMap, "ea", zzx.getAction());
            zza((Map<String, String>) hashMap, "el", zzx.getLabel());
            zza((Map<String, String>) hashMap, "ev", (double) zzx.getValue());
        }
        zzr zzr = (zzr) zzg.zza(zzr.class);
        if (zzr != null) {
            zza((Map<String, String>) hashMap, "cn", zzr.getName());
            zza((Map<String, String>) hashMap, "cs", zzr.getSource());
            zza((Map<String, String>) hashMap, "cm", zzr.zzbd());
            zza((Map<String, String>) hashMap, "ck", zzr.zzbe());
            zza((Map<String, String>) hashMap, "cc", zzr.zzbf());
            zza((Map<String, String>) hashMap, "ci", zzr.getId());
            zza((Map<String, String>) hashMap, "anid", zzr.zzbg());
            zza((Map<String, String>) hashMap, "gclid", zzr.zzbh());
            zza((Map<String, String>) hashMap, "dclid", zzr.zzbi());
            zza((Map<String, String>) hashMap, Param.ACLID, zzr.zzbj());
        }
        zzy zzy = (zzy) zzg.zza(zzy.class);
        if (zzy != null) {
            zza((Map<String, String>) hashMap, "exd", zzy.zzuq);
            zza((Map<String, String>) hashMap, "exf", zzy.zzur);
        }
        zzab zzab = (zzab) zzg.zza(zzab.class);
        if (zzab != null) {
            zza((Map<String, String>) hashMap, "sn", zzab.zzvh);
            zza((Map<String, String>) hashMap, "sa", zzab.zzvi);
            zza((Map<String, String>) hashMap, "st", zzab.zzvj);
        }
        zzac zzac = (zzac) zzg.zza(zzac.class);
        if (zzac != null) {
            zza((Map<String, String>) hashMap, "utv", zzac.zzvk);
            zza((Map<String, String>) hashMap, "utt", (double) zzac.zzvl);
            zza((Map<String, String>) hashMap, "utc", zzac.mCategory);
            zza((Map<String, String>) hashMap, "utl", zzac.zzvm);
        }
        zzs zzs = (zzs) zzg.zza(zzs.class);
        if (zzs != null) {
            for (Entry entry2 : zzs.zzbk().entrySet()) {
                String zze = zzd.zze(((Integer) entry2.getKey()).intValue());
                if (!TextUtils.isEmpty(zze)) {
                    hashMap.put(zze, (String) entry2.getValue());
                }
            }
        }
        zzt zzt = (zzt) zzg.zza(zzt.class);
        if (zzt != null) {
            for (Entry entry3 : zzt.zzbl().entrySet()) {
                String zzg2 = zzd.zzg(((Integer) entry3.getKey()).intValue());
                if (!TextUtils.isEmpty(zzg2)) {
                    hashMap.put(zzg2, zza(((Double) entry3.getValue()).doubleValue()));
                }
            }
        }
        zzw zzw = (zzw) zzg.zza(zzw.class);
        if (zzw != null) {
            ProductAction zzbn = zzw.zzbn();
            if (zzbn != null) {
                for (Entry entry4 : zzbn.build().entrySet()) {
                    if (((String) entry4.getKey()).startsWith("&")) {
                        hashMap.put(((String) entry4.getKey()).substring(1), (String) entry4.getValue());
                    } else {
                        hashMap.put((String) entry4.getKey(), (String) entry4.getValue());
                    }
                }
            }
            int i = 1;
            for (Promotion zzn : zzw.zzbq()) {
                hashMap.putAll(zzn.zzn(zzd.zzk(i)));
                i++;
            }
            int i2 = 1;
            for (Product zzn2 : zzw.zzbo()) {
                hashMap.putAll(zzn2.zzn(zzd.zzi(i2)));
                i2++;
            }
            int i3 = 1;
            for (Entry entry5 : zzw.zzbp().entrySet()) {
                List<Product> list = (List) entry5.getValue();
                String zzn3 = zzd.zzn(i3);
                int i4 = 1;
                for (Product product : list) {
                    String valueOf = String.valueOf(zzn3);
                    String valueOf2 = String.valueOf(zzd.zzl(i4));
                    hashMap.putAll(product.zzn(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf)));
                    i4++;
                }
                if (!TextUtils.isEmpty((CharSequence) entry5.getKey())) {
                    String valueOf3 = String.valueOf(zzn3);
                    String str3 = "nm";
                    hashMap.put(str3.length() != 0 ? valueOf3.concat(str3) : new String(valueOf3), (String) entry5.getKey());
                }
                i3++;
            }
        }
        zzv zzv = (zzv) zzg.zza(zzv.class);
        if (zzv != null) {
            zza((Map<String, String>) hashMap, "ul", zzv.getLanguage());
            zza((Map<String, String>) hashMap, "sd", (double) zzv.zzuk);
            zza(hashMap, "sr", zzv.zzul, zzv.zzum);
            zza(hashMap, "vp", zzv.zzun, zzv.zzuo);
        }
        zzq zzq = (zzq) zzg.zza(zzq.class);
        if (zzq != null) {
            zza((Map<String, String>) hashMap, "an", zzq.zzaz());
            zza((Map<String, String>) hashMap, "aid", zzq.zzbb());
            zza((Map<String, String>) hashMap, "aiid", zzq.zzbc());
            zza((Map<String, String>) hashMap, "av", zzq.zzba());
        }
        return hashMap;
    }

    private static void zza(Map<String, String> map, String str, String str2) {
        if (!TextUtils.isEmpty(str2)) {
            map.put(str, str2);
        }
    }

    private static String zza(double d) {
        if (zzrf == null) {
            zzrf = new DecimalFormat("0.######");
        }
        return zzrf.format(d);
    }

    private static void zza(Map<String, String> map, String str, double d) {
        if (d != FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
            map.put(str, zza(d));
        }
    }

    private static void zza(Map<String, String> map, String str, boolean z) {
        if (z) {
            map.put(str, IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE);
        }
    }

    private static void zza(Map<String, String> map, String str, int i, int i2) {
        if (i > 0 && i2 > 0) {
            StringBuilder sb = new StringBuilder(23);
            sb.append(i);
            sb.append("x");
            sb.append(i2);
            map.put(str, sb.toString());
        }
    }
}
