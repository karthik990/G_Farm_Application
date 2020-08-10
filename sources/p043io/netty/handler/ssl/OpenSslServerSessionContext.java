package p043io.netty.handler.ssl;

import io.netty.internal.tcnative.SSL;
import io.netty.internal.tcnative.SSLContext;

/* renamed from: io.netty.handler.ssl.OpenSslServerSessionContext */
public final class OpenSslServerSessionContext extends OpenSslSessionContext {
    OpenSslServerSessionContext(ReferenceCountedOpenSslContext referenceCountedOpenSslContext) {
        super(referenceCountedOpenSslContext);
    }

    public void setSessionTimeout(int i) {
        if (i >= 0) {
            SSLContext.setSessionCacheTimeout(this.context.ctx, (long) i);
            return;
        }
        throw new IllegalArgumentException();
    }

    public int getSessionTimeout() {
        return (int) SSLContext.getSessionCacheTimeout(this.context.ctx);
    }

    public void setSessionCacheSize(int i) {
        if (i >= 0) {
            SSLContext.setSessionCacheSize(this.context.ctx, (long) i);
            return;
        }
        throw new IllegalArgumentException();
    }

    public int getSessionCacheSize() {
        return (int) SSLContext.getSessionCacheSize(this.context.ctx);
    }

    public void setSessionCacheEnabled(boolean z) {
        SSLContext.setSessionCacheMode(this.context.ctx, z ? SSL.SSL_SESS_CACHE_SERVER : SSL.SSL_SESS_CACHE_OFF);
    }

    public boolean isSessionCacheEnabled() {
        return SSLContext.getSessionCacheMode(this.context.ctx) == SSL.SSL_SESS_CACHE_SERVER;
    }

    public boolean setSessionIdContext(byte[] bArr) {
        return SSLContext.setSessionIdContext(this.context.ctx, bArr);
    }
}
