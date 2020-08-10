package org.hamcrest.core;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class IsNull<T> extends BaseMatcher<T> {
    public boolean matches(Object obj) {
        return obj == null;
    }

    public void describeTo(Description description) {
        description.appendText("null");
    }

    @Factory
    public static Matcher<Object> nullValue() {
        return new IsNull();
    }

    @Factory
    public static Matcher<Object> notNullValue() {
        return IsNot.not(nullValue());
    }

    @Factory
    public static <T> Matcher<T> nullValue(Class<T> cls) {
        return new IsNull();
    }

    @Factory
    public static <T> Matcher<T> notNullValue(Class<T> cls) {
        return IsNot.not(nullValue(cls));
    }
}
