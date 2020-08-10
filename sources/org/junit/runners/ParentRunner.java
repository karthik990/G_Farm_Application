package org.junit.runners;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.internal.AssumptionViolatedException;
import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.internal.runners.rules.RuleMemberValidator;
import org.junit.internal.runners.statements.RunAfters;
import org.junit.internal.runners.statements.RunBefores;
import org.junit.rules.RunRules;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.Filterable;
import org.junit.runner.manipulation.Sortable;
import org.junit.runner.manipulation.Sorter;
import org.junit.runner.notification.RunNotifier;
import org.junit.runner.notification.StoppedByUserException;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerScheduler;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;
import org.junit.validator.AnnotationsValidator;
import org.junit.validator.PublicClassValidator;
import org.junit.validator.TestClassValidator;

public abstract class ParentRunner<T> extends Runner implements Filterable, Sortable {
    private static final List<TestClassValidator> VALIDATORS = Arrays.asList(new TestClassValidator[]{new AnnotationsValidator(), new PublicClassValidator()});
    private final Object childrenLock = new Object();
    private volatile Collection<T> filteredChildren = null;
    private volatile RunnerScheduler scheduler = new RunnerScheduler() {
        public void finished() {
        }

        public void schedule(Runnable runnable) {
            runnable.run();
        }
    };
    private final TestClass testClass;

    /* access modifiers changed from: protected */
    public abstract Description describeChild(T t);

    /* access modifiers changed from: protected */
    public abstract List<T> getChildren();

    /* access modifiers changed from: protected */
    public boolean isIgnored(T t) {
        return false;
    }

    /* access modifiers changed from: protected */
    public abstract void runChild(T t, RunNotifier runNotifier);

    protected ParentRunner(Class<?> cls) throws InitializationError {
        this.testClass = createTestClass(cls);
        validate();
    }

    /* access modifiers changed from: protected */
    public TestClass createTestClass(Class<?> cls) {
        return new TestClass(cls);
    }

    /* access modifiers changed from: protected */
    public void collectInitializationErrors(List<Throwable> list) {
        validatePublicVoidNoArgMethods(BeforeClass.class, true, list);
        validatePublicVoidNoArgMethods(AfterClass.class, true, list);
        validateClassRules(list);
        applyValidators(list);
    }

    private void applyValidators(List<Throwable> list) {
        if (getTestClass().getJavaClass() != null) {
            for (TestClassValidator validateTestClass : VALIDATORS) {
                list.addAll(validateTestClass.validateTestClass(getTestClass()));
            }
        }
    }

    /* access modifiers changed from: protected */
    public void validatePublicVoidNoArgMethods(Class<? extends Annotation> cls, boolean z, List<Throwable> list) {
        for (FrameworkMethod validatePublicVoidNoArg : getTestClass().getAnnotatedMethods(cls)) {
            validatePublicVoidNoArg.validatePublicVoidNoArg(z, list);
        }
    }

    private void validateClassRules(List<Throwable> list) {
        RuleMemberValidator.CLASS_RULE_VALIDATOR.validate(getTestClass(), list);
        RuleMemberValidator.CLASS_RULE_METHOD_VALIDATOR.validate(getTestClass(), list);
    }

    /* access modifiers changed from: protected */
    public Statement classBlock(RunNotifier runNotifier) {
        Statement childrenInvoker = childrenInvoker(runNotifier);
        return !areAllChildrenIgnored() ? withClassRules(withAfterClasses(withBeforeClasses(childrenInvoker))) : childrenInvoker;
    }

    private boolean areAllChildrenIgnored() {
        for (Object isIgnored : getFilteredChildren()) {
            if (!isIgnored(isIgnored)) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public Statement withBeforeClasses(Statement statement) {
        List annotatedMethods = this.testClass.getAnnotatedMethods(BeforeClass.class);
        return annotatedMethods.isEmpty() ? statement : new RunBefores(statement, annotatedMethods, null);
    }

    /* access modifiers changed from: protected */
    public Statement withAfterClasses(Statement statement) {
        List annotatedMethods = this.testClass.getAnnotatedMethods(AfterClass.class);
        return annotatedMethods.isEmpty() ? statement : new RunAfters(statement, annotatedMethods, null);
    }

    private Statement withClassRules(Statement statement) {
        List classRules = classRules();
        return classRules.isEmpty() ? statement : new RunRules(statement, classRules, getDescription());
    }

    /* access modifiers changed from: protected */
    public List<TestRule> classRules() {
        List<TestRule> annotatedMethodValues = this.testClass.getAnnotatedMethodValues(null, ClassRule.class, TestRule.class);
        annotatedMethodValues.addAll(this.testClass.getAnnotatedFieldValues(null, ClassRule.class, TestRule.class));
        return annotatedMethodValues;
    }

    /* access modifiers changed from: protected */
    public Statement childrenInvoker(final RunNotifier runNotifier) {
        return new Statement() {
            public void evaluate() {
                ParentRunner.this.runChildren(runNotifier);
            }
        };
    }

    /* access modifiers changed from: private */
    public void runChildren(final RunNotifier runNotifier) {
        RunnerScheduler runnerScheduler = this.scheduler;
        try {
            for (final Object next : getFilteredChildren()) {
                runnerScheduler.schedule(new Runnable() {
                    public void run() {
                        ParentRunner.this.runChild(next, runNotifier);
                    }
                });
            }
        } finally {
            runnerScheduler.finished();
        }
    }

    /* access modifiers changed from: protected */
    public String getName() {
        return this.testClass.getName();
    }

    public final TestClass getTestClass() {
        return this.testClass;
    }

    /* access modifiers changed from: protected */
    public final void runLeaf(Statement statement, Description description, RunNotifier runNotifier) {
        EachTestNotifier eachTestNotifier = new EachTestNotifier(runNotifier, description);
        eachTestNotifier.fireTestStarted();
        try {
            statement.evaluate();
        } catch (AssumptionViolatedException e) {
            eachTestNotifier.addFailedAssumption(e);
        } catch (Throwable th) {
            eachTestNotifier.fireTestFinished();
            throw th;
        }
        eachTestNotifier.fireTestFinished();
    }

    /* access modifiers changed from: protected */
    public Annotation[] getRunnerAnnotations() {
        return this.testClass.getAnnotations();
    }

    public Description getDescription() {
        Description createSuiteDescription = Description.createSuiteDescription(getName(), getRunnerAnnotations());
        for (Object describeChild : getFilteredChildren()) {
            createSuiteDescription.addChild(describeChild(describeChild));
        }
        return createSuiteDescription;
    }

    public void run(RunNotifier runNotifier) {
        EachTestNotifier eachTestNotifier = new EachTestNotifier(runNotifier, getDescription());
        try {
            classBlock(runNotifier).evaluate();
        } catch (AssumptionViolatedException e) {
            eachTestNotifier.addFailedAssumption(e);
        } catch (StoppedByUserException e2) {
            throw e2;
        } catch (Throwable th) {
            eachTestNotifier.addFailure(th);
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(5:8|9|10|11|25) */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0010, code lost:
        continue;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:10:0x0024 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void filter(org.junit.runner.manipulation.Filter r6) throws org.junit.runner.manipulation.NoTestsRemainException {
        /*
            r5 = this;
            java.lang.Object r0 = r5.childrenLock
            monitor-enter(r0)
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ all -> 0x0042 }
            java.util.Collection r2 = r5.getFilteredChildren()     // Catch:{ all -> 0x0042 }
            r1.<init>(r2)     // Catch:{ all -> 0x0042 }
            java.util.Iterator r2 = r1.iterator()     // Catch:{ all -> 0x0042 }
        L_0x0010:
            boolean r3 = r2.hasNext()     // Catch:{ all -> 0x0042 }
            if (r3 == 0) goto L_0x002c
            java.lang.Object r3 = r2.next()     // Catch:{ all -> 0x0042 }
            boolean r4 = r5.shouldRun(r6, r3)     // Catch:{ all -> 0x0042 }
            if (r4 == 0) goto L_0x0028
            r6.apply(r3)     // Catch:{ NoTestsRemainException -> 0x0024 }
            goto L_0x0010
        L_0x0024:
            r2.remove()     // Catch:{ all -> 0x0042 }
            goto L_0x0010
        L_0x0028:
            r2.remove()     // Catch:{ all -> 0x0042 }
            goto L_0x0010
        L_0x002c:
            java.util.Collection r6 = java.util.Collections.unmodifiableCollection(r1)     // Catch:{ all -> 0x0042 }
            r5.filteredChildren = r6     // Catch:{ all -> 0x0042 }
            java.util.Collection<T> r6 = r5.filteredChildren     // Catch:{ all -> 0x0042 }
            boolean r6 = r6.isEmpty()     // Catch:{ all -> 0x0042 }
            if (r6 != 0) goto L_0x003c
            monitor-exit(r0)     // Catch:{ all -> 0x0042 }
            return
        L_0x003c:
            org.junit.runner.manipulation.NoTestsRemainException r6 = new org.junit.runner.manipulation.NoTestsRemainException     // Catch:{ all -> 0x0042 }
            r6.<init>()     // Catch:{ all -> 0x0042 }
            throw r6     // Catch:{ all -> 0x0042 }
        L_0x0042:
            r6 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0042 }
            goto L_0x0046
        L_0x0045:
            throw r6
        L_0x0046:
            goto L_0x0045
        */
        throw new UnsupportedOperationException("Method not decompiled: org.junit.runners.ParentRunner.filter(org.junit.runner.manipulation.Filter):void");
    }

    public void sort(Sorter sorter) {
        synchronized (this.childrenLock) {
            for (Object apply : getFilteredChildren()) {
                sorter.apply(apply);
            }
            ArrayList arrayList = new ArrayList(getFilteredChildren());
            Collections.sort(arrayList, comparator(sorter));
            this.filteredChildren = Collections.unmodifiableCollection(arrayList);
        }
    }

    private void validate() throws InitializationError {
        ArrayList arrayList = new ArrayList();
        collectInitializationErrors(arrayList);
        if (!arrayList.isEmpty()) {
            throw new InitializationError((List<Throwable>) arrayList);
        }
    }

    private Collection<T> getFilteredChildren() {
        if (this.filteredChildren == null) {
            synchronized (this.childrenLock) {
                if (this.filteredChildren == null) {
                    this.filteredChildren = Collections.unmodifiableCollection(getChildren());
                }
            }
        }
        return this.filteredChildren;
    }

    private boolean shouldRun(Filter filter, T t) {
        return filter.shouldRun(describeChild(t));
    }

    private Comparator<? super T> comparator(final Sorter sorter) {
        return new Comparator<T>() {
            public int compare(T t, T t2) {
                return sorter.compare(ParentRunner.this.describeChild(t), ParentRunner.this.describeChild(t2));
            }
        };
    }

    public void setScheduler(RunnerScheduler runnerScheduler) {
        this.scheduler = runnerScheduler;
    }
}
