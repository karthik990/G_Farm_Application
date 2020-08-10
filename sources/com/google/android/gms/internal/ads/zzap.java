package com.google.android.gms.internal.ads;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public final class zzap {
    public static zzc zzb(zzp zzp) {
        boolean z;
        long j;
        long j2;
        long j3;
        long j4;
        zzp zzp2 = zzp;
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = zzp2.zzab;
        String str = (String) map.get("Date");
        long zzf = str != null ? zzf(str) : 0;
        String str2 = (String) map.get("Cache-Control");
        int i = 0;
        if (str2 != null) {
            String[] split = str2.split(",");
            j2 = 0;
            int i2 = 0;
            j = 0;
            while (i < split.length) {
                String trim = split[i].trim();
                if (trim.equals("no-cache") || trim.equals("no-store")) {
                    return null;
                }
                if (trim.startsWith("max-age=")) {
                    try {
                        j2 = Long.parseLong(trim.substring(8));
                    } catch (Exception unused) {
                    }
                } else if (trim.startsWith("stale-while-revalidate=")) {
                    j = Long.parseLong(trim.substring(23));
                } else if (trim.equals("must-revalidate") || trim.equals("proxy-revalidate")) {
                    i2 = 1;
                }
                i++;
            }
            i = i2;
            z = true;
        } else {
            j2 = 0;
            j = 0;
            z = false;
        }
        String str3 = (String) map.get("Expires");
        long zzf2 = str3 != null ? zzf(str3) : 0;
        String str4 = (String) map.get("Last-Modified");
        long zzf3 = str4 != null ? zzf(str4) : 0;
        String str5 = (String) map.get("ETag");
        if (z) {
            j4 = currentTimeMillis + (j2 * 1000);
            if (i == 0) {
                Long.signum(j);
                j3 = (j * 1000) + j4;
                zzc zzc = new zzc();
                zzc.data = zzp2.data;
                zzc.zza = str5;
                zzc.zze = j4;
                zzc.zzd = j3;
                zzc.zzb = zzf;
                zzc.zzc = zzf3;
                zzc.zzf = map;
                zzc.zzg = zzp2.allHeaders;
                return zzc;
            }
        } else if (zzf <= 0 || zzf2 < zzf) {
            j4 = 0;
        } else {
            j3 = (zzf2 - zzf) + currentTimeMillis;
            j4 = j3;
            zzc zzc2 = new zzc();
            zzc2.data = zzp2.data;
            zzc2.zza = str5;
            zzc2.zze = j4;
            zzc2.zzd = j3;
            zzc2.zzb = zzf;
            zzc2.zzc = zzf3;
            zzc2.zzf = map;
            zzc2.zzg = zzp2.allHeaders;
            return zzc2;
        }
        j3 = j4;
        zzc zzc22 = new zzc();
        zzc22.data = zzp2.data;
        zzc22.zza = str5;
        zzc22.zze = j4;
        zzc22.zzd = j3;
        zzc22.zzb = zzf;
        zzc22.zzc = zzf3;
        zzc22.zzf = map;
        zzc22.zzg = zzp2.allHeaders;
        return zzc22;
    }

    static String zzb(long j) {
        return zzp().format(new Date(j));
    }

    private static long zzf(String str) {
        try {
            return zzp().parse(str).getTime();
        } catch (ParseException e) {
            zzaf.zza(e, "Unable to parse dateStr: %s, falling back to 0", str);
            return 0;
        }
    }

    private static SimpleDateFormat zzp() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return simpleDateFormat;
    }
}
