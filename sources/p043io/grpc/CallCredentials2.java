package p043io.grpc;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import java.util.concurrent.Executor;
import p043io.grpc.CallCredentials.RequestInfo;

/* renamed from: io.grpc.CallCredentials2 */
public abstract class CallCredentials2 implements CallCredentials {

    /* renamed from: io.grpc.CallCredentials2$MetadataApplier */
    public static abstract class MetadataApplier implements p043io.grpc.CallCredentials.MetadataApplier {
    }

    public abstract void applyRequestMetadata(RequestInfo requestInfo, Executor executor, MetadataApplier metadataApplier);

    public final void applyRequestMetadata(MethodDescriptor<?, ?> methodDescriptor, Attributes attributes, Executor executor, final p043io.grpc.CallCredentials.MetadataApplier metadataApplier) {
        final String str = (String) Preconditions.checkNotNull(attributes.get(ATTR_AUTHORITY), "authority");
        final SecurityLevel securityLevel = (SecurityLevel) MoreObjects.firstNonNull(attributes.get(ATTR_SECURITY_LEVEL), SecurityLevel.NONE);
        final MethodDescriptor<?, ?> methodDescriptor2 = methodDescriptor;
        final Attributes attributes2 = attributes;
        C53551 r1 = new RequestInfo() {
            public MethodDescriptor<?, ?> getMethodDescriptor() {
                return methodDescriptor2;
            }

            public SecurityLevel getSecurityLevel() {
                return securityLevel;
            }

            public String getAuthority() {
                return str;
            }

            public Attributes getTransportAttrs() {
                return attributes2;
            }
        };
        applyRequestMetadata(r1, executor, new MetadataApplier() {
            public void apply(Metadata metadata) {
                metadataApplier.apply(metadata);
            }

            public void fail(Status status) {
                metadataApplier.fail(status);
            }
        });
    }
}
