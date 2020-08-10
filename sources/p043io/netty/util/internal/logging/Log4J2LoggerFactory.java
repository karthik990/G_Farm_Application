package p043io.netty.util.internal.logging;

import org.apache.logging.log4j.LogManager;

/* renamed from: io.netty.util.internal.logging.Log4J2LoggerFactory */
public final class Log4J2LoggerFactory extends InternalLoggerFactory {
    public static final InternalLoggerFactory INSTANCE = new Log4J2LoggerFactory();

    public InternalLogger newInstance(String str) {
        return new Log4J2Logger(LogManager.getLogger(str));
    }
}
