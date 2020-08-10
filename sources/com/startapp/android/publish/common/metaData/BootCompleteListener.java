package com.startapp.android.publish.common.metaData;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.startapp.android.publish.adsCommon.Utils.C4936b;
import com.startapp.android.publish.adsCommon.p082f.C5015d;
import com.startapp.android.publish.adsCommon.p082f.C5017f;
import com.startapp.common.p092a.C5155g;

/* compiled from: StartAppSDK */
public class BootCompleteListener extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        try {
            C5155g.m3805a(3, "BootCompleteListener - onReceive");
            long elapsedRealtime = SystemClock.elapsedRealtime() + DefaultLoadErrorHandlingPolicy.DEFAULT_TRACK_BLACKLIST_MS;
            C4936b.m2865a(context);
            C4936b.m2868a(context, Long.valueOf(elapsedRealtime));
            C4936b.m2867a(context, elapsedRealtime);
        } catch (Exception e) {
            C5017f.m3256a(context, C5015d.EXCEPTION, "BootCompleteListener.onReceive - failed to start services", e.getMessage(), "");
        }
    }
}
