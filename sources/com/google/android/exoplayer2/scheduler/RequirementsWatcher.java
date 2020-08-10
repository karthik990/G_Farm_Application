package com.google.android.exoplayer2.scheduler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.ConnectivityManager.NetworkCallback;
import android.net.Network;
import android.net.NetworkRequest;
import android.net.NetworkRequest.Builder;
import android.os.Handler;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

public final class RequirementsWatcher {
    private final Context context;
    /* access modifiers changed from: private */
    public final Handler handler = new Handler(Util.getLooper());
    private final Listener listener;
    /* access modifiers changed from: private */
    public CapabilityValidatedCallback networkCallback;
    private int notMetRequirements;
    private DeviceStatusChangeReceiver receiver;
    private final Requirements requirements;

    private final class CapabilityValidatedCallback extends NetworkCallback {
        private CapabilityValidatedCallback() {
        }

        public void onAvailable(Network network) {
            onNetworkCallback();
        }

        public void onLost(Network network) {
            onNetworkCallback();
        }

        private void onNetworkCallback() {
            RequirementsWatcher.this.handler.post(new Runnable() {
                public final void run() {
                    CapabilityValidatedCallback.this.mo18400x8a9259a2();
                }
            });
        }

        /* renamed from: lambda$onNetworkCallback$0$RequirementsWatcher$CapabilityValidatedCallback */
        public /* synthetic */ void mo18400x8a9259a2() {
            if (RequirementsWatcher.this.networkCallback != null) {
                RequirementsWatcher.this.checkRequirements();
            }
        }
    }

    private class DeviceStatusChangeReceiver extends BroadcastReceiver {
        private DeviceStatusChangeReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (!isInitialStickyBroadcast()) {
                RequirementsWatcher.this.checkRequirements();
            }
        }
    }

    public interface Listener {
        void onRequirementsStateChanged(RequirementsWatcher requirementsWatcher, int i);
    }

    public RequirementsWatcher(Context context2, Listener listener2, Requirements requirements2) {
        this.context = context2.getApplicationContext();
        this.listener = listener2;
        this.requirements = requirements2;
    }

    public int start() {
        this.notMetRequirements = this.requirements.getNotMetRequirements(this.context);
        IntentFilter intentFilter = new IntentFilter();
        if (this.requirements.isNetworkRequired()) {
            if (Util.SDK_INT >= 23) {
                registerNetworkCallbackV23();
            } else {
                intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            }
        }
        if (this.requirements.isChargingRequired()) {
            intentFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
            intentFilter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
        }
        if (this.requirements.isIdleRequired()) {
            if (Util.SDK_INT >= 23) {
                intentFilter.addAction("android.os.action.DEVICE_IDLE_MODE_CHANGED");
            } else {
                intentFilter.addAction("android.intent.action.SCREEN_ON");
                intentFilter.addAction("android.intent.action.SCREEN_OFF");
            }
        }
        this.receiver = new DeviceStatusChangeReceiver();
        this.context.registerReceiver(this.receiver, intentFilter, null, this.handler);
        return this.notMetRequirements;
    }

    public void stop() {
        this.context.unregisterReceiver((BroadcastReceiver) Assertions.checkNotNull(this.receiver));
        this.receiver = null;
        if (this.networkCallback != null) {
            unregisterNetworkCallback();
        }
    }

    public Requirements getRequirements() {
        return this.requirements;
    }

    private void registerNetworkCallbackV23() {
        ConnectivityManager connectivityManager = (ConnectivityManager) Assertions.checkNotNull((ConnectivityManager) this.context.getSystemService("connectivity"));
        NetworkRequest build = new Builder().addCapability(16).build();
        this.networkCallback = new CapabilityValidatedCallback();
        connectivityManager.registerNetworkCallback(build, this.networkCallback);
    }

    private void unregisterNetworkCallback() {
        if (Util.SDK_INT >= 21) {
            ((ConnectivityManager) this.context.getSystemService("connectivity")).unregisterNetworkCallback((NetworkCallback) Assertions.checkNotNull(this.networkCallback));
            this.networkCallback = null;
        }
    }

    /* access modifiers changed from: private */
    public void checkRequirements() {
        int notMetRequirements2 = this.requirements.getNotMetRequirements(this.context);
        if (this.notMetRequirements != notMetRequirements2) {
            this.notMetRequirements = notMetRequirements2;
            this.listener.onRequirementsStateChanged(this, notMetRequirements2);
        }
    }
}
