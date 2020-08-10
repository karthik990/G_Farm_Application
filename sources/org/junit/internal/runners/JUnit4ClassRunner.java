package org.junit.internal.runners;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.Filterable;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runner.manipulation.Sortable;
import org.junit.runner.manipulation.Sorter;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

@Deprecated
public class JUnit4ClassRunner extends Runner implements Filterable, Sortable {
    private TestClass testClass;
    private final List<Method> testMethods = getTestMethods();

    public JUnit4ClassRunner(Class<?> cls) throws InitializationError {
        this.testClass = new TestClass(cls);
        validate();
    }

    /* access modifiers changed from: protected */
    public List<Method> getTestMethods() {
        return this.testClass.getTestMethods();
    }

    /* access modifiers changed from: protected */
    public void validate() throws InitializationError {
        MethodValidator methodValidator = new MethodValidator(this.testClass);
        methodValidator.validateMethodsForDefaultRunner();
        methodValidator.assertValid();
    }

    public void run(final RunNotifier runNotifier) {
        new ClassRoadie(runNotifier, this.testClass, getDescription(), new Runnable() {
            public void run() {
                JUnit4ClassRunner.this.runMethods(runNotifier);
            }
        }).runProtected();
    }

    /* access modifiers changed from: protected */
    public void runMethods(RunNotifier runNotifier) {
        for (Method invokeTestMethod : this.testMethods) {
            invokeTestMethod(invokeTestMethod, runNotifier);
        }
    }

    public Description getDescription() {
        Description createSuiteDescription = Description.createSuiteDescription(getName(), classAnnotations());
        for (Method methodDescription : this.testMethods) {
            createSuiteDescription.addChild(methodDescription(methodDescription));
        }
        return createSuiteDescription;
    }

    /* access modifiers changed from: protected */
    public Annotation[] classAnnotations() {
        return this.testClass.getJavaClass().getAnnotations();
    }

    /* access modifiers changed from: protected */
    public String getName() {
        return getTestClass().getName();
    }

    /* access modifiers changed from: protected */
    public Object createTest() throws Exception {
        return getTestClass().getConstructor().newInstance(new Object[0]);
    }

    /* access modifiers changed from: protected */
    public void invokeTestMethod(Method method, RunNotifier runNotifier) {
        Description methodDescription = methodDescription(method);
        try {
            new MethodRoadie(createTest(), wrapMethod(method), runNotifier, methodDescription).run();
        } catch (InvocationTargetException e) {
            testAborted(runNotifier, methodDescription, e.getCause());
        } catch (Exception e2) {
            testAborted(runNotifier, methodDescription, e2);
        }
    }

    private void testAborted(RunNotifier runNotifier, Description description, Throwable th) {
        runNotifier.fireTestStarted(description);
        runNotifier.fireTestFailure(new Failure(description, th));
        runNotifier.fireTestFinished(description);
    }

    /* access modifiers changed from: protected */
    public TestMethod wrapMethod(Method method) {
        return new TestMethod(method, this.testClass);
    }

    /* access modifiers changed from: protected */
    public String testName(Method method) {
        return method.getName();
    }

    /* access modifiers changed from: protected */
    public Description methodDescription(Method method) {
        return Description.createTestDescription(getTestClass().getJavaClass(), testName(method), testAnnotations(method));
    }

    /* access modifiers changed from: protected */
    public Annotation[] testAnnotations(Method method) {
        return method.getAnnotations();
    }

    public void filter(Filter filter) throws NoTestsRemainException {
        Iterator it = this.testMethods.iterator();
        while (it.hasNext()) {
            if (!filter.shouldRun(methodDescription((Method) it.next()))) {
                it.remove();
            }
        }
        if (this.testMethods.isEmpty()) {
            throw new NoTestsRemainException();
        }
    }

    public void sort(final Sorter sorter) {
        Collections.sort(this.testMethods, new Comparator<Method>() {
            public int compare(Method method, Method method2) {
                return sorter.compare(JUnit4ClassRunner.this.methodDescription(method), JUnit4ClassRunner.this.methodDescription(method2));
            }
        });
    }

    /* access modifiers changed from: protected */
    public TestClass getTestClass() {
        return this.testClass;
    }
}
