package org.junit.internal.runners.statements;

import java.util.ArrayList;
import java.util.List;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;

public class RunAfters extends Statement {
    private final List<FrameworkMethod> afters;
    private final Statement next;
    private final Object target;

    public RunAfters(Statement statement, List<FrameworkMethod> list, Object obj) {
        this.next = statement;
        this.afters = list;
        this.target = obj;
    }

    public void evaluate() throws Throwable {
        ArrayList arrayList = new ArrayList();
        try {
            this.next.evaluate();
            for (FrameworkMethod invokeExplosively : this.afters) {
                try {
                    invokeExplosively.invokeExplosively(this.target, new Object[0]);
                } catch (Throwable th) {
                    arrayList.add(th);
                }
            }
        } catch (Throwable th2) {
            arrayList.add(th2);
        }
        MultipleFailureException.assertEmpty(arrayList);
    }
}
