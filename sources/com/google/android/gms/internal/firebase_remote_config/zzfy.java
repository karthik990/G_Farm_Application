package com.google.android.gms.internal.firebase_remote_config;

import java.util.Comparator;

final class zzfy implements Comparator<zzfw> {
    zzfy() {
    }

    public final /* synthetic */ int compare(Object obj, Object obj2) {
        zzfw zzfw = (zzfw) obj;
        zzfw zzfw2 = (zzfw) obj2;
        zzgd zzgd = (zzgd) zzfw.iterator();
        zzgd zzgd2 = (zzgd) zzfw2.iterator();
        while (zzgd.hasNext() && zzgd2.hasNext()) {
            int compare = Integer.compare(zzfw.zza(zzgd.nextByte()), zzfw.zza(zzgd2.nextByte()));
            if (compare != 0) {
                return compare;
            }
        }
        return Integer.compare(zzfw.size(), zzfw2.size());
    }
}
