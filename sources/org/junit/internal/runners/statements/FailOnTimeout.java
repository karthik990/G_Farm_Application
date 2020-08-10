package org.junit.internal.runners.statements;

import java.lang.Thread.State;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestTimedOutException;

public class FailOnTimeout extends Statement {
    private final boolean lookForStuckThread;
    /* access modifiers changed from: private */
    public final Statement originalStatement;
    private volatile ThreadGroup threadGroup;
    private final TimeUnit timeUnit;
    private final long timeout;

    public static class Builder {
        /* access modifiers changed from: private */
        public boolean lookForStuckThread;
        /* access modifiers changed from: private */
        public long timeout;
        /* access modifiers changed from: private */
        public TimeUnit unit;

        private Builder() {
            this.lookForStuckThread = false;
            this.timeout = 0;
            this.unit = TimeUnit.SECONDS;
        }

        public Builder withTimeout(long j, TimeUnit timeUnit) {
            if (j < 0) {
                throw new IllegalArgumentException("timeout must be non-negative");
            } else if (timeUnit != null) {
                this.timeout = j;
                this.unit = timeUnit;
                return this;
            } else {
                throw new NullPointerException("TimeUnit cannot be null");
            }
        }

        public Builder withLookingForStuckThread(boolean z) {
            this.lookForStuckThread = z;
            return this;
        }

        public FailOnTimeout build(Statement statement) {
            if (statement != null) {
                return new FailOnTimeout(this, statement);
            }
            throw new NullPointerException("statement cannot be null");
        }
    }

    private class CallableStatement implements Callable<Throwable> {
        private final CountDownLatch startLatch;

        private CallableStatement() {
            this.startLatch = new CountDownLatch(1);
        }

        public Throwable call() throws Exception {
            try {
                this.startLatch.countDown();
                FailOnTimeout.this.originalStatement.evaluate();
                return null;
            } catch (Exception e) {
                throw e;
            } catch (Throwable th) {
                return th;
            }
        }

        public void awaitStarted() throws InterruptedException {
            this.startLatch.await();
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    @Deprecated
    public FailOnTimeout(Statement statement, long j) {
        this(builder().withTimeout(j, TimeUnit.MILLISECONDS), statement);
    }

    private FailOnTimeout(Builder builder, Statement statement) {
        this.threadGroup = null;
        this.originalStatement = statement;
        this.timeout = builder.timeout;
        this.timeUnit = builder.unit;
        this.lookForStuckThread = builder.lookForStuckThread;
    }

    public void evaluate() throws Throwable {
        CallableStatement callableStatement = new CallableStatement();
        FutureTask futureTask = new FutureTask(callableStatement);
        this.threadGroup = new ThreadGroup("FailOnTimeoutGroup");
        Thread thread = new Thread(this.threadGroup, futureTask, "Time-limited test");
        thread.setDaemon(true);
        thread.start();
        callableStatement.awaitStarted();
        Throwable result = getResult(futureTask, thread);
        if (result != null) {
            throw result;
        }
    }

    private Throwable getResult(FutureTask<Throwable> futureTask, Thread thread) {
        try {
            if (this.timeout > 0) {
                return (Throwable) futureTask.get(this.timeout, this.timeUnit);
            }
            return (Throwable) futureTask.get();
        } catch (InterruptedException e) {
            return e;
        } catch (ExecutionException e2) {
            return e2.getCause();
        } catch (TimeoutException unused) {
            return createTimeoutException(thread);
        }
    }

    private Exception createTimeoutException(Thread thread) {
        StackTraceElement[] stackTrace = thread.getStackTrace();
        Thread stuckThread = this.lookForStuckThread ? getStuckThread(thread) : null;
        TestTimedOutException testTimedOutException = new TestTimedOutException(this.timeout, this.timeUnit);
        if (stackTrace != null) {
            testTimedOutException.setStackTrace(stackTrace);
            thread.interrupt();
        }
        if (stuckThread == null) {
            return testTimedOutException;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Appears to be stuck in thread ");
        sb.append(stuckThread.getName());
        Exception exc = new Exception(sb.toString());
        exc.setStackTrace(getStackTrace(stuckThread));
        return new MultipleFailureException(Arrays.asList(new Throwable[]{testTimedOutException, exc}));
    }

    private StackTraceElement[] getStackTrace(Thread thread) {
        try {
            return thread.getStackTrace();
        } catch (SecurityException unused) {
            return new StackTraceElement[0];
        }
    }

    private Thread getStuckThread(Thread thread) {
        Thread thread2 = null;
        if (this.threadGroup == null) {
            return null;
        }
        Thread[] threadArray = getThreadArray(this.threadGroup);
        if (threadArray == null) {
            return null;
        }
        long j = 0;
        Thread thread3 = null;
        for (Thread thread4 : threadArray) {
            if (thread4.getState() == State.RUNNABLE) {
                long cpuTime = cpuTime(thread4);
                if (thread3 == null || cpuTime > j) {
                    thread3 = thread4;
                    j = cpuTime;
                }
            }
        }
        if (thread3 != thread) {
            thread2 = thread3;
        }
        return thread2;
    }

    private Thread[] getThreadArray(ThreadGroup threadGroup2) {
        int max = Math.max(threadGroup2.activeCount() * 2, 100);
        int i = 0;
        do {
            Thread[] threadArr = new Thread[max];
            int enumerate = threadGroup2.enumerate(threadArr);
            if (enumerate < max) {
                return copyThreads(threadArr, enumerate);
            }
            max += 100;
            i++;
        } while (i < 5);
        return null;
    }

    private Thread[] copyThreads(Thread[] threadArr, int i) {
        int min = Math.min(i, threadArr.length);
        Thread[] threadArr2 = new Thread[min];
        for (int i2 = 0; i2 < min; i2++) {
            threadArr2[i2] = threadArr[i2];
        }
        return threadArr2;
    }

    private long cpuTime(Thread thread) {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        if (threadMXBean.isThreadCpuTimeSupported()) {
            try {
                return threadMXBean.getThreadCpuTime(thread.getId());
            } catch (UnsupportedOperationException unused) {
            }
        }
        return 0;
    }
}
