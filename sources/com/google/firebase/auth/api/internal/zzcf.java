package com.google.firebase.auth.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.api.internal.TaskApiCall.Builder;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzcr;
import com.google.android.gms.internal.firebase_auth.zze;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.internal.zza;

final class zzcf extends zzen<Void, zza> {
    private final zzcr zznu;

    public zzcf(String str, ActionCodeSettings actionCodeSettings) {
        super(6);
        Preconditions.checkNotEmpty(str, "token cannot be null or empty");
        this.zznu = new zzcr(str, actionCodeSettings);
    }

    public final String zzdu() {
        return "sendEmailVerification";
    }

    public final TaskApiCall<zzdp, Void> zzdv() {
        Feature[] featureArr;
        Builder autoResolveMissingFeatures = TaskApiCall.builder().setAutoResolveMissingFeatures(false);
        if (this.zzqh) {
            featureArr = null;
        } else {
            featureArr = new Feature[]{zze.zzf};
        }
        return autoResolveMissingFeatures.setFeatures(featureArr).run(new zzce(this)).build();
    }

    public final void zzdx() {
        zzc(null);
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ void zzr(zzdp zzdp, TaskCompletionSource taskCompletionSource) throws RemoteException {
        this.zzpu = new zzeu(this, taskCompletionSource);
        if (this.zzqh) {
            zzdp.zzeb().zzb(this.zznu.getToken(), this.zznu.zzdj(), (zzdu) this.zzpq);
        } else {
            zzdp.zzeb().zza(this.zznu, (zzdu) this.zzpq);
        }
    }
}
