package org.junit.runners;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.runner.Runner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.TestClass;
import org.junit.runners.parameterized.BlockJUnit4ClassRunnerWithParametersFactory;
import org.junit.runners.parameterized.ParametersRunnerFactory;
import org.junit.runners.parameterized.TestWithParameters;

public class Parameterized extends Suite {
    private static final ParametersRunnerFactory DEFAULT_FACTORY = new BlockJUnit4ClassRunnerWithParametersFactory();
    private static final List<Runner> NO_RUNNERS = Collections.emptyList();
    private final List<Runner> runners;

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Parameter {
        int value() default 0;
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Parameters {
        String name() default "{index}";
    }

    @Inherited
    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface UseParametersRunnerFactory {
        Class<? extends ParametersRunnerFactory> value() default BlockJUnit4ClassRunnerWithParametersFactory.class;
    }

    public Parameterized(Class<?> cls) throws Throwable {
        super(cls, NO_RUNNERS);
        this.runners = Collections.unmodifiableList(createRunnersForParameters(allParameters(), ((Parameters) getParametersMethod().getAnnotation(Parameters.class)).name(), getParametersRunnerFactory(cls)));
    }

    private ParametersRunnerFactory getParametersRunnerFactory(Class<?> cls) throws InstantiationException, IllegalAccessException {
        UseParametersRunnerFactory useParametersRunnerFactory = (UseParametersRunnerFactory) cls.getAnnotation(UseParametersRunnerFactory.class);
        if (useParametersRunnerFactory == null) {
            return DEFAULT_FACTORY;
        }
        return (ParametersRunnerFactory) useParametersRunnerFactory.value().newInstance();
    }

    /* access modifiers changed from: protected */
    public List<Runner> getChildren() {
        return this.runners;
    }

    private TestWithParameters createTestWithNotNormalizedParameters(String str, int i, Object obj) {
        Object[] objArr;
        if (obj instanceof Object[]) {
            objArr = (Object[]) obj;
        } else {
            objArr = new Object[]{obj};
        }
        return createTestWithParameters(getTestClass(), str, i, objArr);
    }

    private Iterable<Object> allParameters() throws Throwable {
        Object invokeExplosively = getParametersMethod().invokeExplosively(null, new Object[0]);
        if (invokeExplosively instanceof Iterable) {
            return (Iterable) invokeExplosively;
        }
        if (invokeExplosively instanceof Object[]) {
            return Arrays.asList((Object[]) invokeExplosively);
        }
        throw parametersMethodReturnedWrongType();
    }

    private FrameworkMethod getParametersMethod() throws Exception {
        for (FrameworkMethod frameworkMethod : getTestClass().getAnnotatedMethods(Parameters.class)) {
            if (frameworkMethod.isStatic() && frameworkMethod.isPublic()) {
                return frameworkMethod;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("No public static parameters method on class ");
        sb.append(getTestClass().getName());
        throw new Exception(sb.toString());
    }

    private List<Runner> createRunnersForParameters(Iterable<Object> iterable, String str, ParametersRunnerFactory parametersRunnerFactory) throws InitializationError, Exception {
        try {
            List<TestWithParameters> createTestsForParameters = createTestsForParameters(iterable, str);
            ArrayList arrayList = new ArrayList();
            for (TestWithParameters createRunnerForTestWithParameters : createTestsForParameters) {
                arrayList.add(parametersRunnerFactory.createRunnerForTestWithParameters(createRunnerForTestWithParameters));
            }
            return arrayList;
        } catch (ClassCastException unused) {
            throw parametersMethodReturnedWrongType();
        }
    }

    private List<TestWithParameters> createTestsForParameters(Iterable<Object> iterable, String str) throws Exception {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (Object createTestWithNotNormalizedParameters : iterable) {
            int i2 = i + 1;
            arrayList.add(createTestWithNotNormalizedParameters(str, i, createTestWithNotNormalizedParameters));
            i = i2;
        }
        return arrayList;
    }

    private Exception parametersMethodReturnedWrongType() throws Exception {
        return new Exception(MessageFormat.format("{0}.{1}() must return an Iterable of arrays.", new Object[]{getTestClass().getName(), getParametersMethod().getName()}));
    }

    private static TestWithParameters createTestWithParameters(TestClass testClass, String str, int i, Object[] objArr) {
        String format = MessageFormat.format(str.replaceAll("\\{index\\}", Integer.toString(i)), objArr);
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(format);
        sb.append("]");
        return new TestWithParameters(sb.toString(), testClass, Arrays.asList(objArr));
    }
}
