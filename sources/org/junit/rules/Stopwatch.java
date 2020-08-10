package org.junit.rules;

import java.util.concurrent.TimeUnit;
import org.junit.AssumptionViolatedException;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public abstract class Stopwatch implements TestRule {
    private final Clock clock;
    private volatile long endNanos;
    private volatile long startNanos;

    static class Clock {
        Clock() {
        }

        public long nanoTime() {
            return System.nanoTime();
        }
    }

    private class InternalWatcher extends TestWatcher {
        private InternalWatcher() {
        }

        /* access modifiers changed from: protected */
        public void starting(Description description) {
            Stopwatch.this.starting();
        }

        /* access modifiers changed from: protected */
        public void finished(Description description) {
            Stopwatch stopwatch = Stopwatch.this;
            stopwatch.finished(stopwatch.getNanos(), description);
        }

        /* access modifiers changed from: protected */
        public void succeeded(Description description) {
            Stopwatch.this.stopping();
            Stopwatch stopwatch = Stopwatch.this;
            stopwatch.succeeded(stopwatch.getNanos(), description);
        }

        /* access modifiers changed from: protected */
        public void failed(Throwable th, Description description) {
            Stopwatch.this.stopping();
            Stopwatch stopwatch = Stopwatch.this;
            stopwatch.failed(stopwatch.getNanos(), th, description);
        }

        /* access modifiers changed from: protected */
        public void skipped(AssumptionViolatedException assumptionViolatedException, Description description) {
            Stopwatch.this.stopping();
            Stopwatch stopwatch = Stopwatch.this;
            stopwatch.skipped(stopwatch.getNanos(), assumptionViolatedException, description);
        }
    }

    /* access modifiers changed from: protected */
    public void failed(long j, Throwable th, Description description) {
    }

    /* access modifiers changed from: protected */
    public void finished(long j, Description description) {
    }

    /* access modifiers changed from: protected */
    public void skipped(long j, AssumptionViolatedException assumptionViolatedException, Description description) {
    }

    /* access modifiers changed from: protected */
    public void succeeded(long j, Description description) {
    }

    public Stopwatch() {
        this(new Clock());
    }

    Stopwatch(Clock clock2) {
        this.clock = clock2;
    }

    public long runtime(TimeUnit timeUnit) {
        return timeUnit.convert(getNanos(), TimeUnit.NANOSECONDS);
    }

    /* access modifiers changed from: private */
    public long getNanos() {
        if (this.startNanos != 0) {
            long j = this.endNanos;
            if (j == 0) {
                j = this.clock.nanoTime();
            }
            return j - this.startNanos;
        }
        throw new IllegalStateException("Test has not started");
    }

    /* access modifiers changed from: private */
    public void starting() {
        this.startNanos = this.clock.nanoTime();
        this.endNanos = 0;
    }

    /* access modifiers changed from: private */
    public void stopping() {
        this.endNanos = this.clock.nanoTime();
    }

    public final Statement apply(Statement statement, Description description) {
        return new InternalWatcher().apply(statement, description);
    }
}
