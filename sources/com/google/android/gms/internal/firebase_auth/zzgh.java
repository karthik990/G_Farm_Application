package com.google.android.gms.internal.firebase_auth;

import java.util.Comparator;

final class zzgh implements Comparator<zzgf> {
    zzgh() {
    }

    public final /* synthetic */ int compare(Object obj, Object obj2) {
        zzgf zzgf = (zzgf) obj;
        zzgf zzgf2 = (zzgf) obj2;
        zzgo zzgo = (zzgo) zzgf.iterator();
        zzgo zzgo2 = (zzgo) zzgf2.iterator();
        while (zzgo.hasNext() && zzgo2.hasNext()) {
            int compare = Integer.compare(zzgf.zza(zzgo.nextByte()), zzgf.zza(zzgo2.nextByte()));
            if (compare != 0) {
                return compare;
            }
        }
        return Integer.compare(zzgf.size(), zzgf2.size());
    }
}
