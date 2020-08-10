package org.junit.runners;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collections;
import java.util.List;
import org.junit.internal.builders.AllDefaultPossibilitiesBuilder;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

public class Suite extends ParentRunner<Runner> {
    private final List<Runner> runners;

    @Inherited
    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface SuiteClasses {
        Class<?>[] value();
    }

    public static Runner emptySuite() {
        try {
            return new Suite(null, (Class<?>[]) new Class[0]);
        } catch (InitializationError unused) {
            throw new RuntimeException("This shouldn't be possible");
        }
    }

    private static Class<?>[] getAnnotatedClasses(Class<?> cls) throws InitializationError {
        SuiteClasses suiteClasses = (SuiteClasses) cls.getAnnotation(SuiteClasses.class);
        if (suiteClasses != null) {
            return suiteClasses.value();
        }
        throw new InitializationError(String.format("class '%s' must have a SuiteClasses annotation", new Object[]{cls.getName()}));
    }

    public Suite(Class<?> cls, RunnerBuilder runnerBuilder) throws InitializationError {
        this(runnerBuilder, cls, getAnnotatedClasses(cls));
    }

    public Suite(RunnerBuilder runnerBuilder, Class<?>[] clsArr) throws InitializationError {
        this(null, runnerBuilder.runners(null, clsArr));
    }

    protected Suite(Class<?> cls, Class<?>[] clsArr) throws InitializationError {
        this(new AllDefaultPossibilitiesBuilder(true), cls, clsArr);
    }

    protected Suite(RunnerBuilder runnerBuilder, Class<?> cls, Class<?>[] clsArr) throws InitializationError {
        this(cls, runnerBuilder.runners(cls, clsArr));
    }

    protected Suite(Class<?> cls, List<Runner> list) throws InitializationError {
        super(cls);
        this.runners = Collections.unmodifiableList(list);
    }

    /* access modifiers changed from: protected */
    public List<Runner> getChildren() {
        return this.runners;
    }

    /* access modifiers changed from: protected */
    public Description describeChild(Runner runner) {
        return runner.getDescription();
    }

    /* access modifiers changed from: protected */
    public void runChild(Runner runner, RunNotifier runNotifier) {
        runner.run(runNotifier);
    }
}
