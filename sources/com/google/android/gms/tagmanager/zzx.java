package com.google.android.gms.tagmanager;

import android.os.Looper;
import android.os.Message;
import com.google.android.gms.internal.gtm.zzdj;
import com.google.android.gms.tagmanager.ContainerHolder.ContainerAvailableListener;

final class zzx extends zzdj {
    private final ContainerAvailableListener zzaes;
    private final /* synthetic */ zzv zzaet;

    public zzx(zzv zzv, ContainerAvailableListener containerAvailableListener, Looper looper) {
        this.zzaet = zzv;
        super(looper);
        this.zzaes = containerAvailableListener;
    }

    public final void handleMessage(Message message) {
        if (message.what != 1) {
            zzdi.zzav("Don't know how to handle this message.");
            return;
        }
        this.zzaes.onContainerAvailable(this.zzaet, (String) message.obj);
    }
}
