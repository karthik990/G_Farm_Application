package com.google.android.gms.internal.ads;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.BaseGmsClient.BaseOnConnectionFailedListener;

final class zzsr implements BaseOnConnectionFailedListener {
    private final /* synthetic */ zzsm zzbnn;
    private final /* synthetic */ zzaoj zzbno;

    zzsr(zzsm zzsm, zzaoj zzaoj) {
        this.zzbnn = zzsm;
        this.zzbno = zzaoj;
    }

    public final void onConnectionFailed(ConnectionResult connectionResult) {
        synchronized (this.zzbnn.mLock) {
            this.zzbno.setException(new RuntimeException("Connection failed."));
        }
    }
}
