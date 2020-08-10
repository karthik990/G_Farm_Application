package p043io.grpc;

import p043io.grpc.ServerCall.Listener;

/* renamed from: io.grpc.ServerCallHandler */
public interface ServerCallHandler<RequestT, ResponseT> {
    Listener<RequestT> startCall(ServerCall<RequestT, ResponseT> serverCall, Metadata metadata);
}
