package p043io.grpc.internal;

import com.google.common.base.Objects;
import javax.annotation.Nullable;
import p043io.grpc.Attributes;
import p043io.grpc.MethodDescriptor;
import p043io.grpc.ServerStreamTracer.ServerCallInfo;

/* renamed from: io.grpc.internal.ServerCallInfoImpl */
final class ServerCallInfoImpl<ReqT, RespT> extends ServerCallInfo<ReqT, RespT> {
    private final Attributes attributes;
    private final String authority;
    private final MethodDescriptor<ReqT, RespT> methodDescriptor;

    ServerCallInfoImpl(MethodDescriptor<ReqT, RespT> methodDescriptor2, Attributes attributes2, @Nullable String str) {
        this.methodDescriptor = methodDescriptor2;
        this.attributes = attributes2;
        this.authority = str;
    }

    public MethodDescriptor<ReqT, RespT> getMethodDescriptor() {
        return this.methodDescriptor;
    }

    public Attributes getAttributes() {
        return this.attributes;
    }

    @Nullable
    public String getAuthority() {
        return this.authority;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof ServerCallInfoImpl)) {
            return false;
        }
        ServerCallInfoImpl serverCallInfoImpl = (ServerCallInfoImpl) obj;
        if (Objects.equal(this.methodDescriptor, serverCallInfoImpl.methodDescriptor) && Objects.equal(this.attributes, serverCallInfoImpl.attributes) && Objects.equal(this.authority, serverCallInfoImpl.authority)) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        return Objects.hashCode(this.methodDescriptor, this.attributes, this.authority);
    }
}
