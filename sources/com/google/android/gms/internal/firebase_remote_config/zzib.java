package com.google.android.gms.internal.firebase_remote_config;

import java.util.List;

final class zzib extends zzhy {
    private zzib() {
        super();
    }

    /* access modifiers changed from: 0000 */
    public final <L> List<L> zza(Object obj, long j) {
        zzhn zzd = zzd(obj, j);
        if (zzd.zzeq()) {
            return zzd;
        }
        int size = zzd.size();
        zzhn zzu = zzd.zzu(size == 0 ? 10 : size << 1);
        zzkh.zza(obj, j, (Object) zzu);
        return zzu;
    }

    /* access modifiers changed from: 0000 */
    public final void zzb(Object obj, long j) {
        zzd(obj, j).zzer();
    }

    /* access modifiers changed from: 0000 */
    public final <E> void zza(Object obj, Object obj2, long j) {
        zzhn zzd = zzd(obj, j);
        zzhn zzd2 = zzd(obj2, j);
        int size = zzd.size();
        int size2 = zzd2.size();
        if (size > 0 && size2 > 0) {
            if (!zzd.zzeq()) {
                zzd = zzd.zzu(size2 + size);
            }
            zzd.addAll(zzd2);
        }
        if (size > 0) {
            zzd2 = zzd;
        }
        zzkh.zza(obj, j, (Object) zzd2);
    }

    private static <E> zzhn<E> zzd(Object obj, long j) {
        return (zzhn) zzkh.zzp(obj, j);
    }
}
