package com.google.firebase.auth.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.api.internal.TaskApiCall.Builder;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzcn;
import com.google.android.gms.internal.firebase_auth.zze;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.internal.zza;

final class zzdf extends zzen<Void, zza> {
    private final PhoneAuthCredential zzkj;

    public zzdf(PhoneAuthCredential phoneAuthCredential) {
        super(2);
        this.zzkj = (PhoneAuthCredential) Preconditions.checkNotNull(phoneAuthCredential);
    }

    public final String zzdu() {
        return "updatePhoneNumber";
    }

    public final TaskApiCall<zzdp, Void> zzdv() {
        Feature[] featureArr;
        Builder autoResolveMissingFeatures = TaskApiCall.builder().setAutoResolveMissingFeatures(false);
        if (this.zzqh) {
            featureArr = null;
        } else {
            featureArr = new Feature[]{zze.zzf};
        }
        return autoResolveMissingFeatures.setFeatures(featureArr).run(new zzde(this)).build();
    }

    public final void zzdx() {
        ((zza) this.zzps).zza(this.zzpy, zzap.zza(this.zzik, this.zzpz));
        zzc(null);
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ void zzad(zzdp zzdp, TaskCompletionSource taskCompletionSource) throws RemoteException {
        this.zzpu = new zzeu(this, taskCompletionSource);
        if (this.zzqh) {
            zzdp.zzeb().zza(this.zzpr.zzcz(), this.zzkj, (zzdu) this.zzpq);
        } else {
            zzdp.zzeb().zza(new zzcn(this.zzpr.zzcz(), this.zzkj), (zzdu) this.zzpq);
        }
    }
}
