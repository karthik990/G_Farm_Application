package com.twitter.sdk.android.core.internal;

import com.twitter.sdk.android.core.Logger;
import com.twitter.sdk.android.core.Twitter;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public final class ExecutorUtils {
    private static final int CORE_POOL_SIZE;
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final long DEFAULT_TERMINATION_TIMEOUT = 1;
    private static final long KEEP_ALIVE = 1;
    private static final int MAXIMUM_POOL_SIZE;

    static {
        int i = CPU_COUNT;
        CORE_POOL_SIZE = i + 1;
        MAXIMUM_POOL_SIZE = (i * 2) + 1;
    }

    private ExecutorUtils() {
    }

    public static ExecutorService buildThreadPoolExecutorService(String str) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, 1, TimeUnit.SECONDS, new LinkedBlockingQueue(), getNamedThreadFactory(str));
        addDelayedShutdownHook(str, threadPoolExecutor);
        return threadPoolExecutor;
    }

    public static ScheduledExecutorService buildSingleThreadScheduledExecutorService(String str) {
        ScheduledExecutorService newSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor(getNamedThreadFactory(str));
        addDelayedShutdownHook(str, newSingleThreadScheduledExecutor);
        return newSingleThreadScheduledExecutor;
    }

    static ThreadFactory getNamedThreadFactory(String str) {
        return new ThreadFactory(str, new AtomicLong(1)) {
            private final /* synthetic */ String f$0;
            private final /* synthetic */ AtomicLong f$1;

            {
                this.f$0 = r1;
                this.f$1 = r2;
            }

            public final Thread newThread(Runnable runnable) {
                return ExecutorUtils.lambda$getNamedThreadFactory$0(this.f$0, this.f$1, runnable);
            }
        };
    }

    static /* synthetic */ Thread lambda$getNamedThreadFactory$0(String str, AtomicLong atomicLong, Runnable runnable) {
        Thread newThread = Executors.defaultThreadFactory().newThread(runnable);
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(atomicLong.getAndIncrement());
        newThread.setName(sb.toString());
        return newThread;
    }

    static void addDelayedShutdownHook(String str, ExecutorService executorService) {
        addDelayedShutdownHook(str, executorService, 1, TimeUnit.SECONDS);
    }

    static void addDelayedShutdownHook(String str, ExecutorService executorService, long j, TimeUnit timeUnit) {
        Runtime runtime = Runtime.getRuntime();
        $$Lambda$ExecutorUtils$uCsE7HParXQI20ZHGX0xsmVw7E0 r2 = new Runnable(executorService, j, timeUnit, str) {
            private final /* synthetic */ ExecutorService f$0;
            private final /* synthetic */ long f$1;
            private final /* synthetic */ TimeUnit f$2;
            private final /* synthetic */ String f$3;

            {
                this.f$0 = r1;
                this.f$1 = r2;
                this.f$2 = r4;
                this.f$3 = r5;
            }

            public final void run() {
                ExecutorUtils.lambda$addDelayedShutdownHook$1(this.f$0, this.f$1, this.f$2, this.f$3);
            }
        };
        StringBuilder sb = new StringBuilder();
        sb.append("Twitter Shutdown Hook for ");
        sb.append(str);
        runtime.addShutdownHook(new Thread(r2, sb.toString()));
    }

    static /* synthetic */ void lambda$addDelayedShutdownHook$1(ExecutorService executorService, long j, TimeUnit timeUnit, String str) {
        String str2 = "Twitter";
        try {
            executorService.shutdown();
            if (!executorService.awaitTermination(j, timeUnit)) {
                Logger logger = Twitter.getLogger();
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(" did not shutdown in the allocated time. Requesting immediate shutdown.");
                logger.mo20817d(str2, sb.toString());
                executorService.shutdownNow();
            }
        } catch (InterruptedException unused) {
            Twitter.getLogger().mo20817d(str2, String.format(Locale.US, "Interrupted while waiting for %s to shut down. Requesting immediate shutdown.", new Object[]{str}));
            executorService.shutdownNow();
        }
    }
}
