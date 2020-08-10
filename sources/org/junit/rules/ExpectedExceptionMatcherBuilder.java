package org.junit.rules;

import java.util.ArrayList;
import java.util.List;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.matchers.JUnitMatchers;

class ExpectedExceptionMatcherBuilder {
    private final List<Matcher<?>> matchers = new ArrayList();

    private Matcher<Throwable> cast(Matcher<?> matcher) {
        return matcher;
    }

    ExpectedExceptionMatcherBuilder() {
    }

    /* access modifiers changed from: 0000 */
    public void add(Matcher<?> matcher) {
        this.matchers.add(matcher);
    }

    /* access modifiers changed from: 0000 */
    public boolean expectsThrowable() {
        return !this.matchers.isEmpty();
    }

    /* access modifiers changed from: 0000 */
    public Matcher<Throwable> build() {
        return JUnitMatchers.isThrowable(allOfTheMatchers());
    }

    private Matcher<Throwable> allOfTheMatchers() {
        if (this.matchers.size() == 1) {
            return cast((Matcher) this.matchers.get(0));
        }
        return CoreMatchers.allOf((Iterable<Matcher<? super T>>) castedMatchers());
    }

    private List<Matcher<? super Throwable>> castedMatchers() {
        return new ArrayList(this.matchers);
    }
}
