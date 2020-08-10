package com.google.firebase.auth.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.api.internal.TaskApiCall.Builder;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzdj;
import com.google.android.gms.internal.firebase_auth.zze;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.internal.zza;
import com.google.firebase.auth.internal.zzg;
import com.google.firebase.auth.internal.zzm;

final class zzcb extends zzen<AuthResult, zza> {
    private final zzdj zznq;

    public zzcb(PhoneAuthCredential phoneAuthCredential, String str) {
        super(2);
        Preconditions.checkNotNull(phoneAuthCredential, "credential cannot be null");
        this.zznq = new zzdj(phoneAuthCredential.zzn(false), str);
    }

    public final String zzdu() {
        return "reauthenticateWithPhoneCredentialWithData";
    }

    public final TaskApiCall<zzdp, AuthResult> zzdv() {
        Feature[] featureArr;
        Builder autoResolveMissingFeatures = TaskApiCall.builder().setAutoResolveMissingFeatures(false);
        if (this.zzqh) {
            featureArr = null;
        } else {
            featureArr = new Feature[]{zze.zzf};
        }
        return autoResolveMissingFeatures.setFeatures(featureArr).run(new zzca(this)).build();
    }

    public final void zzdx() {
        zzm zza = zzap.zza(this.zzik, this.zzpz);
        if (this.zzpr.getUid().equalsIgnoreCase(zza.getUid())) {
            ((zza) this.zzps).zza(this.zzpy, zza);
            zzc(new zzg(zza));
            return;
        }
        zzc(new Status(FirebaseError.ERROR_USER_MISMATCH));
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ void zzq(zzdp zzdp, TaskCompletionSource taskCompletionSource) throws RemoteException {
        this.zzpu = new zzeu(this, taskCompletionSource);
        if (this.zzqh) {
            zzdp.zzeb().zza(this.zznq.zzdi(), (zzdu) this.zzpq);
        } else {
            zzdp.zzeb().zza(this.zznq, (zzdu) this.zzpq);
        }
    }
}
