package p043io.grpc;

import java.util.concurrent.Executor;
import p043io.grpc.Attributes.Key;

/* renamed from: io.grpc.CallCredentials */
public interface CallCredentials {
    @Deprecated
    public static final Key<String> ATTR_AUTHORITY = Key.create("io.grpc.CallCredentials.authority");
    @Deprecated
    public static final Key<SecurityLevel> ATTR_SECURITY_LEVEL = Key.create("io.grpc.internal.GrpcAttributes.securityLevel");

    @Deprecated
    /* renamed from: io.grpc.CallCredentials$MetadataApplier */
    public interface MetadataApplier {
        void apply(Metadata metadata);

        void fail(Status status);
    }

    /* renamed from: io.grpc.CallCredentials$RequestInfo */
    public static abstract class RequestInfo {
        public abstract String getAuthority();

        public abstract MethodDescriptor<?, ?> getMethodDescriptor();

        public abstract SecurityLevel getSecurityLevel();

        public abstract Attributes getTransportAttrs();
    }

    @Deprecated
    void applyRequestMetadata(MethodDescriptor<?, ?> methodDescriptor, Attributes attributes, Executor executor, MetadataApplier metadataApplier);

    void thisUsesUnstableApi();
}
