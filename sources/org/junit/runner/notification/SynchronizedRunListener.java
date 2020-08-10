package org.junit.runner.notification;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener.ThreadSafe;

@ThreadSafe
final class SynchronizedRunListener extends RunListener {
    private final RunListener listener;
    private final Object monitor;

    SynchronizedRunListener(RunListener runListener, Object obj) {
        this.listener = runListener;
        this.monitor = obj;
    }

    public void testRunStarted(Description description) throws Exception {
        synchronized (this.monitor) {
            this.listener.testRunStarted(description);
        }
    }

    public void testRunFinished(Result result) throws Exception {
        synchronized (this.monitor) {
            this.listener.testRunFinished(result);
        }
    }

    public void testStarted(Description description) throws Exception {
        synchronized (this.monitor) {
            this.listener.testStarted(description);
        }
    }

    public void testFinished(Description description) throws Exception {
        synchronized (this.monitor) {
            this.listener.testFinished(description);
        }
    }

    public void testFailure(Failure failure) throws Exception {
        synchronized (this.monitor) {
            this.listener.testFailure(failure);
        }
    }

    public void testAssumptionFailure(Failure failure) {
        synchronized (this.monitor) {
            this.listener.testAssumptionFailure(failure);
        }
    }

    public void testIgnored(Description description) throws Exception {
        synchronized (this.monitor) {
            this.listener.testIgnored(description);
        }
    }

    public int hashCode() {
        return this.listener.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SynchronizedRunListener)) {
            return false;
        }
        return this.listener.equals(((SynchronizedRunListener) obj).listener);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.listener.toString());
        sb.append(" (with synchronization wrapper)");
        return sb.toString();
    }
}
