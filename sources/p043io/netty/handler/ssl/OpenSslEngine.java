package p043io.netty.handler.ssl;

import p043io.netty.buffer.ByteBufAllocator;

/* renamed from: io.netty.handler.ssl.OpenSslEngine */
public final class OpenSslEngine extends ReferenceCountedOpenSslEngine {
    OpenSslEngine(OpenSslContext openSslContext, ByteBufAllocator byteBufAllocator, String str, int i) {
        super(openSslContext, byteBufAllocator, str, i, false);
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        super.finalize();
        OpenSsl.releaseIfNeeded(this);
    }
}
