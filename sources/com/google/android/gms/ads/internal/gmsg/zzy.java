package com.google.android.gms.ads.internal.gmsg;

import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzakb;
import java.util.Map;

@zzadh
public final class zzy implements zzv<Object> {
    private final zzz zzbmu;

    public zzy(zzz zzz) {
        this.zzbmu = zzz;
    }

    public final void zza(Object obj, Map<String, String> map) {
        String str = "blurRadius";
        Object obj2 = map.get("transparentBackground");
        String str2 = IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE;
        boolean equals = str2.equals(obj2);
        boolean equals2 = str2.equals(map.get("blur"));
        float f = 0.0f;
        try {
            if (map.get(str) != null) {
                f = Float.parseFloat((String) map.get(str));
            }
        } catch (NumberFormatException e) {
            zzakb.zzb("Fail to parse float", e);
        }
        this.zzbmu.zzd(equals);
        this.zzbmu.zza(equals2, f);
    }
}
