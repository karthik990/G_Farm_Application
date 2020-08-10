package com.braintreepayments.browserswitch;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.IBinder;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.browser.customtabs.CustomTabsService;

public class ChromeCustomTabs {
    public static boolean isAvailable(Context context) {
        if (VERSION.SDK_INT < 18) {
            return false;
        }
        Intent intent = new Intent(CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION).setPackage("com.android.chrome");
        C11521 r1 = new ServiceConnection() {
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            }

            public void onServiceDisconnected(ComponentName componentName) {
            }
        };
        boolean bindService = context.bindService(intent, r1, 33);
        context.unbindService(r1);
        return bindService;
    }

    public static Intent addChromeCustomTabsExtras(Context context, Intent intent) {
        if (VERSION.SDK_INT >= 18 && isAvailable(context)) {
            Bundle bundle = new Bundle();
            bundle.putBinder(CustomTabsIntent.EXTRA_SESSION, null);
            intent.putExtras(bundle);
            intent.addFlags(134250496);
        }
        return intent;
    }
}
