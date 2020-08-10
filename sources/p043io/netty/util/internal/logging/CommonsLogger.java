package p043io.netty.util.internal.logging;

import org.apache.commons.logging.Log;

@Deprecated
/* renamed from: io.netty.util.internal.logging.CommonsLogger */
class CommonsLogger extends AbstractInternalLogger {
    private static final long serialVersionUID = 8647838678388394885L;
    private final transient Log logger;

    CommonsLogger(Log log, String str) {
        super(str);
        if (log != null) {
            this.logger = log;
            return;
        }
        throw new NullPointerException("logger");
    }

    public boolean isTraceEnabled() {
        return this.logger.isTraceEnabled();
    }

    public void trace(String str) {
        this.logger.trace(str);
    }

    public void trace(String str, Object obj) {
        if (this.logger.isTraceEnabled()) {
            FormattingTuple format = MessageFormatter.format(str, obj);
            this.logger.trace(format.getMessage(), format.getThrowable());
        }
    }

    public void trace(String str, Object obj, Object obj2) {
        if (this.logger.isTraceEnabled()) {
            FormattingTuple format = MessageFormatter.format(str, obj, obj2);
            this.logger.trace(format.getMessage(), format.getThrowable());
        }
    }

    public void trace(String str, Object... objArr) {
        if (this.logger.isTraceEnabled()) {
            FormattingTuple arrayFormat = MessageFormatter.arrayFormat(str, objArr);
            this.logger.trace(arrayFormat.getMessage(), arrayFormat.getThrowable());
        }
    }

    public void trace(String str, Throwable th) {
        this.logger.trace(str, th);
    }

    public boolean isDebugEnabled() {
        return this.logger.isDebugEnabled();
    }

    public void debug(String str) {
        this.logger.debug(str);
    }

    public void debug(String str, Object obj) {
        if (this.logger.isDebugEnabled()) {
            FormattingTuple format = MessageFormatter.format(str, obj);
            this.logger.debug(format.getMessage(), format.getThrowable());
        }
    }

    public void debug(String str, Object obj, Object obj2) {
        if (this.logger.isDebugEnabled()) {
            FormattingTuple format = MessageFormatter.format(str, obj, obj2);
            this.logger.debug(format.getMessage(), format.getThrowable());
        }
    }

    public void debug(String str, Object... objArr) {
        if (this.logger.isDebugEnabled()) {
            FormattingTuple arrayFormat = MessageFormatter.arrayFormat(str, objArr);
            this.logger.debug(arrayFormat.getMessage(), arrayFormat.getThrowable());
        }
    }

    public void debug(String str, Throwable th) {
        this.logger.debug(str, th);
    }

    public boolean isInfoEnabled() {
        return this.logger.isInfoEnabled();
    }

    public void info(String str) {
        this.logger.info(str);
    }

    public void info(String str, Object obj) {
        if (this.logger.isInfoEnabled()) {
            FormattingTuple format = MessageFormatter.format(str, obj);
            this.logger.info(format.getMessage(), format.getThrowable());
        }
    }

    public void info(String str, Object obj, Object obj2) {
        if (this.logger.isInfoEnabled()) {
            FormattingTuple format = MessageFormatter.format(str, obj, obj2);
            this.logger.info(format.getMessage(), format.getThrowable());
        }
    }

    public void info(String str, Object... objArr) {
        if (this.logger.isInfoEnabled()) {
            FormattingTuple arrayFormat = MessageFormatter.arrayFormat(str, objArr);
            this.logger.info(arrayFormat.getMessage(), arrayFormat.getThrowable());
        }
    }

    public void info(String str, Throwable th) {
        this.logger.info(str, th);
    }

    public boolean isWarnEnabled() {
        return this.logger.isWarnEnabled();
    }

    public void warn(String str) {
        this.logger.warn(str);
    }

    public void warn(String str, Object obj) {
        if (this.logger.isWarnEnabled()) {
            FormattingTuple format = MessageFormatter.format(str, obj);
            this.logger.warn(format.getMessage(), format.getThrowable());
        }
    }

    public void warn(String str, Object obj, Object obj2) {
        if (this.logger.isWarnEnabled()) {
            FormattingTuple format = MessageFormatter.format(str, obj, obj2);
            this.logger.warn(format.getMessage(), format.getThrowable());
        }
    }

    public void warn(String str, Object... objArr) {
        if (this.logger.isWarnEnabled()) {
            FormattingTuple arrayFormat = MessageFormatter.arrayFormat(str, objArr);
            this.logger.warn(arrayFormat.getMessage(), arrayFormat.getThrowable());
        }
    }

    public void warn(String str, Throwable th) {
        this.logger.warn(str, th);
    }

    public boolean isErrorEnabled() {
        return this.logger.isErrorEnabled();
    }

    public void error(String str) {
        this.logger.error(str);
    }

    public void error(String str, Object obj) {
        if (this.logger.isErrorEnabled()) {
            FormattingTuple format = MessageFormatter.format(str, obj);
            this.logger.error(format.getMessage(), format.getThrowable());
        }
    }

    public void error(String str, Object obj, Object obj2) {
        if (this.logger.isErrorEnabled()) {
            FormattingTuple format = MessageFormatter.format(str, obj, obj2);
            this.logger.error(format.getMessage(), format.getThrowable());
        }
    }

    public void error(String str, Object... objArr) {
        if (this.logger.isErrorEnabled()) {
            FormattingTuple arrayFormat = MessageFormatter.arrayFormat(str, objArr);
            this.logger.error(arrayFormat.getMessage(), arrayFormat.getThrowable());
        }
    }

    public void error(String str, Throwable th) {
        this.logger.error(str, th);
    }
}
