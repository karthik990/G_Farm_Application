package org.apache.http.impl.pool;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpConnectionFactory;
import org.apache.http.HttpHost;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.DefaultBHttpClientConnection;
import org.apache.http.impl.DefaultBHttpClientConnectionFactory;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParamConfig;
import org.apache.http.params.HttpParams;
import org.apache.http.pool.ConnFactory;
import org.apache.http.util.Args;
import p043io.grpc.internal.GrpcUtil;

public class BasicConnFactory implements ConnFactory<HttpHost, HttpClientConnection> {
    private final HttpConnectionFactory<? extends HttpClientConnection> connFactory;
    private final int connectTimeout;
    private final SocketFactory plainfactory;
    private final SocketConfig sconfig;
    private final SSLSocketFactory sslfactory;

    @Deprecated
    public BasicConnFactory(SSLSocketFactory sSLSocketFactory, HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP params");
        this.plainfactory = null;
        this.sslfactory = sSLSocketFactory;
        this.connectTimeout = httpParams.getIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 0);
        this.sconfig = HttpParamConfig.getSocketConfig(httpParams);
        this.connFactory = new DefaultBHttpClientConnectionFactory(HttpParamConfig.getConnectionConfig(httpParams));
    }

    @Deprecated
    public BasicConnFactory(HttpParams httpParams) {
        this((SSLSocketFactory) null, httpParams);
    }

    public BasicConnFactory(SocketFactory socketFactory, SSLSocketFactory sSLSocketFactory, int i, SocketConfig socketConfig, ConnectionConfig connectionConfig) {
        this.plainfactory = socketFactory;
        this.sslfactory = sSLSocketFactory;
        this.connectTimeout = i;
        if (socketConfig == null) {
            socketConfig = SocketConfig.DEFAULT;
        }
        this.sconfig = socketConfig;
        if (connectionConfig == null) {
            connectionConfig = ConnectionConfig.DEFAULT;
        }
        this.connFactory = new DefaultBHttpClientConnectionFactory(connectionConfig);
    }

    public BasicConnFactory(int i, SocketConfig socketConfig, ConnectionConfig connectionConfig) {
        this(null, null, i, socketConfig, connectionConfig);
    }

    public BasicConnFactory(SocketConfig socketConfig, ConnectionConfig connectionConfig) {
        this(null, null, 0, socketConfig, connectionConfig);
    }

    public BasicConnFactory() {
        this(null, null, 0, SocketConfig.DEFAULT, ConnectionConfig.DEFAULT);
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public HttpClientConnection create(Socket socket, HttpParams httpParams) throws IOException {
        DefaultBHttpClientConnection defaultBHttpClientConnection = new DefaultBHttpClientConnection(httpParams.getIntParameter(CoreConnectionPNames.SOCKET_BUFFER_SIZE, 8192));
        defaultBHttpClientConnection.bind(socket);
        return defaultBHttpClientConnection;
    }

    public HttpClientConnection create(HttpHost httpHost) throws IOException {
        Socket socket;
        String schemeName = httpHost.getSchemeName();
        String str = HttpHost.DEFAULT_SCHEME_NAME;
        if (str.equalsIgnoreCase(schemeName)) {
            SocketFactory socketFactory = this.plainfactory;
            socket = socketFactory != null ? socketFactory.createSocket() : new Socket();
        } else {
            socket = null;
        }
        String str2 = "https";
        if (str2.equalsIgnoreCase(schemeName)) {
            SocketFactory socketFactory2 = this.sslfactory;
            if (socketFactory2 == null) {
                socketFactory2 = SSLSocketFactory.getDefault();
            }
            socket = socketFactory2.createSocket();
        }
        if (socket != null) {
            String hostName = httpHost.getHostName();
            int port = httpHost.getPort();
            if (port == -1) {
                if (httpHost.getSchemeName().equalsIgnoreCase(str)) {
                    port = 80;
                } else if (httpHost.getSchemeName().equalsIgnoreCase(str2)) {
                    port = GrpcUtil.DEFAULT_PORT_SSL;
                }
            }
            socket.setSoTimeout(this.sconfig.getSoTimeout());
            if (this.sconfig.getSndBufSize() > 0) {
                socket.setSendBufferSize(this.sconfig.getSndBufSize());
            }
            if (this.sconfig.getRcvBufSize() > 0) {
                socket.setReceiveBufferSize(this.sconfig.getRcvBufSize());
            }
            socket.setTcpNoDelay(this.sconfig.isTcpNoDelay());
            int soLinger = this.sconfig.getSoLinger();
            if (soLinger >= 0) {
                socket.setSoLinger(true, soLinger);
            }
            socket.setKeepAlive(this.sconfig.isSoKeepAlive());
            socket.connect(new InetSocketAddress(hostName, port), this.connectTimeout);
            return (HttpClientConnection) this.connFactory.createConnection(socket);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(schemeName);
        sb.append(" scheme is not supported");
        throw new IOException(sb.toString());
    }
}