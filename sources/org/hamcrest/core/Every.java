package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class Every<T> extends TypeSafeDiagnosingMatcher<Iterable<T>> {
    private final Matcher<? super T> matcher;

    public Every(Matcher<? super T> matcher2) {
        this.matcher = matcher2;
    }

    public boolean matchesSafely(Iterable<T> iterable, Description description) {
        for (Object next : iterable) {
            if (!this.matcher.matches(next)) {
                description.appendText("an item ");
                this.matcher.describeMismatch(next, description);
                return false;
            }
        }
        return true;
    }

    public void describeTo(Description description) {
        description.appendText("every item is ").appendDescriptionOf(this.matcher);
    }

    @Factory
    public static <U> Matcher<Iterable<U>> everyItem(Matcher<U> matcher2) {
        return new Every(matcher2);
    }
}
