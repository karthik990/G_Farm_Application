package com.google.android.gms.internal.ads;

import java.util.HashMap;

public final class zzdh extends zzbh<Integer, Long> {
    public Long zzgj;
    public Long zzgk;
    public Long zzsx;

    public zzdh() {
    }

    public zzdh(String str) {
        zzj(str);
    }

    /* access modifiers changed from: protected */
    public final void zzj(String str) {
        HashMap zzk = zzk(str);
        if (zzk != null) {
            this.zzsx = (Long) zzk.get(Integer.valueOf(0));
            this.zzgj = (Long) zzk.get(Integer.valueOf(1));
            this.zzgk = (Long) zzk.get(Integer.valueOf(2));
        }
    }

    /* access modifiers changed from: protected */
    public final HashMap<Integer, Long> zzu() {
        HashMap<Integer, Long> hashMap = new HashMap<>();
        hashMap.put(Integer.valueOf(0), this.zzsx);
        hashMap.put(Integer.valueOf(1), this.zzgj);
        hashMap.put(Integer.valueOf(2), this.zzgk);
        return hashMap;
    }
}
