package com.google.android.gms.analytics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.google.android.gms.internal.gtm.zzcp;

public final class AnalyticsReceiver extends BroadcastReceiver {
    private zzcp zzre;

    public final void onReceive(Context context, Intent intent) {
        if (this.zzre == null) {
            this.zzre = new zzcp();
        }
        zzcp.onReceive(context, intent);
    }
}
