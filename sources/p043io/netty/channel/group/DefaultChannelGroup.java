package p043io.netty.channel.group;

import com.braintreepayments.api.models.PostalAddressParser;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.buffer.ByteBufHolder;
import p043io.netty.channel.Channel;
import p043io.netty.channel.ChannelFuture;
import p043io.netty.channel.ChannelFutureListener;
import p043io.netty.channel.ChannelId;
import p043io.netty.channel.ServerChannel;
import p043io.netty.util.ReferenceCountUtil;
import p043io.netty.util.concurrent.EventExecutor;
import p043io.netty.util.internal.PlatformDependent;
import p043io.netty.util.internal.StringUtil;

/* renamed from: io.netty.channel.group.DefaultChannelGroup */
public class DefaultChannelGroup extends AbstractSet<Channel> implements ChannelGroup {
    private static final AtomicInteger nextId = new AtomicInteger();
    private volatile boolean closed;
    private final EventExecutor executor;
    private final String name;
    private final ConcurrentMap<ChannelId, Channel> nonServerChannels;
    private final ChannelFutureListener remover;
    private final ConcurrentMap<ChannelId, Channel> serverChannels;
    private final boolean stayClosed;
    private final VoidChannelGroupFuture voidFuture;

    public boolean equals(Object obj) {
        return this == obj;
    }

    public DefaultChannelGroup(EventExecutor eventExecutor) {
        this(eventExecutor, false);
    }

    public DefaultChannelGroup(String str, EventExecutor eventExecutor) {
        this(str, eventExecutor, false);
    }

    public DefaultChannelGroup(EventExecutor eventExecutor, boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append("group-0x");
        sb.append(Integer.toHexString(nextId.incrementAndGet()));
        this(sb.toString(), eventExecutor, z);
    }

    public DefaultChannelGroup(String str, EventExecutor eventExecutor, boolean z) {
        this.serverChannels = PlatformDependent.newConcurrentHashMap();
        this.nonServerChannels = PlatformDependent.newConcurrentHashMap();
        this.remover = new ChannelFutureListener() {
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                DefaultChannelGroup.this.remove(channelFuture.channel());
            }
        };
        this.voidFuture = new VoidChannelGroupFuture(this);
        if (str != null) {
            this.name = str;
            this.executor = eventExecutor;
            this.stayClosed = z;
            return;
        }
        throw new NullPointerException(PostalAddressParser.USER_ADDRESS_NAME_KEY);
    }

    public String name() {
        return this.name;
    }

    public Channel find(ChannelId channelId) {
        Channel channel = (Channel) this.nonServerChannels.get(channelId);
        if (channel != null) {
            return channel;
        }
        return (Channel) this.serverChannels.get(channelId);
    }

    public boolean isEmpty() {
        return this.nonServerChannels.isEmpty() && this.serverChannels.isEmpty();
    }

    public int size() {
        return this.nonServerChannels.size() + this.serverChannels.size();
    }

    public boolean contains(Object obj) {
        if (!(obj instanceof Channel)) {
            return false;
        }
        Channel channel = (Channel) obj;
        if (obj instanceof ServerChannel) {
            return this.serverChannels.containsValue(channel);
        }
        return this.nonServerChannels.containsValue(channel);
    }

    public boolean add(Channel channel) {
        boolean z = (channel instanceof ServerChannel ? this.serverChannels : this.nonServerChannels).putIfAbsent(channel.mo66192id(), channel) == null;
        if (z) {
            channel.closeFuture().addListener(this.remover);
        }
        if (this.stayClosed && this.closed) {
            channel.close();
        }
        return z;
    }

    public boolean remove(Object obj) {
        Channel channel;
        if (obj instanceof ChannelId) {
            channel = (Channel) this.nonServerChannels.remove(obj);
            if (channel == null) {
                channel = (Channel) this.serverChannels.remove(obj);
            }
        } else if (obj instanceof Channel) {
            Channel channel2 = (Channel) obj;
            channel = channel2 instanceof ServerChannel ? (Channel) this.serverChannels.remove(channel2.mo66192id()) : (Channel) this.nonServerChannels.remove(channel2.mo66192id());
        } else {
            channel = null;
        }
        if (channel == null) {
            return false;
        }
        channel.closeFuture().removeListener(this.remover);
        return true;
    }

    public void clear() {
        this.nonServerChannels.clear();
        this.serverChannels.clear();
    }

    public Iterator<Channel> iterator() {
        return new CombinedIterator(this.serverChannels.values().iterator(), this.nonServerChannels.values().iterator());
    }

    public Object[] toArray() {
        ArrayList arrayList = new ArrayList(size());
        arrayList.addAll(this.serverChannels.values());
        arrayList.addAll(this.nonServerChannels.values());
        return arrayList.toArray();
    }

    public <T> T[] toArray(T[] tArr) {
        ArrayList arrayList = new ArrayList(size());
        arrayList.addAll(this.serverChannels.values());
        arrayList.addAll(this.nonServerChannels.values());
        return arrayList.toArray(tArr);
    }

    public ChannelGroupFuture close() {
        return close(ChannelMatchers.all());
    }

    public ChannelGroupFuture disconnect() {
        return disconnect(ChannelMatchers.all());
    }

    public ChannelGroupFuture deregister() {
        return deregister(ChannelMatchers.all());
    }

    public ChannelGroupFuture write(Object obj) {
        return write(obj, ChannelMatchers.all());
    }

    private static Object safeDuplicate(Object obj) {
        if (obj instanceof ByteBuf) {
            return ((ByteBuf) obj).retainedDuplicate();
        }
        if (obj instanceof ByteBufHolder) {
            return ((ByteBufHolder) obj).retainedDuplicate();
        }
        return ReferenceCountUtil.retain(obj);
    }

    public ChannelGroupFuture write(Object obj, ChannelMatcher channelMatcher) {
        return write(obj, channelMatcher, false);
    }

    public ChannelGroupFuture write(Object obj, ChannelMatcher channelMatcher, boolean z) {
        ChannelGroupFuture channelGroupFuture;
        if (obj == null) {
            throw new NullPointerException("message");
        } else if (channelMatcher != null) {
            if (z) {
                for (Channel channel : this.nonServerChannels.values()) {
                    if (channelMatcher.matches(channel)) {
                        channel.write(safeDuplicate(obj), channel.voidPromise());
                    }
                }
                channelGroupFuture = this.voidFuture;
            } else {
                LinkedHashMap linkedHashMap = new LinkedHashMap(size());
                for (Channel channel2 : this.nonServerChannels.values()) {
                    if (channelMatcher.matches(channel2)) {
                        linkedHashMap.put(channel2, channel2.write(safeDuplicate(obj)));
                    }
                }
                channelGroupFuture = new DefaultChannelGroupFuture((ChannelGroup) this, (Map<Channel, ChannelFuture>) linkedHashMap, this.executor);
            }
            ReferenceCountUtil.release(obj);
            return channelGroupFuture;
        } else {
            throw new NullPointerException("matcher");
        }
    }

    public ChannelGroup flush() {
        return flush(ChannelMatchers.all());
    }

    public ChannelGroupFuture flushAndWrite(Object obj) {
        return writeAndFlush(obj);
    }

    public ChannelGroupFuture writeAndFlush(Object obj) {
        return writeAndFlush(obj, ChannelMatchers.all());
    }

    public ChannelGroupFuture disconnect(ChannelMatcher channelMatcher) {
        if (channelMatcher != null) {
            LinkedHashMap linkedHashMap = new LinkedHashMap(size());
            for (Channel channel : this.serverChannels.values()) {
                if (channelMatcher.matches(channel)) {
                    linkedHashMap.put(channel, channel.disconnect());
                }
            }
            for (Channel channel2 : this.nonServerChannels.values()) {
                if (channelMatcher.matches(channel2)) {
                    linkedHashMap.put(channel2, channel2.disconnect());
                }
            }
            return new DefaultChannelGroupFuture((ChannelGroup) this, (Map<Channel, ChannelFuture>) linkedHashMap, this.executor);
        }
        throw new NullPointerException("matcher");
    }

    public ChannelGroupFuture close(ChannelMatcher channelMatcher) {
        if (channelMatcher != null) {
            LinkedHashMap linkedHashMap = new LinkedHashMap(size());
            if (this.stayClosed) {
                this.closed = true;
            }
            for (Channel channel : this.serverChannels.values()) {
                if (channelMatcher.matches(channel)) {
                    linkedHashMap.put(channel, channel.close());
                }
            }
            for (Channel channel2 : this.nonServerChannels.values()) {
                if (channelMatcher.matches(channel2)) {
                    linkedHashMap.put(channel2, channel2.close());
                }
            }
            return new DefaultChannelGroupFuture((ChannelGroup) this, (Map<Channel, ChannelFuture>) linkedHashMap, this.executor);
        }
        throw new NullPointerException("matcher");
    }

    public ChannelGroupFuture deregister(ChannelMatcher channelMatcher) {
        if (channelMatcher != null) {
            LinkedHashMap linkedHashMap = new LinkedHashMap(size());
            for (Channel channel : this.serverChannels.values()) {
                if (channelMatcher.matches(channel)) {
                    linkedHashMap.put(channel, channel.deregister());
                }
            }
            for (Channel channel2 : this.nonServerChannels.values()) {
                if (channelMatcher.matches(channel2)) {
                    linkedHashMap.put(channel2, channel2.deregister());
                }
            }
            return new DefaultChannelGroupFuture((ChannelGroup) this, (Map<Channel, ChannelFuture>) linkedHashMap, this.executor);
        }
        throw new NullPointerException("matcher");
    }

    public ChannelGroup flush(ChannelMatcher channelMatcher) {
        for (Channel channel : this.nonServerChannels.values()) {
            if (channelMatcher.matches(channel)) {
                channel.flush();
            }
        }
        return this;
    }

    public ChannelGroupFuture flushAndWrite(Object obj, ChannelMatcher channelMatcher) {
        return writeAndFlush(obj, channelMatcher);
    }

    public ChannelGroupFuture writeAndFlush(Object obj, ChannelMatcher channelMatcher) {
        return writeAndFlush(obj, channelMatcher, false);
    }

    public ChannelGroupFuture writeAndFlush(Object obj, ChannelMatcher channelMatcher, boolean z) {
        ChannelGroupFuture channelGroupFuture;
        if (obj != null) {
            if (z) {
                for (Channel channel : this.nonServerChannels.values()) {
                    if (channelMatcher.matches(channel)) {
                        channel.writeAndFlush(safeDuplicate(obj), channel.voidPromise());
                    }
                }
                channelGroupFuture = this.voidFuture;
            } else {
                LinkedHashMap linkedHashMap = new LinkedHashMap(size());
                for (Channel channel2 : this.nonServerChannels.values()) {
                    if (channelMatcher.matches(channel2)) {
                        linkedHashMap.put(channel2, channel2.writeAndFlush(safeDuplicate(obj)));
                    }
                }
                channelGroupFuture = new DefaultChannelGroupFuture((ChannelGroup) this, (Map<Channel, ChannelFuture>) linkedHashMap, this.executor);
            }
            ReferenceCountUtil.release(obj);
            return channelGroupFuture;
        }
        throw new NullPointerException("message");
    }

    public ChannelGroupFuture newCloseFuture() {
        return newCloseFuture(ChannelMatchers.all());
    }

    public ChannelGroupFuture newCloseFuture(ChannelMatcher channelMatcher) {
        LinkedHashMap linkedHashMap = new LinkedHashMap(size());
        for (Channel channel : this.serverChannels.values()) {
            if (channelMatcher.matches(channel)) {
                linkedHashMap.put(channel, channel.closeFuture());
            }
        }
        for (Channel channel2 : this.nonServerChannels.values()) {
            if (channelMatcher.matches(channel2)) {
                linkedHashMap.put(channel2, channel2.closeFuture());
            }
        }
        return new DefaultChannelGroupFuture((ChannelGroup) this, (Map<Channel, ChannelFuture>) linkedHashMap, this.executor);
    }

    public int hashCode() {
        return System.identityHashCode(this);
    }

    public int compareTo(ChannelGroup channelGroup) {
        int compareTo = name().compareTo(channelGroup.name());
        if (compareTo != 0) {
            return compareTo;
        }
        return System.identityHashCode(this) - System.identityHashCode(channelGroup);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtil.simpleClassName((Object) this));
        sb.append("(name: ");
        sb.append(name());
        sb.append(", size: ");
        sb.append(size());
        sb.append(')');
        return sb.toString();
    }
}
