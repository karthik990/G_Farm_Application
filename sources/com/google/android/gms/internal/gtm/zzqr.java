package com.google.android.gms.internal.gtm;

import com.google.android.gms.internal.gtm.zzrc.zzc;
import java.io.IOException;
import java.util.Map.Entry;

final class zzqr extends zzqq<Object> {
    zzqr() {
    }

    /* access modifiers changed from: 0000 */
    public final boolean zze(zzsk zzsk) {
        return zzsk instanceof zzc;
    }

    /* access modifiers changed from: 0000 */
    public final zzqt<Object> zzr(Object obj) {
        return ((zzc) obj).zzbaq;
    }

    /* access modifiers changed from: 0000 */
    public final zzqt<Object> zzs(Object obj) {
        zzc zzc = (zzc) obj;
        if (zzc.zzbaq.isImmutable()) {
            zzc.zzbaq = (zzqt) zzc.zzbaq.clone();
        }
        return zzc.zzbaq;
    }

    /* access modifiers changed from: 0000 */
    public final void zzt(Object obj) {
        zzr(obj).zzmi();
    }

    /* access modifiers changed from: 0000 */
    public final <UT, UB> UB zza(zzsy zzsy, Object obj, zzqp zzqp, zzqt<Object> zzqt, UB ub, zztr<UT, UB> zztr) throws IOException {
        throw new NoSuchMethodError();
    }

    /* access modifiers changed from: 0000 */
    public final int zzb(Entry<?, ?> entry) {
        entry.getKey();
        throw new NoSuchMethodError();
    }

    /* access modifiers changed from: 0000 */
    public final void zza(zzum zzum, Entry<?, ?> entry) throws IOException {
        entry.getKey();
        throw new NoSuchMethodError();
    }

    /* access modifiers changed from: 0000 */
    public final Object zza(zzqp zzqp, zzsk zzsk, int i) {
        return zzqp.zza(zzsk, i);
    }

    /* access modifiers changed from: 0000 */
    public final void zza(zzsy zzsy, Object obj, zzqp zzqp, zzqt<Object> zzqt) throws IOException {
        throw new NoSuchMethodError();
    }

    /* access modifiers changed from: 0000 */
    public final void zza(zzps zzps, Object obj, zzqp zzqp, zzqt<Object> zzqt) throws IOException {
        throw new NoSuchMethodError();
    }
}
