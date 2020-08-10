package org.hamcrest.core;

import java.util.regex.Pattern;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class DescribedAs<T> extends BaseMatcher<T> {
    private static final Pattern ARG_PATTERN = Pattern.compile("%([0-9]+)");
    private final String descriptionTemplate;
    private final Matcher<T> matcher;
    private final Object[] values;

    public DescribedAs(String str, Matcher<T> matcher2, Object[] objArr) {
        this.descriptionTemplate = str;
        this.matcher = matcher2;
        this.values = (Object[]) objArr.clone();
    }

    public boolean matches(Object obj) {
        return this.matcher.matches(obj);
    }

    public void describeTo(Description description) {
        java.util.regex.Matcher matcher2 = ARG_PATTERN.matcher(this.descriptionTemplate);
        int i = 0;
        while (matcher2.find()) {
            description.appendText(this.descriptionTemplate.substring(i, matcher2.start()));
            description.appendValue(this.values[Integer.parseInt(matcher2.group(1))]);
            i = matcher2.end();
        }
        if (i < this.descriptionTemplate.length()) {
            description.appendText(this.descriptionTemplate.substring(i));
        }
    }

    public void describeMismatch(Object obj, Description description) {
        this.matcher.describeMismatch(obj, description);
    }

    @Factory
    public static <T> Matcher<T> describedAs(String str, Matcher<T> matcher2, Object... objArr) {
        return new DescribedAs(str, matcher2, objArr);
    }
}
