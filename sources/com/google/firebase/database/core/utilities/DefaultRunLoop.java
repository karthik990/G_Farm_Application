package com.google.firebase.database.core.utilities;

import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.RunLoop;
import com.google.firebase.database.core.ThreadInitializer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public abstract class DefaultRunLoop implements RunLoop {
    private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1, new FirebaseThreadFactory()) {
        /* access modifiers changed from: protected */
        public void afterExecute(Runnable runnable, Throwable th) {
            super.afterExecute(runnable, th);
            if (th == null && (runnable instanceof Future)) {
                Future future = (Future) runnable;
                try {
                    if (future.isDone()) {
                        future.get();
                    }
                } catch (CancellationException unused) {
                } catch (ExecutionException e) {
                    th = e.getCause();
                } catch (InterruptedException unused2) {
                    Thread.currentThread().interrupt();
                }
            }
            if (th != null) {
                DefaultRunLoop.this.handleException(th);
            }
        }
    };

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    private class FirebaseThreadFactory implements ThreadFactory {
        private FirebaseThreadFactory() {
        }

        public Thread newThread(Runnable runnable) {
            Thread newThread = DefaultRunLoop.this.getThreadFactory().newThread(runnable);
            ThreadInitializer threadInitializer = DefaultRunLoop.this.getThreadInitializer();
            threadInitializer.setName(newThread, "FirebaseDatabaseWorker");
            threadInitializer.setDaemon(newThread, true);
            threadInitializer.setUncaughtExceptionHandler(newThread, new UncaughtExceptionHandler() {
                public void uncaughtException(Thread thread, Throwable th) {
                    DefaultRunLoop.this.handleException(th);
                }
            });
            return newThread;
        }
    }

    public abstract void handleException(Throwable th);

    /* access modifiers changed from: protected */
    public ThreadFactory getThreadFactory() {
        return Executors.defaultThreadFactory();
    }

    /* access modifiers changed from: protected */
    public ThreadInitializer getThreadInitializer() {
        return ThreadInitializer.defaultInstance;
    }

    public DefaultRunLoop() {
        this.executor.setKeepAliveTime(3, TimeUnit.SECONDS);
    }

    public ScheduledExecutorService getExecutorService() {
        return this.executor;
    }

    public void scheduleNow(Runnable runnable) {
        this.executor.execute(runnable);
    }

    public ScheduledFuture schedule(Runnable runnable, long j) {
        return this.executor.schedule(runnable, j, TimeUnit.MILLISECONDS);
    }

    public void shutdown() {
        this.executor.setCorePoolSize(0);
    }

    public void restart() {
        this.executor.setCorePoolSize(1);
    }

    public static String messageForException(Throwable th) {
        if (th instanceof OutOfMemoryError) {
            return "Firebase Database encountered an OutOfMemoryError. You may need to reduce the amount of data you are syncing to the client (e.g. by using queries or syncing a deeper path). See https://firebase.google.com/docs/database/ios/structure-data#best_practices_for_data_structure and https://firebase.google.com/docs/database/android/retrieve-data#filtering_data";
        }
        if (th instanceof DatabaseException) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Uncaught exception in Firebase Database runloop (");
        sb.append(FirebaseDatabase.getSdkVersion());
        sb.append("). Please report to firebase-database-client@google.com");
        return sb.toString();
    }
}
