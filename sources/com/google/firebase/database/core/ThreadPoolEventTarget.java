package com.google.firebase.database.core;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
class ThreadPoolEventTarget implements EventTarget {
    private final ThreadPoolExecutor executor;

    public ThreadPoolEventTarget(final ThreadFactory threadFactory, final ThreadInitializer threadInitializer) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 3, TimeUnit.SECONDS, new LinkedBlockingQueue(), new ThreadFactory() {
            public Thread newThread(Runnable runnable) {
                Thread newThread = threadFactory.newThread(runnable);
                threadInitializer.setName(newThread, "FirebaseDatabaseEventTarget");
                threadInitializer.setDaemon(newThread, true);
                return newThread;
            }
        });
        this.executor = threadPoolExecutor;
    }

    public void postEvent(Runnable runnable) {
        this.executor.execute(runnable);
    }

    public void shutdown() {
        this.executor.setCorePoolSize(0);
    }

    public void restart() {
        this.executor.setCorePoolSize(1);
    }
}
