package com.google.android.gms.internal.gtm;

import java.util.Comparator;

final class zzpu implements Comparator<zzps> {
    zzpu() {
    }

    public final /* synthetic */ int compare(Object obj, Object obj2) {
        zzps zzps = (zzps) obj;
        zzps zzps2 = (zzps) obj2;
        zzpz zzpz = (zzpz) zzps.iterator();
        zzpz zzpz2 = (zzpz) zzps2.iterator();
        while (zzpz.hasNext() && zzpz2.hasNext()) {
            int compare = Integer.compare(zzps.zza(zzpz.nextByte()), zzps.zza(zzpz2.nextByte()));
            if (compare != 0) {
                return compare;
            }
        }
        return Integer.compare(zzps.size(), zzps2.size());
    }
}
