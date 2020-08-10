package p043io.grpc;

import p043io.grpc.ServerCall.Listener;

/* renamed from: io.grpc.ServerInterceptor */
public interface ServerInterceptor {
    <ReqT, RespT> Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler);
}
