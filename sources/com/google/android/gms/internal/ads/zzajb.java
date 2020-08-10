package com.google.android.gms.internal.ads;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.ads.internal.zzbv;

@zzadh
public final class zzajb {
    public static Uri zzb(Uri uri, Context context) {
        if (!zzbv.zzfh().zzx(context)) {
            return uri;
        }
        String str = "fbs_aeid";
        if (!TextUtils.isEmpty(uri.getQueryParameter(str))) {
            return uri;
        }
        String zzab = zzbv.zzfh().zzab(context);
        Uri zzb = zzb(uri.toString(), str, zzab);
        zzbv.zzfh().zze(context, zzab);
        return zzb;
    }

    private static Uri zzb(String str, String str2, String str3) {
        int indexOf = str.indexOf("&adurl");
        if (indexOf == -1) {
            indexOf = str.indexOf("?adurl");
        }
        if (indexOf == -1) {
            return Uri.parse(str).buildUpon().appendQueryParameter(str2, str3).build();
        }
        int i = indexOf + 1;
        StringBuilder sb = new StringBuilder(str.substring(0, i));
        sb.append(str2);
        sb.append("=");
        sb.append(str3);
        sb.append("&");
        sb.append(str.substring(i));
        return Uri.parse(sb.toString());
    }

    public static String zzb(String str, Context context) {
        if (zzbv.zzfh().zzs(context) && !TextUtils.isEmpty(str)) {
            String zzab = zzbv.zzfh().zzab(context);
            if (zzab == null) {
                return str;
            }
            if (((Boolean) zzkb.zzik().zzd(zznk.zzaxr)).booleanValue()) {
                String str2 = (String) zzkb.zzik().zzd(zznk.zzaxs);
                if (str.contains(str2)) {
                    if (zzbv.zzek().zzcx(str)) {
                        zzbv.zzfh().zze(context, zzab);
                        return str.replace(str2, zzab);
                    } else if (zzbv.zzek().zzcy(str)) {
                        zzbv.zzfh().zzf(context, zzab);
                        return str.replace(str2, zzab);
                    }
                }
            } else {
                String str3 = "fbs_aeid";
                if (!str.contains(str3)) {
                    if (zzbv.zzek().zzcx(str)) {
                        zzbv.zzfh().zze(context, zzab);
                        return zzb(str, str3, zzab).toString();
                    } else if (zzbv.zzek().zzcy(str)) {
                        zzbv.zzfh().zzf(context, zzab);
                        str = zzb(str, str3, zzab).toString();
                    }
                }
            }
        }
        return str;
    }

    public static String zzc(String str, Context context) {
        if (zzbv.zzfh().zzs(context) && !TextUtils.isEmpty(str)) {
            String zzab = zzbv.zzfh().zzab(context);
            if (zzab == null || !zzbv.zzek().zzcy(str)) {
                return str;
            }
            if (((Boolean) zzkb.zzik().zzd(zznk.zzaxr)).booleanValue()) {
                String str2 = (String) zzkb.zzik().zzd(zznk.zzaxs);
                if (str.contains(str2)) {
                    return str.replace(str2, zzab);
                }
            } else {
                String str3 = "fbs_aeid";
                if (!str.contains(str3)) {
                    str = zzb(str, str3, zzab).toString();
                }
            }
        }
        return str;
    }
}
