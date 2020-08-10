package p043io.netty.handler.ssl;

/* renamed from: io.netty.handler.ssl.OpenSslEngineMap */
interface OpenSslEngineMap {
    void add(ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine);

    ReferenceCountedOpenSslEngine get(long j);

    ReferenceCountedOpenSslEngine remove(long j);
}
