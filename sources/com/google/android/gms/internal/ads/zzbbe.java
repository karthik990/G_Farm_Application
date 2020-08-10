package com.google.android.gms.internal.ads;

import com.google.android.gms.internal.ads.zzbbo.zzc;
import java.io.IOException;
import java.util.Map.Entry;

final class zzbbe extends zzbbd<Object> {
    zzbbe() {
    }

    /* access modifiers changed from: 0000 */
    public final int zza(Entry<?, ?> entry) {
        entry.getKey();
        throw new NoSuchMethodError();
    }

    /* access modifiers changed from: 0000 */
    public final Object zza(zzbbb zzbbb, zzbcu zzbcu, int i) {
        return zzbbb.zza(zzbcu, i);
    }

    /* access modifiers changed from: 0000 */
    public final <UT, UB> UB zza(zzbdl zzbdl, Object obj, zzbbb zzbbb, zzbbg<Object> zzbbg, UB ub, zzbee<UT, UB> zzbee) throws IOException {
        throw new NoSuchMethodError();
    }

    /* access modifiers changed from: 0000 */
    public final void zza(zzbah zzbah, Object obj, zzbbb zzbbb, zzbbg<Object> zzbbg) throws IOException {
        throw new NoSuchMethodError();
    }

    /* access modifiers changed from: 0000 */
    public final void zza(zzbdl zzbdl, Object obj, zzbbb zzbbb, zzbbg<Object> zzbbg) throws IOException {
        throw new NoSuchMethodError();
    }

    /* access modifiers changed from: 0000 */
    public final void zza(zzbey zzbey, Entry<?, ?> entry) throws IOException {
        entry.getKey();
        throw new NoSuchMethodError();
    }

    /* access modifiers changed from: 0000 */
    public final void zza(Object obj, zzbbg<Object> zzbbg) {
        ((zzc) obj).zzdtz = zzbbg;
    }

    /* access modifiers changed from: 0000 */
    public final boolean zzh(zzbcu zzbcu) {
        return zzbcu instanceof zzc;
    }

    /* access modifiers changed from: 0000 */
    public final zzbbg<Object> zzm(Object obj) {
        return ((zzc) obj).zzdtz;
    }

    /* access modifiers changed from: 0000 */
    public final zzbbg<Object> zzn(Object obj) {
        zzbbg<Object> zzm = zzm(obj);
        if (!zzm.isImmutable()) {
            return zzm;
        }
        zzbbg<Object> zzbbg = (zzbbg) zzm.clone();
        zza(obj, zzbbg);
        return zzbbg;
    }

    /* access modifiers changed from: 0000 */
    public final void zzo(Object obj) {
        zzm(obj).zzaaz();
    }
}
