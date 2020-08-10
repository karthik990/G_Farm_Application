package org.junit.runners.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.internal.Throwables;

public class MultipleFailureException extends Exception {
    private static final long serialVersionUID = 1;
    private final List<Throwable> fErrors;

    public MultipleFailureException(List<Throwable> list) {
        this.fErrors = new ArrayList(list);
    }

    public List<Throwable> getFailures() {
        return Collections.unmodifiableList(this.fErrors);
    }

    public String getMessage() {
        StringBuilder sb = new StringBuilder(String.format("There were %d errors:", new Object[]{Integer.valueOf(this.fErrors.size())}));
        for (Throwable th : this.fErrors) {
            sb.append(String.format("\n  %s(%s)", new Object[]{th.getClass().getName(), th.getMessage()}));
        }
        return sb.toString();
    }

    public static void assertEmpty(List<Throwable> list) throws Exception {
        if (!list.isEmpty()) {
            if (list.size() == 1) {
                throw Throwables.rethrowAsException((Throwable) list.get(0));
            }
            throw new org.junit.internal.runners.model.MultipleFailureException(list);
        }
    }
}
