package junit.framework;

import java.io.PrintWriter;
import java.io.StringWriter;

public class TestFailure {
    protected Test fFailedTest;
    protected Throwable fThrownException;

    public TestFailure(Test test, Throwable th) {
        this.fFailedTest = test;
        this.fThrownException = th;
    }

    public Test failedTest() {
        return this.fFailedTest;
    }

    public Throwable thrownException() {
        return this.fThrownException;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.fFailedTest);
        sb.append(": ");
        sb.append(this.fThrownException.getMessage());
        return sb.toString();
    }

    public String trace() {
        StringWriter stringWriter = new StringWriter();
        thrownException().printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    public String exceptionMessage() {
        return thrownException().getMessage();
    }

    public boolean isFailure() {
        return thrownException() instanceof AssertionFailedError;
    }
}
