package org.junit.rules;

import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public abstract class Verifier implements TestRule {
    /* access modifiers changed from: protected */
    public void verify() throws Throwable {
    }

    public Statement apply(final Statement statement, Description description) {
        return new Statement() {
            public void evaluate() throws Throwable {
                statement.evaluate();
                Verifier.this.verify();
            }
        };
    }
}
