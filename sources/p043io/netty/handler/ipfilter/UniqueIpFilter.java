package p043io.netty.handler.ipfilter;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Set;
import p043io.netty.channel.ChannelFuture;
import p043io.netty.channel.ChannelFutureListener;
import p043io.netty.channel.ChannelHandler.Sharable;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.util.internal.ConcurrentSet;

@Sharable
/* renamed from: io.netty.handler.ipfilter.UniqueIpFilter */
public class UniqueIpFilter extends AbstractRemoteAddressFilter<InetSocketAddress> {
    /* access modifiers changed from: private */
    public final Set<InetAddress> connected = new ConcurrentSet();

    /* access modifiers changed from: protected */
    public boolean accept(ChannelHandlerContext channelHandlerContext, InetSocketAddress inetSocketAddress) throws Exception {
        final InetAddress address = inetSocketAddress.getAddress();
        if (this.connected.contains(address)) {
            return false;
        }
        this.connected.add(address);
        channelHandlerContext.channel().closeFuture().addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                UniqueIpFilter.this.connected.remove(address);
            }
        });
        return true;
    }
}
