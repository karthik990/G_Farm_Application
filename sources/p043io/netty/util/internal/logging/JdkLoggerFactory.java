package p043io.netty.util.internal.logging;

import java.util.logging.Logger;

/* renamed from: io.netty.util.internal.logging.JdkLoggerFactory */
public class JdkLoggerFactory extends InternalLoggerFactory {
    public static final InternalLoggerFactory INSTANCE = new JdkLoggerFactory();

    public InternalLogger newInstance(String str) {
        return new JdkLogger(Logger.getLogger(str));
    }
}
