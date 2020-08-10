package androidx.work;

import android.content.Context;
import androidx.work.ListenableWorker.Result;
import androidx.work.impl.utils.futures.SettableFuture;
import com.google.common.util.concurrent.ListenableFuture;

public abstract class Worker extends ListenableWorker {
    SettableFuture<Result> mFuture;

    public abstract Result doWork();

    public Worker(Context context, WorkerParameters workerParameters) {
        super(context, workerParameters);
    }

    public final ListenableFuture<Result> startWork() {
        this.mFuture = SettableFuture.create();
        getBackgroundExecutor().execute(new Runnable() {
            public void run() {
                try {
                    Worker.this.mFuture.set(Worker.this.doWork());
                } catch (Throwable th) {
                    Worker.this.mFuture.setException(th);
                }
            }
        });
        return this.mFuture;
    }
}
