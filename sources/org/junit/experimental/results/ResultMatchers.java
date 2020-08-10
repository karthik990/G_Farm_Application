package org.junit.experimental.results;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class ResultMatchers {
    public static Matcher<PrintableResult> isSuccessful() {
        return failureCountIs(0);
    }

    public static Matcher<PrintableResult> failureCountIs(final int i) {
        return new TypeSafeMatcher<PrintableResult>() {
            public void describeTo(Description description) {
                StringBuilder sb = new StringBuilder();
                sb.append("has ");
                sb.append(i);
                sb.append(" failures");
                description.appendText(sb.toString());
            }

            public boolean matchesSafely(PrintableResult printableResult) {
                return printableResult.failureCount() == i;
            }
        };
    }

    public static Matcher<Object> hasSingleFailureContaining(final String str) {
        return new BaseMatcher<Object>() {
            public boolean matches(Object obj) {
                return obj.toString().contains(str) && ResultMatchers.failureCountIs(1).matches(obj);
            }

            public void describeTo(Description description) {
                StringBuilder sb = new StringBuilder();
                sb.append("has single failure containing ");
                sb.append(str);
                description.appendText(sb.toString());
            }
        };
    }

    public static Matcher<PrintableResult> hasFailureContaining(final String str) {
        return new BaseMatcher<PrintableResult>() {
            public boolean matches(Object obj) {
                return obj.toString().contains(str);
            }

            public void describeTo(Description description) {
                StringBuilder sb = new StringBuilder();
                sb.append("has failure containing ");
                sb.append(str);
                description.appendText(sb.toString());
            }
        };
    }
}
