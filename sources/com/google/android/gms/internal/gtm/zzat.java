package com.google.android.gms.internal.gtm;

import android.content.ComponentName;
import android.os.RemoteException;
import com.google.android.gms.analytics.zzk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.stats.ConnectionTracker;
import java.util.Collections;

public final class zzat extends zzan {
    /* access modifiers changed from: private */
    public final zzav zzxa = new zzav(this);
    private zzce zzxb;
    private final zzbs zzxc;
    private final zzcv zzxd;

    protected zzat(zzap zzap) {
        super(zzap);
        this.zzxd = new zzcv(zzap.zzcn());
        this.zzxc = new zzau(this, zzap);
    }

    /* access modifiers changed from: protected */
    public final void zzaw() {
    }

    public final boolean isConnected() {
        zzk.zzav();
        zzdb();
        return this.zzxb != null;
    }

    public final boolean zzb(zzcd zzcd) {
        String str;
        Preconditions.checkNotNull(zzcd);
        zzk.zzav();
        zzdb();
        zzce zzce = this.zzxb;
        if (zzce == null) {
            return false;
        }
        if (zzcd.zzfj()) {
            str = zzbq.zzet();
        } else {
            str = zzbq.zzeu();
        }
        try {
            zzce.zza(zzcd.zzdm(), zzcd.zzfh(), str, Collections.emptyList());
            zzdo();
            return true;
        } catch (RemoteException unused) {
            zzq("Failed to send hits to AnalyticsService");
            return false;
        }
    }

    public final boolean zzdn() {
        zzk.zzav();
        zzdb();
        zzce zzce = this.zzxb;
        if (zzce == null) {
            return false;
        }
        try {
            zzce.zzch();
            zzdo();
            return true;
        } catch (RemoteException unused) {
            zzq("Failed to clear hits from AnalyticsService");
            return false;
        }
    }

    private final void zzdo() {
        this.zzxd.start();
        this.zzxc.zzh(((Long) zzby.zzaaj.get()).longValue());
    }

    public final boolean connect() {
        zzk.zzav();
        zzdb();
        if (this.zzxb != null) {
            return true;
        }
        zzce zzdq = this.zzxa.zzdq();
        if (zzdq == null) {
            return false;
        }
        this.zzxb = zzdq;
        zzdo();
        return true;
    }

    /* access modifiers changed from: private */
    public final void zza(zzce zzce) {
        zzk.zzav();
        this.zzxb = zzce;
        zzdo();
        zzcs().onServiceConnected();
    }

    public final void disconnect() {
        zzk.zzav();
        zzdb();
        try {
            ConnectionTracker.getInstance().unbindService(getContext(), this.zzxa);
        } catch (IllegalArgumentException | IllegalStateException unused) {
        }
        if (this.zzxb != null) {
            this.zzxb = null;
            zzcs().zzck();
        }
    }

    /* access modifiers changed from: private */
    public final void onServiceDisconnected(ComponentName componentName) {
        zzk.zzav();
        if (this.zzxb != null) {
            this.zzxb = null;
            zza("Disconnected from device AnalyticsService", componentName);
            zzcs().zzck();
        }
    }

    /* access modifiers changed from: private */
    public final void zzdp() {
        zzk.zzav();
        if (isConnected()) {
            zzq("Inactivity, disconnecting from device AnalyticsService");
            disconnect();
        }
    }
}
