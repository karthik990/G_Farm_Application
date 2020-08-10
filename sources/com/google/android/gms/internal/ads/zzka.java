package com.google.android.gms.internal.ads;

import android.app.Activity;
import android.os.RemoteException;
import com.google.android.gms.dynamic.ObjectWrapper;

final class zzka extends zza<zzaap> {
    private final /* synthetic */ Activity val$activity;
    private final /* synthetic */ zzjr zzart;

    zzka(zzjr zzjr, Activity activity) {
        this.zzart = zzjr;
        this.val$activity = activity;
        super();
    }

    public final /* synthetic */ Object zza(zzld zzld) throws RemoteException {
        return zzld.createAdOverlay(ObjectWrapper.wrap(this.val$activity));
    }

    public final /* synthetic */ Object zzib() throws RemoteException {
        zzaap zze = this.zzart.zzaro.zze(this.val$activity);
        if (zze != null) {
            return zze;
        }
        zzjr.zza(this.val$activity, "ad_overlay");
        return null;
    }
}
