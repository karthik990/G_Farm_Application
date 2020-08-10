package p043io.netty.util.internal.logging;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/* renamed from: io.netty.util.internal.logging.Log4JLogger */
class Log4JLogger extends AbstractInternalLogger {
    static final String FQCN = Log4JLogger.class.getName();
    private static final long serialVersionUID = 2851357342488183058L;
    final transient Logger logger;
    final boolean traceCapable = isTraceCapable();

    Log4JLogger(Logger logger2) {
        super(logger2.getName());
        this.logger = logger2;
    }

    private boolean isTraceCapable() {
        try {
            this.logger.isTraceEnabled();
            return true;
        } catch (NoSuchMethodError unused) {
            return false;
        }
    }

    public boolean isTraceEnabled() {
        if (this.traceCapable) {
            return this.logger.isTraceEnabled();
        }
        return this.logger.isDebugEnabled();
    }

    public void trace(String str) {
        this.logger.log(FQCN, this.traceCapable ? Level.TRACE : Level.DEBUG, str, null);
    }

    public void trace(String str, Object obj) {
        if (isTraceEnabled()) {
            FormattingTuple format = MessageFormatter.format(str, obj);
            this.logger.log(FQCN, this.traceCapable ? Level.TRACE : Level.DEBUG, format.getMessage(), format.getThrowable());
        }
    }

    public void trace(String str, Object obj, Object obj2) {
        if (isTraceEnabled()) {
            FormattingTuple format = MessageFormatter.format(str, obj, obj2);
            this.logger.log(FQCN, this.traceCapable ? Level.TRACE : Level.DEBUG, format.getMessage(), format.getThrowable());
        }
    }

    public void trace(String str, Object... objArr) {
        if (isTraceEnabled()) {
            FormattingTuple arrayFormat = MessageFormatter.arrayFormat(str, objArr);
            this.logger.log(FQCN, this.traceCapable ? Level.TRACE : Level.DEBUG, arrayFormat.getMessage(), arrayFormat.getThrowable());
        }
    }

    public void trace(String str, Throwable th) {
        this.logger.log(FQCN, this.traceCapable ? Level.TRACE : Level.DEBUG, str, th);
    }

    public boolean isDebugEnabled() {
        return this.logger.isDebugEnabled();
    }

    public void debug(String str) {
        this.logger.log(FQCN, Level.DEBUG, str, null);
    }

    public void debug(String str, Object obj) {
        if (this.logger.isDebugEnabled()) {
            FormattingTuple format = MessageFormatter.format(str, obj);
            this.logger.log(FQCN, Level.DEBUG, format.getMessage(), format.getThrowable());
        }
    }

    public void debug(String str, Object obj, Object obj2) {
        if (this.logger.isDebugEnabled()) {
            FormattingTuple format = MessageFormatter.format(str, obj, obj2);
            this.logger.log(FQCN, Level.DEBUG, format.getMessage(), format.getThrowable());
        }
    }

    public void debug(String str, Object... objArr) {
        if (this.logger.isDebugEnabled()) {
            FormattingTuple arrayFormat = MessageFormatter.arrayFormat(str, objArr);
            this.logger.log(FQCN, Level.DEBUG, arrayFormat.getMessage(), arrayFormat.getThrowable());
        }
    }

    public void debug(String str, Throwable th) {
        this.logger.log(FQCN, Level.DEBUG, str, th);
    }

    public boolean isInfoEnabled() {
        return this.logger.isInfoEnabled();
    }

    public void info(String str) {
        this.logger.log(FQCN, Level.INFO, str, null);
    }

    public void info(String str, Object obj) {
        if (this.logger.isInfoEnabled()) {
            FormattingTuple format = MessageFormatter.format(str, obj);
            this.logger.log(FQCN, Level.INFO, format.getMessage(), format.getThrowable());
        }
    }

    public void info(String str, Object obj, Object obj2) {
        if (this.logger.isInfoEnabled()) {
            FormattingTuple format = MessageFormatter.format(str, obj, obj2);
            this.logger.log(FQCN, Level.INFO, format.getMessage(), format.getThrowable());
        }
    }

    public void info(String str, Object... objArr) {
        if (this.logger.isInfoEnabled()) {
            FormattingTuple arrayFormat = MessageFormatter.arrayFormat(str, objArr);
            this.logger.log(FQCN, Level.INFO, arrayFormat.getMessage(), arrayFormat.getThrowable());
        }
    }

    public void info(String str, Throwable th) {
        this.logger.log(FQCN, Level.INFO, str, th);
    }

    public boolean isWarnEnabled() {
        return this.logger.isEnabledFor(Level.WARN);
    }

    public void warn(String str) {
        this.logger.log(FQCN, Level.WARN, str, null);
    }

    public void warn(String str, Object obj) {
        if (this.logger.isEnabledFor(Level.WARN)) {
            FormattingTuple format = MessageFormatter.format(str, obj);
            this.logger.log(FQCN, Level.WARN, format.getMessage(), format.getThrowable());
        }
    }

    public void warn(String str, Object obj, Object obj2) {
        if (this.logger.isEnabledFor(Level.WARN)) {
            FormattingTuple format = MessageFormatter.format(str, obj, obj2);
            this.logger.log(FQCN, Level.WARN, format.getMessage(), format.getThrowable());
        }
    }

    public void warn(String str, Object... objArr) {
        if (this.logger.isEnabledFor(Level.WARN)) {
            FormattingTuple arrayFormat = MessageFormatter.arrayFormat(str, objArr);
            this.logger.log(FQCN, Level.WARN, arrayFormat.getMessage(), arrayFormat.getThrowable());
        }
    }

    public void warn(String str, Throwable th) {
        this.logger.log(FQCN, Level.WARN, str, th);
    }

    public boolean isErrorEnabled() {
        return this.logger.isEnabledFor(Level.ERROR);
    }

    public void error(String str) {
        this.logger.log(FQCN, Level.ERROR, str, null);
    }

    public void error(String str, Object obj) {
        if (this.logger.isEnabledFor(Level.ERROR)) {
            FormattingTuple format = MessageFormatter.format(str, obj);
            this.logger.log(FQCN, Level.ERROR, format.getMessage(), format.getThrowable());
        }
    }

    public void error(String str, Object obj, Object obj2) {
        if (this.logger.isEnabledFor(Level.ERROR)) {
            FormattingTuple format = MessageFormatter.format(str, obj, obj2);
            this.logger.log(FQCN, Level.ERROR, format.getMessage(), format.getThrowable());
        }
    }

    public void error(String str, Object... objArr) {
        if (this.logger.isEnabledFor(Level.ERROR)) {
            FormattingTuple arrayFormat = MessageFormatter.arrayFormat(str, objArr);
            this.logger.log(FQCN, Level.ERROR, arrayFormat.getMessage(), arrayFormat.getThrowable());
        }
    }

    public void error(String str, Throwable th) {
        this.logger.log(FQCN, Level.ERROR, str, th);
    }
}
