package com.google.android.gms.internal.gtm;

import java.io.IOException;

final class zztt extends zztr<zzts, zzts> {
    zztt() {
    }

    /* access modifiers changed from: 0000 */
    public final boolean zza(zzsy zzsy) {
        return false;
    }

    private static void zza(Object obj, zzts zzts) {
        ((zzrc) obj).zzbak = zzts;
    }

    /* access modifiers changed from: 0000 */
    public final void zzt(Object obj) {
        ((zzrc) obj).zzbak.zzmi();
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ int zzad(Object obj) {
        return ((zzts) obj).zzpe();
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ int zzai(Object obj) {
        return ((zzts) obj).zzrl();
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ Object zzh(Object obj, Object obj2) {
        zzts zzts = (zzts) obj;
        zzts zzts2 = (zzts) obj2;
        if (zzts2.equals(zzts.zzrj())) {
            return zzts;
        }
        return zzts.zza(zzts, zzts2);
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ void zzc(Object obj, zzum zzum) throws IOException {
        ((zzts) obj).zza(zzum);
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ void zza(Object obj, zzum zzum) throws IOException {
        ((zzts) obj).zzb(zzum);
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ void zzg(Object obj, Object obj2) {
        zza(obj, (zzts) obj2);
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ Object zzah(Object obj) {
        zzts zzts = ((zzrc) obj).zzbak;
        if (zzts != zzts.zzrj()) {
            return zzts;
        }
        zzts zzrk = zzts.zzrk();
        zza(obj, zzrk);
        return zzrk;
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ Object zzag(Object obj) {
        return ((zzrc) obj).zzbak;
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ void zzf(Object obj, Object obj2) {
        zza(obj, (zzts) obj2);
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ Object zzaa(Object obj) {
        zzts zzts = (zzts) obj;
        zzts.zzmi();
        return zzts;
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ Object zzri() {
        return zzts.zzrk();
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ void zza(Object obj, int i, Object obj2) {
        ((zzts) obj).zzb((i << 3) | 3, (zzts) obj2);
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ void zza(Object obj, int i, zzps zzps) {
        ((zzts) obj).zzb((i << 3) | 2, zzps);
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ void zzb(Object obj, int i, long j) {
        ((zzts) obj).zzb((i << 3) | 1, Long.valueOf(j));
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ void zzc(Object obj, int i, int i2) {
        ((zzts) obj).zzb((i << 3) | 5, Integer.valueOf(i2));
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ void zza(Object obj, int i, long j) {
        ((zzts) obj).zzb(i << 3, Long.valueOf(j));
    }
}
