package p043io.netty.channel;

import java.util.concurrent.ConcurrentMap;
import p043io.netty.channel.Channel;
import p043io.netty.channel.ChannelHandler.Sharable;
import p043io.netty.util.internal.PlatformDependent;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

@Sharable
/* renamed from: io.netty.channel.ChannelInitializer */
public abstract class ChannelInitializer<C extends Channel> extends ChannelInboundHandlerAdapter {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ChannelInitializer.class);
    private final ConcurrentMap<ChannelHandlerContext, Boolean> initMap = PlatformDependent.newConcurrentHashMap();

    /* access modifiers changed from: protected */
    public abstract void initChannel(C c) throws Exception;

    public final void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (initChannel(channelHandlerContext)) {
            channelHandlerContext.pipeline().fireChannelRegistered();
        } else {
            channelHandlerContext.fireChannelRegistered();
        }
    }

    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) throws Exception {
        InternalLogger internalLogger = logger;
        StringBuilder sb = new StringBuilder();
        sb.append("Failed to initialize a channel. Closing: ");
        sb.append(channelHandlerContext.channel());
        internalLogger.warn(sb.toString(), th);
        channelHandlerContext.close();
    }

    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (channelHandlerContext.channel().isRegistered()) {
            initChannel(channelHandlerContext);
        }
    }

    private boolean initChannel(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.initMap.putIfAbsent(channelHandlerContext, Boolean.TRUE) != null) {
            return false;
        }
        try {
            initChannel((C) channelHandlerContext.channel());
        } catch (Throwable th) {
            remove(channelHandlerContext);
            throw th;
        }
        remove(channelHandlerContext);
        return true;
    }

    private void remove(ChannelHandlerContext channelHandlerContext) {
        try {
            ChannelPipeline pipeline = channelHandlerContext.pipeline();
            if (pipeline.context((ChannelHandler) this) != null) {
                pipeline.remove((ChannelHandler) this);
            }
        } finally {
            this.initMap.remove(channelHandlerContext);
        }
    }
}
