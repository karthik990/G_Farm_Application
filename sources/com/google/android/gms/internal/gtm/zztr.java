package com.google.android.gms.internal.gtm;

import java.io.IOException;

abstract class zztr<T, B> {
    zztr() {
    }

    /* access modifiers changed from: 0000 */
    public abstract void zza(B b, int i, long j);

    /* access modifiers changed from: 0000 */
    public abstract void zza(B b, int i, zzps zzps);

    /* access modifiers changed from: 0000 */
    public abstract void zza(B b, int i, T t);

    /* access modifiers changed from: 0000 */
    public abstract void zza(T t, zzum zzum) throws IOException;

    /* access modifiers changed from: 0000 */
    public abstract boolean zza(zzsy zzsy);

    /* access modifiers changed from: 0000 */
    public abstract T zzaa(B b);

    /* access modifiers changed from: 0000 */
    public abstract int zzad(T t);

    /* access modifiers changed from: 0000 */
    public abstract T zzag(Object obj);

    /* access modifiers changed from: 0000 */
    public abstract B zzah(Object obj);

    /* access modifiers changed from: 0000 */
    public abstract int zzai(T t);

    /* access modifiers changed from: 0000 */
    public abstract void zzb(B b, int i, long j);

    /* access modifiers changed from: 0000 */
    public abstract void zzc(B b, int i, int i2);

    /* access modifiers changed from: 0000 */
    public abstract void zzc(T t, zzum zzum) throws IOException;

    /* access modifiers changed from: 0000 */
    public abstract void zzf(Object obj, T t);

    /* access modifiers changed from: 0000 */
    public abstract void zzg(Object obj, B b);

    /* access modifiers changed from: 0000 */
    public abstract T zzh(T t, T t2);

    /* access modifiers changed from: 0000 */
    public abstract B zzri();

    /* access modifiers changed from: 0000 */
    public abstract void zzt(Object obj);

    /* access modifiers changed from: 0000 */
    public final boolean zza(B b, zzsy zzsy) throws IOException {
        int tag = zzsy.getTag();
        int i = tag >>> 3;
        int i2 = tag & 7;
        if (i2 == 0) {
            zza(b, i, zzsy.zznk());
            return true;
        } else if (i2 == 1) {
            zzb(b, i, zzsy.zznm());
            return true;
        } else if (i2 == 2) {
            zza(b, i, zzsy.zznq());
            return true;
        } else if (i2 == 3) {
            Object zzri = zzri();
            int i3 = 4 | (i << 3);
            while (zzsy.zzog() != Integer.MAX_VALUE) {
                if (!zza((B) zzri, zzsy)) {
                    break;
                }
            }
            if (i3 == zzsy.getTag()) {
                zza(b, i, (T) zzaa(zzri));
                return true;
            }
            throw zzrk.zzps();
        } else if (i2 == 4) {
            return false;
        } else {
            if (i2 == 5) {
                zzc(b, i, zzsy.zznn());
                return true;
            }
            throw zzrk.zzpt();
        }
    }
}
