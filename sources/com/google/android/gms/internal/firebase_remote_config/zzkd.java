package com.google.android.gms.internal.firebase_remote_config;

import java.io.IOException;

final class zzkd extends zzkb<zzkc, zzkc> {
    zzkd() {
    }

    /* access modifiers changed from: 0000 */
    public final boolean zza(zzje zzje) {
        return false;
    }

    private static void zza(Object obj, zzkc zzkc) {
        ((zzhi) obj).zzsw = zzkc;
    }

    /* access modifiers changed from: 0000 */
    public final void zzm(Object obj) {
        ((zzhi) obj).zzsw.zzer();
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ int zzw(Object obj) {
        return ((zzkc) obj).zzgo();
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ int zzac(Object obj) {
        return ((zzkc) obj).zzjg();
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ Object zzi(Object obj, Object obj2) {
        zzkc zzkc = (zzkc) obj;
        zzkc zzkc2 = (zzkc) obj2;
        if (zzkc2.equals(zzkc.zzje())) {
            return zzkc;
        }
        return zzkc.zza(zzkc, zzkc2);
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ void zzc(Object obj, zzkw zzkw) throws IOException {
        ((zzkc) obj).zza(zzkw);
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ void zza(Object obj, zzkw zzkw) throws IOException {
        ((zzkc) obj).zzb(zzkw);
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ void zzh(Object obj, Object obj2) {
        zza(obj, (zzkc) obj2);
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ Object zzab(Object obj) {
        zzkc zzkc = ((zzhi) obj).zzsw;
        if (zzkc != zzkc.zzje()) {
            return zzkc;
        }
        zzkc zzjf = zzkc.zzjf();
        zza(obj, zzjf);
        return zzjf;
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ Object zzaa(Object obj) {
        return ((zzhi) obj).zzsw;
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ void zzg(Object obj, Object obj2) {
        zza(obj, (zzkc) obj2);
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ Object zzt(Object obj) {
        zzkc zzkc = (zzkc) obj;
        zzkc.zzer();
        return zzkc;
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ Object zzjd() {
        return zzkc.zzjf();
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ void zza(Object obj, int i, Object obj2) {
        ((zzkc) obj).zzb((i << 3) | 3, (Object) (zzkc) obj2);
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ void zza(Object obj, int i, zzfw zzfw) {
        ((zzkc) obj).zzb((i << 3) | 2, (Object) zzfw);
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ void zzb(Object obj, int i, long j) {
        ((zzkc) obj).zzb((i << 3) | 1, (Object) Long.valueOf(j));
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ void zzc(Object obj, int i, int i2) {
        ((zzkc) obj).zzb((i << 3) | 5, (Object) Integer.valueOf(i2));
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ void zza(Object obj, int i, long j) {
        ((zzkc) obj).zzb(i << 3, (Object) Long.valueOf(j));
    }
}
