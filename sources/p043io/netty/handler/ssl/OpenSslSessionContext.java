package p043io.netty.handler.ssl;

import io.netty.internal.tcnative.SSL;
import io.netty.internal.tcnative.SSLContext;
import io.netty.internal.tcnative.SessionTicketKey;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionContext;
import p043io.netty.util.internal.ObjectUtil;

/* renamed from: io.netty.handler.ssl.OpenSslSessionContext */
public abstract class OpenSslSessionContext implements SSLSessionContext {
    private static final Enumeration<byte[]> EMPTY = new EmptyEnumeration();
    final ReferenceCountedOpenSslContext context;
    private final OpenSslSessionStats stats;

    /* renamed from: io.netty.handler.ssl.OpenSslSessionContext$EmptyEnumeration */
    private static final class EmptyEnumeration implements Enumeration<byte[]> {
        public boolean hasMoreElements() {
            return false;
        }

        private EmptyEnumeration() {
        }

        public byte[] nextElement() {
            throw new NoSuchElementException();
        }
    }

    public abstract boolean isSessionCacheEnabled();

    public abstract void setSessionCacheEnabled(boolean z);

    OpenSslSessionContext(ReferenceCountedOpenSslContext referenceCountedOpenSslContext) {
        this.context = referenceCountedOpenSslContext;
        this.stats = new OpenSslSessionStats(referenceCountedOpenSslContext);
    }

    public SSLSession getSession(byte[] bArr) {
        if (bArr != null) {
            return null;
        }
        throw new NullPointerException("bytes");
    }

    public Enumeration<byte[]> getIds() {
        return EMPTY;
    }

    @Deprecated
    public void setTicketKeys(byte[] bArr) {
        if (bArr.length % 48 == 0) {
            SessionTicketKey[] sessionTicketKeyArr = new SessionTicketKey[(bArr.length / 48)];
            int i = 0;
            int i2 = 0;
            while (i < sessionTicketKeyArr.length) {
                byte[] copyOfRange = Arrays.copyOfRange(bArr, i2, 16);
                int i3 = i2 + 16;
                byte[] copyOfRange2 = Arrays.copyOfRange(bArr, i3, 16);
                int i4 = i + 16;
                byte[] copyOfRange3 = Arrays.copyOfRange(bArr, i3, 16);
                i2 = i3 + 16;
                sessionTicketKeyArr[i4] = new SessionTicketKey(copyOfRange, copyOfRange2, copyOfRange3);
                i = i4 + 1;
            }
            SSLContext.clearOptions(this.context.ctx, SSL.SSL_OP_NO_TICKET);
            SSLContext.setSessionTicketKeys(this.context.ctx, sessionTicketKeyArr);
            return;
        }
        throw new IllegalArgumentException("keys.length % 48 != 0");
    }

    public void setTicketKeys(OpenSslSessionTicketKey... openSslSessionTicketKeyArr) {
        ObjectUtil.checkNotNull(openSslSessionTicketKeyArr, "keys");
        SSLContext.clearOptions(this.context.ctx, SSL.SSL_OP_NO_TICKET);
        SessionTicketKey[] sessionTicketKeyArr = new SessionTicketKey[openSslSessionTicketKeyArr.length];
        for (int i = 0; i < sessionTicketKeyArr.length; i++) {
            sessionTicketKeyArr[i] = openSslSessionTicketKeyArr[i].key;
        }
        SSLContext.setSessionTicketKeys(this.context.ctx, sessionTicketKeyArr);
    }

    public OpenSslSessionStats stats() {
        return this.stats;
    }
}
