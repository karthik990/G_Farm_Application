package p043io.netty.channel.pool;

import java.io.Closeable;
import p043io.netty.channel.Channel;
import p043io.netty.util.concurrent.Future;
import p043io.netty.util.concurrent.Promise;

/* renamed from: io.netty.channel.pool.ChannelPool */
public interface ChannelPool extends Closeable {
    Future<Channel> acquire();

    Future<Channel> acquire(Promise<Channel> promise);

    void close();

    Future<Void> release(Channel channel);

    Future<Void> release(Channel channel, Promise<Void> promise);
}
