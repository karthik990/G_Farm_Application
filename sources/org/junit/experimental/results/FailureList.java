package org.junit.experimental.results;

import java.util.List;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

class FailureList {
    private final List<Failure> failures;

    public FailureList(List<Failure> list) {
        this.failures = list;
    }

    public Result result() {
        Result result = new Result();
        RunListener createListener = result.createListener();
        for (Failure testFailure : this.failures) {
            try {
                createListener.testFailure(testFailure);
            } catch (Exception unused) {
                throw new RuntimeException("I can't believe this happened");
            }
        }
        return result;
    }
}
