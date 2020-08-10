package com.google.android.gms.internal.ads;

import android.text.TextUtils;
import com.google.android.gms.ads.internal.gmsg.zzv;
import java.lang.ref.WeakReference;
import java.util.Map;

public final class zzpe implements zzv<Object> {
    private final String zzaae;
    private final WeakReference<zzpa> zzyg;

    public zzpe(zzpa zzpa, String str) {
        this.zzyg = new WeakReference<>(zzpa);
        this.zzaae = str;
    }

    public final void zza(Object obj, Map<String, String> map) {
        String str = (String) map.get("ads_id");
        String str2 = (String) map.get("eventName");
        if (!TextUtils.isEmpty(str) && this.zzaae.equals(str)) {
            try {
                Integer.parseInt((String) map.get("eventType"));
            } catch (Exception e) {
                zzakb.zzb("Parse Scion log event type error", e);
            }
            if ("_ai".equals(str2)) {
                zzpa zzpa = (zzpa) this.zzyg.get();
                if (zzpa != null) {
                    zzpa.zzbr();
                }
            } else if ("_ac".equals(str2)) {
                zzpa zzpa2 = (zzpa) this.zzyg.get();
                if (zzpa2 != null) {
                    zzpa2.zzbs();
                }
            }
        }
    }
}
