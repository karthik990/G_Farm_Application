package com.google.android.gms.iid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

final class zzah extends BroadcastReceiver {
    private final /* synthetic */ zzaf zzcx;

    zzah(zzaf zzaf) {
        this.zzcx = zzaf;
    }

    public final void onReceive(Context context, Intent intent) {
        String str = "InstanceID";
        if (Log.isLoggable(str, 3)) {
            Log.d(str, "Received GSF callback via dynamic receiver");
        }
        this.zzcx.zzh(intent);
    }
}
