package p043io.grpc;

import p043io.grpc.MethodDescriptor.Marshaller;

/* renamed from: io.grpc.InternalClientInterceptors */
public final class InternalClientInterceptors {
    public static <ReqT, RespT> ClientInterceptor wrapClientInterceptor(ClientInterceptor clientInterceptor, Marshaller<ReqT> marshaller, Marshaller<RespT> marshaller2) {
        return ClientInterceptors.wrapClientInterceptor(clientInterceptor, marshaller, marshaller2);
    }
}
