package com.applyze;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ApplyzeNetworkChangeReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if (checkInternet(context)) {
            sendSessions(context);
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean checkInternet(Context context) {
        return new ApplyzeServiceManager(context).isNetworkAvailable();
    }

    /* access modifiers changed from: 0000 */
    public void sendSessions(Context context) {
        ServiceUtil.sendSessionsFromListener(context);
    }
}
