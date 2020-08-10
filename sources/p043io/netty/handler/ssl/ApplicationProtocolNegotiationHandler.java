package p043io.netty.handler.ssl;

import p043io.netty.channel.ChannelHandler;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.channel.ChannelInboundHandlerAdapter;
import p043io.netty.util.internal.ObjectUtil;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.handler.ssl.ApplicationProtocolNegotiationHandler */
public abstract class ApplicationProtocolNegotiationHandler extends ChannelInboundHandlerAdapter {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ApplicationProtocolNegotiationHandler.class);
    private final String fallbackProtocol;

    /* access modifiers changed from: protected */
    public abstract void configurePipeline(ChannelHandlerContext channelHandlerContext, String str) throws Exception;

    protected ApplicationProtocolNegotiationHandler(String str) {
        this.fallbackProtocol = (String) ObjectUtil.checkNotNull(str, "fallbackProtocol");
    }

    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        if (obj instanceof SslHandshakeCompletionEvent) {
            channelHandlerContext.pipeline().remove((ChannelHandler) this);
            SslHandshakeCompletionEvent sslHandshakeCompletionEvent = (SslHandshakeCompletionEvent) obj;
            if (sslHandshakeCompletionEvent.isSuccess()) {
                SslHandler sslHandler = (SslHandler) channelHandlerContext.pipeline().get(SslHandler.class);
                if (sslHandler != null) {
                    String applicationProtocol = sslHandler.applicationProtocol();
                    if (applicationProtocol == null) {
                        applicationProtocol = this.fallbackProtocol;
                    }
                    configurePipeline(channelHandlerContext, applicationProtocol);
                } else {
                    throw new IllegalStateException("cannot find a SslHandler in the pipeline (required for application-level protocol negotiation)");
                }
            } else {
                handshakeFailure(channelHandlerContext, sslHandshakeCompletionEvent.cause());
            }
        }
        channelHandlerContext.fireUserEventTriggered(obj);
    }

    /* access modifiers changed from: protected */
    public void handshakeFailure(ChannelHandlerContext channelHandlerContext, Throwable th) throws Exception {
        logger.warn("{} TLS handshake failed:", channelHandlerContext.channel(), th);
        channelHandlerContext.close();
    }

    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) throws Exception {
        logger.warn("{} Failed to select the application-level protocol:", channelHandlerContext.channel(), th);
        channelHandlerContext.close();
    }
}
