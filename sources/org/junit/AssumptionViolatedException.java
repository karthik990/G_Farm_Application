package org.junit;

import org.hamcrest.Matcher;

public class AssumptionViolatedException extends org.junit.internal.AssumptionViolatedException {
    private static final long serialVersionUID = 1;

    public <T> AssumptionViolatedException(T t, Matcher<T> matcher) {
        super((Object) t, matcher);
    }

    public <T> AssumptionViolatedException(String str, T t, Matcher<T> matcher) {
        super(str, t, matcher);
    }

    public AssumptionViolatedException(String str) {
        super(str);
    }

    public AssumptionViolatedException(String str, Throwable th) {
        super(str, th);
    }
}
