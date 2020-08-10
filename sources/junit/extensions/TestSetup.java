package junit.extensions;

import junit.framework.Protectable;
import junit.framework.Test;
import junit.framework.TestResult;

public class TestSetup extends TestDecorator {
    /* access modifiers changed from: protected */
    public void setUp() throws Exception {
    }

    /* access modifiers changed from: protected */
    public void tearDown() throws Exception {
    }

    public TestSetup(Test test) {
        super(test);
    }

    public void run(final TestResult testResult) {
        testResult.runProtected(this, new Protectable() {
            public void protect() throws Exception {
                TestSetup.this.setUp();
                TestSetup.this.basicRun(testResult);
                TestSetup.this.tearDown();
            }
        });
    }
}
