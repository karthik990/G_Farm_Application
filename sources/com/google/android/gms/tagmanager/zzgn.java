package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.gtm.zzl;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

final class zzgn {
    static zzdz<zzl> zza(zzdz<zzl> zzdz, int... iArr) {
        for (int i : iArr) {
            if (!(zzgj.zzh((zzl) zzdz.getObject()) instanceof String)) {
                zzdi.zzav("Escaping can only be applied to strings.");
            } else if (i != 12) {
                StringBuilder sb = new StringBuilder(39);
                sb.append("Unsupported Value Escaping: ");
                sb.append(i);
                zzdi.zzav(sb.toString());
            } else {
                zzdz = zza(zzdz);
            }
        }
        return zzdz;
    }

    static String zzbs(String str) throws UnsupportedEncodingException {
        return URLEncoder.encode(str, "UTF-8").replaceAll("\\+", "%20");
    }

    private static zzdz<zzl> zza(zzdz<zzl> zzdz) {
        try {
            return new zzdz<>(zzgj.zzi(zzbs(zzgj.zzc((zzl) zzdz.getObject()))), zzdz.zziu());
        } catch (UnsupportedEncodingException e) {
            zzdi.zza("Escape URI: unsupported encoding", e);
            return zzdz;
        }
    }
}
