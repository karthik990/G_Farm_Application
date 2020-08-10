package org.junit.internal;

import java.io.PrintStream;
import java.text.NumberFormat;
import java.util.List;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

public class TextListener extends RunListener {
    private final PrintStream writer;

    public TextListener(JUnitSystem jUnitSystem) {
        this(jUnitSystem.out());
    }

    public TextListener(PrintStream printStream) {
        this.writer = printStream;
    }

    public void testRunFinished(Result result) {
        printHeader(result.getRunTime());
        printFailures(result);
        printFooter(result);
    }

    public void testStarted(Description description) {
        this.writer.append('.');
    }

    public void testFailure(Failure failure) {
        this.writer.append('E');
    }

    public void testIgnored(Description description) {
        this.writer.append('I');
    }

    private PrintStream getWriter() {
        return this.writer;
    }

    /* access modifiers changed from: protected */
    public void printHeader(long j) {
        getWriter().println();
        PrintStream writer2 = getWriter();
        StringBuilder sb = new StringBuilder();
        sb.append("Time: ");
        sb.append(elapsedTimeAsString(j));
        writer2.println(sb.toString());
    }

    /* access modifiers changed from: protected */
    public void printFailures(Result result) {
        List<Failure> failures = result.getFailures();
        if (failures.size() != 0) {
            int i = 1;
            if (failures.size() == 1) {
                PrintStream writer2 = getWriter();
                StringBuilder sb = new StringBuilder();
                sb.append("There was ");
                sb.append(failures.size());
                sb.append(" failure:");
                writer2.println(sb.toString());
            } else {
                PrintStream writer3 = getWriter();
                StringBuilder sb2 = new StringBuilder();
                sb2.append("There were ");
                sb2.append(failures.size());
                sb2.append(" failures:");
                writer3.println(sb2.toString());
            }
            for (Failure failure : failures) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("");
                int i2 = i + 1;
                sb3.append(i);
                printFailure(failure, sb3.toString());
                i = i2;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void printFailure(Failure failure, String str) {
        PrintStream writer2 = getWriter();
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(") ");
        sb.append(failure.getTestHeader());
        writer2.println(sb.toString());
        getWriter().print(failure.getTrace());
    }

    /* access modifiers changed from: protected */
    public void printFooter(Result result) {
        if (result.wasSuccessful()) {
            getWriter().println();
            getWriter().print("OK");
            PrintStream writer2 = getWriter();
            StringBuilder sb = new StringBuilder();
            sb.append(" (");
            sb.append(result.getRunCount());
            sb.append(" test");
            sb.append(result.getRunCount() == 1 ? "" : "s");
            sb.append(")");
            writer2.println(sb.toString());
        } else {
            getWriter().println();
            getWriter().println("FAILURES!!!");
            PrintStream writer3 = getWriter();
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Tests run: ");
            sb2.append(result.getRunCount());
            sb2.append(",  Failures: ");
            sb2.append(result.getFailureCount());
            writer3.println(sb2.toString());
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
}
