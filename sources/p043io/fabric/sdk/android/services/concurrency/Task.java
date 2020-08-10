package p043io.fabric.sdk.android.services.concurrency;

/* renamed from: io.fabric.sdk.android.services.concurrency.Task */
public interface Task {
    Throwable getError();

    boolean isFinished();

    void setError(Throwable th);

    void setFinished(boolean z);
}
