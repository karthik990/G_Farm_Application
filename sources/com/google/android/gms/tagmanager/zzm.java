package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.Uri;
import android.net.Uri.Builder;
import com.google.android.gms.internal.gtm.zzb;
import com.google.android.gms.internal.gtm.zzl;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

class zzm extends zzgh {

    /* renamed from: ID */
    private static final String f1585ID = com.google.android.gms.internal.gtm.zza.ARBITRARY_PIXEL.toString();
    private static final String URL = zzb.URL.toString();
    private static final String zzadw = zzb.ADDITIONAL_PARAMS.toString();
    private static final String zzadx = zzb.UNREPEATABLE.toString();
    private static final String zzady;
    private static final Set<String> zzadz = new HashSet();
    private final zza zzaea;
    private final Context zzrm;

    public interface zza {
        zzbx zzgx();
    }

    public zzm(Context context) {
        this(context, new zzn(context));
    }

    private zzm(Context context, zza zza2) {
        super(f1585ID, URL);
        this.zzaea = zza2;
        this.zzrm = context;
    }

    public final void zzd(Map<String, zzl> map) {
        String zzc = map.get(zzadx) != null ? zzgj.zzc((zzl) map.get(zzadx)) : null;
        if (zzc == null || !zzak(zzc)) {
            Builder buildUpon = Uri.parse(zzgj.zzc((zzl) map.get(URL))).buildUpon();
            zzl zzl = (zzl) map.get(zzadw);
            if (zzl != null) {
                Object zzh = zzgj.zzh(zzl);
                if (!(zzh instanceof List)) {
                    String str = "ArbitraryPixel: additional params not a list: not sending partial hit: ";
                    String valueOf = String.valueOf(buildUpon.build().toString());
                    zzdi.zzav(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
                    return;
                }
                for (Object next : (List) zzh) {
                    if (!(next instanceof Map)) {
                        String str2 = "ArbitraryPixel: additional params contains non-map: not sending partial hit: ";
                        String valueOf2 = String.valueOf(buildUpon.build().toString());
                        zzdi.zzav(valueOf2.length() != 0 ? str2.concat(valueOf2) : new String(str2));
                        return;
                    }
                    for (Entry entry : ((Map) next).entrySet()) {
                        buildUpon.appendQueryParameter(entry.getKey().toString(), entry.getValue().toString());
                    }
                }
            }
            String uri = buildUpon.build().toString();
            this.zzaea.zzgx().zzay(uri);
            String str3 = "ArbitraryPixel: url = ";
            String valueOf3 = String.valueOf(uri);
            zzdi.zzab(valueOf3.length() != 0 ? str3.concat(valueOf3) : new String(str3));
            if (zzc != null) {
                synchronized (zzm.class) {
                    zzadz.add(zzc);
                    zzft.zza(this.zzrm, zzady, zzc, "true");
                }
            }
        }
    }

    private final synchronized boolean zzak(String str) {
        if (zzadz.contains(str)) {
            return true;
        }
        if (!this.zzrm.getSharedPreferences(zzady, 0).contains(str)) {
            return false;
        }
        zzadz.add(str);
        return true;
    }

    static {
        String str = f1585ID;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 17);
        sb.append("gtm_");
        sb.append(str);
        sb.append("_unrepeatable");
        zzady = sb.toString();
    }
}
