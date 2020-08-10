package org.hamcrest;

public abstract class DiagnosingMatcher<T> extends BaseMatcher<T> {
    /* access modifiers changed from: protected */
    public abstract boolean matches(Object obj, Description description);

    public final boolean matches(Object obj) {
        return matches(obj, Description.NONE);
    }

    public final void describeMismatch(Object obj, Description description) {
        matches(obj, description);
    }
}
