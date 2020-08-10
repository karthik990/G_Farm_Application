package p043io.grpc.stub;

import com.google.common.base.Preconditions;
import java.util.concurrent.atomic.AtomicReference;
import p043io.grpc.CallOptions;
import p043io.grpc.Channel;
import p043io.grpc.ClientCall;
import p043io.grpc.ClientCall.Listener;
import p043io.grpc.ClientInterceptor;
import p043io.grpc.ForwardingClientCall.SimpleForwardingClientCall;
import p043io.grpc.ForwardingClientCallListener.SimpleForwardingClientCallListener;
import p043io.grpc.Metadata;
import p043io.grpc.MethodDescriptor;
import p043io.grpc.Status;

/* renamed from: io.grpc.stub.MetadataUtils */
public final class MetadataUtils {

    /* renamed from: io.grpc.stub.MetadataUtils$HeaderAttachingClientInterceptor */
    private static final class HeaderAttachingClientInterceptor implements ClientInterceptor {
        /* access modifiers changed from: private */
        public final Metadata extraHeaders;

        /* renamed from: io.grpc.stub.MetadataUtils$HeaderAttachingClientInterceptor$HeaderAttachingClientCall */
        private final class HeaderAttachingClientCall<ReqT, RespT> extends SimpleForwardingClientCall<ReqT, RespT> {
            HeaderAttachingClientCall(ClientCall<ReqT, RespT> clientCall) {
                super(clientCall);
            }

            public void start(Listener<RespT> listener, Metadata metadata) {
                metadata.merge(HeaderAttachingClientInterceptor.this.extraHeaders);
                super.start(listener, metadata);
            }
        }

        HeaderAttachingClientInterceptor(Metadata metadata) {
            this.extraHeaders = (Metadata) Preconditions.checkNotNull(metadata, metadata);
        }

        public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> methodDescriptor, CallOptions callOptions, Channel channel) {
            return new HeaderAttachingClientCall(channel.newCall(methodDescriptor, callOptions));
        }
    }

    /* renamed from: io.grpc.stub.MetadataUtils$MetadataCapturingClientInterceptor */
    private static final class MetadataCapturingClientInterceptor implements ClientInterceptor {
        final AtomicReference<Metadata> headersCapture;
        final AtomicReference<Metadata> trailersCapture;

        /* renamed from: io.grpc.stub.MetadataUtils$MetadataCapturingClientInterceptor$MetadataCapturingClientCall */
        private final class MetadataCapturingClientCall<ReqT, RespT> extends SimpleForwardingClientCall<ReqT, RespT> {

            /* renamed from: io.grpc.stub.MetadataUtils$MetadataCapturingClientInterceptor$MetadataCapturingClientCall$MetadataCapturingClientCallListener */
            private final class MetadataCapturingClientCallListener extends SimpleForwardingClientCallListener<RespT> {
                MetadataCapturingClientCallListener(Listener<RespT> listener) {
                    super(listener);
                }

                public void onHeaders(Metadata metadata) {
                    MetadataCapturingClientInterceptor.this.headersCapture.set(metadata);
                    super.onHeaders(metadata);
                }

                public void onClose(Status status, Metadata metadata) {
                    MetadataCapturingClientInterceptor.this.trailersCapture.set(metadata);
                    super.onClose(status, metadata);
                }
            }

            MetadataCapturingClientCall(ClientCall<ReqT, RespT> clientCall) {
                super(clientCall);
            }

            public void start(Listener<RespT> listener, Metadata metadata) {
                MetadataCapturingClientInterceptor.this.headersCapture.set(null);
                MetadataCapturingClientInterceptor.this.trailersCapture.set(null);
                super.start(new MetadataCapturingClientCallListener(listener), metadata);
            }
        }

        MetadataCapturingClientInterceptor(AtomicReference<Metadata> atomicReference, AtomicReference<Metadata> atomicReference2) {
            this.headersCapture = (AtomicReference) Preconditions.checkNotNull(atomicReference, "headersCapture");
            this.trailersCapture = (AtomicReference) Preconditions.checkNotNull(atomicReference2, "trailersCapture");
        }

        public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> methodDescriptor, CallOptions callOptions, Channel channel) {
            return new MetadataCapturingClientCall(channel.newCall(methodDescriptor, callOptions));
        }
    }

    private MetadataUtils() {
    }

    public static <T extends AbstractStub<T>> T attachHeaders(T t, Metadata metadata) {
        return t.withInterceptors(newAttachHeadersInterceptor(metadata));
    }

    public static ClientInterceptor newAttachHeadersInterceptor(Metadata metadata) {
        return new HeaderAttachingClientInterceptor(metadata);
    }

    public static <T extends AbstractStub<T>> T captureMetadata(T t, AtomicReference<Metadata> atomicReference, AtomicReference<Metadata> atomicReference2) {
        return t.withInterceptors(newCaptureMetadataInterceptor(atomicReference, atomicReference2));
    }

    public static ClientInterceptor newCaptureMetadataInterceptor(AtomicReference<Metadata> atomicReference, AtomicReference<Metadata> atomicReference2) {
        return new MetadataCapturingClientInterceptor(atomicReference, atomicReference2);
    }
}
