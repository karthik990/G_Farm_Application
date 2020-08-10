package com.google.firebase.auth.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.api.internal.TaskApiCall.Builder;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzdt;
import com.google.android.gms.internal.firebase_auth.zze;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.internal.zza;
import com.google.firebase.auth.internal.zzg;
import com.google.firebase.auth.internal.zzm;

final class zzcz extends zzen<AuthResult, zza> {
    private String zzks;

    public zzcz(String str) {
        super(2);
        this.zzks = Preconditions.checkNotEmpty(str, "provider cannot be null or empty");
    }

    public final String zzdu() {
        return "unlinkFederatedCredential";
    }

    public final TaskApiCall<zzdp, AuthResult> zzdv() {
        Feature[] featureArr;
        Builder autoResolveMissingFeatures = TaskApiCall.builder().setAutoResolveMissingFeatures(false);
        if (this.zzqh) {
            featureArr = null;
        } else {
            featureArr = new Feature[]{zze.zzf};
        }
        return autoResolveMissingFeatures.setFeatures(featureArr).run(new zzcy(this)).build();
    }

    public final void zzdx() {
        zzm zza = zzap.zza(this.zzik, this.zzpz);
        ((zza) this.zzps).zza(this.zzpy, zza);
        zzc(new zzg(zza));
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ void zzaa(zzdp zzdp, TaskCompletionSource taskCompletionSource) throws RemoteException {
        this.zzpu = new zzeu(this, taskCompletionSource);
        if (this.zzqh) {
            zzdp.zzeb().zze(this.zzks, this.zzpr.zzcz(), this.zzpq);
        } else {
            zzdp.zzeb().zza(new zzdt(this.zzks, this.zzpr.zzcz()), (zzdu) this.zzpq);
        }
    }
}
