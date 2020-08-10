package com.braintreepayments.api.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class TLSSocketFactory extends SSLSocketFactory {
    private SSLSocketFactory mInternalSSLSocketFactory;

    public TLSSocketFactory() throws SSLException {
        try {
            SSLContext instance = SSLContext.getInstance("TLS");
            instance.init(null, null, null);
            this.mInternalSSLSocketFactory = instance.getSocketFactory();
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            throw new SSLException(e.getMessage());
        }
    }

    public TLSSocketFactory(InputStream inputStream) throws SSLException {
        try {
            KeyStore instance = KeyStore.getInstance(KeyStore.getDefaultType());
            instance.load(null, null);
            for (Certificate certificate : CertificateFactory.getInstance("X.509").generateCertificates(inputStream)) {
                if (certificate instanceof X509Certificate) {
                    instance.setCertificateEntry(((X509Certificate) certificate).getSubjectDN().getName(), certificate);
                }
            }
            TrustManagerFactory instance2 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            instance2.init(instance);
            SSLContext instance3 = SSLContext.getInstance("TLS");
            instance3.init(null, instance2.getTrustManagers(), null);
            this.mInternalSSLSocketFactory = instance3.getSocketFactory();
            try {
                inputStream.close();
            } catch (IOException | NullPointerException unused) {
            }
        } catch (Exception e) {
            throw new SSLException(e.getMessage());
        } catch (Throwable th) {
            try {
                inputStream.close();
            } catch (IOException | NullPointerException unused2) {
            }
            throw th;
        }
    }

    public String[] getDefaultCipherSuites() {
        return this.mInternalSSLSocketFactory.getDefaultCipherSuites();
    }

    public String[] getSupportedCipherSuites() {
        return this.mInternalSSLSocketFactory.getSupportedCipherSuites();
    }

    public Socket createSocket(Socket socket, String str, int i, boolean z) throws IOException {
        return enableTLSOnSocket(this.mInternalSSLSocketFactory.createSocket(socket, str, i, z));
    }

    public Socket createSocket(String str, int i) throws IOException {
        return enableTLSOnSocket(this.mInternalSSLSocketFactory.createSocket(str, i));
    }

    public Socket createSocket(String str, int i, InetAddress inetAddress, int i2) throws IOException {
        return enableTLSOnSocket(this.mInternalSSLSocketFactory.createSocket(str, i, inetAddress, i2));
    }

    public Socket createSocket(InetAddress inetAddress, int i) throws IOException {
        return enableTLSOnSocket(this.mInternalSSLSocketFactory.createSocket(inetAddress, i));
    }

    public Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress2, int i2) throws IOException {
        return enableTLSOnSocket(this.mInternalSSLSocketFactory.createSocket(inetAddress, i, inetAddress2, i2));
    }

    private Socket enableTLSOnSocket(Socket socket) {
        if (socket instanceof SSLSocket) {
            SSLSocket sSLSocket = (SSLSocket) socket;
            ArrayList arrayList = new ArrayList(Arrays.asList(sSLSocket.getSupportedProtocols()));
            arrayList.retainAll(Collections.singletonList("TLSv1.2"));
            sSLSocket.setEnabledProtocols((String[]) arrayList.toArray(new String[arrayList.size()]));
        }
        return socket;
    }
}
