package p043io.netty.channel.group;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import p043io.netty.channel.Channel;
import p043io.netty.channel.ChannelFuture;
import p043io.netty.channel.ChannelFutureListener;
import p043io.netty.util.concurrent.BlockingOperationException;
import p043io.netty.util.concurrent.DefaultPromise;
import p043io.netty.util.concurrent.EventExecutor;
import p043io.netty.util.concurrent.Future;
import p043io.netty.util.concurrent.GenericFutureListener;
import p043io.netty.util.concurrent.ImmediateEventExecutor;

/* renamed from: io.netty.channel.group.DefaultChannelGroupFuture */
final class DefaultChannelGroupFuture extends DefaultPromise<Void> implements ChannelGroupFuture {
    private final ChannelFutureListener childListener = new ChannelFutureListener() {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        static {
            Class<DefaultChannelGroupFuture> cls = DefaultChannelGroupFuture.class;
        }

        public void operationComplete(ChannelFuture channelFuture) throws Exception {
            boolean z;
            boolean isSuccess = channelFuture.isSuccess();
            synchronized (DefaultChannelGroupFuture.this) {
                if (isSuccess) {
                    DefaultChannelGroupFuture.this.successCount = DefaultChannelGroupFuture.this.successCount + 1;
                } else {
                    DefaultChannelGroupFuture.this.failureCount = DefaultChannelGroupFuture.this.failureCount + 1;
                }
                z = DefaultChannelGroupFuture.this.successCount + DefaultChannelGroupFuture.this.failureCount == DefaultChannelGroupFuture.this.futures.size();
            }
            if (!z) {
                return;
            }
            if (DefaultChannelGroupFuture.this.failureCount > 0) {
                ArrayList arrayList = new ArrayList(DefaultChannelGroupFuture.this.failureCount);
                for (ChannelFuture channelFuture2 : DefaultChannelGroupFuture.this.futures.values()) {
                    if (!channelFuture2.isSuccess()) {
                        arrayList.add(new DefaultEntry(channelFuture2.channel(), channelFuture2.cause()));
                    }
                }
                DefaultChannelGroupFuture.this.setFailure0(new ChannelGroupException(arrayList));
                return;
            }
            DefaultChannelGroupFuture.this.setSuccess0();
        }
    };
    /* access modifiers changed from: private */
    public int failureCount;
    /* access modifiers changed from: private */
    public final Map<Channel, ChannelFuture> futures;
    private final ChannelGroup group;
    /* access modifiers changed from: private */
    public int successCount;

    /* renamed from: io.netty.channel.group.DefaultChannelGroupFuture$DefaultEntry */
    private static final class DefaultEntry<K, V> implements Entry<K, V> {
        private final K key;
        private final V value;

        DefaultEntry(K k, V v) {
            this.key = k;
            this.value = v;
        }

        public K getKey() {
            return this.key;
        }

        public V getValue() {
            return this.value;
        }

        public V setValue(V v) {
            throw new UnsupportedOperationException("read-only");
        }
    }

    DefaultChannelGroupFuture(ChannelGroup channelGroup, Collection<ChannelFuture> collection, EventExecutor eventExecutor) {
        super(eventExecutor);
        if (channelGroup == null) {
            throw new NullPointerException("group");
        } else if (collection != null) {
            this.group = channelGroup;
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (ChannelFuture channelFuture : collection) {
                linkedHashMap.put(channelFuture.channel(), channelFuture);
            }
            this.futures = Collections.unmodifiableMap(linkedHashMap);
            for (ChannelFuture addListener : this.futures.values()) {
                addListener.addListener(this.childListener);
            }
            if (this.futures.isEmpty()) {
                setSuccess0();
            }
        } else {
            throw new NullPointerException("futures");
        }
    }

    DefaultChannelGroupFuture(ChannelGroup channelGroup, Map<Channel, ChannelFuture> map, EventExecutor eventExecutor) {
        super(eventExecutor);
        this.group = channelGroup;
        this.futures = Collections.unmodifiableMap(map);
        for (ChannelFuture addListener : this.futures.values()) {
            addListener.addListener(this.childListener);
        }
        if (this.futures.isEmpty()) {
            setSuccess0();
        }
    }

    public ChannelGroup group() {
        return this.group;
    }

    public ChannelFuture find(Channel channel) {
        return (ChannelFuture) this.futures.get(channel);
    }

    public Iterator<ChannelFuture> iterator() {
        return this.futures.values().iterator();
    }

    public synchronized boolean isPartialSuccess() {
        return (this.successCount == 0 || this.successCount == this.futures.size()) ? false : true;
    }

    public synchronized boolean isPartialFailure() {
        return (this.failureCount == 0 || this.failureCount == this.futures.size()) ? false : true;
    }

    public DefaultChannelGroupFuture addListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener) {
        super.addListener((GenericFutureListener) genericFutureListener);
        return this;
    }

    public DefaultChannelGroupFuture addListeners(GenericFutureListener<? extends Future<? super Void>>... genericFutureListenerArr) {
        super.addListeners((GenericFutureListener[]) genericFutureListenerArr);
        return this;
    }

    public DefaultChannelGroupFuture removeListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener) {
        super.removeListener((GenericFutureListener) genericFutureListener);
        return this;
    }

    public DefaultChannelGroupFuture removeListeners(GenericFutureListener<? extends Future<? super Void>>... genericFutureListenerArr) {
        super.removeListeners((GenericFutureListener[]) genericFutureListenerArr);
        return this;
    }

    public DefaultChannelGroupFuture await() throws InterruptedException {
        super.await();
        return this;
    }

    public DefaultChannelGroupFuture awaitUninterruptibly() {
        super.awaitUninterruptibly();
        return this;
    }

    public DefaultChannelGroupFuture syncUninterruptibly() {
        super.syncUninterruptibly();
        return this;
    }

    public DefaultChannelGroupFuture sync() throws InterruptedException {
        super.sync();
        return this;
    }

    public ChannelGroupException cause() {
        return (ChannelGroupException) super.cause();
    }

    /* access modifiers changed from: private */
    public void setSuccess0() {
        super.setSuccess(null);
    }

    /* access modifiers changed from: private */
    public void setFailure0(ChannelGroupException channelGroupException) {
        super.setFailure(channelGroupException);
    }

    public DefaultChannelGroupFuture setSuccess(Void voidR) {
        throw new IllegalStateException();
    }

    public boolean trySuccess(Void voidR) {
        throw new IllegalStateException();
    }

    public DefaultChannelGroupFuture setFailure(Throwable th) {
        throw new IllegalStateException();
    }

    public boolean tryFailure(Throwable th) {
        throw new IllegalStateException();
    }

    /* access modifiers changed from: protected */
    public void checkDeadLock() {
        EventExecutor executor = executor();
        if (executor != null && executor != ImmediateEventExecutor.INSTANCE && executor.inEventLoop()) {
            throw new BlockingOperationException();
        }
    }
}
