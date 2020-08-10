package lib.android.paypal.com.magnessdk.network;

import java.net.InetAddress;
import java.net.Socket;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import lib.android.paypal.com.magnessdk.p046b.C5988a;

/* renamed from: lib.android.paypal.com.magnessdk.network.k */
final class C6007k extends SSLSocketFactory {

    /* renamed from: a */
    private static final String f4502a = C6007k.class.getSimpleName();

    /* renamed from: b */
    private static TrustManager[] f4503b;

    /* renamed from: c */
    private SSLContext f4504c;

    /* renamed from: d */
    private SSLSocketFactory f4505d;

    /* renamed from: e */
    private TrustManager[] f4506e;

    private C6007k() {
        try {
            this.f4504c = SSLContext.getInstance("TLS");
            this.f4506e = m4129b();
            this.f4504c.init(null, this.f4506e, null);
            this.f4505d = this.f4504c.getSocketFactory();
        } catch (Exception e) {
            C5988a.m4032a(getClass(), 3, (Throwable) e);
        }
    }

    /* renamed from: a */
    private Socket m4127a(Socket socket) {
        if (socket instanceof SSLSocket) {
            SSLSocket sSLSocket = (SSLSocket) socket;
            ArrayList arrayList = new ArrayList(Arrays.asList(sSLSocket.getSupportedProtocols()));
            arrayList.retainAll(Arrays.asList(new String[]{"TLSv1.2", "TLSv1.1", "TLSv1"}));
            sSLSocket.setEnabledProtocols((String[]) arrayList.toArray(new String[arrayList.size()]));
        }
        return socket;
    }

    /* renamed from: a */
    static C6007k m4128a() {
        return new C6007k();
    }

    /* renamed from: b */
    private TrustManager[] m4129b() {
        if (f4503b == null) {
            try {
                KeyStore instance = KeyStore.getInstance(KeyStore.getDefaultType());
                instance.load(null, null);
                for (Certificate certificate : CertificateFactory.getInstance("X.509").generateCertificates(PayPalCertificate.getCertInputStream())) {
                    if (certificate instanceof X509Certificate) {
                        instance.setCertificateEntry(((X509Certificate) certificate).getSubjectDN().getName(), certificate);
                    }
                }
                TrustManagerFactory instance2 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                instance2.init(instance);
                this.f4506e = instance2.getTrustManagers();
            } catch (Exception e) {
                C5988a.m4032a(getClass(), 3, (Throwable) e);
            }
            f4503b = this.f4506e;
        }
        return f4503b;
    }

    public Socket createSocket(String str, int i) {
        return m4127a(this.f4505d.createSocket(str, i));
    }

    public Socket createSocket(String str, int i, InetAddress inetAddress, int i2) {
        return m4127a(this.f4505d.createSocket(str, i, inetAddress, i2));
    }

    public Socket createSocket(InetAddress inetAddress, int i) {
        return m4127a(this.f4505d.createSocket(inetAddress, i));
    }

    public Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress2, int i2) {
        return m4127a(this.f4505d.createSocket(inetAddress, i, inetAddress2, i2));
    }

    public Socket createSocket(Socket socket, String str, int i, boolean z) {
        return m4127a(this.f4505d.createSocket(socket, str, i, z));
    }

    public String[] getDefaultCipherSuites() {
        return this.f4505d.getDefaultCipherSuites();
    }

    public String[] getSupportedCipherSuites() {
        return this.f4505d.getSupportedCipherSuites();
    }
}
