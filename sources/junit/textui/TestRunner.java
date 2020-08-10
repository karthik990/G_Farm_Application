package junit.textui;

import java.io.PrintStream;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;
import junit.framework.TestSuite;
import junit.runner.BaseTestRunner;
import junit.runner.Version;

public class TestRunner extends BaseTestRunner {
    public static final int EXCEPTION_EXIT = 2;
    public static final int FAILURE_EXIT = 1;
    public static final int SUCCESS_EXIT = 0;
    private ResultPrinter fPrinter;

    public void testEnded(String str) {
    }

    public void testFailed(int i, Test test, Throwable th) {
    }

    public void testStarted(String str) {
    }

    public TestRunner() {
        this(System.out);
    }

    public TestRunner(PrintStream printStream) {
        this(new ResultPrinter(printStream));
    }

    public TestRunner(ResultPrinter resultPrinter) {
        this.fPrinter = resultPrinter;
    }

    public static void run(Class<? extends TestCase> cls) {
        run((Test) new TestSuite(cls));
    }

    public static TestResult run(Test test) {
        return new TestRunner().doRun(test);
    }

    public static void runAndWait(Test test) {
        new TestRunner().doRun(test, true);
    }

    /* access modifiers changed from: protected */
    public TestResult createTestResult() {
        return new TestResult();
    }

    public TestResult doRun(Test test) {
        return doRun(test, false);
    }

    public TestResult doRun(Test test, boolean z) {
        TestResult createTestResult = createTestResult();
        createTestResult.addListener(this.fPrinter);
        long currentTimeMillis = System.currentTimeMillis();
        test.run(createTestResult);
        this.fPrinter.print(createTestResult, System.currentTimeMillis() - currentTimeMillis);
        pause(z);
        return createTestResult;
    }

    /* access modifiers changed from: protected */
    public void pause(boolean z) {
        if (z) {
            this.fPrinter.printWaitPrompt();
            try {
                System.in.read();
            } catch (Exception unused) {
            }
        }
    }

    public static void main(String[] strArr) {
        try {
            if (!new TestRunner().start(strArr).wasSuccessful()) {
                System.exit(1);
            }
            System.exit(0);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(2);
        }
    }

    public TestResult start(String[] strArr) throws Exception {
        String str = "";
        String str2 = str;
        String str3 = str2;
        int i = 0;
        boolean z = false;
        while (i < strArr.length) {
            if (strArr[i].equals("-wait")) {
                z = true;
            } else if (strArr[i].equals("-c")) {
                i++;
                str2 = extractClassName(strArr[i]);
            } else if (strArr[i].equals("-m")) {
                i++;
                String str4 = strArr[i];
                int lastIndexOf = str4.lastIndexOf(46);
                String substring = str4.substring(0, lastIndexOf);
                str3 = str4.substring(lastIndexOf + 1);
                str2 = substring;
            } else if (strArr[i].equals("-v")) {
                PrintStream printStream = System.err;
                StringBuilder sb = new StringBuilder();
                sb.append("JUnit ");
                sb.append(Version.m4011id());
                sb.append(" by Kent Beck and Erich Gamma");
                printStream.println(sb.toString());
            } else {
                str2 = strArr[i];
            }
            i++;
        }
        if (!str2.equals(str)) {
            try {
                if (!str3.equals(str)) {
                    return runSingleMethod(str2, str3, z);
                }
                return doRun(getTest(str2), z);
            } catch (Exception e) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Could not create and run test suite: ");
                sb2.append(e);
                throw new Exception(sb2.toString());
            }
        } else {
            throw new Exception("Usage: TestRunner [-wait] testCaseName, where name is the name of the TestCase class");
        }
    }

    /* access modifiers changed from: protected */
    public TestResult runSingleMethod(String str, String str2, boolean z) throws Exception {
        return doRun(TestSuite.createTest(loadSuiteClass(str).asSubclass(TestCase.class), str2), z);
    }

    /* access modifiers changed from: protected */
    public void runFailed(String str) {
        System.err.println(str);
        System.exit(1);
    }

    public void setPrinter(ResultPrinter resultPrinter) {
        this.fPrinter = resultPrinter;
    }
}
