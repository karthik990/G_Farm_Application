package org.hamcrest;

public abstract class BaseMatcher<T> implements Matcher<T> {
    @Deprecated
    public final void _dont_implement_Matcher___instead_extend_BaseMatcher_() {
    }

    public void describeMismatch(Object obj, Description description) {
        description.appendText("was ").appendValue(obj);
    }

    public String toString() {
        return StringDescription.toString(this);
    }
}
