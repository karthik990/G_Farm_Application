package p043io.netty.handler.ipfilter;

import java.net.SocketAddress;
import p043io.netty.channel.ChannelFuture;
import p043io.netty.channel.ChannelFutureListener;
import p043io.netty.channel.ChannelHandler;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.channel.ChannelInboundHandlerAdapter;

/* renamed from: io.netty.handler.ipfilter.AbstractRemoteAddressFilter */
public abstract class AbstractRemoteAddressFilter<T extends SocketAddress> extends ChannelInboundHandlerAdapter {
    /* access modifiers changed from: protected */
    public abstract boolean accept(ChannelHandlerContext channelHandlerContext, T t) throws Exception;

    /* access modifiers changed from: protected */
    public void channelAccepted(ChannelHandlerContext channelHandlerContext, T t) {
    }

    /* access modifiers changed from: protected */
    public ChannelFuture channelRejected(ChannelHandlerContext channelHandlerContext, T t) {
        return null;
    }

    public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        handleNewChannel(channelHandlerContext);
        channelHandlerContext.fireChannelRegistered();
    }

    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (handleNewChannel(channelHandlerContext)) {
            channelHandlerContext.fireChannelActive();
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("cannot determine to accept or reject a channel: ");
        sb.append(channelHandlerContext.channel());
        throw new IllegalStateException(sb.toString());
    }

    private boolean handleNewChannel(ChannelHandlerContext channelHandlerContext) throws Exception {
        SocketAddress remoteAddress = channelHandlerContext.channel().remoteAddress();
        if (remoteAddress == null) {
            return false;
        }
        channelHandlerContext.pipeline().remove((ChannelHandler) this);
        if (accept(channelHandlerContext, remoteAddress)) {
            channelAccepted(channelHandlerContext, remoteAddress);
        } else {
            ChannelFuture channelRejected = channelRejected(channelHandlerContext, remoteAddress);
            if (channelRejected != null) {
                channelRejected.addListener(ChannelFutureListener.CLOSE);
            } else {
                channelHandlerContext.close();
            }
        }
        return true;
    }
}
