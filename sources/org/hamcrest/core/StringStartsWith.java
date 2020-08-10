package org.hamcrest.core;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class StringStartsWith extends SubstringMatcher {
    /* access modifiers changed from: protected */
    public String relationship() {
        return "starting with";
    }

    public StringStartsWith(String str) {
        super(str);
    }

    /* access modifiers changed from: protected */
    public boolean evalSubstringOf(String str) {
        return str.startsWith(this.substring);
    }

    @Factory
    public static Matcher<String> startsWith(String str) {
        return new StringStartsWith(str);
    }
}
