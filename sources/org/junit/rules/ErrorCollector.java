package org.junit.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.runners.model.MultipleFailureException;

public class ErrorCollector extends Verifier {
    private List<Throwable> errors = new ArrayList();

    /* access modifiers changed from: protected */
    public void verify() throws Throwable {
        MultipleFailureException.assertEmpty(this.errors);
    }

    public void addError(Throwable th) {
        this.errors.add(th);
    }

    public <T> void checkThat(T t, Matcher<T> matcher) {
        checkThat("", t, matcher);
    }

    public <T> void checkThat(final String str, final T t, final Matcher<T> matcher) {
        checkSucceeds(new Callable<Object>() {
            public Object call() throws Exception {
                Assert.assertThat(str, t, matcher);
                return t;
            }
        });
    }

    public <T> T checkSucceeds(Callable<T> callable) {
        try {
            return callable.call();
        } catch (Throwable th) {
            addError(th);
            return null;
        }
    }
}
