package com.google.android.gms.iid;

import android.util.Log;

final class zzj implements Runnable {
    private final /* synthetic */ zzg zzbj;
    private final /* synthetic */ zzi zzbk;

    zzj(zzi zzi, zzg zzg) {
        this.zzbk = zzi;
        this.zzbj = zzg;
    }

    public final void run() {
        String str = "EnhancedIntentService";
        if (Log.isLoggable(str, 3)) {
            Log.d(str, "bg processing of the intent starting now");
        }
        this.zzbk.zzbi.handleIntent(this.zzbj.intent);
        this.zzbj.finish();
    }
}
