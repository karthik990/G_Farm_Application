package p043io.netty.handler.timeout;

import p043io.netty.channel.ChannelException;

/* renamed from: io.netty.handler.timeout.TimeoutException */
public class TimeoutException extends ChannelException {
    private static final long serialVersionUID = 4673641882869672533L;

    public Throwable fillInStackTrace() {
        return this;
    }

    TimeoutException() {
    }
}
