package p043io.grpc.stub;

/* renamed from: io.grpc.stub.ClientResponseObserver */
public interface ClientResponseObserver<ReqT, RespT> extends StreamObserver<RespT> {
    void beforeStart(ClientCallStreamObserver<ReqT> clientCallStreamObserver);
}
