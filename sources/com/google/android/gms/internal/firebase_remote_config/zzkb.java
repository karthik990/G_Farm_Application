package com.google.android.gms.internal.firebase_remote_config;

import java.io.IOException;

abstract class zzkb<T, B> {
    zzkb() {
    }

    /* access modifiers changed from: 0000 */
    public abstract void zza(B b, int i, long j);

    /* access modifiers changed from: 0000 */
    public abstract void zza(B b, int i, zzfw zzfw);

    /* access modifiers changed from: 0000 */
    public abstract void zza(B b, int i, T t);

    /* access modifiers changed from: 0000 */
    public abstract void zza(T t, zzkw zzkw) throws IOException;

    /* access modifiers changed from: 0000 */
    public abstract boolean zza(zzje zzje);

    /* access modifiers changed from: 0000 */
    public abstract T zzaa(Object obj);

    /* access modifiers changed from: 0000 */
    public abstract B zzab(Object obj);

    /* access modifiers changed from: 0000 */
    public abstract int zzac(T t);

    /* access modifiers changed from: 0000 */
    public abstract void zzb(B b, int i, long j);

    /* access modifiers changed from: 0000 */
    public abstract void zzc(B b, int i, int i2);

    /* access modifiers changed from: 0000 */
    public abstract void zzc(T t, zzkw zzkw) throws IOException;

    /* access modifiers changed from: 0000 */
    public abstract void zzg(Object obj, T t);

    /* access modifiers changed from: 0000 */
    public abstract void zzh(Object obj, B b);

    /* access modifiers changed from: 0000 */
    public abstract T zzi(T t, T t2);

    /* access modifiers changed from: 0000 */
    public abstract B zzjd();

    /* access modifiers changed from: 0000 */
    public abstract void zzm(Object obj);

    /* access modifiers changed from: 0000 */
    public abstract T zzt(B b);

    /* access modifiers changed from: 0000 */
    public abstract int zzw(T t);

    /* access modifiers changed from: 0000 */
    public final boolean zza(B b, zzje zzje) throws IOException {
        int tag = zzje.getTag();
        int i = tag >>> 3;
        int i2 = tag & 7;
        if (i2 == 0) {
            zza(b, i, zzje.zzff());
            return true;
        } else if (i2 == 1) {
            zzb(b, i, zzje.zzfh());
            return true;
        } else if (i2 == 2) {
            zza(b, i, zzje.zzfl());
            return true;
        } else if (i2 == 3) {
            Object zzjd = zzjd();
            int i3 = 4 | (i << 3);
            while (zzje.zzgb() != Integer.MAX_VALUE) {
                if (!zza((B) zzjd, zzje)) {
                    break;
                }
            }
            if (i3 == zzje.getTag()) {
                zza(b, i, (T) zzt(zzjd));
                return true;
            }
            throw zzho.zzhk();
        } else if (i2 == 4) {
            return false;
        } else {
            if (i2 == 5) {
                zzc(b, i, zzje.zzfi());
                return true;
            }
            throw zzho.zzhl();
        }
    }
}
