package p043io.netty.handler.ssl;

import java.security.Principal;
import java.security.cert.Certificate;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionContext;
import javax.security.cert.X509Certificate;

/* renamed from: io.netty.handler.ssl.JdkSslSession */
final class JdkSslSession implements SSLSession, ApplicationProtocolAccessor {
    private volatile String applicationProtocol;
    private final SSLEngine engine;

    JdkSslSession(SSLEngine sSLEngine) {
        this.engine = sSLEngine;
    }

    private SSLSession unwrap() {
        return this.engine.getSession();
    }

    public String getProtocol() {
        return unwrap().getProtocol();
    }

    public String getApplicationProtocol() {
        return this.applicationProtocol;
    }

    /* access modifiers changed from: 0000 */
    public void setApplicationProtocol(String str) {
        this.applicationProtocol = str;
    }

    public byte[] getId() {
        return unwrap().getId();
    }

    public SSLSessionContext getSessionContext() {
        return unwrap().getSessionContext();
    }

    public long getCreationTime() {
        return unwrap().getCreationTime();
    }

    public long getLastAccessedTime() {
        return unwrap().getLastAccessedTime();
    }

    public void invalidate() {
        unwrap().invalidate();
    }

    public boolean isValid() {
        return unwrap().isValid();
    }

    public void putValue(String str, Object obj) {
        unwrap().putValue(str, obj);
    }

    public Object getValue(String str) {
        return unwrap().getValue(str);
    }

    public void removeValue(String str) {
        unwrap().removeValue(str);
    }

    public String[] getValueNames() {
        return unwrap().getValueNames();
    }

    public Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException {
        return unwrap().getPeerCertificates();
    }

    public Certificate[] getLocalCertificates() {
        return unwrap().getLocalCertificates();
    }

    public X509Certificate[] getPeerCertificateChain() throws SSLPeerUnverifiedException {
        return unwrap().getPeerCertificateChain();
    }

    public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
        return unwrap().getPeerPrincipal();
    }

    public Principal getLocalPrincipal() {
        return unwrap().getLocalPrincipal();
    }

    public String getCipherSuite() {
        return unwrap().getCipherSuite();
    }

    public String getPeerHost() {
        return unwrap().getPeerHost();
    }

    public int getPeerPort() {
        return unwrap().getPeerPort();
    }

    public int getPacketBufferSize() {
        return unwrap().getPacketBufferSize();
    }

    public int getApplicationBufferSize() {
        return unwrap().getApplicationBufferSize();
    }
}
