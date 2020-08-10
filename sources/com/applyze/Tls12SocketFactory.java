package com.applyze;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class Tls12SocketFactory extends SSLSocketFactory {
    private static final String[] TLS_V12_ONLY = {"TLSv1.2"};
    final SSLSocketFactory delegate;

    public Tls12SocketFactory(SSLSocketFactory sSLSocketFactory) {
        this.delegate = sSLSocketFactory;
    }

    public String[] getDefaultCipherSuites() {
        return this.delegate.getDefaultCipherSuites();
    }

    public String[] getSupportedCipherSuites() {
        return this.delegate.getSupportedCipherSuites();
    }

    public Socket createSocket(Socket socket, String str, int i, boolean z) throws IOException {
        return patch(this.delegate.createSocket(socket, str, i, z));
    }

    public Socket createSocket(String str, int i) throws IOException, UnknownHostException {
        return patch(this.delegate.createSocket(str, i));
    }

    public Socket createSocket(String str, int i, InetAddress inetAddress, int i2) throws IOException, UnknownHostException {
        return patch(this.delegate.createSocket(str, i, inetAddress, i2));
    }

    public Socket createSocket(InetAddress inetAddress, int i) throws IOException {
        return patch(this.delegate.createSocket(inetAddress, i));
    }

    public Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress2, int i2) throws IOException {
        return patch(this.delegate.createSocket(inetAddress, i, inetAddress2, i2));
    }

    private Socket patch(Socket socket) {
        if (socket instanceof SSLSocket) {
            ((SSLSocket) socket).setEnabledProtocols(TLS_V12_ONLY);
        }
        return socket;
    }
}
