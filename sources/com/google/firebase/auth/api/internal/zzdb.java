package com.google.firebase.auth.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.api.internal.TaskApiCall.Builder;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzbp;
import com.google.android.gms.internal.firebase_auth.zze;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.internal.zza;

final class zzdb extends zzen<Void, zza> {
    private final String zzif;

    public zzdb(String str) {
        super(2);
        this.zzif = Preconditions.checkNotEmpty(str, "email cannot be null or empty");
    }

    public final String zzdu() {
        return "updateEmail";
    }

    public final TaskApiCall<zzdp, Void> zzdv() {
        Feature[] featureArr;
        Builder autoResolveMissingFeatures = TaskApiCall.builder().setAutoResolveMissingFeatures(false);
        if (this.zzqh) {
            featureArr = null;
        } else {
            featureArr = new Feature[]{zze.zzf};
        }
        return autoResolveMissingFeatures.setFeatures(featureArr).run(new zzda(this)).build();
    }

    public final void zzdx() {
        ((zza) this.zzps).zza(this.zzpy, zzap.zza(this.zzik, this.zzpz));
        zzc(null);
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ void zzab(zzdp zzdp, TaskCompletionSource taskCompletionSource) throws RemoteException {
        this.zzpu = new zzeu(this, taskCompletionSource);
        if (this.zzqh) {
            zzdp.zzeb().zza(this.zzpr.zzcz(), this.zzif, (zzdu) this.zzpq);
        } else {
            zzdp.zzeb().zza(new zzbp(this.zzpr.zzcz(), this.zzif), (zzdu) this.zzpq);
        }
    }
}
