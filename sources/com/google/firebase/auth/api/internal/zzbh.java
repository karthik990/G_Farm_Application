package com.google.firebase.auth.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.api.internal.TaskApiCall.Builder;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzcl;
import com.google.android.gms.internal.firebase_auth.zze;
import com.google.android.gms.internal.firebase_auth.zzfm;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.internal.zza;
import com.google.firebase.auth.internal.zzc;
import com.google.firebase.auth.internal.zzg;
import com.google.firebase.auth.internal.zzm;

final class zzbh extends zzen<AuthResult, zza> {
    private final zzfm zznd;

    public zzbh(AuthCredential authCredential) {
        super(2);
        Preconditions.checkNotNull(authCredential, "credential cannot be null");
        this.zznd = zzc.zza(authCredential, null);
    }

    public final String zzdu() {
        return "linkFederatedCredential";
    }

    public final TaskApiCall<zzdp, AuthResult> zzdv() {
        Feature[] featureArr;
        Builder autoResolveMissingFeatures = TaskApiCall.builder().setAutoResolveMissingFeatures(false);
        if (this.zzqh) {
            featureArr = null;
        } else {
            featureArr = new Feature[]{zze.zzf};
        }
        return autoResolveMissingFeatures.setFeatures(featureArr).run(new zzbg(this)).build();
    }

    public final void zzdx() {
        zzm zza = zzap.zza(this.zzik, this.zzpz);
        ((zza) this.zzps).zza(this.zzpy, zza);
        zzc(new zzg(zza));
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ void zzg(zzdp zzdp, TaskCompletionSource taskCompletionSource) throws RemoteException {
        this.zzpu = new zzeu(this, taskCompletionSource);
        if (this.zzqh) {
            zzdp.zzeb().zza(this.zzpr.zzcz(), this.zznd, (zzdu) this.zzpq);
        } else {
            zzdp.zzeb().zza(new zzcl(this.zzpr.zzcz(), this.zznd), (zzdu) this.zzpq);
        }
    }
}
