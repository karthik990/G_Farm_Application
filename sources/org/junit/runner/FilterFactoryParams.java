package org.junit.runner;

public final class FilterFactoryParams {
    private final String args;
    private final Description topLevelDescription;

    public FilterFactoryParams(Description description, String str) {
        if (str == null || description == null) {
            throw new NullPointerException();
        }
        this.topLevelDescription = description;
        this.args = str;
    }

    public String getArgs() {
        return this.args;
    }

    public Description getTopLevelDescription() {
        return this.topLevelDescription;
    }
}
