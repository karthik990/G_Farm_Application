package com.google.firebase.auth.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.api.internal.TaskApiCall.Builder;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzcj;
import com.google.android.gms.internal.firebase_auth.zze;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.internal.zza;
import com.google.firebase.auth.internal.zzg;
import com.google.firebase.auth.internal.zzm;

final class zzbf extends zzen<AuthResult, zza> {
    private final EmailAuthCredential zzkn;

    public zzbf(EmailAuthCredential emailAuthCredential) {
        super(2);
        this.zzkn = (EmailAuthCredential) Preconditions.checkNotNull(emailAuthCredential, "credential cannot be null");
        Preconditions.checkNotEmpty(emailAuthCredential.getEmail(), "email cannot be null");
        Preconditions.checkNotEmpty(emailAuthCredential.getPassword(), "password cannot be null");
    }

    public final String zzdu() {
        return "linkEmailAuthCredential";
    }

    public final TaskApiCall<zzdp, AuthResult> zzdv() {
        Feature[] featureArr;
        Builder autoResolveMissingFeatures = TaskApiCall.builder().setAutoResolveMissingFeatures(false);
        if (this.zzqh) {
            featureArr = null;
        } else {
            featureArr = new Feature[]{zze.zzf};
        }
        return autoResolveMissingFeatures.setFeatures(featureArr).run(new zzbe(this)).build();
    }

    public final void zzdx() {
        zzm zza = zzap.zza(this.zzik, this.zzpz);
        ((zza) this.zzps).zza(this.zzpy, zza);
        zzc(new zzg(zza));
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ void zzf(zzdp zzdp, TaskCompletionSource taskCompletionSource) throws RemoteException {
        this.zzpu = new zzeu(this, taskCompletionSource);
        if (this.zzqh) {
            zzdp.zzeb().zza(this.zzkn.getEmail(), this.zzkn.getPassword(), this.zzpr.zzcz(), this.zzpq);
        } else {
            zzdp.zzeb().zza(new zzcj(this.zzkn.getEmail(), this.zzkn.getPassword(), this.zzpr.zzcz()), (zzdu) this.zzpq);
        }
    }
}
