package p043io.grpc.stub;

import com.google.common.base.Preconditions;
import p043io.grpc.Metadata;
import p043io.grpc.MethodDescriptor;
import p043io.grpc.ServerCall;
import p043io.grpc.ServerCall.Listener;
import p043io.grpc.ServerCallHandler;
import p043io.grpc.Status;

/* renamed from: io.grpc.stub.ServerCalls */
public final class ServerCalls {
    static final String MISSING_REQUEST = "Half-closed without a request";
    static final String TOO_MANY_REQUESTS = "Too many requests";

    /* renamed from: io.grpc.stub.ServerCalls$BidiStreamingMethod */
    public interface BidiStreamingMethod<ReqT, RespT> extends StreamingRequestMethod<ReqT, RespT> {
    }

    /* renamed from: io.grpc.stub.ServerCalls$ClientStreamingMethod */
    public interface ClientStreamingMethod<ReqT, RespT> extends StreamingRequestMethod<ReqT, RespT> {
    }

    /* renamed from: io.grpc.stub.ServerCalls$NoopStreamObserver */
    static class NoopStreamObserver<V> implements StreamObserver<V> {
        public void onCompleted() {
        }

        public void onError(Throwable th) {
        }

        public void onNext(V v) {
        }

        NoopStreamObserver() {
        }
    }

    /* renamed from: io.grpc.stub.ServerCalls$ServerCallStreamObserverImpl */
    private static final class ServerCallStreamObserverImpl<ReqT, RespT> extends ServerCallStreamObserver<RespT> {
        /* access modifiers changed from: private */
        public boolean autoFlowControlEnabled = true;
        final ServerCall<ReqT, RespT> call;
        volatile boolean cancelled;
        private boolean frozen;
        /* access modifiers changed from: private */
        public Runnable onCancelHandler;
        /* access modifiers changed from: private */
        public Runnable onReadyHandler;
        private boolean sentHeaders;

        ServerCallStreamObserverImpl(ServerCall<ReqT, RespT> serverCall) {
            this.call = serverCall;
        }

        /* access modifiers changed from: private */
        public void freeze() {
            this.frozen = true;
        }

        public void setMessageCompression(boolean z) {
            this.call.setMessageCompression(z);
        }

        public void setCompression(String str) {
            this.call.setCompression(str);
        }

        public void onNext(RespT respt) {
            if (!this.cancelled) {
                if (!this.sentHeaders) {
                    this.call.sendHeaders(new Metadata());
                    this.sentHeaders = true;
                }
                this.call.sendMessage(respt);
                return;
            }
            throw Status.CANCELLED.withDescription("call already cancelled").asRuntimeException();
        }

        public void onError(Throwable th) {
            Metadata trailersFromThrowable = Status.trailersFromThrowable(th);
            if (trailersFromThrowable == null) {
                trailersFromThrowable = new Metadata();
            }
            this.call.close(Status.fromThrowable(th), trailersFromThrowable);
        }

        public void onCompleted() {
            if (!this.cancelled) {
                this.call.close(Status.f3691OK, new Metadata());
                return;
            }
            throw Status.CANCELLED.withDescription("call already cancelled").asRuntimeException();
        }

        public boolean isReady() {
            return this.call.isReady();
        }

        public void setOnReadyHandler(Runnable runnable) {
            Preconditions.checkState(!this.frozen, "Cannot alter onReadyHandler after initialization");
            this.onReadyHandler = runnable;
        }

        public boolean isCancelled() {
            return this.call.isCancelled();
        }

        public void setOnCancelHandler(Runnable runnable) {
            Preconditions.checkState(!this.frozen, "Cannot alter onCancelHandler after initialization");
            this.onCancelHandler = runnable;
        }

        public void disableAutoInboundFlowControl() {
            Preconditions.checkState(!this.frozen, "Cannot disable auto flow control after initialization");
            this.autoFlowControlEnabled = false;
        }

        public void request(int i) {
            this.call.request(i);
        }
    }

    /* renamed from: io.grpc.stub.ServerCalls$ServerStreamingMethod */
    public interface ServerStreamingMethod<ReqT, RespT> extends UnaryRequestMethod<ReqT, RespT> {
    }

    /* renamed from: io.grpc.stub.ServerCalls$StreamingRequestMethod */
    private interface StreamingRequestMethod<ReqT, RespT> {
        StreamObserver<ReqT> invoke(StreamObserver<RespT> streamObserver);
    }

    /* renamed from: io.grpc.stub.ServerCalls$StreamingServerCallHandler */
    private static final class StreamingServerCallHandler<ReqT, RespT> implements ServerCallHandler<ReqT, RespT> {
        private final StreamingRequestMethod<ReqT, RespT> method;

        /* renamed from: io.grpc.stub.ServerCalls$StreamingServerCallHandler$StreamingServerCallListener */
        private final class StreamingServerCallListener extends Listener<ReqT> {
            private final ServerCall<ReqT, RespT> call;
            private boolean halfClosed = false;
            private final StreamObserver<ReqT> requestObserver;
            private final ServerCallStreamObserverImpl<ReqT, RespT> responseObserver;

            StreamingServerCallListener(StreamObserver<ReqT> streamObserver, ServerCallStreamObserverImpl<ReqT, RespT> serverCallStreamObserverImpl, ServerCall<ReqT, RespT> serverCall) {
                this.requestObserver = streamObserver;
                this.responseObserver = serverCallStreamObserverImpl;
                this.call = serverCall;
            }

            public void onMessage(ReqT reqt) {
                this.requestObserver.onNext(reqt);
                if (this.responseObserver.autoFlowControlEnabled) {
                    this.call.request(1);
                }
            }

            public void onHalfClose() {
                this.halfClosed = true;
                this.requestObserver.onCompleted();
            }

            public void onCancel() {
                ServerCallStreamObserverImpl<ReqT, RespT> serverCallStreamObserverImpl = this.responseObserver;
                serverCallStreamObserverImpl.cancelled = true;
                if (serverCallStreamObserverImpl.onCancelHandler != null) {
                    this.responseObserver.onCancelHandler.run();
                }
                if (!this.halfClosed) {
                    this.requestObserver.onError(Status.CANCELLED.withDescription("cancelled before receiving half close").asRuntimeException());
                }
            }

            public void onReady() {
                if (this.responseObserver.onReadyHandler != null) {
                    this.responseObserver.onReadyHandler.run();
                }
            }
        }

        StreamingServerCallHandler(StreamingRequestMethod<ReqT, RespT> streamingRequestMethod) {
            this.method = streamingRequestMethod;
        }

        public Listener<ReqT> startCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata) {
            ServerCallStreamObserverImpl serverCallStreamObserverImpl = new ServerCallStreamObserverImpl(serverCall);
            StreamObserver invoke = this.method.invoke(serverCallStreamObserverImpl);
            serverCallStreamObserverImpl.freeze();
            if (serverCallStreamObserverImpl.autoFlowControlEnabled) {
                serverCall.request(1);
            }
            return new StreamingServerCallListener(invoke, serverCallStreamObserverImpl, serverCall);
        }
    }

    /* renamed from: io.grpc.stub.ServerCalls$UnaryMethod */
    public interface UnaryMethod<ReqT, RespT> extends UnaryRequestMethod<ReqT, RespT> {
    }

    /* renamed from: io.grpc.stub.ServerCalls$UnaryRequestMethod */
    private interface UnaryRequestMethod<ReqT, RespT> {
        void invoke(ReqT reqt, StreamObserver<RespT> streamObserver);
    }

    /* renamed from: io.grpc.stub.ServerCalls$UnaryServerCallHandler */
    private static final class UnaryServerCallHandler<ReqT, RespT> implements ServerCallHandler<ReqT, RespT> {
        /* access modifiers changed from: private */
        public final UnaryRequestMethod<ReqT, RespT> method;

        /* renamed from: io.grpc.stub.ServerCalls$UnaryServerCallHandler$UnaryServerCallListener */
        private final class UnaryServerCallListener extends Listener<ReqT> {
            private final ServerCall<ReqT, RespT> call;
            private boolean canInvoke = true;
            private ReqT request;
            private final ServerCallStreamObserverImpl<ReqT, RespT> responseObserver;

            UnaryServerCallListener(ServerCallStreamObserverImpl<ReqT, RespT> serverCallStreamObserverImpl, ServerCall<ReqT, RespT> serverCall) {
                this.call = serverCall;
                this.responseObserver = serverCallStreamObserverImpl;
            }

            public void onMessage(ReqT reqt) {
                if (this.request != null) {
                    this.call.close(Status.INTERNAL.withDescription(ServerCalls.TOO_MANY_REQUESTS), new Metadata());
                    this.canInvoke = false;
                    return;
                }
                this.request = reqt;
            }

            public void onHalfClose() {
                if (this.canInvoke) {
                    if (this.request == null) {
                        this.call.close(Status.INTERNAL.withDescription(ServerCalls.MISSING_REQUEST), new Metadata());
                        return;
                    }
                    UnaryServerCallHandler.this.method.invoke(this.request, this.responseObserver);
                    this.responseObserver.freeze();
                    if (this.call.isReady()) {
                        onReady();
                    }
                }
            }

            public void onCancel() {
                ServerCallStreamObserverImpl<ReqT, RespT> serverCallStreamObserverImpl = this.responseObserver;
                serverCallStreamObserverImpl.cancelled = true;
                if (serverCallStreamObserverImpl.onCancelHandler != null) {
                    this.responseObserver.onCancelHandler.run();
                }
            }

            public void onReady() {
                if (this.responseObserver.onReadyHandler != null) {
                    this.responseObserver.onReadyHandler.run();
                }
            }
        }

        UnaryServerCallHandler(UnaryRequestMethod<ReqT, RespT> unaryRequestMethod) {
            this.method = unaryRequestMethod;
        }

        public Listener<ReqT> startCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata) {
            Preconditions.checkArgument(serverCall.getMethodDescriptor().getType().clientSendsOneMessage(), "asyncUnaryRequestCall is only for clientSendsOneMessage methods");
            ServerCallStreamObserverImpl serverCallStreamObserverImpl = new ServerCallStreamObserverImpl(serverCall);
            serverCall.request(2);
            return new UnaryServerCallListener(serverCallStreamObserverImpl, serverCall);
        }
    }

    private ServerCalls() {
    }

    public static <ReqT, RespT> ServerCallHandler<ReqT, RespT> asyncUnaryCall(UnaryMethod<ReqT, RespT> unaryMethod) {
        return asyncUnaryRequestCall(unaryMethod);
    }

    public static <ReqT, RespT> ServerCallHandler<ReqT, RespT> asyncServerStreamingCall(ServerStreamingMethod<ReqT, RespT> serverStreamingMethod) {
        return asyncUnaryRequestCall(serverStreamingMethod);
    }

    public static <ReqT, RespT> ServerCallHandler<ReqT, RespT> asyncClientStreamingCall(ClientStreamingMethod<ReqT, RespT> clientStreamingMethod) {
        return asyncStreamingRequestCall(clientStreamingMethod);
    }

    public static <ReqT, RespT> ServerCallHandler<ReqT, RespT> asyncBidiStreamingCall(BidiStreamingMethod<ReqT, RespT> bidiStreamingMethod) {
        return asyncStreamingRequestCall(bidiStreamingMethod);
    }

    private static <ReqT, RespT> ServerCallHandler<ReqT, RespT> asyncUnaryRequestCall(UnaryRequestMethod<ReqT, RespT> unaryRequestMethod) {
        return new UnaryServerCallHandler(unaryRequestMethod);
    }

    private static <ReqT, RespT> ServerCallHandler<ReqT, RespT> asyncStreamingRequestCall(StreamingRequestMethod<ReqT, RespT> streamingRequestMethod) {
        return new StreamingServerCallHandler(streamingRequestMethod);
    }

    public static void asyncUnimplementedUnaryCall(MethodDescriptor<?, ?> methodDescriptor, StreamObserver<?> streamObserver) {
        Preconditions.checkNotNull(methodDescriptor, "methodDescriptor");
        Preconditions.checkNotNull(streamObserver, "responseObserver");
        streamObserver.onError(Status.UNIMPLEMENTED.withDescription(String.format("Method %s is unimplemented", new Object[]{methodDescriptor.getFullMethodName()})).asRuntimeException());
    }

    public static <T> StreamObserver<T> asyncUnimplementedStreamingCall(MethodDescriptor<?, ?> methodDescriptor, StreamObserver<?> streamObserver) {
        asyncUnimplementedUnaryCall(methodDescriptor, streamObserver);
        return new NoopStreamObserver();
    }
}
