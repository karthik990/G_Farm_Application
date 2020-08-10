package com.google.android.gms.internal.ads;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.firebase.analytics.FirebaseAnalytics.Event;

final class zzjt extends zza<zzks> {
    private final /* synthetic */ Context val$context;
    private final /* synthetic */ zzjn zzarq;
    private final /* synthetic */ String zzarr;
    private final /* synthetic */ zzjr zzart;

    zzjt(zzjr zzjr, Context context, zzjn zzjn, String str) {
        this.zzart = zzjr;
        this.val$context = context;
        this.zzarq = zzjn;
        this.zzarr = str;
        super();
    }

    public final /* synthetic */ Object zza(zzld zzld) throws RemoteException {
        return zzld.createSearchAdManager(ObjectWrapper.wrap(this.val$context), this.zzarq, this.zzarr, GooglePlayServicesUtilLight.GOOGLE_PLAY_SERVICES_VERSION_CODE);
    }

    public final /* synthetic */ Object zzib() throws RemoteException {
        zzks zza = this.zzart.zzarj.zza(this.val$context, this.zzarq, this.zzarr, null, 3);
        if (zza != null) {
            return zza;
        }
        zzjr.zza(this.val$context, Event.SEARCH);
        return new zzmj();
    }
}
