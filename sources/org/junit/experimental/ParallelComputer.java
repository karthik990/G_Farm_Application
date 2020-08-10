package org.junit.experimental;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.runner.Computer;
import org.junit.runner.Runner;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;
import org.junit.runners.model.RunnerScheduler;

public class ParallelComputer extends Computer {
    private final boolean classes;
    private final boolean methods;

    public ParallelComputer(boolean z, boolean z2) {
        this.classes = z;
        this.methods = z2;
    }

    public static Computer classes() {
        return new ParallelComputer(true, false);
    }

    public static Computer methods() {
        return new ParallelComputer(false, true);
    }

    private static Runner parallelize(Runner runner) {
        if (runner instanceof ParentRunner) {
            ((ParentRunner) runner).setScheduler(new RunnerScheduler() {
                private final ExecutorService fService = Executors.newCachedThreadPool();

                public void schedule(Runnable runnable) {
                    this.fService.submit(runnable);
                }

                public void finished() {
                    try {
                        this.fService.shutdown();
                        this.fService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace(System.err);
                    }
                }
            });
        }
        return runner;
    }

    public Runner getSuite(RunnerBuilder runnerBuilder, Class<?>[] clsArr) throws InitializationError {
        Runner suite = super.getSuite(runnerBuilder, clsArr);
        return this.classes ? parallelize(suite) : suite;
    }

    /* access modifiers changed from: protected */
    public Runner getRunner(RunnerBuilder runnerBuilder, Class<?> cls) throws Throwable {
        Runner runner = super.getRunner(runnerBuilder, cls);
        return this.methods ? parallelize(runner) : runner;
    }
}
