package com.google.android.gms.internal.firebase_remote_config;

import java.util.concurrent.Executor;

final /* synthetic */ class zzel implements Executor {
    static final Executor zzki = new zzel();

    private zzel() {
    }

    public final void execute(Runnable runnable) {
        runnable.run();
    }
}
