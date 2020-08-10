package com.google.android.gms.ads.internal.gmsg;

import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.gms.internal.ads.zzaqw;
import java.util.Map;

final class zzo implements zzv<zzaqw> {
    zzo() {
    }

    public final /* synthetic */ void zza(Object obj, Map map) {
        ((zzaqw) obj).zzu(IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE.equals(map.get("custom_close")));
    }
}
