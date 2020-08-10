package com.google.android.gms.internal.firebase_auth;

import java.io.IOException;

abstract class zzkk<T, B> {
    zzkk() {
    }

    /* access modifiers changed from: 0000 */
    public abstract void zza(B b, int i, long j);

    /* access modifiers changed from: 0000 */
    public abstract void zza(B b, int i, zzgf zzgf);

    /* access modifiers changed from: 0000 */
    public abstract void zza(B b, int i, T t);

    /* access modifiers changed from: 0000 */
    public abstract void zza(T t, zzlh zzlh) throws IOException;

    /* access modifiers changed from: 0000 */
    public abstract boolean zza(zzjp zzjp);

    /* access modifiers changed from: 0000 */
    public abstract void zzb(B b, int i, long j);

    /* access modifiers changed from: 0000 */
    public abstract void zzc(B b, int i, int i2);

    /* access modifiers changed from: 0000 */
    public abstract void zzc(T t, zzlh zzlh) throws IOException;

    /* access modifiers changed from: 0000 */
    public abstract void zzf(Object obj);

    /* access modifiers changed from: 0000 */
    public abstract void zzf(Object obj, T t);

    /* access modifiers changed from: 0000 */
    public abstract void zzg(Object obj, B b);

    /* access modifiers changed from: 0000 */
    public abstract T zzh(T t, T t2);

    /* access modifiers changed from: 0000 */
    public abstract B zzkn();

    /* access modifiers changed from: 0000 */
    public abstract T zzl(B b);

    /* access modifiers changed from: 0000 */
    public abstract int zzq(T t);

    /* access modifiers changed from: 0000 */
    public abstract T zzs(Object obj);

    /* access modifiers changed from: 0000 */
    public abstract B zzt(Object obj);

    /* access modifiers changed from: 0000 */
    public abstract int zzu(T t);

    /* access modifiers changed from: 0000 */
    public final boolean zza(B b, zzjp zzjp) throws IOException {
        int tag = zzjp.getTag();
        int i = tag >>> 3;
        int i2 = tag & 7;
        if (i2 == 0) {
            zza(b, i, zzjp.zzgk());
            return true;
        } else if (i2 == 1) {
            zzb(b, i, zzjp.zzgm());
            return true;
        } else if (i2 == 2) {
            zza(b, i, zzjp.zzgq());
            return true;
        } else if (i2 == 3) {
            Object zzkn = zzkn();
            int i3 = 4 | (i << 3);
            while (zzjp.zzhg() != Integer.MAX_VALUE) {
                if (!zza((B) zzkn, zzjp)) {
                    break;
                }
            }
            if (i3 == zzjp.getTag()) {
                zza(b, i, (T) zzl(zzkn));
                return true;
            }
            throw zzic.zziv();
        } else if (i2 == 4) {
            return false;
        } else {
            if (i2 == 5) {
                zzc(b, i, zzjp.zzgn());
                return true;
            }
            throw zzic.zziw();
        }
    }
}
