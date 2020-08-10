package p043io.netty.bootstrap;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import p043io.netty.bootstrap.AbstractBootstrap;
import p043io.netty.channel.Channel;
import p043io.netty.channel.ChannelFactory;
import p043io.netty.channel.ChannelFuture;
import p043io.netty.channel.ChannelFutureListener;
import p043io.netty.channel.ChannelHandler;
import p043io.netty.channel.ChannelOption;
import p043io.netty.channel.ChannelPromise;
import p043io.netty.channel.DefaultChannelPromise;
import p043io.netty.channel.EventLoopGroup;
import p043io.netty.channel.ReflectiveChannelFactory;
import p043io.netty.util.AttributeKey;
import p043io.netty.util.concurrent.EventExecutor;
import p043io.netty.util.concurrent.GlobalEventExecutor;
import p043io.netty.util.internal.SocketUtils;
import p043io.netty.util.internal.StringUtil;
import p043io.netty.util.internal.logging.InternalLogger;

/* renamed from: io.netty.bootstrap.AbstractBootstrap */
public abstract class AbstractBootstrap<B extends AbstractBootstrap<B, C>, C extends Channel> implements Cloneable {
    private final Map<AttributeKey<?>, Object> attrs = new LinkedHashMap();
    private volatile ChannelFactory<? extends C> channelFactory;
    volatile EventLoopGroup group;
    private volatile ChannelHandler handler;
    private volatile SocketAddress localAddress;
    private final Map<ChannelOption<?>, Object> options = new LinkedHashMap();

    /* renamed from: io.netty.bootstrap.AbstractBootstrap$PendingRegistrationPromise */
    static final class PendingRegistrationPromise extends DefaultChannelPromise {
        private volatile boolean registered;

        PendingRegistrationPromise(Channel channel) {
            super(channel);
        }

        /* access modifiers changed from: 0000 */
        public void registered() {
            this.registered = true;
        }

        /* access modifiers changed from: protected */
        public EventExecutor executor() {
            if (this.registered) {
                return super.executor();
            }
            return GlobalEventExecutor.INSTANCE;
        }
    }

    public abstract B clone();

    public abstract AbstractBootstrapConfig<B, C> config();

    /* access modifiers changed from: 0000 */
    public abstract void init(Channel channel) throws Exception;

    AbstractBootstrap() {
    }

    AbstractBootstrap(AbstractBootstrap<B, C> abstractBootstrap) {
        this.group = abstractBootstrap.group;
        this.channelFactory = abstractBootstrap.channelFactory;
        this.handler = abstractBootstrap.handler;
        this.localAddress = abstractBootstrap.localAddress;
        synchronized (abstractBootstrap.options) {
            this.options.putAll(abstractBootstrap.options);
        }
        synchronized (abstractBootstrap.attrs) {
            this.attrs.putAll(abstractBootstrap.attrs);
        }
    }

    public B group(EventLoopGroup eventLoopGroup) {
        if (eventLoopGroup == null) {
            throw new NullPointerException("group");
        } else if (this.group == null) {
            this.group = eventLoopGroup;
            return this;
        } else {
            throw new IllegalStateException("group set already");
        }
    }

    public B channel(Class<? extends C> cls) {
        if (cls != null) {
            return channelFactory((ChannelFactory<? extends C>) new ReflectiveChannelFactory<Object>(cls));
        }
        throw new NullPointerException("channelClass");
    }

    @Deprecated
    public B channelFactory(ChannelFactory<? extends C> channelFactory2) {
        if (channelFactory2 == null) {
            throw new NullPointerException("channelFactory");
        } else if (this.channelFactory == null) {
            this.channelFactory = channelFactory2;
            return this;
        } else {
            throw new IllegalStateException("channelFactory set already");
        }
    }

    public B channelFactory(ChannelFactory<? extends C> channelFactory2) {
        return channelFactory((ChannelFactory<? extends C>) channelFactory2);
    }

    public B localAddress(SocketAddress socketAddress) {
        this.localAddress = socketAddress;
        return this;
    }

    public B localAddress(int i) {
        return localAddress((SocketAddress) new InetSocketAddress(i));
    }

    public B localAddress(String str, int i) {
        return localAddress((SocketAddress) SocketUtils.socketAddress(str, i));
    }

    public B localAddress(InetAddress inetAddress, int i) {
        return localAddress((SocketAddress) new InetSocketAddress(inetAddress, i));
    }

    public <T> B option(ChannelOption<T> channelOption, T t) {
        if (channelOption != null) {
            if (t == null) {
                synchronized (this.options) {
                    this.options.remove(channelOption);
                }
            } else {
                synchronized (this.options) {
                    this.options.put(channelOption, t);
                }
            }
            return this;
        }
        throw new NullPointerException("option");
    }

    public <T> B attr(AttributeKey<T> attributeKey, T t) {
        if (attributeKey != null) {
            if (t == null) {
                synchronized (this.attrs) {
                    this.attrs.remove(attributeKey);
                }
            } else {
                synchronized (this.attrs) {
                    this.attrs.put(attributeKey, t);
                }
            }
            return this;
        }
        throw new NullPointerException("key");
    }

    public B validate() {
        if (this.group == null) {
            throw new IllegalStateException("group not set");
        } else if (this.channelFactory != null) {
            return this;
        } else {
            throw new IllegalStateException("channel or channelFactory not set");
        }
    }

    public ChannelFuture register() {
        validate();
        return initAndRegister();
    }

    public ChannelFuture bind() {
        validate();
        SocketAddress socketAddress = this.localAddress;
        if (socketAddress != null) {
            return doBind(socketAddress);
        }
        throw new IllegalStateException("localAddress not set");
    }

    public ChannelFuture bind(int i) {
        return bind((SocketAddress) new InetSocketAddress(i));
    }

    public ChannelFuture bind(String str, int i) {
        return bind((SocketAddress) SocketUtils.socketAddress(str, i));
    }

    public ChannelFuture bind(InetAddress inetAddress, int i) {
        return bind((SocketAddress) new InetSocketAddress(inetAddress, i));
    }

    public ChannelFuture bind(SocketAddress socketAddress) {
        validate();
        if (socketAddress != null) {
            return doBind(socketAddress);
        }
        throw new NullPointerException("localAddress");
    }

    private ChannelFuture doBind(SocketAddress socketAddress) {
        ChannelFuture initAndRegister = initAndRegister();
        final Channel channel = initAndRegister.channel();
        if (initAndRegister.cause() != null) {
            return initAndRegister;
        }
        if (initAndRegister.isDone()) {
            ChannelPromise newPromise = channel.newPromise();
            doBind0(initAndRegister, channel, socketAddress, newPromise);
            return newPromise;
        }
        PendingRegistrationPromise pendingRegistrationPromise = new PendingRegistrationPromise(channel);
        final PendingRegistrationPromise pendingRegistrationPromise2 = pendingRegistrationPromise;
        final ChannelFuture channelFuture = initAndRegister;
        final SocketAddress socketAddress2 = socketAddress;
        C55161 r0 = new ChannelFutureListener() {
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                Throwable cause = channelFuture.cause();
                if (cause != null) {
                    pendingRegistrationPromise2.setFailure(cause);
                    return;
                }
                pendingRegistrationPromise2.registered();
                AbstractBootstrap.doBind0(channelFuture, channel, socketAddress2, pendingRegistrationPromise2);
            }
        };
        initAndRegister.addListener(r0);
        return pendingRegistrationPromise;
    }

    /* access modifiers changed from: 0000 */
    public final ChannelFuture initAndRegister() {
        Channel channel = null;
        try {
            channel = this.channelFactory.newChannel();
            init(channel);
            ChannelFuture register = config().group().register(channel);
            if (register.cause() != null) {
                if (channel.isRegistered()) {
                    channel.close();
                } else {
                    channel.unsafe().closeForcibly();
                }
            }
            return register;
        } catch (Throwable th) {
            if (channel != null) {
                channel.unsafe().closeForcibly();
            }
            return new DefaultChannelPromise(channel, GlobalEventExecutor.INSTANCE).setFailure(th);
        }
    }

    /* access modifiers changed from: private */
    public static void doBind0(final ChannelFuture channelFuture, final Channel channel, final SocketAddress socketAddress, final ChannelPromise channelPromise) {
        channel.eventLoop().execute(new Runnable() {
            public void run() {
                if (channelFuture.isSuccess()) {
                    channel.bind(socketAddress, channelPromise).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                } else {
                    channelPromise.setFailure(channelFuture.cause());
                }
            }
        });
    }

    public B handler(ChannelHandler channelHandler) {
        if (channelHandler != null) {
            this.handler = channelHandler;
            return this;
        }
        throw new NullPointerException("handler");
    }

    @Deprecated
    public final EventLoopGroup group() {
        return this.group;
    }

    static <K, V> Map<K, V> copiedMap(Map<K, V> map) {
        synchronized (map) {
            if (map.isEmpty()) {
                Map<K, V> emptyMap = Collections.emptyMap();
                return emptyMap;
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap(map);
            return Collections.unmodifiableMap(linkedHashMap);
        }
    }

    /* access modifiers changed from: 0000 */
    public final Map<ChannelOption<?>, Object> options0() {
        return this.options;
    }

    /* access modifiers changed from: 0000 */
    public final Map<AttributeKey<?>, Object> attrs0() {
        return this.attrs;
    }

    /* access modifiers changed from: 0000 */
    public final SocketAddress localAddress() {
        return this.localAddress;
    }

    /* access modifiers changed from: 0000 */
    public final ChannelFactory<? extends C> channelFactory() {
        return this.channelFactory;
    }

    /* access modifiers changed from: 0000 */
    public final ChannelHandler handler() {
        return this.handler;
    }

    /* access modifiers changed from: 0000 */
    public final Map<ChannelOption<?>, Object> options() {
        return copiedMap(this.options);
    }

    /* access modifiers changed from: 0000 */
    public final Map<AttributeKey<?>, Object> attrs() {
        return copiedMap(this.attrs);
    }

    static void setChannelOptions(Channel channel, Map<ChannelOption<?>, Object> map, InternalLogger internalLogger) {
        for (Entry entry : map.entrySet()) {
            setChannelOption(channel, (ChannelOption) entry.getKey(), entry.getValue(), internalLogger);
        }
    }

    static void setChannelOptions(Channel channel, Entry<ChannelOption<?>, Object>[] entryArr, InternalLogger internalLogger) {
        for (Entry<ChannelOption<?>, Object> entry : entryArr) {
            setChannelOption(channel, (ChannelOption) entry.getKey(), entry.getValue(), internalLogger);
        }
    }

    private static void setChannelOption(Channel channel, ChannelOption<?> channelOption, Object obj, InternalLogger internalLogger) {
        try {
            if (!channel.config().setOption(channelOption, obj)) {
                internalLogger.warn("Unknown channel option '{}' for channel '{}'", channelOption, channel);
            }
        } catch (Throwable th) {
            internalLogger.warn("Failed to set channel option '{}' with value '{}' for channel '{}'", channelOption, obj, channel, th);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtil.simpleClassName((Object) this));
        sb.append('(');
        sb.append(config());
        sb.append(')');
        return sb.toString();
    }
}
