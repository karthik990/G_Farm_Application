package com.google.android.gms.internal.gtm;

import java.util.List;

final class zzrx extends zzru {
    private zzrx() {
        super();
    }

    /* access modifiers changed from: 0000 */
    public final <L> List<L> zza(Object obj, long j) {
        zzrj zzd = zzd(obj, j);
        if (zzd.zzmy()) {
            return zzd;
        }
        int size = zzd.size();
        zzrj zzaj = zzd.zzaj(size == 0 ? 10 : size << 1);
        zztx.zza(obj, j, (Object) zzaj);
        return zzaj;
    }

    /* access modifiers changed from: 0000 */
    public final void zzb(Object obj, long j) {
        zzd(obj, j).zzmi();
    }

    /* access modifiers changed from: 0000 */
    public final <E> void zza(Object obj, Object obj2, long j) {
        zzrj zzd = zzd(obj, j);
        zzrj zzd2 = zzd(obj2, j);
        int size = zzd.size();
        int size2 = zzd2.size();
        if (size > 0 && size2 > 0) {
            if (!zzd.zzmy()) {
                zzd = zzd.zzaj(size2 + size);
            }
            zzd.addAll(zzd2);
        }
        if (size > 0) {
            zzd2 = zzd;
        }
        zztx.zza(obj, j, (Object) zzd2);
    }

    private static <E> zzrj<E> zzd(Object obj, long j) {
        return (zzrj) zztx.zzp(obj, j);
    }
}
