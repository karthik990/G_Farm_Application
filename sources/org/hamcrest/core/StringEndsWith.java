package org.hamcrest.core;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class StringEndsWith extends SubstringMatcher {
    /* access modifiers changed from: protected */
    public String relationship() {
        return "ending with";
    }

    public StringEndsWith(String str) {
        super(str);
    }

    /* access modifiers changed from: protected */
    public boolean evalSubstringOf(String str) {
        return str.endsWith(this.substring);
    }

    @Factory
    public static Matcher<String> endsWith(String str) {
        return new StringEndsWith(str);
    }
}
