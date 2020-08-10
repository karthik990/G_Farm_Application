package com.applyze;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ScreenLockListener extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null) {
            String action = intent.getAction();
            char c = 65535;
            int hashCode = action.hashCode();
            if (hashCode != -2128145023) {
                if (hashCode != 823795052) {
                    if (hashCode == 1561941187 && action.equals("android.intent.action.ANSWER")) {
                        c = 1;
                    }
                } else if (action.equals("android.intent.action.USER_PRESENT")) {
                    c = 2;
                }
            } else if (action.equals("android.intent.action.SCREEN_OFF")) {
                c = 0;
            }
            if (c == 0) {
                ServiceUtil.screenState(context, false);
            } else if (c == 1) {
                ServiceUtil.screenState(context, false);
            } else if (c == 2) {
                ServiceUtil.screenState(context, true);
            }
        }
    }
}
