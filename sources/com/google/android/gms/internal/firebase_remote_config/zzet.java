package com.google.android.gms.internal.firebase_remote_config;

import android.util.Log;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigValue;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;
import org.json.JSONException;

public final class zzet {
    public static final Charset zzlb = Charset.forName("UTF-8");
    static final Pattern zzlc = Pattern.compile("^(1|true|t|yes|y|on)$", 2);
    static final Pattern zzld = Pattern.compile("^(0|false|f|no|n|off|)$", 2);
    private final zzeh zzjf;
    private final zzeh zzjg;

    public zzet(zzeh zzeh, zzeh zzeh2) {
        this.zzjf = zzeh;
        this.zzjg = zzeh2;
    }

    public final String getString(String str) {
        String str2 = "String";
        String zza = zza(this.zzjf, str, str2);
        if (zza != null) {
            return zza;
        }
        String zza2 = zza(this.zzjg, str, str2);
        return zza2 != null ? zza2 : "";
    }

    public final boolean getBoolean(String str) {
        String str2 = "Boolean";
        String zza = zza(this.zzjf, str, str2);
        if (zza != null) {
            if (zzlc.matcher(zza).matches()) {
                return true;
            }
            if (zzld.matcher(zza).matches()) {
                return false;
            }
        }
        String zza2 = zza(this.zzjg, str, str2);
        if (zza2 != null) {
            if (zzlc.matcher(zza2).matches()) {
                return true;
            }
            if (zzld.matcher(zza2).matches()) {
            }
        }
        return false;
    }

    public final byte[] getByteArray(String str) {
        String str2 = "ByteArray";
        String zza = zza(this.zzjf, str, str2);
        if (zza != null) {
            return zza.getBytes(zzlb);
        }
        String zza2 = zza(this.zzjg, str, str2);
        if (zza2 != null) {
            return zza2.getBytes(zzlb);
        }
        return FirebaseRemoteConfig.DEFAULT_VALUE_FOR_BYTE_ARRAY;
    }

    public final double getDouble(String str) {
        Double zza = zza(this.zzjf, str);
        if (zza != null) {
            return zza.doubleValue();
        }
        Double zza2 = zza(this.zzjg, str);
        return zza2 != null ? zza2.doubleValue() : FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
    }

    public final long getLong(String str) {
        Long zzb = zzb(this.zzjf, str);
        if (zzb != null) {
            return zzb.longValue();
        }
        Long zzb2 = zzb(this.zzjg, str);
        if (zzb2 != null) {
            return zzb2.longValue();
        }
        return 0;
    }

    public final FirebaseRemoteConfigValue getValue(String str) {
        String str2 = "FirebaseRemoteConfigValue";
        String zza = zza(this.zzjf, str, str2);
        if (zza != null) {
            return new zzfb(zza, 2);
        }
        String zza2 = zza(this.zzjg, str, str2);
        if (zza2 != null) {
            return new zzfb(zza2, 1);
        }
        return new zzfb("", 0);
    }

    public final Set<String> getKeysByPrefix(String str) {
        if (str == null) {
            str = "";
        }
        TreeSet treeSet = new TreeSet();
        zzeo zzb = this.zzjf.zzb(5);
        if (zzb != null) {
            treeSet.addAll(zza(str, zzb));
        }
        zzeo zzb2 = this.zzjg.zzb(5);
        if (zzb2 != null) {
            treeSet.addAll(zza(str, zzb2));
        }
        return treeSet;
    }

    private static TreeSet<String> zza(String str, zzeo zzeo) {
        TreeSet<String> treeSet = new TreeSet<>();
        Iterator keys = zzeo.zzcq().keys();
        while (keys.hasNext()) {
            String str2 = (String) keys.next();
            if (str2.startsWith(str)) {
                treeSet.add(str2);
            }
        }
        return treeSet;
    }

    private static String zza(zzeh zzeh, String str, String str2) {
        zzeo zzb = zzeh.zzb(5);
        if (zzb == null) {
            return null;
        }
        try {
            return zzb.zzcq().getString(str);
        } catch (JSONException unused) {
            zze(str, str2);
            return null;
        }
    }

    private static Double zza(zzeh zzeh, String str) {
        zzeo zzb = zzeh.zzb(5);
        if (zzb == null) {
            return null;
        }
        try {
            return Double.valueOf(zzb.zzcq().getDouble(str));
        } catch (JSONException unused) {
            zze(str, "Double");
            return null;
        }
    }

    private static Long zzb(zzeh zzeh, String str) {
        zzeo zzb = zzeh.zzb(5);
        if (zzb == null) {
            return null;
        }
        try {
            return Long.valueOf(zzb.zzcq().getLong(str));
        } catch (JSONException unused) {
            zze(str, "Long");
            return null;
        }
    }

    private static void zze(String str, String str2) {
        Object[] objArr = {str2, str};
        Log.w("FirebaseRemoteConfig", String.format("No value of type '%s' exists for parameter key '%s'.", objArr));
    }
}
