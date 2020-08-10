package p043io.grpc.okhttp;

import com.google.common.base.Preconditions;
import com.google.common.p049io.BaseEncoding;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import okio.Buffer;
import p043io.grpc.Attributes;
import p043io.grpc.Metadata;
import p043io.grpc.MethodDescriptor;
import p043io.grpc.MethodDescriptor.MethodType;
import p043io.grpc.Status;
import p043io.grpc.internal.AbstractClientStream;
import p043io.grpc.internal.ClientStreamListener.RpcProgress;
import p043io.grpc.internal.Http2ClientStreamTransportState;
import p043io.grpc.internal.StatsTraceContext;
import p043io.grpc.internal.TransportTracer;
import p043io.grpc.internal.WritableBuffer;
import p043io.grpc.okhttp.internal.framed.ErrorCode;
import p043io.grpc.okhttp.internal.framed.Header;

/* renamed from: io.grpc.okhttp.OkHttpClientStream */
class OkHttpClientStream extends AbstractClientStream {
    public static final int ABSENT_ID = -1;
    /* access modifiers changed from: private */
    public static final Buffer EMPTY_BUFFER = new Buffer();
    private static final int WINDOW_UPDATE_THRESHOLD = 32767;
    private final Attributes attributes;
    /* access modifiers changed from: private */
    public String authority;
    /* access modifiers changed from: private */

    /* renamed from: id */
    public volatile int f3696id = -1;
    /* access modifiers changed from: private */
    public final MethodDescriptor<?, ?> method;
    private Object outboundFlowState;
    private final Sink sink = new Sink();
    /* access modifiers changed from: private */
    public final TransportState state;
    /* access modifiers changed from: private */
    public final StatsTraceContext statsTraceCtx;
    /* access modifiers changed from: private */
    public boolean useGet = false;
    /* access modifiers changed from: private */
    public final String userAgent;

    /* renamed from: io.grpc.okhttp.OkHttpClientStream$PendingData */
    private static class PendingData {
        Buffer buffer;
        boolean endOfStream;
        boolean flush;

        PendingData(Buffer buffer2, boolean z, boolean z2) {
            this.buffer = buffer2;
            this.endOfStream = z;
            this.flush = z2;
        }
    }

    /* renamed from: io.grpc.okhttp.OkHttpClientStream$Sink */
    class Sink implements Sink {
        Sink() {
        }

        public void writeHeaders(Metadata metadata, byte[] bArr) {
            StringBuilder sb = new StringBuilder();
            sb.append("/");
            sb.append(OkHttpClientStream.this.method.getFullMethodName());
            String sb2 = sb.toString();
            if (bArr != null) {
                OkHttpClientStream.this.useGet = true;
                StringBuilder sb3 = new StringBuilder();
                sb3.append(sb2);
                sb3.append("?");
                sb3.append(BaseEncoding.base64().encode(bArr));
                sb2 = sb3.toString();
            }
            synchronized (OkHttpClientStream.this.state.lock) {
                OkHttpClientStream.this.state.streamReady(metadata, sb2);
            }
        }

        public void writeFrame(WritableBuffer writableBuffer, boolean z, boolean z2, int i) {
            Buffer buffer;
            if (writableBuffer == null) {
                buffer = OkHttpClientStream.EMPTY_BUFFER;
            } else {
                buffer = ((OkHttpWritableBuffer) writableBuffer).buffer();
                int size = (int) buffer.size();
                if (size > 0) {
                    OkHttpClientStream.this.onSendingBytes(size);
                }
            }
            synchronized (OkHttpClientStream.this.state.lock) {
                OkHttpClientStream.this.state.sendBuffer(buffer, z, z2);
                OkHttpClientStream.this.getTransportTracer().reportMessageSent(i);
            }
        }

        public void request(int i) {
            synchronized (OkHttpClientStream.this.state.lock) {
                OkHttpClientStream.this.state.requestMessagesFromDeframer(i);
            }
        }

        public void cancel(Status status) {
            synchronized (OkHttpClientStream.this.state.lock) {
                OkHttpClientStream.this.state.cancel(status, true, null);
            }
        }
    }

    /* renamed from: io.grpc.okhttp.OkHttpClientStream$TransportState */
    class TransportState extends Http2ClientStreamTransportState {
        private boolean cancelSent = false;
        private final AsyncFrameWriter frameWriter;
        /* access modifiers changed from: private */
        public final Object lock;
        private final OutboundFlowController outboundFlow;
        private Queue<PendingData> pendingData = new ArrayDeque();
        private int processedWindow = 65535;
        private List<Header> requestHeaders;
        private final OkHttpClientTransport transport;
        private int window = 65535;

        public TransportState(int i, StatsTraceContext statsTraceContext, Object obj, AsyncFrameWriter asyncFrameWriter, OutboundFlowController outboundFlowController, OkHttpClientTransport okHttpClientTransport) {
            super(i, statsTraceContext, OkHttpClientStream.this.getTransportTracer());
            this.lock = Preconditions.checkNotNull(obj, "lock");
            this.frameWriter = asyncFrameWriter;
            this.outboundFlow = outboundFlowController;
            this.transport = okHttpClientTransport;
        }

        public void start(int i) {
            Preconditions.checkState(OkHttpClientStream.this.f3696id == -1, "the stream has been started with id %s", i);
            OkHttpClientStream.this.f3696id = i;
            OkHttpClientStream.this.state.onStreamAllocated();
            if (this.pendingData != null) {
                this.frameWriter.synStream(OkHttpClientStream.this.useGet, false, OkHttpClientStream.this.f3696id, 0, this.requestHeaders);
                OkHttpClientStream.this.statsTraceCtx.clientOutboundHeaders();
                this.requestHeaders = null;
                boolean z = false;
                while (!this.pendingData.isEmpty()) {
                    PendingData pendingData2 = (PendingData) this.pendingData.poll();
                    this.outboundFlow.data(pendingData2.endOfStream, OkHttpClientStream.this.f3696id, pendingData2.buffer, false);
                    if (pendingData2.flush) {
                        z = true;
                    }
                }
                if (z) {
                    this.outboundFlow.flush();
                }
                this.pendingData = null;
            }
        }

        /* access modifiers changed from: protected */
        public void onStreamAllocated() {
            super.onStreamAllocated();
            getTransportTracer().reportLocalStreamStarted();
        }

        /* access modifiers changed from: protected */
        public void http2ProcessingFailed(Status status, boolean z, Metadata metadata) {
            cancel(status, z, metadata);
        }

        public void deframeFailed(Throwable th) {
            http2ProcessingFailed(Status.fromThrowable(th), true, new Metadata());
        }

        public void bytesRead(int i) {
            this.processedWindow -= i;
            int i2 = this.processedWindow;
            if (i2 <= OkHttpClientStream.WINDOW_UPDATE_THRESHOLD) {
                int i3 = 65535 - i2;
                this.window += i3;
                this.processedWindow = i2 + i3;
                this.frameWriter.windowUpdate(OkHttpClientStream.this.mo65414id(), (long) i3);
            }
        }

        public void deframerClosed(boolean z) {
            onEndOfStream();
            super.deframerClosed(z);
        }

        public void runOnTransportThread(Runnable runnable) {
            synchronized (this.lock) {
                runnable.run();
            }
        }

        public void transportHeadersReceived(List<Header> list, boolean z) {
            if (z) {
                transportTrailersReceived(C5498Utils.convertTrailers(list));
            } else {
                transportHeadersReceived(C5498Utils.convertHeaders(list));
            }
        }

        public void transportDataReceived(Buffer buffer, boolean z) {
            this.window -= (int) buffer.size();
            if (this.window < 0) {
                this.frameWriter.rstStream(OkHttpClientStream.this.mo65414id(), ErrorCode.FLOW_CONTROL_ERROR);
                this.transport.finishStream(OkHttpClientStream.this.mo65414id(), Status.INTERNAL.withDescription("Received data size exceeded our receiving window size"), RpcProgress.PROCESSED, false, null, null);
                return;
            }
            super.transportDataReceived(new OkHttpReadableBuffer(buffer), z);
        }

        private void onEndOfStream() {
            if (!isOutboundClosed()) {
                this.transport.finishStream(OkHttpClientStream.this.mo65414id(), null, RpcProgress.PROCESSED, false, ErrorCode.CANCEL, null);
            } else {
                this.transport.finishStream(OkHttpClientStream.this.mo65414id(), null, RpcProgress.PROCESSED, false, null, null);
            }
        }

        /* access modifiers changed from: private */
        public void cancel(Status status, boolean z, Metadata metadata) {
            if (!this.cancelSent) {
                this.cancelSent = true;
                if (this.pendingData != null) {
                    this.transport.removePendingStream(OkHttpClientStream.this);
                    this.requestHeaders = null;
                    for (PendingData pendingData2 : this.pendingData) {
                        pendingData2.buffer.clear();
                    }
                    this.pendingData = null;
                    if (metadata == null) {
                        metadata = new Metadata();
                    }
                    transportReportStatus(status, true, metadata);
                } else {
                    this.transport.finishStream(OkHttpClientStream.this.mo65414id(), status, RpcProgress.PROCESSED, z, ErrorCode.CANCEL, metadata);
                }
            }
        }

        /* access modifiers changed from: private */
        public void sendBuffer(Buffer buffer, boolean z, boolean z2) {
            if (!this.cancelSent) {
                Queue<PendingData> queue = this.pendingData;
                if (queue != null) {
                    queue.add(new PendingData(buffer, z, z2));
                } else {
                    Preconditions.checkState(OkHttpClientStream.this.mo65414id() != -1, "streamId should be set");
                    this.outboundFlow.data(z, OkHttpClientStream.this.mo65414id(), buffer, z2);
                }
            }
        }

        /* access modifiers changed from: private */
        public void streamReady(Metadata metadata, String str) {
            this.requestHeaders = Headers.createRequestHeaders(metadata, str, OkHttpClientStream.this.authority, OkHttpClientStream.this.userAgent, OkHttpClientStream.this.useGet);
            this.transport.streamReadyToStart(OkHttpClientStream.this);
        }
    }

    OkHttpClientStream(MethodDescriptor<?, ?> methodDescriptor, Metadata metadata, AsyncFrameWriter asyncFrameWriter, OkHttpClientTransport okHttpClientTransport, OutboundFlowController outboundFlowController, Object obj, int i, String str, String str2, StatsTraceContext statsTraceContext, TransportTracer transportTracer) {
        super(new OkHttpWritableBufferAllocator(), statsTraceContext, transportTracer, metadata, methodDescriptor.isSafe());
        StatsTraceContext statsTraceContext2 = statsTraceContext;
        this.statsTraceCtx = (StatsTraceContext) Preconditions.checkNotNull(statsTraceContext2, "statsTraceCtx");
        this.method = methodDescriptor;
        this.authority = str;
        this.userAgent = str2;
        this.attributes = okHttpClientTransport.getAttributes();
        TransportState transportState = new TransportState(i, statsTraceContext2, obj, asyncFrameWriter, outboundFlowController, okHttpClientTransport);
        this.state = transportState;
    }

    /* access modifiers changed from: protected */
    public TransportState transportState() {
        return this.state;
    }

    /* access modifiers changed from: protected */
    public Sink abstractClientStreamSink() {
        return this.sink;
    }

    public MethodType getType() {
        return this.method.getType();
    }

    /* renamed from: id */
    public int mo65414id() {
        return this.f3696id;
    }

    /* access modifiers changed from: 0000 */
    public boolean useGet() {
        return this.useGet;
    }

    public void setAuthority(String str) {
        this.authority = (String) Preconditions.checkNotNull(str, "authority");
    }

    public Attributes getAttributes() {
        return this.attributes;
    }

    /* access modifiers changed from: 0000 */
    public void setOutboundFlowState(Object obj) {
        this.outboundFlowState = obj;
    }

    /* access modifiers changed from: 0000 */
    public Object getOutboundFlowState() {
        return this.outboundFlowState;
    }
}
