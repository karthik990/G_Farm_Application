package com.google.android.gms.internal.ads;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.dynamic.ObjectWrapper;

final class zzjw extends zza<zzlj> {
    private final /* synthetic */ Context val$context;
    private final /* synthetic */ zzjr zzart;

    zzjw(zzjr zzjr, Context context) {
        this.zzart = zzjr;
        this.val$context = context;
        super();
    }

    public final /* synthetic */ Object zza(zzld zzld) throws RemoteException {
        return zzld.getMobileAdsSettingsManagerWithClientJarVersion(ObjectWrapper.wrap(this.val$context), GooglePlayServicesUtilLight.GOOGLE_PLAY_SERVICES_VERSION_CODE);
    }

    public final /* synthetic */ Object zzib() throws RemoteException {
        zzlj zzg = this.zzart.zzarl.zzg(this.val$context);
        if (zzg != null) {
            return zzg;
        }
        zzjr.zza(this.val$context, "mobile_ads_settings");
        return new zzml();
    }
}
