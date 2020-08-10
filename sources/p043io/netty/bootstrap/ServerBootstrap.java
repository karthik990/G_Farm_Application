package p043io.netty.bootstrap;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import p043io.netty.channel.Channel;
import p043io.netty.channel.ChannelConfig;
import p043io.netty.channel.ChannelFuture;
import p043io.netty.channel.ChannelFutureListener;
import p043io.netty.channel.ChannelHandler;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.channel.ChannelInboundHandlerAdapter;
import p043io.netty.channel.ChannelInitializer;
import p043io.netty.channel.ChannelOption;
import p043io.netty.channel.ChannelPipeline;
import p043io.netty.channel.EventLoopGroup;
import p043io.netty.channel.ServerChannel;
import p043io.netty.util.AttributeKey;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.bootstrap.ServerBootstrap */
public class ServerBootstrap extends AbstractBootstrap<ServerBootstrap, ServerChannel> {
    /* access modifiers changed from: private */
    public static final InternalLogger logger = InternalLoggerFactory.getInstance(ServerBootstrap.class);
    private final Map<AttributeKey<?>, Object> childAttrs = new LinkedHashMap();
    private volatile EventLoopGroup childGroup;
    private volatile ChannelHandler childHandler;
    private final Map<ChannelOption<?>, Object> childOptions = new LinkedHashMap();
    /* access modifiers changed from: private */
    public final ServerBootstrapConfig config = new ServerBootstrapConfig(this);

    /* renamed from: io.netty.bootstrap.ServerBootstrap$ServerBootstrapAcceptor */
    private static class ServerBootstrapAcceptor extends ChannelInboundHandlerAdapter {
        private final Entry<AttributeKey<?>, Object>[] childAttrs;
        private final EventLoopGroup childGroup;
        private final ChannelHandler childHandler;
        private final Entry<ChannelOption<?>, Object>[] childOptions;
        private final Runnable enableAutoReadTask;

        ServerBootstrapAcceptor(final Channel channel, EventLoopGroup eventLoopGroup, ChannelHandler channelHandler, Entry<ChannelOption<?>, Object>[] entryArr, Entry<AttributeKey<?>, Object>[] entryArr2) {
            this.childGroup = eventLoopGroup;
            this.childHandler = channelHandler;
            this.childOptions = entryArr;
            this.childAttrs = entryArr2;
            this.enableAutoReadTask = new Runnable() {
                public void run() {
                    channel.config().setAutoRead(true);
                }
            };
        }

        public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) {
            Entry<AttributeKey<?>, Object>[] entryArr;
            final Channel channel = (Channel) obj;
            channel.pipeline().addLast(this.childHandler);
            AbstractBootstrap.setChannelOptions(channel, this.childOptions, ServerBootstrap.logger);
            for (Entry<AttributeKey<?>, Object> entry : this.childAttrs) {
                channel.attr((AttributeKey) entry.getKey()).set(entry.getValue());
            }
            try {
                this.childGroup.register(channel).addListener(new ChannelFutureListener() {
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        if (!channelFuture.isSuccess()) {
                            ServerBootstrapAcceptor.forceClose(channel, channelFuture.cause());
                        }
                    }
                });
            } catch (Throwable th) {
                forceClose(channel, th);
            }
        }

        /* access modifiers changed from: private */
        public static void forceClose(Channel channel, Throwable th) {
            channel.unsafe().closeForcibly();
            ServerBootstrap.logger.warn("Failed to register an accepted channel: {}", channel, th);
        }

        public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) throws Exception {
            ChannelConfig config = channelHandlerContext.channel().config();
            if (config.isAutoRead()) {
                config.setAutoRead(false);
                channelHandlerContext.channel().eventLoop().schedule(this.enableAutoReadTask, 1, TimeUnit.SECONDS);
            }
            channelHandlerContext.fireExceptionCaught(th);
        }
    }

    public ServerBootstrap() {
    }

    private ServerBootstrap(ServerBootstrap serverBootstrap) {
        super(serverBootstrap);
        this.childGroup = serverBootstrap.childGroup;
        this.childHandler = serverBootstrap.childHandler;
        synchronized (serverBootstrap.childOptions) {
            this.childOptions.putAll(serverBootstrap.childOptions);
        }
        synchronized (serverBootstrap.childAttrs) {
            this.childAttrs.putAll(serverBootstrap.childAttrs);
        }
    }

    public ServerBootstrap group(EventLoopGroup eventLoopGroup) {
        return group(eventLoopGroup, eventLoopGroup);
    }

    public ServerBootstrap group(EventLoopGroup eventLoopGroup, EventLoopGroup eventLoopGroup2) {
        super.group(eventLoopGroup);
        if (eventLoopGroup2 == null) {
            throw new NullPointerException("childGroup");
        } else if (this.childGroup == null) {
            this.childGroup = eventLoopGroup2;
            return this;
        } else {
            throw new IllegalStateException("childGroup set already");
        }
    }

    public <T> ServerBootstrap childOption(ChannelOption<T> channelOption, T t) {
        if (channelOption != null) {
            if (t == null) {
                synchronized (this.childOptions) {
                    this.childOptions.remove(channelOption);
                }
            } else {
                synchronized (this.childOptions) {
                    this.childOptions.put(channelOption, t);
                }
            }
            return this;
        }
        throw new NullPointerException("childOption");
    }

    public <T> ServerBootstrap childAttr(AttributeKey<T> attributeKey, T t) {
        if (attributeKey != null) {
            if (t == null) {
                this.childAttrs.remove(attributeKey);
            } else {
                this.childAttrs.put(attributeKey, t);
            }
            return this;
        }
        throw new NullPointerException("childKey");
    }

    public ServerBootstrap childHandler(ChannelHandler channelHandler) {
        if (channelHandler != null) {
            this.childHandler = channelHandler;
            return this;
        }
        throw new NullPointerException("childHandler");
    }

    /* access modifiers changed from: 0000 */
    public void init(Channel channel) throws Exception {
        final Entry[] entryArr;
        final Entry[] entryArr2;
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
        ChannelPipeline pipeline = channel.pipeline();
        final EventLoopGroup eventLoopGroup = this.childGroup;
        final ChannelHandler channelHandler = this.childHandler;
        synchronized (this.childOptions) {
            entryArr = (Entry[]) this.childOptions.entrySet().toArray(newOptionArray(this.childOptions.size()));
        }
        synchronized (this.childAttrs) {
            entryArr2 = (Entry[]) this.childAttrs.entrySet().toArray(newAttrArray(this.childAttrs.size()));
        }
        C55211 r0 = new ChannelInitializer<Channel>() {
            public void initChannel(final Channel channel) throws Exception {
                final ChannelPipeline pipeline = channel.pipeline();
                ChannelHandler handler = ServerBootstrap.this.config.handler();
                if (handler != null) {
                    pipeline.addLast(handler);
                }
                channel.eventLoop().execute(new Runnable() {
                    public void run() {
                        ChannelPipeline channelPipeline = pipeline;
                        ServerBootstrapAcceptor serverBootstrapAcceptor = new ServerBootstrapAcceptor(channel, eventLoopGroup, channelHandler, entryArr, entryArr2);
                        channelPipeline.addLast(serverBootstrapAcceptor);
                    }
                });
            }
        };
        pipeline.addLast(r0);
    }

    public ServerBootstrap validate() {
        super.validate();
        if (this.childHandler != null) {
            if (this.childGroup == null) {
                logger.warn("childGroup is not set. Using parentGroup instead.");
                this.childGroup = this.config.group();
            }
            return this;
        }
        throw new IllegalStateException("childHandler not set");
    }

    private static Entry<AttributeKey<?>, Object>[] newAttrArray(int i) {
        return new Entry[i];
    }

    private static Entry<ChannelOption<?>, Object>[] newOptionArray(int i) {
        return new Entry[i];
    }

    public ServerBootstrap clone() {
        return new ServerBootstrap(this);
    }

    @Deprecated
    public EventLoopGroup childGroup() {
        return this.childGroup;
    }

    /* access modifiers changed from: 0000 */
    public final ChannelHandler childHandler() {
        return this.childHandler;
    }

    /* access modifiers changed from: 0000 */
    public final Map<ChannelOption<?>, Object> childOptions() {
        return copiedMap(this.childOptions);
    }

    /* access modifiers changed from: 0000 */
    public final Map<AttributeKey<?>, Object> childAttrs() {
        return copiedMap(this.childAttrs);
    }

    public final ServerBootstrapConfig config() {
        return this.config;
    }
}
