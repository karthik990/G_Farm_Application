package org.junit.internal.matchers;

import java.lang.Throwable;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class ThrowableMessageMatcher<T extends Throwable> extends TypeSafeMatcher<T> {
    private final Matcher<String> matcher;

    public ThrowableMessageMatcher(Matcher<String> matcher2) {
        this.matcher = matcher2;
    }

    public void describeTo(Description description) {
        description.appendText("exception with message ");
        description.appendDescriptionOf(this.matcher);
    }

    /* access modifiers changed from: protected */
    public boolean matchesSafely(T t) {
        return this.matcher.matches(t.getMessage());
    }

    /* access modifiers changed from: protected */
    public void describeMismatchSafely(T t, Description description) {
        description.appendText("message ");
        this.matcher.describeMismatch(t.getMessage(), description);
    }

    @Factory
    public static <T extends Throwable> Matcher<T> hasMessage(Matcher<String> matcher2) {
        return new ThrowableMessageMatcher(matcher2);
    }
}
