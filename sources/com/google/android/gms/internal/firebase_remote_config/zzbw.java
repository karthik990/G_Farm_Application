package com.google.android.gms.internal.firebase_remote_config;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map.Entry;

final class zzbw extends AbstractSet<Entry<String, Object>> {
    private final /* synthetic */ zzbt zzfi;

    zzbw(zzbt zzbt) {
        this.zzfi = zzbt;
    }

    public final int size() {
        int i = 0;
        for (String zzae : this.zzfi.zzar.zzer) {
            if (this.zzfi.zzar.zzae(zzae).zzh(this.zzfi.zzff) != null) {
                i++;
            }
        }
        return i;
    }

    public final void clear() {
        for (String zzae : this.zzfi.zzar.zzer) {
            this.zzfi.zzar.zzae(zzae).zzb(this.zzfi.zzff, null);
        }
    }

    public final boolean isEmpty() {
        for (String zzae : this.zzfi.zzar.zzer) {
            if (this.zzfi.zzar.zzae(zzae).zzh(this.zzfi.zzff) != null) {
                return false;
            }
        }
        return true;
    }

    public final /* synthetic */ Iterator iterator() {
        return new zzbv(this.zzfi);
    }
}
