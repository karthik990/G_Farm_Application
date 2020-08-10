package org.hamcrest;

import org.hamcrest.Description.NullDescription;
import org.hamcrest.internal.ReflectiveTypeFinder;

public abstract class TypeSafeDiagnosingMatcher<T> extends BaseMatcher<T> {
    private static final ReflectiveTypeFinder TYPE_FINDER = new ReflectiveTypeFinder("matchesSafely", 2, 0);
    private final Class<?> expectedType;

    /* access modifiers changed from: protected */
    public abstract boolean matchesSafely(T t, Description description);

    protected TypeSafeDiagnosingMatcher(Class<?> cls) {
        this.expectedType = cls;
    }

    protected TypeSafeDiagnosingMatcher(ReflectiveTypeFinder reflectiveTypeFinder) {
        this.expectedType = reflectiveTypeFinder.findExpectedType(getClass());
    }

    protected TypeSafeDiagnosingMatcher() {
        this(TYPE_FINDER);
    }

    public final boolean matches(Object obj) {
        return obj != null && this.expectedType.isInstance(obj) && matchesSafely(obj, new NullDescription());
    }

    public final void describeMismatch(Object obj, Description description) {
        if (obj == null || !this.expectedType.isInstance(obj)) {
            super.describeMismatch(obj, description);
        } else {
            matchesSafely(obj, description);
        }
    }
}
