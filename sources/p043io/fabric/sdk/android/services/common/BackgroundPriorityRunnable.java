package p043io.fabric.sdk.android.services.common;

import android.os.Process;

/* renamed from: io.fabric.sdk.android.services.common.BackgroundPriorityRunnable */
public abstract class BackgroundPriorityRunnable implements Runnable {
    /* access modifiers changed from: protected */
    public abstract void onRun();

    public final void run() {
        Process.setThreadPriority(10);
        onRun();
    }
}
