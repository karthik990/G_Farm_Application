package org.junit.internal.runners;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.junit.internal.AssumptionViolatedException;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.TestTimedOutException;

@Deprecated
public class MethodRoadie {
    private final Description description;
    private final RunNotifier notifier;
    private final Object test;
    private TestMethod testMethod;

    public MethodRoadie(Object obj, TestMethod testMethod2, RunNotifier runNotifier, Description description2) {
        this.test = obj;
        this.notifier = runNotifier;
        this.description = description2;
        this.testMethod = testMethod2;
    }

    public void run() {
        if (this.testMethod.isIgnored()) {
            this.notifier.fireTestIgnored(this.description);
            return;
        }
        this.notifier.fireTestStarted(this.description);
        try {
            long timeout = this.testMethod.getTimeout();
            if (timeout > 0) {
                runWithTimeout(timeout);
            } else {
                runTest();
            }
        } finally {
            this.notifier.fireTestFinished(this.description);
        }
    }

    private void runWithTimeout(final long j) {
        runBeforesThenTestThenAfters(new Runnable() {
            public void run() {
                ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
                Future submit = newSingleThreadExecutor.submit(new Callable<Object>() {
                    public Object call() throws Exception {
                        MethodRoadie.this.runTestMethod();
                        return null;
                    }
                });
                newSingleThreadExecutor.shutdown();
                try {
                    if (!newSingleThreadExecutor.awaitTermination(j, TimeUnit.MILLISECONDS)) {
                        newSingleThreadExecutor.shutdownNow();
                    }
                    submit.get(0, TimeUnit.MILLISECONDS);
                } catch (TimeoutException unused) {
                    MethodRoadie.this.addFailure(new TestTimedOutException(j, TimeUnit.MILLISECONDS));
                } catch (Exception e) {
                    MethodRoadie.this.addFailure(e);
                }
            }
        });
    }

    public void runTest() {
        runBeforesThenTestThenAfters(new Runnable() {
            public void run() {
                MethodRoadie.this.runTestMethod();
            }
        });
    }

    /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0009 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void runBeforesThenTestThenAfters(java.lang.Runnable r2) {
        /*
            r1 = this;
            r1.runBefores()     // Catch:{ FailedBefore -> 0x0015, Exception -> 0x0009 }
            r2.run()     // Catch:{ FailedBefore -> 0x0015, Exception -> 0x0009 }
            goto L_0x0015
        L_0x0007:
            r2 = move-exception
            goto L_0x0011
        L_0x0009:
            java.lang.RuntimeException r2 = new java.lang.RuntimeException     // Catch:{ all -> 0x0007 }
            java.lang.String r0 = "test should never throw an exception to this level"
            r2.<init>(r0)     // Catch:{ all -> 0x0007 }
            throw r2     // Catch:{ all -> 0x0007 }
        L_0x0011:
            r1.runAfters()
            throw r2
        L_0x0015:
            r1.runAfters()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.junit.internal.runners.MethodRoadie.runBeforesThenTestThenAfters(java.lang.Runnable):void");
    }

    /* access modifiers changed from: protected */
    public void runTestMethod() {
        try {
            this.testMethod.invoke(this.test);
            if (this.testMethod.expectsException()) {
                StringBuilder sb = new StringBuilder();
                sb.append("Expected exception: ");
                sb.append(this.testMethod.getExpectedException().getName());
                addFailure(new AssertionError(sb.toString()));
            }
        } catch (InvocationTargetException e) {
            Throwable targetException = e.getTargetException();
            if (!(targetException instanceof AssumptionViolatedException)) {
                if (!this.testMethod.expectsException()) {
                    addFailure(targetException);
                } else if (this.testMethod.isUnexpected(targetException)) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Unexpected exception, expected<");
                    sb2.append(this.testMethod.getExpectedException().getName());
                    sb2.append("> but was<");
                    sb2.append(targetException.getClass().getName());
                    sb2.append(">");
                    addFailure(new Exception(sb2.toString(), targetException));
                }
            }
        } catch (Throwable th) {
            addFailure(th);
        }
    }

    private void runBefores() throws FailedBefore {
        try {
            for (Method invoke : this.testMethod.getBefores()) {
                invoke.invoke(this.test, new Object[0]);
            }
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        } catch (AssumptionViolatedException unused) {
            throw new FailedBefore();
        } catch (Throwable th) {
            addFailure(th);
            throw new FailedBefore();
        }
    }

    private void runAfters() {
        for (Method invoke : this.testMethod.getAfters()) {
            try {
                invoke.invoke(this.test, new Object[0]);
            } catch (InvocationTargetException e) {
                addFailure(e.getTargetException());
            } catch (Throwable th) {
                addFailure(th);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void addFailure(Throwable th) {
        this.notifier.fireTestFailure(new Failure(this.description, th));
    }
}
