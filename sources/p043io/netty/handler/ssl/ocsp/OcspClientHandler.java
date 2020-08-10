package p043io.netty.handler.ssl.ocsp;

import javax.net.ssl.SSLHandshakeException;
import p043io.netty.channel.ChannelHandler;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.channel.ChannelInboundHandlerAdapter;
import p043io.netty.handler.ssl.ReferenceCountedOpenSslEngine;
import p043io.netty.handler.ssl.SslHandshakeCompletionEvent;
import p043io.netty.util.internal.ObjectUtil;
import p043io.netty.util.internal.ThrowableUtil;

/* renamed from: io.netty.handler.ssl.ocsp.OcspClientHandler */
public abstract class OcspClientHandler extends ChannelInboundHandlerAdapter {
    private static final SSLHandshakeException OCSP_VERIFICATION_EXCEPTION = ((SSLHandshakeException) ThrowableUtil.unknownStackTrace(new SSLHandshakeException("Bad OCSP response"), OcspClientHandler.class, "verify(...)"));
    private final ReferenceCountedOpenSslEngine engine;

    /* access modifiers changed from: protected */
    public abstract boolean verify(ChannelHandlerContext channelHandlerContext, ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine) throws Exception;

    protected OcspClientHandler(ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine) {
        this.engine = (ReferenceCountedOpenSslEngine) ObjectUtil.checkNotNull(referenceCountedOpenSslEngine, "engine");
    }

    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        if (obj instanceof SslHandshakeCompletionEvent) {
            channelHandlerContext.pipeline().remove((ChannelHandler) this);
            if (((SslHandshakeCompletionEvent) obj).isSuccess() && !verify(channelHandlerContext, this.engine)) {
                throw OCSP_VERIFICATION_EXCEPTION;
            }
        }
        channelHandlerContext.fireUserEventTriggered(obj);
    }
}
