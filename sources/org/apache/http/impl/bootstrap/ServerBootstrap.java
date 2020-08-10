package org.apache.http.impl.bootstrap;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLContext;
import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.ExceptionLogger;
import org.apache.http.HttpConnectionFactory;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponseFactory;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.DefaultBHttpServerConnection;
import org.apache.http.protocol.HttpExpectationVerifier;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpRequestHandler;
import org.apache.http.protocol.HttpRequestHandlerMapper;

public class ServerBootstrap {
    private ConnectionReuseStrategy connStrategy;
    private ConnectionConfig connectionConfig;
    private HttpConnectionFactory<? extends DefaultBHttpServerConnection> connectionFactory;
    private ExceptionLogger exceptionLogger;
    private HttpExpectationVerifier expectationVerifier;
    private Map<String, HttpRequestHandler> handlerMap;
    private HttpRequestHandlerMapper handlerMapper;
    private HttpProcessor httpProcessor;
    private int listenerPort;
    private InetAddress localAddress;
    private LinkedList<HttpRequestInterceptor> requestFirst;
    private LinkedList<HttpRequestInterceptor> requestLast;
    private HttpResponseFactory responseFactory;
    private LinkedList<HttpResponseInterceptor> responseFirst;
    private LinkedList<HttpResponseInterceptor> responseLast;
    private String serverInfo;
    private ServerSocketFactory serverSocketFactory;
    private SocketConfig socketConfig;
    private SSLContext sslContext;
    private SSLServerSetupHandler sslSetupHandler;

    private ServerBootstrap() {
    }

    public static ServerBootstrap bootstrap() {
        return new ServerBootstrap();
    }

    public final ServerBootstrap setListenerPort(int i) {
        this.listenerPort = i;
        return this;
    }

    public final ServerBootstrap setLocalAddress(InetAddress inetAddress) {
        this.localAddress = inetAddress;
        return this;
    }

    public final ServerBootstrap setSocketConfig(SocketConfig socketConfig2) {
        this.socketConfig = socketConfig2;
        return this;
    }

    public final ServerBootstrap setConnectionConfig(ConnectionConfig connectionConfig2) {
        this.connectionConfig = connectionConfig2;
        return this;
    }

    public final ServerBootstrap setHttpProcessor(HttpProcessor httpProcessor2) {
        this.httpProcessor = httpProcessor2;
        return this;
    }

    public final ServerBootstrap addInterceptorFirst(HttpResponseInterceptor httpResponseInterceptor) {
        if (httpResponseInterceptor == null) {
            return this;
        }
        if (this.responseFirst == null) {
            this.responseFirst = new LinkedList<>();
        }
        this.responseFirst.addFirst(httpResponseInterceptor);
        return this;
    }

    public final ServerBootstrap addInterceptorLast(HttpResponseInterceptor httpResponseInterceptor) {
        if (httpResponseInterceptor == null) {
            return this;
        }
        if (this.responseLast == null) {
            this.responseLast = new LinkedList<>();
        }
        this.responseLast.addLast(httpResponseInterceptor);
        return this;
    }

    public final ServerBootstrap addInterceptorFirst(HttpRequestInterceptor httpRequestInterceptor) {
        if (httpRequestInterceptor == null) {
            return this;
        }
        if (this.requestFirst == null) {
            this.requestFirst = new LinkedList<>();
        }
        this.requestFirst.addFirst(httpRequestInterceptor);
        return this;
    }

    public final ServerBootstrap addInterceptorLast(HttpRequestInterceptor httpRequestInterceptor) {
        if (httpRequestInterceptor == null) {
            return this;
        }
        if (this.requestLast == null) {
            this.requestLast = new LinkedList<>();
        }
        this.requestLast.addLast(httpRequestInterceptor);
        return this;
    }

    public final ServerBootstrap setServerInfo(String str) {
        this.serverInfo = str;
        return this;
    }

    public final ServerBootstrap setConnectionReuseStrategy(ConnectionReuseStrategy connectionReuseStrategy) {
        this.connStrategy = connectionReuseStrategy;
        return this;
    }

    public final ServerBootstrap setResponseFactory(HttpResponseFactory httpResponseFactory) {
        this.responseFactory = httpResponseFactory;
        return this;
    }

    public final ServerBootstrap setHandlerMapper(HttpRequestHandlerMapper httpRequestHandlerMapper) {
        this.handlerMapper = httpRequestHandlerMapper;
        return this;
    }

    public final ServerBootstrap registerHandler(String str, HttpRequestHandler httpRequestHandler) {
        if (!(str == null || httpRequestHandler == null)) {
            if (this.handlerMap == null) {
                this.handlerMap = new HashMap();
            }
            this.handlerMap.put(str, httpRequestHandler);
        }
        return this;
    }

    public final ServerBootstrap setExpectationVerifier(HttpExpectationVerifier httpExpectationVerifier) {
        this.expectationVerifier = httpExpectationVerifier;
        return this;
    }

    public final ServerBootstrap setConnectionFactory(HttpConnectionFactory<? extends DefaultBHttpServerConnection> httpConnectionFactory) {
        this.connectionFactory = httpConnectionFactory;
        return this;
    }

    public final ServerBootstrap setSslSetupHandler(SSLServerSetupHandler sSLServerSetupHandler) {
        this.sslSetupHandler = sSLServerSetupHandler;
        return this;
    }

    public final ServerBootstrap setServerSocketFactory(ServerSocketFactory serverSocketFactory2) {
        this.serverSocketFactory = serverSocketFactory2;
        return this;
    }

    public final ServerBootstrap setSslContext(SSLContext sSLContext) {
        this.sslContext = sSLContext;
        return this;
    }

    public final ServerBootstrap setExceptionLogger(ExceptionLogger exceptionLogger2) {
        this.exceptionLogger = exceptionLogger2;
        return this;
    }

    /* JADX WARNING: type inference failed for: r1v2, types: [org.apache.http.protocol.HttpRequestHandlerMapper] */
    /* JADX WARNING: type inference failed for: r1v3 */
    /* JADX WARNING: type inference failed for: r7v0, types: [org.apache.http.protocol.HttpRequestHandlerMapper] */
    /* JADX WARNING: type inference failed for: r1v23, types: [org.apache.http.protocol.UriHttpRequestHandlerMapper] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r1v2, types: [org.apache.http.protocol.HttpRequestHandlerMapper]
      assigns: [org.apache.http.protocol.HttpRequestHandlerMapper, org.apache.http.protocol.UriHttpRequestHandlerMapper]
      uses: [?[int, boolean, OBJECT, ARRAY, byte, short, char], ?[OBJECT, ARRAY], org.apache.http.protocol.UriHttpRequestHandlerMapper]
      mth insns count: 110
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x010b  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x0115  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x0117  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x011f  */
    /* JADX WARNING: Unknown variable types count: 2 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.apache.http.impl.bootstrap.HttpServer create() {
        /*
            r17 = this;
            r0 = r17
            org.apache.http.protocol.HttpProcessor r1 = r0.httpProcessor
            r2 = 0
            if (r1 != 0) goto L_0x009a
            org.apache.http.protocol.HttpProcessorBuilder r1 = org.apache.http.protocol.HttpProcessorBuilder.create()
            java.util.LinkedList<org.apache.http.HttpRequestInterceptor> r3 = r0.requestFirst
            if (r3 == 0) goto L_0x0023
            java.util.Iterator r3 = r3.iterator()
        L_0x0013:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x0023
            java.lang.Object r4 = r3.next()
            org.apache.http.HttpRequestInterceptor r4 = (org.apache.http.HttpRequestInterceptor) r4
            r1.addFirst(r4)
            goto L_0x0013
        L_0x0023:
            java.util.LinkedList<org.apache.http.HttpResponseInterceptor> r3 = r0.responseFirst
            if (r3 == 0) goto L_0x003b
            java.util.Iterator r3 = r3.iterator()
        L_0x002b:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x003b
            java.lang.Object r4 = r3.next()
            org.apache.http.HttpResponseInterceptor r4 = (org.apache.http.HttpResponseInterceptor) r4
            r1.addFirst(r4)
            goto L_0x002b
        L_0x003b:
            java.lang.String r3 = r0.serverInfo
            if (r3 != 0) goto L_0x0041
            java.lang.String r3 = "Apache-HttpCore/1.1"
        L_0x0041:
            r4 = 4
            org.apache.http.HttpResponseInterceptor[] r4 = new org.apache.http.HttpResponseInterceptor[r4]
            org.apache.http.protocol.ResponseDate r5 = new org.apache.http.protocol.ResponseDate
            r5.<init>()
            r4[r2] = r5
            r5 = 1
            org.apache.http.protocol.ResponseServer r6 = new org.apache.http.protocol.ResponseServer
            r6.<init>(r3)
            r4[r5] = r6
            r3 = 2
            org.apache.http.protocol.ResponseContent r5 = new org.apache.http.protocol.ResponseContent
            r5.<init>()
            r4[r3] = r5
            r3 = 3
            org.apache.http.protocol.ResponseConnControl r5 = new org.apache.http.protocol.ResponseConnControl
            r5.<init>()
            r4[r3] = r5
            r1.addAll(r4)
            java.util.LinkedList<org.apache.http.HttpRequestInterceptor> r3 = r0.requestLast
            if (r3 == 0) goto L_0x007e
            java.util.Iterator r3 = r3.iterator()
        L_0x006e:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x007e
            java.lang.Object r4 = r3.next()
            org.apache.http.HttpRequestInterceptor r4 = (org.apache.http.HttpRequestInterceptor) r4
            r1.addLast(r4)
            goto L_0x006e
        L_0x007e:
            java.util.LinkedList<org.apache.http.HttpResponseInterceptor> r3 = r0.responseLast
            if (r3 == 0) goto L_0x0096
            java.util.Iterator r3 = r3.iterator()
        L_0x0086:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x0096
            java.lang.Object r4 = r3.next()
            org.apache.http.HttpResponseInterceptor r4 = (org.apache.http.HttpResponseInterceptor) r4
            r1.addLast(r4)
            goto L_0x0086
        L_0x0096:
            org.apache.http.protocol.HttpProcessor r1 = r1.build()
        L_0x009a:
            r4 = r1
            org.apache.http.protocol.HttpRequestHandlerMapper r1 = r0.handlerMapper
            if (r1 != 0) goto L_0x00cc
            org.apache.http.protocol.UriHttpRequestHandlerMapper r1 = new org.apache.http.protocol.UriHttpRequestHandlerMapper
            r1.<init>()
            java.util.Map<java.lang.String, org.apache.http.protocol.HttpRequestHandler> r3 = r0.handlerMap
            if (r3 == 0) goto L_0x00cc
            java.util.Set r3 = r3.entrySet()
            java.util.Iterator r3 = r3.iterator()
        L_0x00b0:
            boolean r5 = r3.hasNext()
            if (r5 == 0) goto L_0x00cc
            java.lang.Object r5 = r3.next()
            java.util.Map$Entry r5 = (java.util.Map.Entry) r5
            java.lang.Object r6 = r5.getKey()
            java.lang.String r6 = (java.lang.String) r6
            java.lang.Object r5 = r5.getValue()
            org.apache.http.protocol.HttpRequestHandler r5 = (org.apache.http.protocol.HttpRequestHandler) r5
            r1.register(r6, r5)
            goto L_0x00b0
        L_0x00cc:
            r7 = r1
            org.apache.http.ConnectionReuseStrategy r1 = r0.connStrategy
            if (r1 != 0) goto L_0x00d3
            org.apache.http.impl.DefaultConnectionReuseStrategy r1 = org.apache.http.impl.DefaultConnectionReuseStrategy.INSTANCE
        L_0x00d3:
            r5 = r1
            org.apache.http.HttpResponseFactory r1 = r0.responseFactory
            if (r1 != 0) goto L_0x00da
            org.apache.http.impl.DefaultHttpResponseFactory r1 = org.apache.http.impl.DefaultHttpResponseFactory.INSTANCE
        L_0x00da:
            r6 = r1
            org.apache.http.protocol.HttpService r13 = new org.apache.http.protocol.HttpService
            org.apache.http.protocol.HttpExpectationVerifier r8 = r0.expectationVerifier
            r3 = r13
            r3.<init>(r4, r5, r6, r7, r8)
            javax.net.ServerSocketFactory r1 = r0.serverSocketFactory
            if (r1 != 0) goto L_0x00f4
            javax.net.ssl.SSLContext r1 = r0.sslContext
            if (r1 == 0) goto L_0x00f0
            javax.net.ssl.SSLServerSocketFactory r1 = r1.getServerSocketFactory()
            goto L_0x00f4
        L_0x00f0:
            javax.net.ServerSocketFactory r1 = javax.net.ServerSocketFactory.getDefault()
        L_0x00f4:
            r12 = r1
            org.apache.http.HttpConnectionFactory<? extends org.apache.http.impl.DefaultBHttpServerConnection> r1 = r0.connectionFactory
            if (r1 != 0) goto L_0x0106
            org.apache.http.config.ConnectionConfig r1 = r0.connectionConfig
            if (r1 == 0) goto L_0x0104
            org.apache.http.impl.DefaultBHttpServerConnectionFactory r3 = new org.apache.http.impl.DefaultBHttpServerConnectionFactory
            r3.<init>(r1)
            r14 = r3
            goto L_0x0107
        L_0x0104:
            org.apache.http.impl.DefaultBHttpServerConnectionFactory r1 = org.apache.http.impl.DefaultBHttpServerConnectionFactory.INSTANCE
        L_0x0106:
            r14 = r1
        L_0x0107:
            org.apache.http.ExceptionLogger r1 = r0.exceptionLogger
            if (r1 != 0) goto L_0x010d
            org.apache.http.ExceptionLogger r1 = org.apache.http.ExceptionLogger.NO_OP
        L_0x010d:
            r16 = r1
            org.apache.http.impl.bootstrap.HttpServer r1 = new org.apache.http.impl.bootstrap.HttpServer
            int r3 = r0.listenerPort
            if (r3 <= 0) goto L_0x0117
            r9 = r3
            goto L_0x0118
        L_0x0117:
            r9 = 0
        L_0x0118:
            java.net.InetAddress r10 = r0.localAddress
            org.apache.http.config.SocketConfig r2 = r0.socketConfig
            if (r2 == 0) goto L_0x011f
            goto L_0x0121
        L_0x011f:
            org.apache.http.config.SocketConfig r2 = org.apache.http.config.SocketConfig.DEFAULT
        L_0x0121:
            r11 = r2
            org.apache.http.impl.bootstrap.SSLServerSetupHandler r15 = r0.sslSetupHandler
            r8 = r1
            r8.<init>(r9, r10, r11, r12, r13, r14, r15, r16)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.bootstrap.ServerBootstrap.create():org.apache.http.impl.bootstrap.HttpServer");
    }
}
