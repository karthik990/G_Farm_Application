package p043io.netty.handler.timeout;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import p043io.netty.channel.ChannelFuture;
import p043io.netty.channel.ChannelFutureListener;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.channel.ChannelOutboundHandlerAdapter;
import p043io.netty.channel.ChannelPromise;

/* renamed from: io.netty.handler.timeout.WriteTimeoutHandler */
public class WriteTimeoutHandler extends ChannelOutboundHandlerAdapter {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long MIN_TIMEOUT_NANOS = TimeUnit.MILLISECONDS.toNanos(1);
    private boolean closed;
    private WriteTimeoutTask lastTask;
    private final long timeoutNanos;

    /* renamed from: io.netty.handler.timeout.WriteTimeoutHandler$WriteTimeoutTask */
    private final class WriteTimeoutTask implements Runnable, ChannelFutureListener {
        private final ChannelHandlerContext ctx;
        WriteTimeoutTask next;
        WriteTimeoutTask prev;
        private final ChannelPromise promise;
        ScheduledFuture<?> scheduledFuture;

        WriteTimeoutTask(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) {
            this.ctx = channelHandlerContext;
            this.promise = channelPromise;
        }

        public void run() {
            if (!this.promise.isDone()) {
                try {
                    WriteTimeoutHandler.this.writeTimedOut(this.ctx);
                } catch (Throwable th) {
                    this.ctx.fireExceptionCaught(th);
                }
            }
            WriteTimeoutHandler.this.removeWriteTimeoutTask(this);
        }

        public void operationComplete(ChannelFuture channelFuture) throws Exception {
            this.scheduledFuture.cancel(false);
            WriteTimeoutHandler.this.removeWriteTimeoutTask(this);
        }
    }

    public WriteTimeoutHandler(int i) {
        this((long) i, TimeUnit.SECONDS);
    }

    public WriteTimeoutHandler(long j, TimeUnit timeUnit) {
        if (timeUnit == null) {
            throw new NullPointerException("unit");
        } else if (j <= 0) {
            this.timeoutNanos = 0;
        } else {
            this.timeoutNanos = Math.max(timeUnit.toNanos(j), MIN_TIMEOUT_NANOS);
        }
    }

    public void write(ChannelHandlerContext channelHandlerContext, Object obj, ChannelPromise channelPromise) throws Exception {
        if (this.timeoutNanos > 0) {
            channelPromise = channelPromise.unvoid();
            scheduleTimeout(channelHandlerContext, channelPromise);
        }
        channelHandlerContext.write(obj, channelPromise);
    }

    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        WriteTimeoutTask writeTimeoutTask = this.lastTask;
        this.lastTask = null;
        while (writeTimeoutTask != null) {
            writeTimeoutTask.scheduledFuture.cancel(false);
            WriteTimeoutTask writeTimeoutTask2 = writeTimeoutTask.prev;
            writeTimeoutTask.prev = null;
            writeTimeoutTask.next = null;
            writeTimeoutTask = writeTimeoutTask2;
        }
    }

    private void scheduleTimeout(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) {
        WriteTimeoutTask writeTimeoutTask = new WriteTimeoutTask(channelHandlerContext, channelPromise);
        writeTimeoutTask.scheduledFuture = channelHandlerContext.executor().schedule((Runnable) writeTimeoutTask, this.timeoutNanos, TimeUnit.NANOSECONDS);
        if (!writeTimeoutTask.scheduledFuture.isDone()) {
            addWriteTimeoutTask(writeTimeoutTask);
            channelPromise.addListener(writeTimeoutTask);
        }
    }

    private void addWriteTimeoutTask(WriteTimeoutTask writeTimeoutTask) {
        WriteTimeoutTask writeTimeoutTask2 = this.lastTask;
        if (writeTimeoutTask2 == null) {
            this.lastTask = writeTimeoutTask;
            return;
        }
        writeTimeoutTask2.next = writeTimeoutTask;
        writeTimeoutTask.prev = writeTimeoutTask2;
        this.lastTask = writeTimeoutTask;
    }

    /* access modifiers changed from: private */
    public void removeWriteTimeoutTask(WriteTimeoutTask writeTimeoutTask) {
        WriteTimeoutTask writeTimeoutTask2 = this.lastTask;
        if (writeTimeoutTask == writeTimeoutTask2) {
            this.lastTask = writeTimeoutTask2.prev;
            WriteTimeoutTask writeTimeoutTask3 = this.lastTask;
            if (writeTimeoutTask3 != null) {
                writeTimeoutTask3.next = null;
            }
        } else if (writeTimeoutTask.prev != null || writeTimeoutTask.next != null) {
            if (writeTimeoutTask.prev == null) {
                writeTimeoutTask.next.prev = null;
            } else {
                writeTimeoutTask.prev.next = writeTimeoutTask.next;
                writeTimeoutTask.next.prev = writeTimeoutTask.prev;
            }
        } else {
            return;
        }
        writeTimeoutTask.prev = null;
        writeTimeoutTask.next = null;
    }

    /* access modifiers changed from: protected */
    public void writeTimedOut(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!this.closed) {
            channelHandlerContext.fireExceptionCaught(WriteTimeoutException.INSTANCE);
            channelHandlerContext.close();
            this.closed = true;
        }
    }
}
