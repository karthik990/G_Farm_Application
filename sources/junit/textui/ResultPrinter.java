package junit.textui;

import androidx.exifinterface.media.ExifInterface;
import java.io.PrintStream;
import java.text.NumberFormat;
import java.util.Enumeration;
import junit.framework.AssertionFailedError;
import junit.framework.Test;
import junit.framework.TestFailure;
import junit.framework.TestListener;
import junit.framework.TestResult;
import junit.runner.BaseTestRunner;

public class ResultPrinter implements TestListener {
    int fColumn = 0;
    PrintStream fWriter;

    public void endTest(Test test) {
    }

    public ResultPrinter(PrintStream printStream) {
        this.fWriter = printStream;
    }

    /* access modifiers changed from: 0000 */
    public synchronized void print(TestResult testResult, long j) {
        printHeader(j);
        printErrors(testResult);
        printFailures(testResult);
        printFooter(testResult);
    }

    /* access modifiers changed from: 0000 */
    public void printWaitPrompt() {
        getWriter().println();
        getWriter().println("<RETURN> to continue");
    }

    /* access modifiers changed from: protected */
    public void printHeader(long j) {
        getWriter().println();
        PrintStream writer = getWriter();
        StringBuilder sb = new StringBuilder();
        sb.append("Time: ");
        sb.append(elapsedTimeAsString(j));
        writer.println(sb.toString());
    }

    /* access modifiers changed from: protected */
    public void printErrors(TestResult testResult) {
        printDefects(testResult.errors(), testResult.errorCount(), "error");
    }

    /* access modifiers changed from: protected */
    public void printFailures(TestResult testResult) {
        printDefects(testResult.failures(), testResult.failureCount(), "failure");
    }

    /* access modifiers changed from: protected */
    public void printDefects(Enumeration<TestFailure> enumeration, int i, String str) {
        if (i != 0) {
            String str2 = " ";
            if (i == 1) {
                PrintStream writer = getWriter();
                StringBuilder sb = new StringBuilder();
                sb.append("There was ");
                sb.append(i);
                sb.append(str2);
                sb.append(str);
                sb.append(":");
                writer.println(sb.toString());
            } else {
                PrintStream writer2 = getWriter();
                StringBuilder sb2 = new StringBuilder();
                sb2.append("There were ");
                sb2.append(i);
                sb2.append(str2);
                sb2.append(str);
                sb2.append("s:");
                writer2.println(sb2.toString());
            }
            int i2 = 1;
            while (enumeration.hasMoreElements()) {
                printDefect((TestFailure) enumeration.nextElement(), i2);
                i2++;
            }
        }
    }

    public void printDefect(TestFailure testFailure, int i) {
        printDefectHeader(testFailure, i);
        printDefectTrace(testFailure);
    }

    /* access modifiers changed from: protected */
    public void printDefectHeader(TestFailure testFailure, int i) {
        PrintStream writer = getWriter();
        StringBuilder sb = new StringBuilder();
        sb.append(i);
        sb.append(") ");
        sb.append(testFailure.failedTest());
        writer.print(sb.toString());
    }

    /* access modifiers changed from: protected */
    public void printDefectTrace(TestFailure testFailure) {
        getWriter().print(BaseTestRunner.getFilteredTrace(testFailure.trace()));
    }

    /* access modifiers changed from: protected */
    public void printFooter(TestResult testResult) {
        if (testResult.wasSuccessful()) {
            getWriter().println();
            getWriter().print("OK");
            PrintStream writer = getWriter();
            StringBuilder sb = new StringBuilder();
            sb.append(" (");
            sb.append(testResult.runCount());
            sb.append(" test");
            sb.append(testResult.runCount() == 1 ? "" : "s");
            sb.append(")");
            writer.println(sb.toString());
        } else {
            getWriter().println();
            getWriter().println("FAILURES!!!");
            PrintStream writer2 = getWriter();
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Tests run: ");
            sb2.append(testResult.runCount());
            sb2.append(",  Failures: ");
            sb2.append(testResult.failureCount());
            sb2.append(",  Errors: ");
            sb2.append(testResult.errorCount());
            writer2.println(sb2.toString());
        }
        getWriter().println();
    }

    /* access modifiers changed from: protected */
    public String elapsedTimeAsString(long j) {
        NumberFormat instance = NumberFormat.getInstance();
        double d = (double) j;
        Double.isNaN(d);
        return instance.format(d / 1000.0d);
    }

    public PrintStream getWriter() {
        return this.fWriter;
    }

    public void addError(Test test, Throwable th) {
        getWriter().print(ExifInterface.LONGITUDE_EAST);
    }

    public void addFailure(Test test, AssertionFailedError assertionFailedError) {
        getWriter().print("F");
    }

    public void startTest(Test test) {
        getWriter().print(".");
        int i = this.fColumn;
        this.fColumn = i + 1;
        if (i >= 40) {
            getWriter().println();
            this.fColumn = 0;
        }
    }
}
