package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.internal.Preconditions;
import java.util.Map;

final class zzal implements zzam {
    private final int zzmg;
    private final int zzmh;
    private final Map<String, Integer> zzmi;

    public zzal(int i, int i2, Map<String, Integer> map) {
        this.zzmg = i;
        this.zzmh = i2;
        this.zzmi = (Map) Preconditions.checkNotNull(map);
    }

    public final boolean zzbx(String str) {
        int i = this.zzmg;
        if (i == 0) {
            return true;
        }
        if (this.zzmh <= i) {
            return false;
        }
        Integer num = (Integer) this.zzmi.get(str);
        if (num == null) {
            num = Integer.valueOf(0);
        }
        if (num.intValue() <= this.zzmg || this.zzmh < num.intValue()) {
            return false;
        }
        return true;
    }
}
