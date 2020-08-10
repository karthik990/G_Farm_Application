package org.junit.internal.runners.statements;

import java.util.List;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class RunBefores extends Statement {
    private final List<FrameworkMethod> befores;
    private final Statement next;
    private final Object target;

    public RunBefores(Statement statement, List<FrameworkMethod> list, Object obj) {
        this.next = statement;
        this.befores = list;
        this.target = obj;
    }

    public void evaluate() throws Throwable {
        for (FrameworkMethod invokeExplosively : this.befores) {
            invokeExplosively.invokeExplosively(this.target, new Object[0]);
        }
        this.next.evaluate();
    }
}
