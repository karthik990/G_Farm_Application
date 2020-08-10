package com.google.firebase.database.logging;

import com.google.firebase.database.logging.Logger.Level;
import com.mobiroller.constants.Constants;
import java.io.PrintWriter;
import java.io.StringWriter;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class LogWrapper {
    private final String component;
    private final Logger logger;
    private final String prefix;

    private static String exceptionStacktrace(Throwable th) {
        StringWriter stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    public LogWrapper(Logger logger2, String str) {
        this(logger2, str, null);
    }

    public LogWrapper(Logger logger2, String str, String str2) {
        this.logger = logger2;
        this.component = str;
        this.prefix = str2;
    }

    public void error(String str, Throwable th) {
        StringBuilder sb = new StringBuilder();
        sb.append(toLog(str, new Object[0]));
        sb.append(Constants.NEW_LINE);
        sb.append(exceptionStacktrace(th));
        this.logger.onLogMessage(Level.ERROR, this.component, sb.toString(), now());
    }

    public void warn(String str) {
        warn(str, null);
    }

    public void warn(String str, Throwable th) {
        String log = toLog(str, new Object[0]);
        if (th != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(log);
            sb.append(Constants.NEW_LINE);
            sb.append(exceptionStacktrace(th));
            log = sb.toString();
        }
        this.logger.onLogMessage(Level.WARN, this.component, log, now());
    }

    public void info(String str) {
        this.logger.onLogMessage(Level.INFO, this.component, toLog(str, new Object[0]), now());
    }

    public void debug(String str, Object... objArr) {
        debug(str, null, objArr);
    }

    public boolean logsDebug() {
        return this.logger.getLogLevel().ordinal() <= Level.DEBUG.ordinal();
    }

    public void debug(String str, Throwable th, Object... objArr) {
        if (logsDebug()) {
            String log = toLog(str, objArr);
            if (th != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(log);
                sb.append(Constants.NEW_LINE);
                sb.append(exceptionStacktrace(th));
                log = sb.toString();
            }
            this.logger.onLogMessage(Level.DEBUG, this.component, log, now());
        }
    }

    private long now() {
        return System.currentTimeMillis();
    }

    private String toLog(String str, Object... objArr) {
        if (objArr.length > 0) {
            str = String.format(str, objArr);
        }
        if (this.prefix == null) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.prefix);
        sb.append(" - ");
        sb.append(str);
        return sb.toString();
    }
}
