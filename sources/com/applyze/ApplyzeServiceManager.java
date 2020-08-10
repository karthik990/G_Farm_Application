package com.applyze;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

class ApplyzeServiceManager {
    private Context context;

    ApplyzeServiceManager(Context context2) {
        this.context = context2;
    }

    /* access modifiers changed from: 0000 */
    public boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
