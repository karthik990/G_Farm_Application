package org.junit.internal.runners;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;

public class ErrorReportingRunner extends Runner {
    private final List<Throwable> causes;
    private final Class<?> testClass;

    public ErrorReportingRunner(Class<?> cls, Throwable th) {
        if (cls != null) {
            this.testClass = cls;
            this.causes = getCauses(th);
            return;
        }
        throw new NullPointerException("Test class cannot be null");
    }

    public Description getDescription() {
        Description createSuiteDescription = Description.createSuiteDescription(this.testClass);
        for (Throwable describeCause : this.causes) {
            createSuiteDescription.addChild(describeCause(describeCause));
        }
        return createSuiteDescription;
    }

    public void run(RunNotifier runNotifier) {
        for (Throwable runCause : this.causes) {
            runCause(runCause, runNotifier);
        }
    }

    private List<Throwable> getCauses(Throwable th) {
        if (th instanceof InvocationTargetException) {
            return getCauses(th.getCause());
        }
        if (th instanceof InitializationError) {
            return ((InitializationError) th).getCauses();
        }
        if (th instanceof InitializationError) {
            return ((InitializationError) th).getCauses();
        }
        return Arrays.asList(new Throwable[]{th});
    }

    private Description describeCause(Throwable th) {
        return Description.createTestDescription(this.testClass, "initializationError");
    }

    private void runCause(Throwable th, RunNotifier runNotifier) {
        Description describeCause = describeCause(th);
        runNotifier.fireTestStarted(describeCause);
        runNotifier.fireTestFailure(new Failure(describeCause, th));
        runNotifier.fireTestFinished(describeCause);
    }
}
