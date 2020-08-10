package com.google.firebase.auth.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.api.internal.TaskApiCall.Builder;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzcv;
import com.google.android.gms.internal.firebase_auth.zze;
import com.google.android.gms.internal.firebase_auth.zzfe;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks;

final class zzdl extends zzen<Void, OnVerificationStateChangedCallbacks> {
    private final zzcv zzoq;

    public zzdl(zzfe zzfe) {
        super(8);
        Preconditions.checkNotNull(zzfe);
        this.zzoq = new zzcv(zzfe);
    }

    public final String zzdu() {
        return "verifyPhoneNumber";
    }

    public final void zzdx() {
    }

    public final TaskApiCall<zzdp, Void> zzdv() {
        Feature[] featureArr;
        Builder autoResolveMissingFeatures = TaskApiCall.builder().setAutoResolveMissingFeatures(false);
        if (this.zzqh) {
            featureArr = null;
        } else {
            featureArr = new Feature[]{zze.zzf};
        }
        return autoResolveMissingFeatures.setFeatures(featureArr).run(new zzdk(this)).build();
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ void zzag(zzdp zzdp, TaskCompletionSource taskCompletionSource) throws RemoteException {
        this.zzpu = new zzeu(this, taskCompletionSource);
        if (this.zzqh) {
            zzdp.zzeb().zza(this.zzoq.zzdk(), (zzdu) this.zzpq);
        } else {
            zzdp.zzeb().zza(this.zzoq, (zzdu) this.zzpq);
        }
    }
}
