package org.junit.matchers;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.hamcrest.core.CombinableMatcher.CombinableBothMatcher;
import org.hamcrest.core.CombinableMatcher.CombinableEitherMatcher;
import org.junit.internal.matchers.StacktracePrintingMatcher;

public class JUnitMatchers {
    @Deprecated
    public static <T> Matcher<Iterable<? super T>> hasItem(T t) {
        return CoreMatchers.hasItem(t);
    }

    @Deprecated
    public static <T> Matcher<Iterable<? super T>> hasItem(Matcher<? super T> matcher) {
        return CoreMatchers.hasItem(matcher);
    }

    @Deprecated
    public static <T> Matcher<Iterable<T>> hasItems(T... tArr) {
        return CoreMatchers.hasItems(tArr);
    }

    @Deprecated
    public static <T> Matcher<Iterable<T>> hasItems(Matcher<? super T>... matcherArr) {
        return CoreMatchers.hasItems(matcherArr);
    }

    @Deprecated
    public static <T> Matcher<Iterable<T>> everyItem(Matcher<T> matcher) {
        return CoreMatchers.everyItem(matcher);
    }

    @Deprecated
    public static Matcher<String> containsString(String str) {
        return CoreMatchers.containsString(str);
    }

    @Deprecated
    public static <T> CombinableBothMatcher<T> both(Matcher<? super T> matcher) {
        return CoreMatchers.both(matcher);
    }

    @Deprecated
    public static <T> CombinableEitherMatcher<T> either(Matcher<? super T> matcher) {
        return CoreMatchers.either(matcher);
    }

    public static <T extends Throwable> Matcher<T> isThrowable(Matcher<T> matcher) {
        return StacktracePrintingMatcher.isThrowable(matcher);
    }

    public static <T extends Exception> Matcher<T> isException(Matcher<T> matcher) {
        return StacktracePrintingMatcher.isException(matcher);
    }
}
