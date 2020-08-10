package com.mobiroller.services;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import androidx.legacy.content.WakefulBroadcastReceiver;

public class GCMBroadcastReceiver extends WakefulBroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        ComponentName componentName = new ComponentName(context.getPackageName(), FCMMessagingService.class.getName());
        if (VERSION.SDK_INT < 26) {
            startWakefulService(context, intent.setComponent(componentName));
            setResultCode(-1);
        }
    }
}
