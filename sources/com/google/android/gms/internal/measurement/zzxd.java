package com.google.android.gms.internal.measurement;

import java.io.IOException;

abstract class zzxd<T, B> {
    zzxd() {
    }

    /* access modifiers changed from: 0000 */
    public abstract void zza(B b, int i, long j);

    /* access modifiers changed from: 0000 */
    public abstract void zza(B b, int i, zzte zzte);

    /* access modifiers changed from: 0000 */
    public abstract void zza(B b, int i, T t);

    /* access modifiers changed from: 0000 */
    public abstract void zza(T t, zzxy zzxy) throws IOException;

    /* access modifiers changed from: 0000 */
    public abstract boolean zza(zzwk zzwk);

    /* access modifiers changed from: 0000 */
    public abstract T zzaf(B b);

    /* access modifiers changed from: 0000 */
    public abstract int zzai(T t);

    /* access modifiers changed from: 0000 */
    public abstract T zzal(Object obj);

    /* access modifiers changed from: 0000 */
    public abstract B zzam(Object obj);

    /* access modifiers changed from: 0000 */
    public abstract int zzan(T t);

    /* access modifiers changed from: 0000 */
    public abstract void zzb(B b, int i, long j);

    /* access modifiers changed from: 0000 */
    public abstract void zzc(B b, int i, int i2);

    /* access modifiers changed from: 0000 */
    public abstract void zzc(T t, zzxy zzxy) throws IOException;

    /* access modifiers changed from: 0000 */
    public abstract void zzf(Object obj, T t);

    /* access modifiers changed from: 0000 */
    public abstract void zzg(Object obj, B b);

    /* access modifiers changed from: 0000 */
    public abstract T zzh(T t, T t2);

    /* access modifiers changed from: 0000 */
    public abstract void zzy(Object obj);

    /* access modifiers changed from: 0000 */
    public abstract B zzyk();

    /* access modifiers changed from: 0000 */
    public final boolean zza(B b, zzwk zzwk) throws IOException {
        int tag = zzwk.getTag();
        int i = tag >>> 3;
        int i2 = tag & 7;
        if (i2 == 0) {
            zza(b, i, zzwk.zzul());
            return true;
        } else if (i2 == 1) {
            zzb(b, i, zzwk.zzun());
            return true;
        } else if (i2 == 2) {
            zza(b, i, zzwk.zzur());
            return true;
        } else if (i2 == 3) {
            Object zzyk = zzyk();
            int i3 = 4 | (i << 3);
            while (zzwk.zzvh() != Integer.MAX_VALUE) {
                if (!zza((B) zzyk, zzwk)) {
                    break;
                }
            }
            if (i3 == zzwk.getTag()) {
                zza(b, i, (T) zzaf(zzyk));
                return true;
            }
            throw zzuv.zzwt();
        } else if (i2 == 4) {
            return false;
        } else {
            if (i2 == 5) {
                zzc(b, i, zzwk.zzuo());
                return true;
            }
            throw zzuv.zzwu();
        }
    }
}
