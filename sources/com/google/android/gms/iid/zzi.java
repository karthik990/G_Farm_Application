package com.google.android.gms.iid;

import android.os.Binder;
import android.os.Process;
import android.util.Log;

public final class zzi extends Binder {
    /* access modifiers changed from: private */
    public final zze zzbi;

    zzi(zze zze) {
        this.zzbi = zze;
    }

    public final void zzd(zzg zzg) {
        if (Binder.getCallingUid() == Process.myUid()) {
            String str = "EnhancedIntentService";
            if (Log.isLoggable(str, 3)) {
                Log.d(str, "service received new intent via bind strategy");
            }
            if (Log.isLoggable(str, 3)) {
                Log.d(str, "intent being queued for bg execution");
            }
            this.zzbi.zzax.execute(new zzj(this, zzg));
            return;
        }
        throw new SecurityException("Binding only allowed within app");
    }
}
