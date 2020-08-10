package p043io.netty.util.internal.logging;

import org.apache.commons.logging.LogFactory;

@Deprecated
/* renamed from: io.netty.util.internal.logging.CommonsLoggerFactory */
public class CommonsLoggerFactory extends InternalLoggerFactory {
    public static final InternalLoggerFactory INSTANCE = new CommonsLoggerFactory();

    public InternalLogger newInstance(String str) {
        return new CommonsLogger(LogFactory.getLog(str), str);
    }
}
