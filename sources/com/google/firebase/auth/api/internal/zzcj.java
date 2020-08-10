package com.google.firebase.auth.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.api.internal.TaskApiCall.Builder;
import com.google.android.gms.internal.firebase_auth.zzcx;
import com.google.android.gms.internal.firebase_auth.zze;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.internal.zza;

final class zzcj extends zzen<Void, zza> {
    private final zzcx zznz;

    public zzcj(String str) {
        super(9);
        this.zznz = new zzcx(str);
    }

    public final String zzdu() {
        return "setFirebaseUIVersion";
    }

    public final TaskApiCall<zzdp, Void> zzdv() {
        Feature[] featureArr;
        Builder autoResolveMissingFeatures = TaskApiCall.builder().setAutoResolveMissingFeatures(false);
        if (this.zzqh) {
            featureArr = null;
        } else {
            featureArr = new Feature[]{zze.zzf};
        }
        return autoResolveMissingFeatures.setFeatures(featureArr).run(new zzci(this)).build();
    }

    public final void zzdx() {
        zzc(null);
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ void zzt(zzdp zzdp, TaskCompletionSource taskCompletionSource) throws RemoteException {
        this.zzpu = new zzeu(this, taskCompletionSource);
        if (this.zzqh) {
            zzdp.zzeb().zzk(this.zznz.zzdl(), this.zzpq);
        } else {
            zzdp.zzeb().zza(this.zznz, (zzdu) this.zzpq);
        }
    }
}
