package p043io.netty.channel.local;

import java.net.SocketAddress;
import java.util.ArrayDeque;
import java.util.Queue;
import p043io.netty.channel.AbstractServerChannel;
import p043io.netty.channel.ChannelConfig;
import p043io.netty.channel.ChannelPipeline;
import p043io.netty.channel.DefaultChannelConfig;
import p043io.netty.channel.EventLoop;
import p043io.netty.channel.SingleThreadEventLoop;
import p043io.netty.util.concurrent.SingleThreadEventExecutor;

/* renamed from: io.netty.channel.local.LocalServerChannel */
public class LocalServerChannel extends AbstractServerChannel {
    private volatile boolean acceptInProgress;
    private final ChannelConfig config = new DefaultChannelConfig(this);
    private final Queue<Object> inboundBuffer = new ArrayDeque();
    private volatile LocalAddress localAddress;
    private final Runnable shutdownHook = new Runnable() {
        public void run() {
            LocalServerChannel.this.unsafe().close(LocalServerChannel.this.unsafe().voidPromise());
        }
    };
    private volatile int state;

    public LocalServerChannel() {
        config().setAllocator(new PreferHeapByteBufAllocator(this.config.getAllocator()));
    }

    public ChannelConfig config() {
        return this.config;
    }

    public LocalAddress localAddress() {
        return (LocalAddress) super.localAddress();
    }

    public LocalAddress remoteAddress() {
        return (LocalAddress) super.remoteAddress();
    }

    public boolean isOpen() {
        return this.state < 2;
    }

    public boolean isActive() {
        return this.state == 1;
    }

    /* access modifiers changed from: protected */
    public boolean isCompatible(EventLoop eventLoop) {
        return eventLoop instanceof SingleThreadEventLoop;
    }

    /* access modifiers changed from: protected */
    public SocketAddress localAddress0() {
        return this.localAddress;
    }

    /* access modifiers changed from: protected */
    public void doRegister() throws Exception {
        ((SingleThreadEventExecutor) eventLoop()).addShutdownHook(this.shutdownHook);
    }

    /* access modifiers changed from: protected */
    public void doBind(SocketAddress socketAddress) throws Exception {
        this.localAddress = LocalChannelRegistry.register(this, this.localAddress, socketAddress);
        this.state = 1;
    }

    /* access modifiers changed from: protected */
    public void doClose() throws Exception {
        if (this.state <= 1) {
            if (this.localAddress != null) {
                LocalChannelRegistry.unregister(this.localAddress);
                this.localAddress = null;
            }
            this.state = 2;
        }
    }

    /* access modifiers changed from: protected */
    public void doDeregister() throws Exception {
        ((SingleThreadEventExecutor) eventLoop()).removeShutdownHook(this.shutdownHook);
    }

    /* access modifiers changed from: protected */
    public void doBeginRead() throws Exception {
        if (!this.acceptInProgress) {
            Queue<Object> queue = this.inboundBuffer;
            if (queue.isEmpty()) {
                this.acceptInProgress = true;
                return;
            }
            ChannelPipeline pipeline = pipeline();
            while (true) {
                Object poll = queue.poll();
                if (poll == null) {
                    pipeline.fireChannelReadComplete();
                    return;
                }
                pipeline.fireChannelRead(poll);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public LocalChannel serve(LocalChannel localChannel) {
        final LocalChannel newLocalChannel = newLocalChannel(localChannel);
        if (eventLoop().inEventLoop()) {
            serve0(newLocalChannel);
        } else {
            eventLoop().execute(new Runnable() {
                public void run() {
                    LocalServerChannel.this.serve0(newLocalChannel);
                }
            });
        }
        return newLocalChannel;
    }

    /* access modifiers changed from: protected */
    public LocalChannel newLocalChannel(LocalChannel localChannel) {
        return new LocalChannel(this, localChannel);
    }

    /* access modifiers changed from: private */
    public void serve0(LocalChannel localChannel) {
        this.inboundBuffer.add(localChannel);
        if (this.acceptInProgress) {
            this.acceptInProgress = false;
            ChannelPipeline pipeline = pipeline();
            while (true) {
                Object poll = this.inboundBuffer.poll();
                if (poll == null) {
                    pipeline.fireChannelReadComplete();
                    return;
                }
                pipeline.fireChannelRead(poll);
            }
        }
    }
}
