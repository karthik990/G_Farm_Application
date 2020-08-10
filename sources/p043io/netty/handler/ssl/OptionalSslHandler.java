package p043io.netty.handler.ssl;

import java.util.List;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.channel.ChannelHandler;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.handler.codec.ByteToMessageDecoder;
import p043io.netty.util.ReferenceCountUtil;
import p043io.netty.util.internal.ObjectUtil;

/* renamed from: io.netty.handler.ssl.OptionalSslHandler */
public class OptionalSslHandler extends ByteToMessageDecoder {
    private final SslContext sslContext;

    /* access modifiers changed from: protected */
    public ChannelHandler newNonSslHandler(ChannelHandlerContext channelHandlerContext) {
        return null;
    }

    /* access modifiers changed from: protected */
    public String newNonSslHandlerName() {
        return null;
    }

    /* access modifiers changed from: protected */
    public String newSslHandlerName() {
        return null;
    }

    public OptionalSslHandler(SslContext sslContext2) {
        this.sslContext = (SslContext) ObjectUtil.checkNotNull(sslContext2, "sslContext");
    }

    /* access modifiers changed from: protected */
    public void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() >= 5) {
            if (SslHandler.isEncrypted(byteBuf)) {
                handleSsl(channelHandlerContext);
            } else {
                handleNonSsl(channelHandlerContext);
            }
        }
    }

    private void handleSsl(ChannelHandlerContext channelHandlerContext) {
        SslHandler sslHandler = null;
        try {
            sslHandler = newSslHandler(channelHandlerContext, this.sslContext);
            channelHandlerContext.pipeline().replace((ChannelHandler) this, newSslHandlerName(), (ChannelHandler) sslHandler);
        } catch (Throwable th) {
            if (sslHandler != null) {
                ReferenceCountUtil.safeRelease(sslHandler.engine());
            }
            throw th;
        }
    }

    private void handleNonSsl(ChannelHandlerContext channelHandlerContext) {
        ChannelHandler newNonSslHandler = newNonSslHandler(channelHandlerContext);
        if (newNonSslHandler != null) {
            channelHandlerContext.pipeline().replace((ChannelHandler) this, newNonSslHandlerName(), newNonSslHandler);
        } else {
            channelHandlerContext.pipeline().remove((ChannelHandler) this);
        }
    }

    /* access modifiers changed from: protected */
    public SslHandler newSslHandler(ChannelHandlerContext channelHandlerContext, SslContext sslContext2) {
        return sslContext2.newHandler(channelHandlerContext.alloc());
    }
}
