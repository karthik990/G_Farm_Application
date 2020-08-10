package org.graylog2.gelfclient;

import java.io.File;
import java.net.InetSocketAddress;

public class GelfConfiguration {
    private static final String DEFAULT_HOSTNAME = "127.0.0.1";
    private static final int DEFAULT_PORT = 12201;
    private int connectTimeout;
    private final String hostname;
    private int maxInflightSends;
    private final int port;
    private int queueSize;
    private int reconnectDelay;
    private int sendBufferSize;
    private boolean tcpKeepAlive;
    private boolean tcpNoDelay;
    private boolean tlsCertVerificationEnabled;
    private boolean tlsEnabled;
    private File tlsTrustCertChainFile;
    private GelfTransports transport;

    public GelfConfiguration(String str, int i) {
        this.transport = GelfTransports.TCP;
        this.queueSize = 512;
        this.tlsEnabled = false;
        this.tlsTrustCertChainFile = null;
        this.tlsCertVerificationEnabled = true;
        this.reconnectDelay = 500;
        this.connectTimeout = 1000;
        this.tcpNoDelay = false;
        this.tcpKeepAlive = false;
        this.sendBufferSize = -1;
        this.maxInflightSends = 512;
        this.hostname = checkHostname(str);
        this.port = checkPort(i);
    }

    public GelfConfiguration(InetSocketAddress inetSocketAddress) {
        this(inetSocketAddress.getHostString(), inetSocketAddress.getPort());
    }

    public GelfConfiguration(String str) {
        this(str, DEFAULT_PORT);
    }

    public GelfConfiguration(int i) {
        this(DEFAULT_HOSTNAME, i);
    }

    public GelfConfiguration() {
        this(DEFAULT_HOSTNAME, DEFAULT_PORT);
    }

    public String getHostname() {
        return this.hostname;
    }

    public int getPort() {
        return this.port;
    }

    public InetSocketAddress getRemoteAddress() {
        return new InetSocketAddress(this.hostname, this.port);
    }

    public GelfTransports getTransport() {
        return this.transport;
    }

    public GelfConfiguration transport(GelfTransports gelfTransports) {
        this.transport = gelfTransports;
        return this;
    }

    public int getQueueSize() {
        return this.queueSize;
    }

    public GelfConfiguration queueSize(int i) {
        this.queueSize = i;
        return this;
    }

    public boolean isTlsEnabled() {
        return this.tlsEnabled;
    }

    public GelfConfiguration enableTls() {
        this.tlsEnabled = true;
        return this;
    }

    public GelfConfiguration disableTls() {
        this.tlsEnabled = false;
        return this;
    }

    public File getTlsTrustCertChainFile() {
        return this.tlsTrustCertChainFile;
    }

    public GelfConfiguration tlsTrustCertChainFile(File file) {
        this.tlsTrustCertChainFile = file;
        return this;
    }

    public boolean isTlsCertVerificationEnabled() {
        return this.tlsCertVerificationEnabled;
    }

    public GelfConfiguration enableTlsCertVerification() {
        this.tlsCertVerificationEnabled = true;
        return this;
    }

    public GelfConfiguration disableTlsCertVerification() {
        this.tlsCertVerificationEnabled = false;
        return this;
    }

    public int getReconnectDelay() {
        return this.reconnectDelay;
    }

    public GelfConfiguration reconnectDelay(int i) {
        this.reconnectDelay = i;
        return this;
    }

    public int getConnectTimeout() {
        return this.connectTimeout;
    }

    public GelfConfiguration connectTimeout(int i) {
        this.connectTimeout = i;
        return this;
    }

    public boolean isTcpNoDelay() {
        return this.tcpNoDelay;
    }

    public GelfConfiguration tcpNoDelay(boolean z) {
        this.tcpNoDelay = z;
        return this;
    }

    public boolean isTcpKeepAlive() {
        return this.tcpKeepAlive;
    }

    public GelfConfiguration tcpKeepAlive(boolean z) {
        this.tcpKeepAlive = z;
        return this;
    }

    public int getSendBufferSize() {
        return this.sendBufferSize;
    }

    public GelfConfiguration sendBufferSize(int i) {
        this.sendBufferSize = i;
        return this;
    }

    private String checkHostname(String str) {
        if (str == null) {
            throw new IllegalArgumentException("hostname can't be null");
        } else if (!str.trim().isEmpty()) {
            return str;
        } else {
            throw new IllegalArgumentException("hostname can't be empty");
        }
    }

    private int checkPort(int i) {
        if (i >= 1 && i <= 65535) {
            return i;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("port out of range: ");
        sb.append(i);
        throw new IllegalArgumentException(sb.toString());
    }

    public int getMaxInflightSends() {
        return this.maxInflightSends;
    }

    public GelfConfiguration maxInflightSends(int i) {
        this.maxInflightSends = i;
        return this;
    }
}
