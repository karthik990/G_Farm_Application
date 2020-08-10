package org.junit.internal.builders;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

public class IgnoredClassRunner extends Runner {
    private final Class<?> clazz;

    public IgnoredClassRunner(Class<?> cls) {
        this.clazz = cls;
    }

    public void run(RunNotifier runNotifier) {
        runNotifier.fireTestIgnored(getDescription());
    }

    public Description getDescription() {
        return Description.createSuiteDescription(this.clazz);
    }
}
