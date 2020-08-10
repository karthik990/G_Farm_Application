package p043io.netty.handler.logging;

import p043io.netty.util.internal.logging.InternalLogLevel;

/* renamed from: io.netty.handler.logging.LogLevel */
public enum LogLevel {
    TRACE(InternalLogLevel.TRACE),
    DEBUG(InternalLogLevel.DEBUG),
    INFO(InternalLogLevel.INFO),
    WARN(InternalLogLevel.WARN),
    ERROR(InternalLogLevel.ERROR);
    
    private final InternalLogLevel internalLevel;

    private LogLevel(InternalLogLevel internalLogLevel) {
        this.internalLevel = internalLogLevel;
    }

    public InternalLogLevel toInternalLevel() {
        return this.internalLevel;
    }
}
