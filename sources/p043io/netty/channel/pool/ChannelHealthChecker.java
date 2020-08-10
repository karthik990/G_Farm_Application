package p043io.netty.channel.pool;

import p043io.netty.channel.Channel;
import p043io.netty.util.concurrent.Future;

/* renamed from: io.netty.channel.pool.ChannelHealthChecker */
public interface ChannelHealthChecker {
    public static final ChannelHealthChecker ACTIVE = new ChannelHealthChecker() {
        public Future<Boolean> isHealthy(Channel channel) {
            return channel.eventLoop().newSucceededFuture(channel.isActive() ? Boolean.TRUE : Boolean.FALSE);
        }
    };

    Future<Boolean> isHealthy(Channel channel);
}
