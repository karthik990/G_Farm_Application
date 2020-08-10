package com.google.android.gms.internal.firebase_remote_config;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

final class zzen<TResult> implements OnCanceledListener, OnFailureListener, OnSuccessListener<TResult> {
    private final CountDownLatch zzkj;

    private zzen() {
        this.zzkj = new CountDownLatch(1);
    }

    public final void onSuccess(TResult tresult) {
        this.zzkj.countDown();
    }

    public final void onFailure(Exception exc) {
        this.zzkj.countDown();
    }

    public final void onCanceled() {
        this.zzkj.countDown();
    }

    public final boolean await(long j, TimeUnit timeUnit) throws InterruptedException {
        return this.zzkj.await(5, timeUnit);
    }
}
