package com.google.android.gms.internal.ads;

import java.lang.Thread.UncaughtExceptionHandler;

final class zzadd implements UncaughtExceptionHandler {
    private final /* synthetic */ zzadb zzccb;
    private final /* synthetic */ UncaughtExceptionHandler zzccc;

    zzadd(zzadb zzadb, UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.zzccb = zzadb;
        this.zzccc = uncaughtExceptionHandler;
    }

    public final void uncaughtException(Thread thread, Throwable th) {
        try {
            this.zzccb.zza(thread, th);
            UncaughtExceptionHandler uncaughtExceptionHandler = this.zzccc;
            if (uncaughtExceptionHandler != null) {
                uncaughtExceptionHandler.uncaughtException(thread, th);
            }
        } catch (Throwable th2) {
            UncaughtExceptionHandler uncaughtExceptionHandler2 = this.zzccc;
            if (uncaughtExceptionHandler2 != null) {
                uncaughtExceptionHandler2.uncaughtException(thread, th);
            }
            throw th2;
        }
    }
}
