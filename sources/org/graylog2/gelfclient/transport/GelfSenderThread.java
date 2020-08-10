package org.graylog2.gelfclient.transport;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import org.graylog2.gelfclient.GelfMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import p043io.netty.channel.Channel;

public class GelfSenderThread {
    /* access modifiers changed from: private */
    public static final Logger LOG = LoggerFactory.getLogger(GelfSenderThread.class);
    /* access modifiers changed from: private */
    public Channel channel;
    /* access modifiers changed from: private */
    public final Condition connectedCond;
    /* access modifiers changed from: private */
    public final AtomicBoolean keepRunning = new AtomicBoolean(true);
    /* access modifiers changed from: private */
    public final ReentrantLock lock;
    /* access modifiers changed from: private */
    public final int maxInflightSends;
    private final Thread senderThread;

    public GelfSenderThread(final BlockingQueue<GelfMessage> blockingQueue, int i) {
        this.maxInflightSends = i;
        this.lock = new ReentrantLock();
        this.connectedCond = this.lock.newCondition();
        if (i > 0) {
            this.senderThread = new Thread(new Runnable() {
                /* JADX WARNING: Can't wrap try/catch for region: R(5:9|10|11|12|(1:41)(2:43|39)) */
                /* JADX WARNING: Code restructure failed: missing block: B:29:0x00a1, code lost:
                    r0 = move-exception;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:30:0x00a2, code lost:
                    org.graylog2.gelfclient.transport.GelfSenderThread.access$100(r8.this$0).unlock();
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:31:0x00ab, code lost:
                    throw r0;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:42:0x0022, code lost:
                    continue;
                 */
                /* JADX WARNING: Failed to process nested try/catch */
                /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0040 */
                /* JADX WARNING: Removed duplicated region for block: B:41:0x004c A[EDGE_INSN: B:41:0x004c->B:14:0x004c ?: BREAK  , SYNTHETIC] */
                /* JADX WARNING: Removed duplicated region for block: B:43:0x0022 A[SYNTHETIC] */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void run() {
                    /*
                        r8 = this;
                        java.util.concurrent.atomic.AtomicInteger r0 = new java.util.concurrent.atomic.AtomicInteger
                        r1 = 0
                        r0.<init>(r1)
                        org.graylog2.gelfclient.transport.GelfSenderThread$1$1 r1 = new org.graylog2.gelfclient.transport.GelfSenderThread$1$1
                        r1.<init>(r0)
                        r2 = 0
                        r3 = r2
                    L_0x000d:
                        org.graylog2.gelfclient.transport.GelfSenderThread r4 = org.graylog2.gelfclient.transport.GelfSenderThread.this
                        java.util.concurrent.atomic.AtomicBoolean r4 = r4.keepRunning
                        boolean r4 = r4.get()
                        if (r4 == 0) goto L_0x00ac
                        org.graylog2.gelfclient.transport.GelfSenderThread r4 = org.graylog2.gelfclient.transport.GelfSenderThread.this
                        java.util.concurrent.locks.ReentrantLock r4 = r4.lock
                        r4.lock()
                    L_0x0022:
                        org.graylog2.gelfclient.transport.GelfSenderThread r4 = org.graylog2.gelfclient.transport.GelfSenderThread.this     // Catch:{ all -> 0x00a1 }
                        io.netty.channel.Channel r4 = r4.channel     // Catch:{ all -> 0x00a1 }
                        if (r4 == 0) goto L_0x0036
                        org.graylog2.gelfclient.transport.GelfSenderThread r4 = org.graylog2.gelfclient.transport.GelfSenderThread.this     // Catch:{ all -> 0x00a1 }
                        io.netty.channel.Channel r4 = r4.channel     // Catch:{ all -> 0x00a1 }
                        boolean r4 = r4.isActive()     // Catch:{ all -> 0x00a1 }
                        if (r4 != 0) goto L_0x004c
                    L_0x0036:
                        org.graylog2.gelfclient.transport.GelfSenderThread r4 = org.graylog2.gelfclient.transport.GelfSenderThread.this     // Catch:{ InterruptedException -> 0x0040 }
                        java.util.concurrent.locks.Condition r4 = r4.connectedCond     // Catch:{ InterruptedException -> 0x0040 }
                        r4.await()     // Catch:{ InterruptedException -> 0x0040 }
                        goto L_0x0022
                    L_0x0040:
                        org.graylog2.gelfclient.transport.GelfSenderThread r4 = org.graylog2.gelfclient.transport.GelfSenderThread.this     // Catch:{ all -> 0x00a1 }
                        java.util.concurrent.atomic.AtomicBoolean r4 = r4.keepRunning     // Catch:{ all -> 0x00a1 }
                        boolean r4 = r4.get()     // Catch:{ all -> 0x00a1 }
                        if (r4 != 0) goto L_0x0022
                    L_0x004c:
                        if (r3 != 0) goto L_0x005b
                        java.util.concurrent.BlockingQueue r4 = r3     // Catch:{ InterruptedException -> 0x0096 }
                        r5 = 100
                        java.util.concurrent.TimeUnit r7 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ InterruptedException -> 0x0096 }
                        java.lang.Object r4 = r4.poll(r5, r7)     // Catch:{ InterruptedException -> 0x0096 }
                        org.graylog2.gelfclient.GelfMessage r4 = (org.graylog2.gelfclient.GelfMessage) r4     // Catch:{ InterruptedException -> 0x0096 }
                        r3 = r4
                    L_0x005b:
                        if (r3 == 0) goto L_0x0096
                        org.graylog2.gelfclient.transport.GelfSenderThread r4 = org.graylog2.gelfclient.transport.GelfSenderThread.this     // Catch:{ InterruptedException -> 0x0096 }
                        io.netty.channel.Channel r4 = r4.channel     // Catch:{ InterruptedException -> 0x0096 }
                        if (r4 == 0) goto L_0x0096
                        org.graylog2.gelfclient.transport.GelfSenderThread r4 = org.graylog2.gelfclient.transport.GelfSenderThread.this     // Catch:{ InterruptedException -> 0x0096 }
                        io.netty.channel.Channel r4 = r4.channel     // Catch:{ InterruptedException -> 0x0096 }
                        boolean r4 = r4.isActive()     // Catch:{ InterruptedException -> 0x0096 }
                        if (r4 == 0) goto L_0x0096
                    L_0x0071:
                        int r4 = r0.get()     // Catch:{ InterruptedException -> 0x0096 }
                        org.graylog2.gelfclient.transport.GelfSenderThread r5 = org.graylog2.gelfclient.transport.GelfSenderThread.this     // Catch:{ InterruptedException -> 0x0096 }
                        int r5 = r5.maxInflightSends     // Catch:{ InterruptedException -> 0x0096 }
                        if (r4 <= r5) goto L_0x0085
                        r4 = 1
                        java.util.concurrent.TimeUnit r6 = java.util.concurrent.TimeUnit.MICROSECONDS     // Catch:{ InterruptedException -> 0x0096 }
                        org.graylog2.gelfclient.util.Uninterruptibles.sleepUninterruptibly(r4, r6)     // Catch:{ InterruptedException -> 0x0096 }
                        goto L_0x0071
                    L_0x0085:
                        r0.incrementAndGet()     // Catch:{ InterruptedException -> 0x0096 }
                        org.graylog2.gelfclient.transport.GelfSenderThread r4 = org.graylog2.gelfclient.transport.GelfSenderThread.this     // Catch:{ InterruptedException -> 0x0096 }
                        io.netty.channel.Channel r4 = r4.channel     // Catch:{ InterruptedException -> 0x0096 }
                        io.netty.channel.ChannelFuture r4 = r4.writeAndFlush(r3)     // Catch:{ InterruptedException -> 0x0096 }
                        r4.addListener(r1)     // Catch:{ InterruptedException -> 0x0096 }
                        r3 = r2
                    L_0x0096:
                        org.graylog2.gelfclient.transport.GelfSenderThread r4 = org.graylog2.gelfclient.transport.GelfSenderThread.this
                        java.util.concurrent.locks.ReentrantLock r4 = r4.lock
                        r4.unlock()
                        goto L_0x000d
                    L_0x00a1:
                        r0 = move-exception
                        org.graylog2.gelfclient.transport.GelfSenderThread r1 = org.graylog2.gelfclient.transport.GelfSenderThread.this
                        java.util.concurrent.locks.ReentrantLock r1 = r1.lock
                        r1.unlock()
                        throw r0
                    L_0x00ac:
                        org.slf4j.Logger r0 = org.graylog2.gelfclient.transport.GelfSenderThread.LOG
                        java.lang.String r1 = "GelfSenderThread exiting!"
                        r0.debug(r1)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: org.graylog2.gelfclient.transport.GelfSenderThread.C61181.run():void");
                }
            });
            this.senderThread.setDaemon(true);
            Thread thread = this.senderThread;
            StringBuilder sb = new StringBuilder();
            sb.append("GelfSenderThread-");
            sb.append(this.senderThread.getId());
            thread.setName(sb.toString());
            return;
        }
        throw new IllegalArgumentException("maxInflightSends must be larger than 0");
    }

    /* JADX INFO: finally extract failed */
    public void start(Channel channel2) {
        this.lock.lock();
        try {
            this.channel = channel2;
            this.connectedCond.signalAll();
            this.lock.unlock();
            this.senderThread.start();
        } catch (Throwable th) {
            this.lock.unlock();
            throw th;
        }
    }

    public void stop() {
        this.keepRunning.set(false);
        this.senderThread.interrupt();
    }
}
