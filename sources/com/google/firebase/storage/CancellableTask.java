package com.google.firebase.storage;

import android.app.Activity;
import com.google.android.gms.tasks.Task;
import java.util.concurrent.Executor;

/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
public abstract class CancellableTask<TState> extends Task<TState> {
    public abstract CancellableTask<TState> addOnProgressListener(Activity activity, OnProgressListener<? super TState> onProgressListener);

    public abstract CancellableTask<TState> addOnProgressListener(OnProgressListener<? super TState> onProgressListener);

    public abstract CancellableTask<TState> addOnProgressListener(Executor executor, OnProgressListener<? super TState> onProgressListener);

    public abstract boolean cancel();

    public abstract boolean isCanceled();

    public abstract boolean isInProgress();
}
