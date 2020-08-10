package org.junit.runners;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.Test.None;
import org.junit.internal.runners.model.ReflectiveCallable;
import org.junit.internal.runners.rules.RuleMemberValidator;
import org.junit.internal.runners.statements.ExpectException;
import org.junit.internal.runners.statements.Fail;
import org.junit.internal.runners.statements.FailOnTimeout;
import org.junit.internal.runners.statements.InvokeMethod;
import org.junit.internal.runners.statements.RunAfters;
import org.junit.internal.runners.statements.RunBefores;
import org.junit.rules.MethodRule;
import org.junit.rules.RunRules;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

public class BlockJUnit4ClassRunner extends ParentRunner<FrameworkMethod> {
    private final ConcurrentHashMap<FrameworkMethod, Description> methodDescriptions = new ConcurrentHashMap<>();

    public BlockJUnit4ClassRunner(Class<?> cls) throws InitializationError {
        super(cls);
    }

    /* access modifiers changed from: protected */
    public void runChild(FrameworkMethod frameworkMethod, RunNotifier runNotifier) {
        Description describeChild = describeChild(frameworkMethod);
        if (isIgnored(frameworkMethod)) {
            runNotifier.fireTestIgnored(describeChild);
        } else {
            runLeaf(methodBlock(frameworkMethod), describeChild, runNotifier);
        }
    }

    /* access modifiers changed from: protected */
    public boolean isIgnored(FrameworkMethod frameworkMethod) {
        return frameworkMethod.getAnnotation(Ignore.class) != null;
    }

    /* access modifiers changed from: protected */
    public Description describeChild(FrameworkMethod frameworkMethod) {
        Description description = (Description) this.methodDescriptions.get(frameworkMethod);
        if (description != null) {
            return description;
        }
        Description createTestDescription = Description.createTestDescription(getTestClass().getJavaClass(), testName(frameworkMethod), frameworkMethod.getAnnotations());
        this.methodDescriptions.putIfAbsent(frameworkMethod, createTestDescription);
        return createTestDescription;
    }

    /* access modifiers changed from: protected */
    public List<FrameworkMethod> getChildren() {
        return computeTestMethods();
    }

    /* access modifiers changed from: protected */
    public List<FrameworkMethod> computeTestMethods() {
        return getTestClass().getAnnotatedMethods(Test.class);
    }

    /* access modifiers changed from: protected */
    public void collectInitializationErrors(List<Throwable> list) {
        super.collectInitializationErrors(list);
        validateNoNonStaticInnerClass(list);
        validateConstructor(list);
        validateInstanceMethods(list);
        validateFields(list);
        validateMethods(list);
    }

    /* access modifiers changed from: protected */
    public void validateNoNonStaticInnerClass(List<Throwable> list) {
        if (getTestClass().isANonStaticInnerClass()) {
            StringBuilder sb = new StringBuilder();
            sb.append("The inner class ");
            sb.append(getTestClass().getName());
            sb.append(" is not static.");
            list.add(new Exception(sb.toString()));
        }
    }

    /* access modifiers changed from: protected */
    public void validateConstructor(List<Throwable> list) {
        validateOnlyOneConstructor(list);
        validateZeroArgConstructor(list);
    }

    /* access modifiers changed from: protected */
    public void validateOnlyOneConstructor(List<Throwable> list) {
        if (!hasOneConstructor()) {
            list.add(new Exception("Test class should have exactly one public constructor"));
        }
    }

    /* access modifiers changed from: protected */
    public void validateZeroArgConstructor(List<Throwable> list) {
        if (!getTestClass().isANonStaticInnerClass() && hasOneConstructor() && getTestClass().getOnlyConstructor().getParameterTypes().length != 0) {
            list.add(new Exception("Test class should have exactly one public zero-argument constructor"));
        }
    }

    private boolean hasOneConstructor() {
        return getTestClass().getJavaClass().getConstructors().length == 1;
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void validateInstanceMethods(List<Throwable> list) {
        validatePublicVoidNoArgMethods(After.class, false, list);
        validatePublicVoidNoArgMethods(Before.class, false, list);
        validateTestMethods(list);
        if (computeTestMethods().size() == 0) {
            list.add(new Exception("No runnable methods"));
        }
    }

    /* access modifiers changed from: protected */
    public void validateFields(List<Throwable> list) {
        RuleMemberValidator.RULE_VALIDATOR.validate(getTestClass(), list);
    }

    private void validateMethods(List<Throwable> list) {
        RuleMemberValidator.RULE_METHOD_VALIDATOR.validate(getTestClass(), list);
    }

    /* access modifiers changed from: protected */
    public void validateTestMethods(List<Throwable> list) {
        validatePublicVoidNoArgMethods(Test.class, false, list);
    }

    /* access modifiers changed from: protected */
    public Object createTest() throws Exception {
        return getTestClass().getOnlyConstructor().newInstance(new Object[0]);
    }

    /* access modifiers changed from: protected */
    public String testName(FrameworkMethod frameworkMethod) {
        return frameworkMethod.getName();
    }

    /* access modifiers changed from: protected */
    public Statement methodBlock(FrameworkMethod frameworkMethod) {
        try {
            Object run = new ReflectiveCallable() {
                /* access modifiers changed from: protected */
                public Object runReflectiveCall() throws Throwable {
                    return BlockJUnit4ClassRunner.this.createTest();
                }
            }.run();
            return withRules(frameworkMethod, run, withAfters(frameworkMethod, run, withBefores(frameworkMethod, run, withPotentialTimeout(frameworkMethod, run, possiblyExpectingExceptions(frameworkMethod, run, methodInvoker(frameworkMethod, run))))));
        } catch (Throwable th) {
            return new Fail(th);
        }
    }

    /* access modifiers changed from: protected */
    public Statement methodInvoker(FrameworkMethod frameworkMethod, Object obj) {
        return new InvokeMethod(frameworkMethod, obj);
    }

    /* access modifiers changed from: protected */
    public Statement possiblyExpectingExceptions(FrameworkMethod frameworkMethod, Object obj, Statement statement) {
        Test test = (Test) frameworkMethod.getAnnotation(Test.class);
        return expectsException(test) ? new ExpectException(statement, getExpectedException(test)) : statement;
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public Statement withPotentialTimeout(FrameworkMethod frameworkMethod, Object obj, Statement statement) {
        long timeout = getTimeout((Test) frameworkMethod.getAnnotation(Test.class));
        if (timeout <= 0) {
            return statement;
        }
        return FailOnTimeout.builder().withTimeout(timeout, TimeUnit.MILLISECONDS).build(statement);
    }

    /* access modifiers changed from: protected */
    public Statement withBefores(FrameworkMethod frameworkMethod, Object obj, Statement statement) {
        List annotatedMethods = getTestClass().getAnnotatedMethods(Before.class);
        return annotatedMethods.isEmpty() ? statement : new RunBefores(statement, annotatedMethods, obj);
    }

    /* access modifiers changed from: protected */
    public Statement withAfters(FrameworkMethod frameworkMethod, Object obj, Statement statement) {
        List annotatedMethods = getTestClass().getAnnotatedMethods(After.class);
        return annotatedMethods.isEmpty() ? statement : new RunAfters(statement, annotatedMethods, obj);
    }

    private Statement withRules(FrameworkMethod frameworkMethod, Object obj, Statement statement) {
        List testRules = getTestRules(obj);
        return withTestRules(frameworkMethod, testRules, withMethodRules(frameworkMethod, testRules, obj, statement));
    }

    private Statement withMethodRules(FrameworkMethod frameworkMethod, List<TestRule> list, Object obj, Statement statement) {
        for (MethodRule methodRule : getMethodRules(obj)) {
            if (!list.contains(methodRule)) {
                statement = methodRule.apply(statement, frameworkMethod, obj);
            }
        }
        return statement;
    }

    private List<MethodRule> getMethodRules(Object obj) {
        return rules(obj);
    }

    /* access modifiers changed from: protected */
    public List<MethodRule> rules(Object obj) {
        List<MethodRule> annotatedMethodValues = getTestClass().getAnnotatedMethodValues(obj, Rule.class, MethodRule.class);
        annotatedMethodValues.addAll(getTestClass().getAnnotatedFieldValues(obj, Rule.class, MethodRule.class));
        return annotatedMethodValues;
    }

    private Statement withTestRules(FrameworkMethod frameworkMethod, List<TestRule> list, Statement statement) {
        return list.isEmpty() ? statement : new RunRules(statement, list, describeChild(frameworkMethod));
    }

    /* access modifiers changed from: protected */
    public List<TestRule> getTestRules(Object obj) {
        List<TestRule> annotatedMethodValues = getTestClass().getAnnotatedMethodValues(obj, Rule.class, TestRule.class);
        annotatedMethodValues.addAll(getTestClass().getAnnotatedFieldValues(obj, Rule.class, TestRule.class));
        return annotatedMethodValues;
    }

    private Class<? extends Throwable> getExpectedException(Test test) {
        if (test == null || test.expected() == None.class) {
            return null;
        }
        return test.expected();
    }

    private boolean expectsException(Test test) {
        return getExpectedException(test) != null;
    }

    private long getTimeout(Test test) {
        if (test == null) {
            return 0;
        }
        return test.timeout();
    }
}
