package com.google.android.gms.internal.gtm;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ServiceInfo;
import android.text.TextUtils;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.HttpUtils;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import kotlin.jvm.internal.CharCompanionObject;

public final class zzcz {
    private static final char[] zzact = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static Map<String, String> zzaf(String str) {
        HashMap hashMap = new HashMap();
        for (String split : str.split("&")) {
            String[] split2 = split.split("=", 3);
            String str2 = null;
            if (split2.length > 1) {
                hashMap.put(split2[0], TextUtils.isEmpty(split2[1]) ? null : split2[1]);
                if (split2.length == 3 && !TextUtils.isEmpty(split2[1]) && !hashMap.containsKey(split2[1])) {
                    String str3 = split2[1];
                    if (!TextUtils.isEmpty(split2[2])) {
                        str2 = split2[2];
                    }
                    hashMap.put(str3, str2);
                }
            } else if (split2.length == 1 && split2[0].length() != 0) {
                hashMap.put(split2[0], null);
            }
        }
        return hashMap;
    }

    public static String zzc(boolean z) {
        return z ? IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE : "0";
    }

    public static double zza(String str, double d) {
        if (str == null) {
            return 100.0d;
        }
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException unused) {
            return 100.0d;
        }
    }

    public static long zzag(String str) {
        long j = 0;
        if (str == null) {
            return 0;
        }
        try {
            j = Long.parseLong(str);
        } catch (NumberFormatException unused) {
        }
        return j;
    }

    public static boolean zzb(String str, boolean z) {
        if (str == null || str.equalsIgnoreCase("true") || str.equalsIgnoreCase("yes") || str.equalsIgnoreCase(IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE) || (!str.equalsIgnoreCase("false") && !str.equalsIgnoreCase("no") && !str.equalsIgnoreCase("0"))) {
            return true;
        }
        return false;
    }

    public static String zzah(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (str.contains("?")) {
            String[] split = str.split("[\\?]");
            if (split.length > 1) {
                str = split[1];
            }
        }
        String str2 = "=";
        if (str.contains("%3D")) {
            try {
                str = URLDecoder.decode(str, "UTF-8");
            } catch (UnsupportedEncodingException unused) {
                return null;
            }
        } else if (!str.contains(str2)) {
            return null;
        }
        Map zzaf = zzaf(str);
        String[] strArr = {"dclid", "utm_source", "gclid", Param.ACLID, "utm_campaign", "utm_medium", "utm_term", "utm_content", "utm_id", "anid", "gmob_t"};
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 11; i++) {
            if (!TextUtils.isEmpty((CharSequence) zzaf.get(strArr[i]))) {
                if (sb.length() > 0) {
                    sb.append("&");
                }
                sb.append(strArr[i]);
                sb.append(str2);
                sb.append((String) zzaf.get(strArr[i]));
            }
        }
        return sb.toString();
    }

    public static zzr zza(zzci zzci, String str) {
        Preconditions.checkNotNull(zzci);
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        new HashMap();
        try {
            String str2 = "?";
            String valueOf = String.valueOf(str);
            Map parse = HttpUtils.parse(new URI(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2)), "UTF-8");
            zzr zzr = new zzr();
            zzr.zzf((String) parse.get("utm_content"));
            zzr.zzd((String) parse.get("utm_medium"));
            zzr.setName((String) parse.get("utm_campaign"));
            zzr.zzc((String) parse.get("utm_source"));
            zzr.zze((String) parse.get("utm_term"));
            zzr.zzg((String) parse.get("utm_id"));
            zzr.zzh((String) parse.get("anid"));
            zzr.zzi((String) parse.get("gclid"));
            zzr.zzj((String) parse.get("dclid"));
            zzr.zzk((String) parse.get(Param.ACLID));
            return zzr;
        } catch (URISyntaxException e) {
            zzci.zzd("No valid campaign data found", e);
            return null;
        }
    }

    public static String zza(Locale locale) {
        if (locale == null) {
            return null;
        }
        String language = locale.getLanguage();
        if (TextUtils.isEmpty(language)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(language.toLowerCase(locale));
        if (!TextUtils.isEmpty(locale.getCountry())) {
            sb.append("-");
            sb.append(locale.getCountry().toLowerCase(locale));
        }
        return sb.toString();
    }

    public static void zzb(Map<String, String> map, String str, String str2) {
        if (str2 != null && !map.containsKey(str)) {
            map.put(str, str2);
        }
    }

    public static void zzc(Map<String, String> map, String str, String str2) {
        if (str2 != null && TextUtils.isEmpty((CharSequence) map.get(str))) {
            map.put(str, str2);
        }
    }

    public static void zzb(Map<String, String> map, String str, boolean z) {
        if (!map.containsKey(str)) {
            map.put(str, z ? IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE : "0");
        }
    }

    public static void zza(Map<String, String> map, String str, Map<String, String> map2) {
        zzb(map, str, (String) map2.get(str));
    }

    public static MessageDigest zzai(String str) {
        int i = 0;
        while (i < 2) {
            try {
                MessageDigest instance = MessageDigest.getInstance(str);
                if (instance != null) {
                    return instance;
                }
                i++;
            } catch (NoSuchAlgorithmException unused) {
            }
        }
        return null;
    }

    public static boolean zza(double d, String str) {
        int i;
        if (d > FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE && d < 100.0d) {
            if (!TextUtils.isEmpty(str)) {
                i = 0;
                for (int length = str.length() - 1; length >= 0; length--) {
                    char charAt = str.charAt(length);
                    i = ((i << 6) & CharCompanionObject.MAX_VALUE) + charAt + (charAt << 14);
                    int i2 = 266338304 & i;
                    if (i2 != 0) {
                        i ^= i2 >> 21;
                    }
                }
            } else {
                i = 1;
            }
            if (((double) (i % 10000)) >= d * 100.0d) {
                return true;
            }
        }
        return false;
    }

    public static boolean zzaj(String str) {
        if (!TextUtils.isEmpty(str) && str.startsWith("http:")) {
            return false;
        }
        return true;
    }

    public static boolean zza(Context context, String str, boolean z) {
        try {
            ActivityInfo receiverInfo = context.getPackageManager().getReceiverInfo(new ComponentName(context, str), 0);
            if (receiverInfo != null && receiverInfo.enabled && (!z || receiverInfo.exported)) {
                return true;
            }
        } catch (NameNotFoundException unused) {
        }
        return false;
    }

    public static boolean zzc(Context context, String str) {
        try {
            ServiceInfo serviceInfo = context.getPackageManager().getServiceInfo(new ComponentName(context, str), 0);
            if (serviceInfo != null && serviceInfo.enabled) {
                return true;
            }
        } catch (NameNotFoundException unused) {
        }
        return false;
    }
}
