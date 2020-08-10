package com.google.android.gms.measurement.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.google.android.gms.common.internal.Preconditions;

class zzbb extends BroadcastReceiver {
    private static final String zzabi = zzbb.class.getName();
    private boolean zzabj;
    private boolean zzabk;
    /* access modifiers changed from: private */
    public final zzfn zzamx;

    zzbb(zzfn zzfn) {
        Preconditions.checkNotNull(zzfn);
        this.zzamx = zzfn;
    }

    public void onReceive(Context context, Intent intent) {
        this.zzamx.zzlx();
        String action = intent.getAction();
        this.zzamx.zzgt().zzjo().zzg("NetworkBroadcastReceiver received action", action);
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(action)) {
            boolean zzfb = this.zzamx.zzlt().zzfb();
            if (this.zzabk != zzfb) {
                this.zzabk = zzfb;
                this.zzamx.zzgs().zzc((Runnable) new zzbc(this, zzfb));
            }
            return;
        }
        this.zzamx.zzgt().zzjj().zzg("NetworkBroadcastReceiver received unknown action", action);
    }

    public final void zzey() {
        this.zzamx.zzlx();
        this.zzamx.zzgs().zzaf();
        if (!this.zzabj) {
            this.zzamx.getContext().registerReceiver(this, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
            this.zzabk = this.zzamx.zzlt().zzfb();
            this.zzamx.zzgt().zzjo().zzg("Registering connectivity change receiver. Network connected", Boolean.valueOf(this.zzabk));
            this.zzabj = true;
        }
    }

    public final void unregister() {
        this.zzamx.zzlx();
        this.zzamx.zzgs().zzaf();
        this.zzamx.zzgs().zzaf();
        if (this.zzabj) {
            this.zzamx.zzgt().zzjo().zzby("Unregistering connectivity change receiver");
            this.zzabj = false;
            this.zzabk = false;
            try {
                this.zzamx.getContext().unregisterReceiver(this);
            } catch (IllegalArgumentException e) {
                this.zzamx.zzgt().zzjg().zzg("Failed to unregister the network broadcast receiver", e);
            }
        }
    }
}
