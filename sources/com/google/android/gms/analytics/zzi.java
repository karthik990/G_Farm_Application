package com.google.android.gms.analytics;

import android.text.TextUtils;
import com.google.android.gms.analytics.zzi;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import org.objectweb.asm.signature.SignatureVisitor;

public abstract class zzi<T extends zzi> {
    public abstract void zzb(T t);

    public static String zza(Map map) {
        return zza(map, 1);
    }

    public static String zza(Object obj) {
        return zza(obj, 0);
    }

    private static String zza(Object obj, int i) {
        if (i > 10) {
            return "ERROR: Recursive toString calls";
        }
        String str = "";
        if (obj == null) {
            return str;
        }
        if (obj instanceof String) {
            if (TextUtils.isEmpty((String) obj)) {
                return str;
            }
            return obj.toString();
        } else if (obj instanceof Integer) {
            if (((Integer) obj).intValue() == 0) {
                return str;
            }
            return obj.toString();
        } else if (obj instanceof Long) {
            if (((Long) obj).longValue() == 0) {
                return str;
            }
            return obj.toString();
        } else if (obj instanceof Double) {
            if (((Double) obj).doubleValue() == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
                return str;
            }
            return obj.toString();
        } else if (!(obj instanceof Boolean)) {
            String str2 = ", ";
            if (obj instanceof List) {
                StringBuilder sb = new StringBuilder();
                if (i > 0) {
                    sb.append("[");
                }
                List list = (List) obj;
                int length = sb.length();
                for (Object next : list) {
                    if (sb.length() > length) {
                        sb.append(str2);
                    }
                    sb.append(zza(next, i + 1));
                }
                if (i > 0) {
                    sb.append("]");
                }
                return sb.toString();
            } else if (!(obj instanceof Map)) {
                return obj.toString();
            } else {
                StringBuilder sb2 = new StringBuilder();
                boolean z = false;
                int i2 = 0;
                for (Entry entry : new TreeMap((Map) obj).entrySet()) {
                    String zza = zza(entry.getValue(), i + 1);
                    if (!TextUtils.isEmpty(zza)) {
                        if (i > 0 && !z) {
                            sb2.append("{");
                            i2 = sb2.length();
                            z = true;
                        }
                        if (sb2.length() > i2) {
                            sb2.append(str2);
                        }
                        sb2.append((String) entry.getKey());
                        sb2.append(SignatureVisitor.INSTANCEOF);
                        sb2.append(zza);
                    }
                }
                if (z) {
                    sb2.append("}");
                }
                return sb2.toString();
            }
        } else if (!((Boolean) obj).booleanValue()) {
            return str;
        } else {
            return obj.toString();
        }
    }
}
