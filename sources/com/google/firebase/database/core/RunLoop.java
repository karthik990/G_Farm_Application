package com.google.firebase.database.core;

import java.util.concurrent.ScheduledFuture;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public interface RunLoop {
    void restart();

    ScheduledFuture schedule(Runnable runnable, long j);

    void scheduleNow(Runnable runnable);

    void shutdown();
}
