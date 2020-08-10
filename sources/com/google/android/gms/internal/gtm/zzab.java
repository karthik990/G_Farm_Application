package com.google.android.gms.internal.gtm;

import android.text.TextUtils;
import com.google.android.gms.analytics.zzi;
import java.util.HashMap;

public final class zzab extends zzi<zzab> {
    public String zzvh;
    public String zzvi;
    public String zzvj;

    public final String toString() {
        HashMap hashMap = new HashMap();
        hashMap.put("network", this.zzvh);
        hashMap.put("action", this.zzvi);
        hashMap.put("target", this.zzvj);
        return zza((Object) hashMap);
    }

    public final /* synthetic */ void zzb(zzi zzi) {
        zzab zzab = (zzab) zzi;
        if (!TextUtils.isEmpty(this.zzvh)) {
            zzab.zzvh = this.zzvh;
        }
        if (!TextUtils.isEmpty(this.zzvi)) {
            zzab.zzvi = this.zzvi;
        }
        if (!TextUtils.isEmpty(this.zzvj)) {
            zzab.zzvj = this.zzvj;
        }
    }
}
