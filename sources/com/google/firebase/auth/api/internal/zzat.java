package com.google.firebase.auth.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.api.internal.TaskApiCall.Builder;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzbt;
import com.google.android.gms.internal.firebase_auth.zze;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.ActionCodeResult;
import com.google.firebase.auth.internal.zza;
import com.google.firebase.auth.internal.zzb;

final class zzat extends zzen<ActionCodeResult, zza> {
    private final zzbt zzmr;

    public zzat(String str, String str2) {
        super(4);
        Preconditions.checkNotEmpty(str, "code cannot be null or empty");
        this.zzmr = new zzbt(str, str2);
    }

    public final String zzdu() {
        return "checkActionCode";
    }

    public final TaskApiCall<zzdp, ActionCodeResult> zzdv() {
        Feature[] featureArr;
        Builder autoResolveMissingFeatures = TaskApiCall.builder().setAutoResolveMissingFeatures(false);
        if (this.zzqh) {
            featureArr = null;
        } else {
            featureArr = new Feature[]{zze.zzf};
        }
        return autoResolveMissingFeatures.setFeatures(featureArr).run(new zzas(this)).build();
    }

    public final void zzdx() {
        zzc(new zzb(this.zzqb));
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ void zzb(zzdp zzdp, TaskCompletionSource taskCompletionSource) throws RemoteException {
        this.zzpu = new zzeu(this, taskCompletionSource);
        if (this.zzqh) {
            zzdp.zzeb().zzi(this.zzmr.zzcn(), this.zzpq);
        } else {
            zzdp.zzeb().zza(this.zzmr, (zzdu) this.zzpq);
        }
    }
}
