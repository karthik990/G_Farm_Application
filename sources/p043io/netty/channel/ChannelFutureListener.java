package p043io.netty.channel;

import p043io.netty.util.concurrent.GenericFutureListener;

/* renamed from: io.netty.channel.ChannelFutureListener */
public interface ChannelFutureListener extends GenericFutureListener<ChannelFuture> {
    public static final ChannelFutureListener CLOSE = new ChannelFutureListener() {
        public void operationComplete(ChannelFuture channelFuture) {
            channelFuture.channel().close();
        }
    };
    public static final ChannelFutureListener CLOSE_ON_FAILURE = new ChannelFutureListener() {
        public void operationComplete(ChannelFuture channelFuture) {
            if (!channelFuture.isSuccess()) {
                channelFuture.channel().close();
            }
        }
    };
    public static final ChannelFutureListener FIRE_EXCEPTION_ON_FAILURE = new ChannelFutureListener() {
        public void operationComplete(ChannelFuture channelFuture) {
            if (!channelFuture.isSuccess()) {
                channelFuture.channel().pipeline().fireExceptionCaught(channelFuture.cause());
            }
        }
    };
}
