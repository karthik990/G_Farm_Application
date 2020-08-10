package p043io.grpc.okhttp;

import androidx.core.app.NotificationCompat;
import com.google.common.base.Preconditions;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;
import javax.annotation.Nullable;
import okio.Buffer;
import p043io.grpc.okhttp.internal.framed.FrameWriter;

/* renamed from: io.grpc.okhttp.OutboundFlowController */
class OutboundFlowController {
    /* access modifiers changed from: private */
    public final OutboundFlowState connectionState = new OutboundFlowState(0);
    /* access modifiers changed from: private */
    public final FrameWriter frameWriter;
    /* access modifiers changed from: private */
    public int initialWindowSize = 65535;
    private final OkHttpClientTransport transport;

    /* renamed from: io.grpc.okhttp.OutboundFlowController$OutboundFlowState */
    private final class OutboundFlowState {
        int allocatedBytes;
        final Queue<Frame> pendingWriteQueue;
        int queuedBytes;
        OkHttpClientStream stream;
        final int streamId;
        int window;

        /* renamed from: io.grpc.okhttp.OutboundFlowController$OutboundFlowState$Frame */
        private final class Frame {
            static final /* synthetic */ boolean $assertionsDisabled = false;
            final Buffer data;
            final boolean endStream;
            boolean enqueued;

            static {
                Class<OutboundFlowController> cls = OutboundFlowController.class;
            }

            Frame(Buffer buffer, boolean z) {
                this.data = buffer;
                this.endStream = z;
            }

            /* access modifiers changed from: 0000 */
            public int size() {
                return (int) this.data.size();
            }

            /* access modifiers changed from: 0000 */
            public void enqueue() {
                if (!this.enqueued) {
                    this.enqueued = true;
                    OutboundFlowState.this.pendingWriteQueue.offer(this);
                    OutboundFlowState.this.queuedBytes += size();
                }
            }

            /* access modifiers changed from: 0000 */
            public void write() {
                do {
                    int size = size();
                    int min = Math.min(size, OutboundFlowController.this.frameWriter.maxDataLength());
                    if (min == size) {
                        int i = -size;
                        OutboundFlowController.this.connectionState.incrementStreamWindow(i);
                        OutboundFlowState.this.incrementStreamWindow(i);
                        try {
                            OutboundFlowController.this.frameWriter.data(this.endStream, OutboundFlowState.this.streamId, this.data, size);
                            OutboundFlowState.this.stream.transportState().onSentBytes(size);
                            if (this.enqueued) {
                                OutboundFlowState.this.queuedBytes -= size;
                                OutboundFlowState.this.pendingWriteQueue.remove(this);
                            }
                            return;
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        split(min).write();
                    }
                } while (size() > 0);
            }

            /* access modifiers changed from: 0000 */
            public Frame split(int i) {
                int min = Math.min(i, (int) this.data.size());
                Buffer buffer = new Buffer();
                buffer.write(this.data, (long) min);
                Frame frame = new Frame(buffer, false);
                if (this.enqueued) {
                    OutboundFlowState.this.queuedBytes -= min;
                }
                return frame;
            }
        }

        OutboundFlowState(int i) {
            this.window = OutboundFlowController.this.initialWindowSize;
            this.streamId = i;
            this.pendingWriteQueue = new ArrayDeque(2);
        }

        OutboundFlowState(OutboundFlowController outboundFlowController, OkHttpClientStream okHttpClientStream) {
            this(okHttpClientStream.mo65414id());
            this.stream = okHttpClientStream;
        }

        /* access modifiers changed from: 0000 */
        public int window() {
            return this.window;
        }

        /* access modifiers changed from: 0000 */
        public void allocateBytes(int i) {
            this.allocatedBytes += i;
        }

        /* access modifiers changed from: 0000 */
        public int allocatedBytes() {
            return this.allocatedBytes;
        }

        /* access modifiers changed from: 0000 */
        public int unallocatedBytes() {
            return streamableBytes() - this.allocatedBytes;
        }

        /* access modifiers changed from: 0000 */
        public void clearAllocatedBytes() {
            this.allocatedBytes = 0;
        }

        /* access modifiers changed from: 0000 */
        public int incrementStreamWindow(int i) {
            if (i <= 0 || Integer.MAX_VALUE - i >= this.window) {
                this.window += i;
                return this.window;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Window size overflow for stream: ");
            sb.append(this.streamId);
            throw new IllegalArgumentException(sb.toString());
        }

        /* access modifiers changed from: 0000 */
        public int writableWindow() {
            return Math.min(this.window, OutboundFlowController.this.connectionState.window());
        }

        /* access modifiers changed from: 0000 */
        public int streamableBytes() {
            return Math.max(0, Math.min(this.window, this.queuedBytes));
        }

        /* access modifiers changed from: 0000 */
        public Frame newFrame(Buffer buffer, boolean z) {
            return new Frame(buffer, z);
        }

        /* access modifiers changed from: 0000 */
        public boolean hasFrame() {
            return !this.pendingWriteQueue.isEmpty();
        }

        private Frame peek() {
            return (Frame) this.pendingWriteQueue.peek();
        }

        /* access modifiers changed from: 0000 */
        public int writeBytes(int i, WriteStatus writeStatus) {
            int min = Math.min(i, writableWindow());
            int i2 = 0;
            while (hasFrame()) {
                Frame peek = peek();
                if (min >= peek.size()) {
                    writeStatus.incrementNumWrites();
                    i2 += peek.size();
                    peek.write();
                } else if (min <= 0) {
                    break;
                } else {
                    Frame split = peek.split(min);
                    writeStatus.incrementNumWrites();
                    i2 += split.size();
                    split.write();
                }
                min = Math.min(i - i2, writableWindow());
            }
            return i2;
        }
    }

    /* renamed from: io.grpc.okhttp.OutboundFlowController$WriteStatus */
    private static final class WriteStatus {
        int numWrites;

        private WriteStatus() {
        }

        /* access modifiers changed from: 0000 */
        public void incrementNumWrites() {
            this.numWrites++;
        }

        /* access modifiers changed from: 0000 */
        public boolean hasWritten() {
            return this.numWrites > 0;
        }
    }

    OutboundFlowController(OkHttpClientTransport okHttpClientTransport, FrameWriter frameWriter2) {
        this.transport = (OkHttpClientTransport) Preconditions.checkNotNull(okHttpClientTransport, NotificationCompat.CATEGORY_TRANSPORT);
        this.frameWriter = (FrameWriter) Preconditions.checkNotNull(frameWriter2, "frameWriter");
    }

    /* access modifiers changed from: 0000 */
    public boolean initialOutboundWindowSize(int i) {
        OkHttpClientStream[] activeStreams;
        if (i >= 0) {
            int i2 = i - this.initialWindowSize;
            this.initialWindowSize = i;
            for (OkHttpClientStream okHttpClientStream : this.transport.getActiveStreams()) {
                OutboundFlowState outboundFlowState = (OutboundFlowState) okHttpClientStream.getOutboundFlowState();
                if (outboundFlowState == null) {
                    okHttpClientStream.setOutboundFlowState(new OutboundFlowState(this, okHttpClientStream));
                } else {
                    outboundFlowState.incrementStreamWindow(i2);
                }
            }
            if (i2 > 0) {
                return true;
            }
            return false;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Invalid initial window size: ");
        sb.append(i);
        throw new IllegalArgumentException(sb.toString());
    }

    /* access modifiers changed from: 0000 */
    public int windowUpdate(@Nullable OkHttpClientStream okHttpClientStream, int i) {
        if (okHttpClientStream == null) {
            int incrementStreamWindow = this.connectionState.incrementStreamWindow(i);
            writeStreams();
            return incrementStreamWindow;
        }
        OutboundFlowState state = state(okHttpClientStream);
        int incrementStreamWindow2 = state.incrementStreamWindow(i);
        WriteStatus writeStatus = new WriteStatus();
        state.writeBytes(state.writableWindow(), writeStatus);
        if (writeStatus.hasWritten()) {
            flush();
        }
        return incrementStreamWindow2;
    }

    /* access modifiers changed from: 0000 */
    public void data(boolean z, int i, Buffer buffer, boolean z2) {
        Preconditions.checkNotNull(buffer, Param.SOURCE);
        OkHttpClientStream stream = this.transport.getStream(i);
        if (stream != null) {
            OutboundFlowState state = state(stream);
            int writableWindow = state.writableWindow();
            boolean hasFrame = state.hasFrame();
            Frame newFrame = state.newFrame(buffer, z);
            if (hasFrame || writableWindow < newFrame.size()) {
                newFrame.enqueue();
                if (hasFrame || writableWindow <= 0) {
                    if (z2) {
                        flush();
                    }
                    return;
                }
                newFrame.split(writableWindow).write();
                if (z2) {
                    flush();
                }
                return;
            }
            newFrame.write();
            if (z2) {
                flush();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void flush() {
        try {
            this.frameWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private OutboundFlowState state(OkHttpClientStream okHttpClientStream) {
        OutboundFlowState outboundFlowState = (OutboundFlowState) okHttpClientStream.getOutboundFlowState();
        if (outboundFlowState != null) {
            return outboundFlowState;
        }
        OutboundFlowState outboundFlowState2 = new OutboundFlowState(this, okHttpClientStream);
        okHttpClientStream.setOutboundFlowState(outboundFlowState2);
        return outboundFlowState2;
    }

    /* access modifiers changed from: 0000 */
    public void writeStreams() {
        int i;
        OkHttpClientStream[] activeStreams = this.transport.getActiveStreams();
        int window = this.connectionState.window();
        int length = activeStreams.length;
        while (true) {
            i = 0;
            if (length <= 0 || window <= 0) {
                WriteStatus writeStatus = new WriteStatus();
                OkHttpClientStream[] activeStreams2 = this.transport.getActiveStreams();
                int length2 = activeStreams2.length;
            } else {
                int ceil = (int) Math.ceil((double) (((float) window) / ((float) length)));
                int i2 = 0;
                while (i < length && window > 0) {
                    OkHttpClientStream okHttpClientStream = activeStreams[i];
                    OutboundFlowState state = state(okHttpClientStream);
                    int min = Math.min(window, Math.min(state.unallocatedBytes(), ceil));
                    if (min > 0) {
                        state.allocateBytes(min);
                        window -= min;
                    }
                    if (state.unallocatedBytes() > 0) {
                        int i3 = i2 + 1;
                        activeStreams[i2] = okHttpClientStream;
                        i2 = i3;
                    }
                    i++;
                }
                length = i2;
            }
        }
        WriteStatus writeStatus2 = new WriteStatus();
        OkHttpClientStream[] activeStreams22 = this.transport.getActiveStreams();
        int length22 = activeStreams22.length;
        while (i < length22) {
            OutboundFlowState state2 = state(activeStreams22[i]);
            state2.writeBytes(state2.allocatedBytes(), writeStatus2);
            state2.clearAllocatedBytes();
            i++;
        }
        if (writeStatus2.hasWritten()) {
            flush();
        }
    }
}
