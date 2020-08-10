package com.google.firebase.auth.api.internal;

import android.os.RemoteException;
import android.text.TextUtils;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.api.internal.TaskApiCall.Builder;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzcf;
import com.google.android.gms.internal.firebase_auth.zze;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.internal.zza;
import com.google.firebase.auth.internal.zzan;

final class zzbd extends zzen<GetTokenResult, zza> {
    private final zzcf zzna;

    public zzbd(String str) {
        super(1);
        Preconditions.checkNotEmpty(str, "refresh token cannot be null");
        this.zzna = new zzcf(str);
    }

    public final String zzdu() {
        return "getAccessToken";
    }

    public final TaskApiCall<zzdp, GetTokenResult> zzdv() {
        Feature[] featureArr;
        Builder autoResolveMissingFeatures = TaskApiCall.builder().setAutoResolveMissingFeatures(false);
        if (this.zzqh) {
            featureArr = null;
        } else {
            featureArr = new Feature[]{zze.zzf};
        }
        return autoResolveMissingFeatures.setFeatures(featureArr).run(new zzbc(this)).build();
    }

    public final void zzdx() {
        if (TextUtils.isEmpty(this.zzpy.zzs())) {
            this.zzpy.zzcm(this.zzna.zzs());
        }
        ((zza) this.zzps).zza(this.zzpy, this.zzpr);
        zzc(zzan.zzdf(this.zzpy.getAccessToken()));
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ void zze(zzdp zzdp, TaskCompletionSource taskCompletionSource) throws RemoteException {
        this.zzpu = new zzeu(this, taskCompletionSource);
        if (this.zzqh) {
            zzdp.zzeb().zza(this.zzna.zzs(), (zzdu) this.zzpq);
        } else {
            zzdp.zzeb().zza(this.zzna, (zzdu) this.zzpq);
        }
    }
}
