package androidx.work;

import android.os.Build.VERSION;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class Configuration {
    public static final int MIN_SCHEDULER_LIMIT = 20;
    private final Executor mExecutor;
    private final int mLoggingLevel;
    private final int mMaxJobSchedulerId;
    private final int mMaxSchedulerLimit;
    private final int mMinJobSchedulerId;
    private final Executor mTaskExecutor;
    private final WorkerFactory mWorkerFactory;

    public static final class Builder {
        Executor mExecutor;
        int mLoggingLevel = 4;
        int mMaxJobSchedulerId = Integer.MAX_VALUE;
        int mMaxSchedulerLimit = 20;
        int mMinJobSchedulerId = 0;
        Executor mTaskExecutor;
        WorkerFactory mWorkerFactory;

        public Builder setWorkerFactory(WorkerFactory workerFactory) {
            this.mWorkerFactory = workerFactory;
            return this;
        }

        public Builder setExecutor(Executor executor) {
            this.mExecutor = executor;
            return this;
        }

        public Builder setTaskExecutor(Executor executor) {
            this.mTaskExecutor = executor;
            return this;
        }

        public Builder setJobSchedulerJobIdRange(int i, int i2) {
            if (i2 - i >= 1000) {
                this.mMinJobSchedulerId = i;
                this.mMaxJobSchedulerId = i2;
                return this;
            }
            throw new IllegalArgumentException("WorkManager needs a range of at least 1000 job ids.");
        }

        public Builder setMaxSchedulerLimit(int i) {
            if (i >= 20) {
                this.mMaxSchedulerLimit = Math.min(i, 50);
                return this;
            }
            throw new IllegalArgumentException("WorkManager needs to be able to schedule at least 20 jobs in JobScheduler.");
        }

        public Builder setMinimumLoggingLevel(int i) {
            this.mLoggingLevel = i;
            return this;
        }

        public Configuration build() {
            return new Configuration(this);
        }
    }

    public interface Provider {
        Configuration getWorkManagerConfiguration();
    }

    Configuration(Builder builder) {
        if (builder.mExecutor == null) {
            this.mExecutor = createDefaultExecutor();
        } else {
            this.mExecutor = builder.mExecutor;
        }
        if (builder.mTaskExecutor == null) {
            this.mTaskExecutor = createDefaultExecutor();
        } else {
            this.mTaskExecutor = builder.mTaskExecutor;
        }
        if (builder.mWorkerFactory == null) {
            this.mWorkerFactory = WorkerFactory.getDefaultWorkerFactory();
        } else {
            this.mWorkerFactory = builder.mWorkerFactory;
        }
        this.mLoggingLevel = builder.mLoggingLevel;
        this.mMinJobSchedulerId = builder.mMinJobSchedulerId;
        this.mMaxJobSchedulerId = builder.mMaxJobSchedulerId;
        this.mMaxSchedulerLimit = builder.mMaxSchedulerLimit;
    }

    public Executor getExecutor() {
        return this.mExecutor;
    }

    public Executor getTaskExecutor() {
        return this.mTaskExecutor;
    }

    public WorkerFactory getWorkerFactory() {
        return this.mWorkerFactory;
    }

    public int getMinimumLoggingLevel() {
        return this.mLoggingLevel;
    }

    public int getMinJobSchedulerId() {
        return this.mMinJobSchedulerId;
    }

    public int getMaxJobSchedulerId() {
        return this.mMaxJobSchedulerId;
    }

    public int getMaxSchedulerLimit() {
        if (VERSION.SDK_INT == 23) {
            return this.mMaxSchedulerLimit / 2;
        }
        return this.mMaxSchedulerLimit;
    }

    private Executor createDefaultExecutor() {
        return Executors.newFixedThreadPool(Math.max(2, Math.min(Runtime.getRuntime().availableProcessors() - 1, 4)));
    }
}
