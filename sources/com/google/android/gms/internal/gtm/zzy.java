package com.google.android.gms.internal.gtm;

import android.text.TextUtils;
import com.anjlab.android.iab.p020v3.Constants;
import com.google.android.gms.analytics.zzi;
import com.google.android.gms.measurement.AppMeasurement.Param;
import java.util.HashMap;

public final class zzy extends zzi<zzy> {
    public String zzuq;
    public boolean zzur;

    public final String toString() {
        HashMap hashMap = new HashMap();
        hashMap.put(Constants.RESPONSE_DESCRIPTION, this.zzuq);
        hashMap.put(Param.FATAL, Boolean.valueOf(this.zzur));
        return zza((Object) hashMap);
    }

    public final /* synthetic */ void zzb(zzi zzi) {
        zzy zzy = (zzy) zzi;
        if (!TextUtils.isEmpty(this.zzuq)) {
            zzy.zzuq = this.zzuq;
        }
        boolean z = this.zzur;
        if (z) {
            zzy.zzur = z;
        }
    }
}
