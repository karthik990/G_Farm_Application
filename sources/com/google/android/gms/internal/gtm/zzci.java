package com.google.android.gms.internal.gtm;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.common.internal.Preconditions;
import java.util.Map;
import java.util.Map.Entry;
import org.objectweb.asm.signature.SignatureVisitor;
import p043io.netty.util.internal.StringUtil;

public class zzci extends zzan {
    private static zzci zzabl;

    public zzci(zzap zzap) {
        super(zzap);
    }

    /* access modifiers changed from: protected */
    public final void zzaw() {
        synchronized (zzci.class) {
            zzabl = this;
        }
    }

    public static zzci zzfn() {
        return zzabl;
    }

    public final void zza(zzcd zzcd, String str) {
        String zzcd2 = zzcd != null ? zzcd.toString() : "no hit data";
        String str2 = "Discarding hit. ";
        String valueOf = String.valueOf(str);
        zzd(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2), zzcd2);
    }

    public final void zza(Map<String, String> map, String str) {
        String str2;
        if (map != null) {
            StringBuilder sb = new StringBuilder();
            for (Entry entry : map.entrySet()) {
                if (sb.length() > 0) {
                    sb.append(StringUtil.COMMA);
                }
                sb.append((String) entry.getKey());
                sb.append(SignatureVisitor.INSTANCEOF);
                sb.append((String) entry.getValue());
            }
            str2 = sb.toString();
        } else {
            str2 = "no hit data";
        }
        String str3 = "Discarding hit. ";
        String valueOf = String.valueOf(str);
        zzd(valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3), str2);
    }

    public final synchronized void zzb(int i, String str, Object obj, Object obj2, Object obj3) {
        Preconditions.checkNotNull(str);
        if (i < 0) {
            i = 0;
        }
        if (i >= 9) {
            i = 8;
        }
        char c = zzcp().zzem() ? 'C' : 'c';
        char charAt = "01VDIWEA?".charAt(i);
        String str2 = zzao.VERSION;
        String zzc = zzc(str, zzd(obj), zzd(obj2), zzd(obj3));
        StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 4 + String.valueOf(zzc).length());
        sb.append(ExifInterface.GPS_MEASUREMENT_3D);
        sb.append(charAt);
        sb.append(c);
        sb.append(str2);
        sb.append(":");
        sb.append(zzc);
        String sb2 = sb.toString();
        if (sb2.length() > 1024) {
            sb2 = sb2.substring(0, 1024);
        }
        zzcm zzdf = zzcm().zzdf();
        if (zzdf != null) {
            zzdf.zzga().zzae(sb2);
        }
    }

    private static String zzd(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Integer) {
            obj = Long.valueOf((long) ((Integer) obj).intValue());
        }
        String str = "-";
        if (obj instanceof Long) {
            Long l = (Long) obj;
            if (Math.abs(l.longValue()) < 100) {
                return String.valueOf(obj);
            }
            if (String.valueOf(obj).charAt(0) != '-') {
                str = "";
            }
            String valueOf = String.valueOf(Math.abs(l.longValue()));
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(Math.round(Math.pow(10.0d, (double) (valueOf.length() - 1))));
            sb.append("...");
            sb.append(str);
            sb.append(Math.round(Math.pow(10.0d, (double) valueOf.length()) - 1.0d));
            return sb.toString();
        } else if (obj instanceof Boolean) {
            return String.valueOf(obj);
        } else {
            return obj instanceof Throwable ? obj.getClass().getCanonicalName() : str;
        }
    }
}
