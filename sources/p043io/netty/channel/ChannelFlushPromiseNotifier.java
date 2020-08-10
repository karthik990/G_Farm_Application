package p043io.netty.channel;

import java.util.ArrayDeque;
import java.util.Queue;

/* renamed from: io.netty.channel.ChannelFlushPromiseNotifier */
public final class ChannelFlushPromiseNotifier {
    private final Queue<FlushCheckpoint> flushCheckpoints;
    private final boolean tryNotify;
    private long writeCounter;

    /* renamed from: io.netty.channel.ChannelFlushPromiseNotifier$DefaultFlushCheckpoint */
    private static class DefaultFlushCheckpoint implements FlushCheckpoint {
        private long checkpoint;
        private final ChannelPromise future;

        DefaultFlushCheckpoint(long j, ChannelPromise channelPromise) {
            this.checkpoint = j;
            this.future = channelPromise;
        }

        public long flushCheckpoint() {
            return this.checkpoint;
        }

        public void flushCheckpoint(long j) {
            this.checkpoint = j;
        }

        public ChannelPromise promise() {
            return this.future;
        }
    }

    /* renamed from: io.netty.channel.ChannelFlushPromiseNotifier$FlushCheckpoint */
    interface FlushCheckpoint {
        long flushCheckpoint();

        void flushCheckpoint(long j);

        ChannelPromise promise();
    }

    public ChannelFlushPromiseNotifier(boolean z) {
        this.flushCheckpoints = new ArrayDeque();
        this.tryNotify = z;
    }

    public ChannelFlushPromiseNotifier() {
        this(false);
    }

    @Deprecated
    public ChannelFlushPromiseNotifier add(ChannelPromise channelPromise, int i) {
        return add(channelPromise, (long) i);
    }

    public ChannelFlushPromiseNotifier add(ChannelPromise channelPromise, long j) {
        if (channelPromise == null) {
            throw new NullPointerException("promise");
        } else if (j >= 0) {
            long j2 = this.writeCounter + j;
            if (channelPromise instanceof FlushCheckpoint) {
                FlushCheckpoint flushCheckpoint = (FlushCheckpoint) channelPromise;
                flushCheckpoint.flushCheckpoint(j2);
                this.flushCheckpoints.add(flushCheckpoint);
            } else {
                this.flushCheckpoints.add(new DefaultFlushCheckpoint(j2, channelPromise));
            }
            return this;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("pendingDataSize must be >= 0 but was ");
            sb.append(j);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public ChannelFlushPromiseNotifier increaseWriteCounter(long j) {
        if (j >= 0) {
            this.writeCounter += j;
            return this;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("delta must be >= 0 but was ");
        sb.append(j);
        throw new IllegalArgumentException(sb.toString());
    }

    public long writeCounter() {
        return this.writeCounter;
    }

    public ChannelFlushPromiseNotifier notifyPromises() {
        notifyPromises0(null);
        return this;
    }

    @Deprecated
    public ChannelFlushPromiseNotifier notifyFlushFutures() {
        return notifyPromises();
    }

    public ChannelFlushPromiseNotifier notifyPromises(Throwable th) {
        notifyPromises();
        while (true) {
            FlushCheckpoint flushCheckpoint = (FlushCheckpoint) this.flushCheckpoints.poll();
            if (flushCheckpoint == null) {
                return this;
            }
            if (this.tryNotify) {
                flushCheckpoint.promise().tryFailure(th);
            } else {
                flushCheckpoint.promise().setFailure(th);
            }
        }
    }

    @Deprecated
    public ChannelFlushPromiseNotifier notifyFlushFutures(Throwable th) {
        return notifyPromises(th);
    }

    public ChannelFlushPromiseNotifier notifyPromises(Throwable th, Throwable th2) {
        notifyPromises0(th);
        while (true) {
            FlushCheckpoint flushCheckpoint = (FlushCheckpoint) this.flushCheckpoints.poll();
            if (flushCheckpoint == null) {
                return this;
            }
            if (this.tryNotify) {
                flushCheckpoint.promise().tryFailure(th2);
            } else {
                flushCheckpoint.promise().setFailure(th2);
            }
        }
    }

    @Deprecated
    public ChannelFlushPromiseNotifier notifyFlushFutures(Throwable th, Throwable th2) {
        return notifyPromises(th, th2);
    }

    private void notifyPromises0(Throwable th) {
        if (this.flushCheckpoints.isEmpty()) {
            this.writeCounter = 0;
            return;
        }
        long j = this.writeCounter;
        while (true) {
            FlushCheckpoint flushCheckpoint = (FlushCheckpoint) this.flushCheckpoints.peek();
            if (flushCheckpoint == null) {
                this.writeCounter = 0;
                break;
            } else if (flushCheckpoint.flushCheckpoint() <= j) {
                this.flushCheckpoints.remove();
                ChannelPromise promise = flushCheckpoint.promise();
                if (th == null) {
                    if (this.tryNotify) {
                        promise.trySuccess();
                    } else {
                        promise.setSuccess();
                    }
                } else if (this.tryNotify) {
                    promise.tryFailure(th);
                } else {
                    promise.setFailure(th);
                }
            } else if (j > 0 && this.flushCheckpoints.size() == 1) {
                this.writeCounter = 0;
                flushCheckpoint.flushCheckpoint(flushCheckpoint.flushCheckpoint() - j);
            }
        }
        long j2 = this.writeCounter;
        if (j2 >= 549755813888L) {
            this.writeCounter = 0;
            for (FlushCheckpoint flushCheckpoint2 : this.flushCheckpoints) {
                flushCheckpoint2.flushCheckpoint(flushCheckpoint2.flushCheckpoint() - j2);
            }
        }
    }
}
