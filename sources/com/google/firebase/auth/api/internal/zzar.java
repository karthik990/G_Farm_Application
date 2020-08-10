package com.google.firebase.auth.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.api.internal.TaskApiCall.Builder;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzbn;
import com.google.android.gms.internal.firebase_auth.zze;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.internal.zza;

final class zzar extends zzen<Void, zza> {
    private final zzbn zzmp;

    public zzar(String str, String str2) {
        super(7);
        Preconditions.checkNotEmpty(str, "code cannot be null or empty");
        this.zzmp = new zzbn(str, str2);
    }

    public final String zzdu() {
        return "applyActionCode";
    }

    public final TaskApiCall<zzdp, Void> zzdv() {
        Feature[] featureArr;
        Builder autoResolveMissingFeatures = TaskApiCall.builder().setAutoResolveMissingFeatures(false);
        if (this.zzqh) {
            featureArr = null;
        } else {
            featureArr = new Feature[]{zze.zzf};
        }
        return autoResolveMissingFeatures.setFeatures(featureArr).run(new zzaq(this)).build();
    }

    public final void zzdx() {
        zzc(null);
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ void zza(zzdp zzdp, TaskCompletionSource taskCompletionSource) throws RemoteException {
        this.zzpu = new zzeu(this, taskCompletionSource);
        if (this.zzqh) {
            zzdp.zzeb().zzj(this.zzmp.zzcn(), this.zzpq);
        } else {
            zzdp.zzeb().zza(this.zzmp, (zzdu) this.zzpq);
        }
    }
}
