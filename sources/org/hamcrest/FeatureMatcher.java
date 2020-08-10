package org.hamcrest;

import org.hamcrest.internal.ReflectiveTypeFinder;

public abstract class FeatureMatcher<T, U> extends TypeSafeDiagnosingMatcher<T> {
    private static final ReflectiveTypeFinder TYPE_FINDER = new ReflectiveTypeFinder("featureValueOf", 1, 0);
    private final String featureDescription;
    private final String featureName;
    private final Matcher<? super U> subMatcher;

    /* access modifiers changed from: protected */
    public abstract U featureValueOf(T t);

    public FeatureMatcher(Matcher<? super U> matcher, String str, String str2) {
        super(TYPE_FINDER);
        this.subMatcher = matcher;
        this.featureDescription = str;
        this.featureName = str2;
    }

    /* access modifiers changed from: protected */
    public boolean matchesSafely(T t, Description description) {
        Object featureValueOf = featureValueOf(t);
        if (this.subMatcher.matches(featureValueOf)) {
            return true;
        }
        description.appendText(this.featureName).appendText(" ");
        this.subMatcher.describeMismatch(featureValueOf, description);
        return false;
    }

    public final void describeTo(Description description) {
        description.appendText(this.featureDescription).appendText(" ").appendDescriptionOf(this.subMatcher);
    }
}
