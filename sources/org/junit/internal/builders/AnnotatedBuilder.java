package org.junit.internal.builders;

import java.lang.reflect.Modifier;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

public class AnnotatedBuilder extends RunnerBuilder {
    private static final String CONSTRUCTOR_ERROR_FORMAT = "Custom runner class %s should have a public constructor with signature %s(Class testClass)";
    private final RunnerBuilder suiteBuilder;

    public AnnotatedBuilder(RunnerBuilder runnerBuilder) {
        this.suiteBuilder = runnerBuilder;
    }

    public Runner runnerForClass(Class<?> cls) throws Exception {
        Class<?> cls2 = cls;
        while (cls2 != null) {
            RunWith runWith = (RunWith) cls2.getAnnotation(RunWith.class);
            if (runWith != null) {
                return buildRunner(runWith.value(), cls);
            }
            cls2 = getEnclosingClassForNonStaticMemberClass(cls2);
        }
        return null;
    }

    private Class<?> getEnclosingClassForNonStaticMemberClass(Class<?> cls) {
        if (!cls.isMemberClass() || Modifier.isStatic(cls.getModifiers())) {
            return null;
        }
        return cls.getEnclosingClass();
    }

    public Runner buildRunner(Class<? extends Runner> cls, Class<?> cls2) throws Exception {
        try {
            return (Runner) cls.getConstructor(new Class[]{Class.class}).newInstance(new Object[]{cls2});
        } catch (NoSuchMethodException unused) {
            try {
                return (Runner) cls.getConstructor(new Class[]{Class.class, RunnerBuilder.class}).newInstance(new Object[]{cls2, this.suiteBuilder});
            } catch (NoSuchMethodException unused2) {
                String simpleName = cls.getSimpleName();
                throw new InitializationError(String.format(CONSTRUCTOR_ERROR_FORMAT, new Object[]{simpleName, simpleName}));
            }
        }
    }
}
