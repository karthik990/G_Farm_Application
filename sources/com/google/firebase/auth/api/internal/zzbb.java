package com.google.firebase.auth.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.api.internal.TaskApiCall.Builder;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzch;
import com.google.android.gms.internal.firebase_auth.zze;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.auth.internal.zza;
import com.google.firebase.auth.internal.zzw;

final class zzbb extends zzen<SignInMethodQueryResult, zza> {
    private final zzch zzmy;

    public zzbb(String str, String str2) {
        super(3);
        Preconditions.checkNotEmpty(str, "email cannot be null or empty");
        this.zzmy = new zzch(str, str2);
    }

    public final String zzdu() {
        return "fetchSignInMethodsForEmail";
    }

    public final TaskApiCall<zzdp, SignInMethodQueryResult> zzdv() {
        Feature[] featureArr;
        Builder autoResolveMissingFeatures = TaskApiCall.builder().setAutoResolveMissingFeatures(false);
        if (this.zzqh) {
            featureArr = null;
        } else {
            featureArr = new Feature[]{zze.zzf};
        }
        return autoResolveMissingFeatures.setFeatures(featureArr).run(new zzba(this)).build();
    }

    public final void zzdx() {
        zzc(new zzw(this.zzqa.getSignInMethods()));
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ void zzd(zzdp zzdp, TaskCompletionSource taskCompletionSource) throws RemoteException {
        this.zzpu = new zzeu(this, taskCompletionSource);
        if (this.zzqh) {
            zzdp.zzeb().zzc(this.zzmy.getEmail(), this.zzpq);
        } else {
            zzdp.zzeb().zza(this.zzmy, (zzdu) this.zzpq);
        }
    }
}
