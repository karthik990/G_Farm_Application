package junit.extensions;

import junit.framework.Test;
import junit.framework.TestResult;

public class RepeatedTest extends TestDecorator {
    private int fTimesRepeat;

    public RepeatedTest(Test test, int i) {
        super(test);
        if (i >= 0) {
            this.fTimesRepeat = i;
            return;
        }
        throw new IllegalArgumentException("Repetition count must be >= 0");
    }

    public int countTestCases() {
        return super.countTestCases() * this.fTimesRepeat;
    }

    public void run(TestResult testResult) {
        for (int i = 0; i < this.fTimesRepeat && !testResult.shouldStop(); i++) {
            super.run(testResult);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("(repeated)");
        return sb.toString();
    }
}
