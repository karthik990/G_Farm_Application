package org.graylog2.gelfclient.transport;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import org.graylog2.gelfclient.GelfConfiguration;
import org.graylog2.gelfclient.GelfMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import p043io.netty.channel.EventLoopGroup;
import p043io.netty.channel.nio.NioEventLoopGroup;
import p043io.netty.util.concurrent.DefaultThreadFactory;

public abstract class AbstractGelfTransport implements GelfTransport {
    /* access modifiers changed from: private */
    public static final Logger LOG = LoggerFactory.getLogger(AbstractGelfTransport.class);
    protected final GelfConfiguration config;
    protected final BlockingQueue<GelfMessage> queue;
    private final EventLoopGroup workerGroup;

    /* access modifiers changed from: protected */
    public abstract void createBootstrap(EventLoopGroup eventLoopGroup);

    public AbstractGelfTransport(GelfConfiguration gelfConfiguration, BlockingQueue<GelfMessage> blockingQueue) {
        this.workerGroup = new NioEventLoopGroup(0, (ThreadFactory) new DefaultThreadFactory(getClass(), true));
        this.config = gelfConfiguration;
        this.queue = blockingQueue;
        createBootstrap(this.workerGroup);
    }

    public AbstractGelfTransport(GelfConfiguration gelfConfiguration) {
        this(gelfConfiguration, new LinkedBlockingQueue(gelfConfiguration.getQueueSize()));
    }

    /* access modifiers changed from: protected */
    public void scheduleReconnect(final EventLoopGroup eventLoopGroup) {
        eventLoopGroup.schedule((Runnable) new Runnable() {
            public void run() {
                AbstractGelfTransport.LOG.debug("Starting reconnect!");
                AbstractGelfTransport.this.createBootstrap(eventLoopGroup);
            }
        }, (long) this.config.getReconnectDelay(), TimeUnit.MILLISECONDS);
    }

    public void send(GelfMessage gelfMessage) throws InterruptedException {
        LOG.debug("Sending message: {}", (Object) gelfMessage.toString());
        this.queue.put(gelfMessage);
    }

    public boolean trySend(GelfMessage gelfMessage) {
        LOG.debug("Trying to send message: {}", (Object) gelfMessage.toString());
        return this.queue.offer(gelfMessage);
    }

    public void stop() {
        this.workerGroup.shutdownGracefully().awaitUninterruptibly();
    }
}
