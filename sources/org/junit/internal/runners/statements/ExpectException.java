package org.junit.internal.runners.statements;

import org.junit.internal.AssumptionViolatedException;
import org.junit.runners.model.Statement;

public class ExpectException extends Statement {
    private final Class<? extends Throwable> expected;
    private final Statement next;

    public ExpectException(Statement statement, Class<? extends Throwable> cls) {
        this.next = statement;
        this.expected = cls;
    }

    public void evaluate() throws Exception {
        boolean z;
        try {
            this.next.evaluate();
            z = true;
        } catch (AssumptionViolatedException e) {
            throw e;
        } catch (Throwable th) {
            if (this.expected.isAssignableFrom(th.getClass())) {
                z = false;
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Unexpected exception, expected<");
                sb.append(this.expected.getName());
                sb.append("> but was<");
                sb.append(th.getClass().getName());
                sb.append(">");
                throw new Exception(sb.toString(), th);
            }
        }
        if (z) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Expected exception: ");
            sb2.append(this.expected.getName());
            throw new AssertionError(sb2.toString());
        }
    }
}
