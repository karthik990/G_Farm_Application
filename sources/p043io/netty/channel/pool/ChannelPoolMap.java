package p043io.netty.channel.pool;

import p043io.netty.channel.pool.ChannelPool;

/* renamed from: io.netty.channel.pool.ChannelPoolMap */
public interface ChannelPoolMap<K, P extends ChannelPool> {
    boolean contains(K k);

    P get(K k);
}
