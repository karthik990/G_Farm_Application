package p043io.netty.handler.timeout;

import java.util.concurrent.TimeUnit;
import p043io.netty.channel.ChannelHandlerContext;

/* renamed from: io.netty.handler.timeout.ReadTimeoutHandler */
public class ReadTimeoutHandler extends IdleStateHandler {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private boolean closed;

    public ReadTimeoutHandler(int i) {
        this((long) i, TimeUnit.SECONDS);
    }

    public ReadTimeoutHandler(long j, TimeUnit timeUnit) {
        super(j, 0, 0, timeUnit);
    }

    /* access modifiers changed from: protected */
    public final void channelIdle(ChannelHandlerContext channelHandlerContext, IdleStateEvent idleStateEvent) throws Exception {
        readTimedOut(channelHandlerContext);
    }

    /* access modifiers changed from: protected */
    public void readTimedOut(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!this.closed) {
            channelHandlerContext.fireExceptionCaught(ReadTimeoutException.INSTANCE);
            channelHandlerContext.close();
            this.closed = true;
        }
    }
}
