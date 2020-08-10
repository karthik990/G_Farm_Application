package org.junit.runner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.internal.Classes;
import org.junit.runner.FilterFactory.FilterNotCreatedException;
import org.junit.runners.model.InitializationError;

class JUnitCommandLineParseResult {
    private final List<Class<?>> classes = new ArrayList();
    private final List<String> filterSpecs = new ArrayList();
    private final List<Throwable> parserErrors = new ArrayList();

    public static class CommandLineParserError extends Exception {
        private static final long serialVersionUID = 1;

        public CommandLineParserError(String str) {
            super(str);
        }
    }

    JUnitCommandLineParseResult() {
    }

    public List<String> getFilterSpecs() {
        return Collections.unmodifiableList(this.filterSpecs);
    }

    public List<Class<?>> getClasses() {
        return Collections.unmodifiableList(this.classes);
    }

    public static JUnitCommandLineParseResult parse(String[] strArr) {
        JUnitCommandLineParseResult jUnitCommandLineParseResult = new JUnitCommandLineParseResult();
        jUnitCommandLineParseResult.parseArgs(strArr);
        return jUnitCommandLineParseResult;
    }

    private void parseArgs(String[] strArr) {
        parseParameters(parseOptions(strArr));
    }

    /* access modifiers changed from: 0000 */
    public String[] parseOptions(String... strArr) {
        String str;
        int i = 0;
        while (true) {
            if (i == strArr.length) {
                break;
            }
            String str2 = strArr[i];
            String str3 = "--";
            if (str2.equals(str3)) {
                return copyArray(strArr, i + 1, strArr.length);
            }
            if (!str2.startsWith(str3)) {
                return copyArray(strArr, i, strArr.length);
            }
            String str4 = "--filter";
            if (str2.startsWith("--filter=") || str2.equals(str4)) {
                if (str2.equals(str4)) {
                    i++;
                    if (i >= strArr.length) {
                        List<Throwable> list = this.parserErrors;
                        StringBuilder sb = new StringBuilder();
                        sb.append(str2);
                        sb.append(" value not specified");
                        list.add(new CommandLineParserError(sb.toString()));
                        break;
                    }
                    str = strArr[i];
                } else {
                    str = str2.substring(str2.indexOf(61) + 1);
                }
                this.filterSpecs.add(str);
            } else {
                List<Throwable> list2 = this.parserErrors;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("JUnit knows nothing about the ");
                sb2.append(str2);
                sb2.append(" option");
                list2.add(new CommandLineParserError(sb2.toString()));
            }
            i++;
        }
        return new String[0];
    }

    private String[] copyArray(String[] strArr, int i, int i2) {
        ArrayList arrayList = new ArrayList();
        while (i != i2) {
            arrayList.add(strArr[i]);
            i++;
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    /* access modifiers changed from: 0000 */
    public void parseParameters(String[] strArr) {
        for (String str : strArr) {
            try {
                this.classes.add(Classes.getClass(str));
            } catch (ClassNotFoundException e) {
                List<Throwable> list = this.parserErrors;
                StringBuilder sb = new StringBuilder();
                sb.append("Could not find class [");
                sb.append(str);
                sb.append("]");
                list.add(new IllegalArgumentException(sb.toString(), e));
            }
        }
    }

    private Request errorReport(Throwable th) {
        return Request.errorReport(JUnitCommandLineParseResult.class, th);
    }

    public Request createRequest(Computer computer) {
        if (!this.parserErrors.isEmpty()) {
            return errorReport(new InitializationError(this.parserErrors));
        }
        List<Class<?>> list = this.classes;
        return applyFilterSpecs(Request.classes(computer, (Class[]) list.toArray(new Class[list.size()])));
    }

    private Request applyFilterSpecs(Request request) {
        try {
            for (String createFilterFromFilterSpec : this.filterSpecs) {
                request = request.filterWith(FilterFactories.createFilterFromFilterSpec(request, createFilterFromFilterSpec));
            }
            return request;
        } catch (FilterNotCreatedException e) {
            return errorReport(e);
        }
    }
}
