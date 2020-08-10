package org.hamcrest.core;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class StringContains extends SubstringMatcher {
    /* access modifiers changed from: protected */
    public String relationship() {
        return "containing";
    }

    public StringContains(String str) {
        super(str);
    }

    /* access modifiers changed from: protected */
    public boolean evalSubstringOf(String str) {
        return str.indexOf(this.substring) >= 0;
    }

    @Factory
    public static Matcher<String> containsString(String str) {
        return new StringContains(str);
    }
}
