package p043io.netty.channel;

import p043io.netty.util.ReferenceCountUtil;
import p043io.netty.util.internal.TypeParameterMatcher;

/* renamed from: io.netty.channel.SimpleChannelInboundHandler */
public abstract class SimpleChannelInboundHandler<I> extends ChannelInboundHandlerAdapter {
    private final boolean autoRelease;
    private final TypeParameterMatcher matcher;

    /* access modifiers changed from: protected */
    public abstract void channelRead0(ChannelHandlerContext channelHandlerContext, I i) throws Exception;

    protected SimpleChannelInboundHandler() {
        this(true);
    }

    protected SimpleChannelInboundHandler(boolean z) {
        this.matcher = TypeParameterMatcher.find(this, SimpleChannelInboundHandler.class, "I");
        this.autoRelease = z;
    }

    protected SimpleChannelInboundHandler(Class<? extends I> cls) {
        this(cls, true);
    }

    protected SimpleChannelInboundHandler(Class<? extends I> cls, boolean z) {
        this.matcher = TypeParameterMatcher.get(cls);
        this.autoRelease = z;
    }

    public boolean acceptInboundMessage(Object obj) throws Exception {
        return this.matcher.match(obj);
    }

    public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        boolean z = true;
        try {
            if (acceptInboundMessage(obj)) {
                channelRead0(channelHandlerContext, obj);
            } else {
                z = false;
                channelHandlerContext.fireChannelRead(obj);
            }
        } finally {
            if (this.autoRelease && z) {
                ReferenceCountUtil.release(obj);
            }
        }
    }
}
