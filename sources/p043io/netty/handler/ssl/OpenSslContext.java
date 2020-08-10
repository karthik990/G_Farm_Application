package p043io.netty.handler.ssl;

import java.security.cert.Certificate;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;
import p043io.netty.buffer.ByteBufAllocator;

/* renamed from: io.netty.handler.ssl.OpenSslContext */
public abstract class OpenSslContext extends ReferenceCountedOpenSslContext {
    OpenSslContext(Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long j, long j2, int i, Certificate[] certificateArr, ClientAuth clientAuth, String[] strArr, boolean z, boolean z2) throws SSLException {
        super(iterable, cipherSuiteFilter, applicationProtocolConfig, j, j2, i, certificateArr, clientAuth, strArr, z, z2, false);
    }

    OpenSslContext(Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, OpenSslApplicationProtocolNegotiator openSslApplicationProtocolNegotiator, long j, long j2, int i, Certificate[] certificateArr, ClientAuth clientAuth, String[] strArr, boolean z, boolean z2) throws SSLException {
        super(iterable, cipherSuiteFilter, openSslApplicationProtocolNegotiator, j, j2, i, certificateArr, clientAuth, strArr, z, z2, false);
    }

    /* access modifiers changed from: 0000 */
    public final SSLEngine newEngine0(ByteBufAllocator byteBufAllocator, String str, int i) {
        return new OpenSslEngine(this, byteBufAllocator, str, i);
    }

    /* access modifiers changed from: protected */
    public final void finalize() throws Throwable {
        super.finalize();
        OpenSsl.releaseIfNeeded(this);
    }
}
