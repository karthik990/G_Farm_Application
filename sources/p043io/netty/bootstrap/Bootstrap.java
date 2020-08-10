package p043io.netty.bootstrap;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Map;
import java.util.Map.Entry;
import p043io.netty.channel.Channel;
import p043io.netty.channel.ChannelFuture;
import p043io.netty.channel.ChannelFutureListener;
import p043io.netty.channel.ChannelPromise;
import p043io.netty.channel.EventLoopGroup;
import p043io.netty.resolver.AddressResolver;
import p043io.netty.resolver.AddressResolverGroup;
import p043io.netty.resolver.DefaultAddressResolverGroup;
import p043io.netty.util.AttributeKey;
import p043io.netty.util.concurrent.Future;
import p043io.netty.util.concurrent.FutureListener;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.bootstrap.Bootstrap */
public class Bootstrap extends AbstractBootstrap<Bootstrap, Channel> {
    private static final AddressResolverGroup<?> DEFAULT_RESOLVER = DefaultAddressResolverGroup.INSTANCE;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(Bootstrap.class);
    private final BootstrapConfig config = new BootstrapConfig(this);
    private volatile SocketAddress remoteAddress;
    private volatile AddressResolverGroup<SocketAddress> resolver = DEFAULT_RESOLVER;

    public Bootstrap() {
    }

    private Bootstrap(Bootstrap bootstrap) {
        super(bootstrap);
        this.resolver = bootstrap.resolver;
        this.remoteAddress = bootstrap.remoteAddress;
    }

    public Bootstrap resolver(AddressResolverGroup<?> addressResolverGroup) {
        if (addressResolverGroup == null) {
            addressResolverGroup = DEFAULT_RESOLVER;
        }
        this.resolver = addressResolverGroup;
        return this;
    }

    public Bootstrap remoteAddress(SocketAddress socketAddress) {
        this.remoteAddress = socketAddress;
        return this;
    }

    public Bootstrap remoteAddress(String str, int i) {
        this.remoteAddress = InetSocketAddress.createUnresolved(str, i);
        return this;
    }

    public Bootstrap remoteAddress(InetAddress inetAddress, int i) {
        this.remoteAddress = new InetSocketAddress(inetAddress, i);
        return this;
    }

    public ChannelFuture connect() {
        validate();
        SocketAddress socketAddress = this.remoteAddress;
        if (socketAddress != null) {
            return doResolveAndConnect(socketAddress, this.config.localAddress());
        }
        throw new IllegalStateException("remoteAddress not set");
    }

    public ChannelFuture connect(String str, int i) {
        return connect(InetSocketAddress.createUnresolved(str, i));
    }

    public ChannelFuture connect(InetAddress inetAddress, int i) {
        return connect(new InetSocketAddress(inetAddress, i));
    }

    public ChannelFuture connect(SocketAddress socketAddress) {
        if (socketAddress != null) {
            validate();
            return doResolveAndConnect(socketAddress, this.config.localAddress());
        }
        throw new NullPointerException("remoteAddress");
    }

    public ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress2) {
        if (socketAddress != null) {
            validate();
            return doResolveAndConnect(socketAddress, socketAddress2);
        }
        throw new NullPointerException("remoteAddress");
    }

    private ChannelFuture doResolveAndConnect(SocketAddress socketAddress, SocketAddress socketAddress2) {
        ChannelFuture initAndRegister = initAndRegister();
        final Channel channel = initAndRegister.channel();
        if (!initAndRegister.isDone()) {
            PendingRegistrationPromise pendingRegistrationPromise = new PendingRegistrationPromise(channel);
            final PendingRegistrationPromise pendingRegistrationPromise2 = pendingRegistrationPromise;
            final SocketAddress socketAddress3 = socketAddress;
            final SocketAddress socketAddress4 = socketAddress2;
            C55181 r1 = new ChannelFutureListener() {
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    Throwable cause = channelFuture.cause();
                    if (cause != null) {
                        pendingRegistrationPromise2.setFailure(cause);
                        return;
                    }
                    pendingRegistrationPromise2.registered();
                    Bootstrap.this.doResolveAndConnect0(channel, socketAddress3, socketAddress4, pendingRegistrationPromise2);
                }
            };
            initAndRegister.addListener(r1);
            return pendingRegistrationPromise;
        } else if (!initAndRegister.isSuccess()) {
            return initAndRegister;
        } else {
            return doResolveAndConnect0(channel, socketAddress, socketAddress2, channel.newPromise());
        }
    }

    /* access modifiers changed from: private */
    public ChannelFuture doResolveAndConnect0(final Channel channel, SocketAddress socketAddress, final SocketAddress socketAddress2, final ChannelPromise channelPromise) {
        try {
            AddressResolver resolver2 = this.resolver.getResolver(channel.eventLoop());
            if (resolver2.isSupported(socketAddress)) {
                if (!resolver2.isResolved(socketAddress)) {
                    Future resolve = resolver2.resolve(socketAddress);
                    if (resolve.isDone()) {
                        Throwable cause = resolve.cause();
                        if (cause != null) {
                            channel.close();
                            channelPromise.setFailure(cause);
                        } else {
                            doConnect((SocketAddress) resolve.getNow(), socketAddress2, channelPromise);
                        }
                        return channelPromise;
                    }
                    resolve.addListener(new FutureListener<SocketAddress>() {
                        public void operationComplete(Future<SocketAddress> future) throws Exception {
                            if (future.cause() != null) {
                                channel.close();
                                channelPromise.setFailure(future.cause());
                                return;
                            }
                            Bootstrap.doConnect((SocketAddress) future.getNow(), socketAddress2, channelPromise);
                        }
                    });
                    return channelPromise;
                }
            }
            doConnect(socketAddress, socketAddress2, channelPromise);
            return channelPromise;
        } catch (Throwable th) {
            channelPromise.tryFailure(th);
        }
    }

    /* access modifiers changed from: private */
    public static void doConnect(final SocketAddress socketAddress, final SocketAddress socketAddress2, final ChannelPromise channelPromise) {
        final Channel channel = channelPromise.channel();
        channel.eventLoop().execute(new Runnable() {
            public void run() {
                SocketAddress socketAddress = socketAddress2;
                if (socketAddress == null) {
                    channel.connect(socketAddress, channelPromise);
                } else {
                    channel.connect(socketAddress, socketAddress, channelPromise);
                }
                channelPromise.addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public void init(Channel channel) throws Exception {
        channel.pipeline().addLast(this.config.handler());
        Map options0 = options0();
        synchronized (options0) {
            setChannelOptions(channel, options0, logger);
        }
        Map attrs0 = attrs0();
        synchronized (attrs0) {
            for (Entry entry : attrs0.entrySet()) {
                channel.attr((AttributeKey) entry.getKey()).set(entry.getValue());
            }
        }
    }

    public Bootstrap validate() {
        super.validate();
        if (this.config.handler() != null) {
            return this;
        }
        throw new IllegalStateException("handler not set");
    }

    public Bootstrap clone() {
        return new Bootstrap(this);
    }

    public Bootstrap clone(EventLoopGroup eventLoopGroup) {
        Bootstrap bootstrap = new Bootstrap(this);
        bootstrap.group = eventLoopGroup;
        return bootstrap;
    }

    public final BootstrapConfig config() {
        return this.config;
    }

    /* access modifiers changed from: 0000 */
    public final SocketAddress remoteAddress() {
        return this.remoteAddress;
    }

    /* access modifiers changed from: 0000 */
    public final AddressResolverGroup<?> resolver() {
        return this.resolver;
    }
}
