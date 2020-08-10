package com.google.android.gms.internal.gtm;

import android.content.Context;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.DefaultClock;
import java.util.HashMap;
import java.util.Map;

public final class zzoq {
    private String zzafk;
    private Map<String, Object> zzasp;
    private final Map<String, Object> zzasq;
    private final zzpa zzauz;
    private final Context zzrm;
    private final Clock zzsd;

    public zzoq(Context context) {
        this(context, new HashMap(), new zzpa(context), DefaultClock.getInstance());
    }

    private zzoq(Context context, Map<String, Object> map, zzpa zzpa, Clock clock) {
        this.zzafk = null;
        this.zzasp = new HashMap();
        this.zzrm = context;
        this.zzsd = clock;
        this.zzauz = zzpa;
        this.zzasq = map;
    }

    public final void zzcr(String str) {
        this.zzafk = str;
    }
}
