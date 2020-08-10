package p043io.netty.handler.ssl.util;

import java.net.Socket;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509TrustManager;
import p043io.netty.util.internal.ObjectUtil;

/* renamed from: io.netty.handler.ssl.util.X509TrustManagerWrapper */
final class X509TrustManagerWrapper extends X509ExtendedTrustManager {
    private final X509TrustManager delegate;

    X509TrustManagerWrapper(X509TrustManager x509TrustManager) {
        this.delegate = (X509TrustManager) ObjectUtil.checkNotNull(x509TrustManager, "delegate");
    }

    public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
        this.delegate.checkClientTrusted(x509CertificateArr, str);
    }

    public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str, Socket socket) throws CertificateException {
        this.delegate.checkClientTrusted(x509CertificateArr, str);
    }

    public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str, SSLEngine sSLEngine) throws CertificateException {
        this.delegate.checkClientTrusted(x509CertificateArr, str);
    }

    public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
        this.delegate.checkServerTrusted(x509CertificateArr, str);
    }

    public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str, Socket socket) throws CertificateException {
        this.delegate.checkServerTrusted(x509CertificateArr, str);
    }

    public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str, SSLEngine sSLEngine) throws CertificateException {
        this.delegate.checkServerTrusted(x509CertificateArr, str);
    }

    public X509Certificate[] getAcceptedIssuers() {
        return this.delegate.getAcceptedIssuers();
    }
}
