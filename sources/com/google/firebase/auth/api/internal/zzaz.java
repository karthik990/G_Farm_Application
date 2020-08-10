package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.api.internal.TaskApiCall.Builder;
import com.google.android.gms.internal.firebase_auth.zze;
import com.google.firebase.auth.internal.zzaa;

final class zzaz extends zzen<Void, zzaa> {
    public zzaz() {
        super(5);
    }

    public final String zzdu() {
        return "delete";
    }

    public final TaskApiCall<zzdp, Void> zzdv() {
        Feature[] featureArr;
        Builder autoResolveMissingFeatures = TaskApiCall.builder().setAutoResolveMissingFeatures(false);
        if (this.zzqh) {
            featureArr = null;
        } else {
            featureArr = new Feature[]{zze.zzf};
        }
        return autoResolveMissingFeatures.setFeatures(featureArr).run(new zzay(this)).build();
    }

    public final void zzdx() {
        ((zzaa) this.zzps).zzcv();
        zzc(null);
    }
}
