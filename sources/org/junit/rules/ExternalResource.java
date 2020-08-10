package org.junit.rules;

import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public abstract class ExternalResource implements TestRule {
    /* access modifiers changed from: protected */
    public void after() {
    }

    /* access modifiers changed from: protected */
    public void before() throws Throwable {
    }

    public Statement apply(Statement statement, Description description) {
        return statement(statement);
    }

    private Statement statement(final Statement statement) {
        return new Statement() {
            public void evaluate() throws Throwable {
                ExternalResource.this.before();
                try {
                    statement.evaluate();
                } finally {
                    ExternalResource.this.after();
                }
            }
        };
    }
}
