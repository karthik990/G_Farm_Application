package p043io.netty.util.internal.logging;

import org.apache.log4j.Logger;

/* renamed from: io.netty.util.internal.logging.Log4JLoggerFactory */
public class Log4JLoggerFactory extends InternalLoggerFactory {
    public static final InternalLoggerFactory INSTANCE = new Log4JLoggerFactory();

    public InternalLogger newInstance(String str) {
        return new Log4JLogger(Logger.getLogger(str));
    }
}
