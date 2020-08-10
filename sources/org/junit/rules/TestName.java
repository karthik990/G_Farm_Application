package org.junit.rules;

import org.junit.runner.Description;

public class TestName extends TestWatcher {
    private String name;

    /* access modifiers changed from: protected */
    public void starting(Description description) {
        this.name = description.getMethodName();
    }

    public String getMethodName() {
        return this.name;
    }
}
