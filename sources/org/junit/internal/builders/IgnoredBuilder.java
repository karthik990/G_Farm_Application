package org.junit.internal.builders;

import org.junit.Ignore;
import org.junit.runner.Runner;
import org.junit.runners.model.RunnerBuilder;

public class IgnoredBuilder extends RunnerBuilder {
    public Runner runnerForClass(Class<?> cls) {
        if (cls.getAnnotation(Ignore.class) != null) {
            return new IgnoredClassRunner(cls);
        }
        return null;
    }
}
