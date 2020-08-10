package com.pierfrancescosoffritti.androidyoutubeplayer.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NetworkReceiver extends BroadcastReceiver {

    /* renamed from: a */
    private NetworkListener f2353a;

    public interface NetworkListener {
        void onNetworkAvailable();

        void onNetworkUnavailable();
    }

    public NetworkReceiver(NetworkListener networkListener) {
        this.f2353a = networkListener;
    }

    public void onReceive(Context context, Intent intent) {
        if (C4603Utils.isOnline(context)) {
            this.f2353a.onNetworkAvailable();
        } else {
            this.f2353a.onNetworkUnavailable();
        }
    }
}
