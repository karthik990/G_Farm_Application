package p043io.netty.channel.pool;

import java.io.Closeable;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;
import p043io.netty.channel.pool.ChannelPool;
import p043io.netty.util.internal.ObjectUtil;
import p043io.netty.util.internal.PlatformDependent;
import p043io.netty.util.internal.ReadOnlyIterator;

/* renamed from: io.netty.channel.pool.AbstractChannelPoolMap */
public abstract class AbstractChannelPoolMap<K, P extends ChannelPool> implements ChannelPoolMap<K, P>, Iterable<Entry<K, P>>, Closeable {
    private final ConcurrentMap<K, P> map = PlatformDependent.newConcurrentHashMap();

    /* access modifiers changed from: protected */
    public abstract P newPool(K k);

    public final P get(K k) {
        P p = (ChannelPool) this.map.get(ObjectUtil.checkNotNull(k, "key"));
        if (p != null) {
            return p;
        }
        P newPool = newPool(k);
        ChannelPool channelPool = (ChannelPool) this.map.putIfAbsent(k, newPool);
        if (channelPool == null) {
            return newPool;
        }
        newPool.close();
        return channelPool;
    }

    public final boolean remove(K k) {
        ChannelPool channelPool = (ChannelPool) this.map.remove(ObjectUtil.checkNotNull(k, "key"));
        if (channelPool == null) {
            return false;
        }
        channelPool.close();
        return true;
    }

    public final Iterator<Entry<K, P>> iterator() {
        return new ReadOnlyIterator(this.map.entrySet().iterator());
    }

    public final int size() {
        return this.map.size();
    }

    public final boolean isEmpty() {
        return this.map.isEmpty();
    }

    public final boolean contains(K k) {
        return this.map.containsKey(ObjectUtil.checkNotNull(k, "key"));
    }

    public final void close() {
        for (Object remove : this.map.keySet()) {
            remove(remove);
        }
    }
}
