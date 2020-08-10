package org.junit.rules;

import java.util.ArrayList;
import java.util.List;
import org.junit.internal.AssumptionViolatedException;
import org.junit.runner.Description;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;

public abstract class TestWatcher implements TestRule {
    /* access modifiers changed from: protected */
    public void failed(Throwable th, Description description) {
    }

    /* access modifiers changed from: protected */
    public void finished(Description description) {
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void skipped(AssumptionViolatedException assumptionViolatedException, Description description) {
    }

    /* access modifiers changed from: protected */
    public void starting(Description description) {
    }

    /* access modifiers changed from: protected */
    public void succeeded(Description description) {
    }

    public Statement apply(final Statement statement, final Description description) {
        return new Statement() {
            public void evaluate() throws Throwable {
                ArrayList arrayList = new ArrayList();
                TestWatcher.this.startingQuietly(description, arrayList);
                try {
                    statement.evaluate();
                    TestWatcher.this.succeededQuietly(description, arrayList);
                } catch (AssumptionViolatedException e) {
                    arrayList.add(e);
                    TestWatcher.this.skippedQuietly(e, description, arrayList);
                } catch (Throwable th) {
                    TestWatcher.this.finishedQuietly(description, arrayList);
                    throw th;
                }
                TestWatcher.this.finishedQuietly(description, arrayList);
                MultipleFailureException.assertEmpty(arrayList);
            }
        };
    }

    /* access modifiers changed from: private */
    public void succeededQuietly(Description description, List<Throwable> list) {
        try {
            succeeded(description);
        } catch (Throwable th) {
            list.add(th);
        }
    }

    /* access modifiers changed from: private */
    public void failedQuietly(Throwable th, Description description, List<Throwable> list) {
        try {
            failed(th, description);
        } catch (Throwable th2) {
            list.add(th2);
        }
    }

    /* access modifiers changed from: private */
    public void skippedQuietly(AssumptionViolatedException assumptionViolatedException, Description description, List<Throwable> list) {
        try {
            if (assumptionViolatedException instanceof org.junit.AssumptionViolatedException) {
                skipped((org.junit.AssumptionViolatedException) assumptionViolatedException, description);
            } else {
                skipped(assumptionViolatedException, description);
            }
        } catch (Throwable th) {
            list.add(th);
        }
    }

    /* access modifiers changed from: private */
    public void startingQuietly(Description description, List<Throwable> list) {
        try {
            starting(description);
        } catch (Throwable th) {
            list.add(th);
        }
    }

    /* access modifiers changed from: private */
    public void finishedQuietly(Description description, List<Throwable> list) {
        try {
            finished(description);
        } catch (Throwable th) {
            list.add(th);
        }
    }

    /* access modifiers changed from: protected */
    public void skipped(org.junit.AssumptionViolatedException assumptionViolatedException, Description description) {
        skipped((AssumptionViolatedException) assumptionViolatedException, description);
    }
}
