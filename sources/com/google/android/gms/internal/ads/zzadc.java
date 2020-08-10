package com.google.android.gms.internal.ads;

import java.lang.Thread.UncaughtExceptionHandler;

final class zzadc implements UncaughtExceptionHandler {
    private final /* synthetic */ UncaughtExceptionHandler zzcca;
    private final /* synthetic */ zzadb zzccb;

    zzadc(zzadb zzadb, UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.zzccb = zzadb;
        this.zzcca = uncaughtExceptionHandler;
    }

    public final void uncaughtException(Thread thread, Throwable th) {
        try {
            this.zzccb.zza(thread, th);
            UncaughtExceptionHandler uncaughtExceptionHandler = this.zzcca;
            if (uncaughtExceptionHandler != null) {
                uncaughtExceptionHandler.uncaughtException(thread, th);
            }
        } catch (Throwable th2) {
            UncaughtExceptionHandler uncaughtExceptionHandler2 = this.zzcca;
            if (uncaughtExceptionHandler2 != null) {
                uncaughtExceptionHandler2.uncaughtException(thread, th);
            }
            throw th2;
        }
    }
}
