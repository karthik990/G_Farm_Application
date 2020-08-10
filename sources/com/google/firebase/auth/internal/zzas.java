package com.google.firebase.auth.internal;

import android.app.Application;
import android.content.Context;
import com.google.android.gms.common.api.internal.BackgroundDetector;
import com.google.android.gms.internal.firebase_auth.zzes;
import com.google.firebase.FirebaseApp;

public final class zzas {
    private volatile int zzuv;
    /* access modifiers changed from: private */
    public final zzv zzuw;
    /* access modifiers changed from: private */
    public volatile boolean zzux;

    public zzas(FirebaseApp firebaseApp) {
        this(firebaseApp.getApplicationContext(), new zzv(firebaseApp));
    }

    private zzas(Context context, zzv zzv) {
        this.zzux = false;
        this.zzuv = 0;
        this.zzuw = zzv;
        BackgroundDetector.initialize((Application) context.getApplicationContext());
        BackgroundDetector.getInstance().addListener(new zzav(this));
    }

    public final void zzj(int i) {
        if (i > 0 && this.zzuv == 0) {
            this.zzuv = i;
            if (zzfq()) {
                this.zzuw.zzfh();
            }
        } else if (i == 0 && this.zzuv != 0) {
            this.zzuw.cancel();
        }
        this.zzuv = i;
    }

    public final void zzc(zzes zzes) {
        if (zzes != null) {
            long zzt = zzes.zzt();
            if (zzt <= 0) {
                zzt = 3600;
            }
            long zzev = zzes.zzev() + (zzt * 1000);
            zzv zzv = this.zzuw;
            zzv.zztv = zzev;
            zzv.zztw = -1;
            if (zzfq()) {
                this.zzuw.zzfh();
            }
        }
    }

    public final void cancel() {
        this.zzuw.cancel();
    }

    /* access modifiers changed from: private */
    public final boolean zzfq() {
        return this.zzuv > 0 && !this.zzux;
    }
}
