package org.apache.http.impl.client;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.auth.AuthSchemeProvider;
import org.apache.http.client.AuthenticationStrategy;
import org.apache.http.client.BackoffManager;
import org.apache.http.client.ConnectionBackoffStrategy;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.client.UserTokenHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.InputStreamFactory;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Lookup;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.SchemePortResolver;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.conn.util.PublicSuffixMatcher;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.impl.execchain.ClientExecChain;
import org.apache.http.impl.execchain.MainClientExec;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.util.TextUtils;

public class HttpClientBuilder {
    private boolean authCachingDisabled;
    private Lookup<AuthSchemeProvider> authSchemeRegistry;
    private boolean automaticRetriesDisabled;
    private BackoffManager backoffManager;
    private List<Closeable> closeables;
    private HttpClientConnectionManager connManager;
    private boolean connManagerShared;
    private long connTimeToLive = -1;
    private TimeUnit connTimeToLiveTimeUnit = TimeUnit.MILLISECONDS;
    private ConnectionBackoffStrategy connectionBackoffStrategy;
    private boolean connectionStateDisabled;
    private boolean contentCompressionDisabled;
    private Map<String, InputStreamFactory> contentDecoderMap;
    private boolean cookieManagementDisabled;
    private Lookup<CookieSpecProvider> cookieSpecRegistry;
    private CookieStore cookieStore;
    private CredentialsProvider credentialsProvider;
    private ConnectionConfig defaultConnectionConfig;
    private Collection<? extends Header> defaultHeaders;
    private RequestConfig defaultRequestConfig;
    private SocketConfig defaultSocketConfig;
    private boolean defaultUserAgentDisabled;
    private DnsResolver dnsResolver;
    private boolean evictExpiredConnections;
    private boolean evictIdleConnections;
    private HostnameVerifier hostnameVerifier;
    private HttpProcessor httpprocessor;
    private ConnectionKeepAliveStrategy keepAliveStrategy;
    private int maxConnPerRoute = 0;
    private int maxConnTotal = 0;
    private long maxIdleTime;
    private TimeUnit maxIdleTimeUnit;
    private HttpHost proxy;
    private AuthenticationStrategy proxyAuthStrategy;
    private PublicSuffixMatcher publicSuffixMatcher;
    private boolean redirectHandlingDisabled;
    private RedirectStrategy redirectStrategy;
    private HttpRequestExecutor requestExec;
    private LinkedList<HttpRequestInterceptor> requestFirst;
    private LinkedList<HttpRequestInterceptor> requestLast;
    private LinkedList<HttpResponseInterceptor> responseFirst;
    private LinkedList<HttpResponseInterceptor> responseLast;
    private HttpRequestRetryHandler retryHandler;
    private ConnectionReuseStrategy reuseStrategy;
    private HttpRoutePlanner routePlanner;
    private SchemePortResolver schemePortResolver;
    private ServiceUnavailableRetryStrategy serviceUnavailStrategy;
    private SSLContext sslContext;
    private LayeredConnectionSocketFactory sslSocketFactory;
    private boolean systemProperties;
    private AuthenticationStrategy targetAuthStrategy;
    private String userAgent;
    private UserTokenHandler userTokenHandler;

    /* access modifiers changed from: protected */
    public ClientExecChain decorateMainExec(ClientExecChain clientExecChain) {
        return clientExecChain;
    }

    /* access modifiers changed from: protected */
    public ClientExecChain decorateProtocolExec(ClientExecChain clientExecChain) {
        return clientExecChain;
    }

    public static HttpClientBuilder create() {
        return new HttpClientBuilder();
    }

    protected HttpClientBuilder() {
    }

    public final HttpClientBuilder setRequestExecutor(HttpRequestExecutor httpRequestExecutor) {
        this.requestExec = httpRequestExecutor;
        return this;
    }

    @Deprecated
    public final HttpClientBuilder setHostnameVerifier(X509HostnameVerifier x509HostnameVerifier) {
        this.hostnameVerifier = x509HostnameVerifier;
        return this;
    }

    public final HttpClientBuilder setSSLHostnameVerifier(HostnameVerifier hostnameVerifier2) {
        this.hostnameVerifier = hostnameVerifier2;
        return this;
    }

    public final HttpClientBuilder setPublicSuffixMatcher(PublicSuffixMatcher publicSuffixMatcher2) {
        this.publicSuffixMatcher = publicSuffixMatcher2;
        return this;
    }

    @Deprecated
    public final HttpClientBuilder setSslcontext(SSLContext sSLContext) {
        return setSSLContext(sSLContext);
    }

    public final HttpClientBuilder setSSLContext(SSLContext sSLContext) {
        this.sslContext = sSLContext;
        return this;
    }

    public final HttpClientBuilder setSSLSocketFactory(LayeredConnectionSocketFactory layeredConnectionSocketFactory) {
        this.sslSocketFactory = layeredConnectionSocketFactory;
        return this;
    }

    public final HttpClientBuilder setMaxConnTotal(int i) {
        this.maxConnTotal = i;
        return this;
    }

    public final HttpClientBuilder setMaxConnPerRoute(int i) {
        this.maxConnPerRoute = i;
        return this;
    }

    public final HttpClientBuilder setDefaultSocketConfig(SocketConfig socketConfig) {
        this.defaultSocketConfig = socketConfig;
        return this;
    }

    public final HttpClientBuilder setDefaultConnectionConfig(ConnectionConfig connectionConfig) {
        this.defaultConnectionConfig = connectionConfig;
        return this;
    }

    public final HttpClientBuilder setConnectionTimeToLive(long j, TimeUnit timeUnit) {
        this.connTimeToLive = j;
        this.connTimeToLiveTimeUnit = timeUnit;
        return this;
    }

    public final HttpClientBuilder setConnectionManager(HttpClientConnectionManager httpClientConnectionManager) {
        this.connManager = httpClientConnectionManager;
        return this;
    }

    public final HttpClientBuilder setConnectionManagerShared(boolean z) {
        this.connManagerShared = z;
        return this;
    }

    public final HttpClientBuilder setConnectionReuseStrategy(ConnectionReuseStrategy connectionReuseStrategy) {
        this.reuseStrategy = connectionReuseStrategy;
        return this;
    }

    public final HttpClientBuilder setKeepAliveStrategy(ConnectionKeepAliveStrategy connectionKeepAliveStrategy) {
        this.keepAliveStrategy = connectionKeepAliveStrategy;
        return this;
    }

    public final HttpClientBuilder setTargetAuthenticationStrategy(AuthenticationStrategy authenticationStrategy) {
        this.targetAuthStrategy = authenticationStrategy;
        return this;
    }

    public final HttpClientBuilder setProxyAuthenticationStrategy(AuthenticationStrategy authenticationStrategy) {
        this.proxyAuthStrategy = authenticationStrategy;
        return this;
    }

    public final HttpClientBuilder setUserTokenHandler(UserTokenHandler userTokenHandler2) {
        this.userTokenHandler = userTokenHandler2;
        return this;
    }

    public final HttpClientBuilder disableConnectionState() {
        this.connectionStateDisabled = true;
        return this;
    }

    public final HttpClientBuilder setSchemePortResolver(SchemePortResolver schemePortResolver2) {
        this.schemePortResolver = schemePortResolver2;
        return this;
    }

    public final HttpClientBuilder setUserAgent(String str) {
        this.userAgent = str;
        return this;
    }

    public final HttpClientBuilder setDefaultHeaders(Collection<? extends Header> collection) {
        this.defaultHeaders = collection;
        return this;
    }

    public final HttpClientBuilder addInterceptorFirst(HttpResponseInterceptor httpResponseInterceptor) {
        if (httpResponseInterceptor == null) {
            return this;
        }
        if (this.responseFirst == null) {
            this.responseFirst = new LinkedList<>();
        }
        this.responseFirst.addFirst(httpResponseInterceptor);
        return this;
    }

    public final HttpClientBuilder addInterceptorLast(HttpResponseInterceptor httpResponseInterceptor) {
        if (httpResponseInterceptor == null) {
            return this;
        }
        if (this.responseLast == null) {
            this.responseLast = new LinkedList<>();
        }
        this.responseLast.addLast(httpResponseInterceptor);
        return this;
    }

    public final HttpClientBuilder addInterceptorFirst(HttpRequestInterceptor httpRequestInterceptor) {
        if (httpRequestInterceptor == null) {
            return this;
        }
        if (this.requestFirst == null) {
            this.requestFirst = new LinkedList<>();
        }
        this.requestFirst.addFirst(httpRequestInterceptor);
        return this;
    }

    public final HttpClientBuilder addInterceptorLast(HttpRequestInterceptor httpRequestInterceptor) {
        if (httpRequestInterceptor == null) {
            return this;
        }
        if (this.requestLast == null) {
            this.requestLast = new LinkedList<>();
        }
        this.requestLast.addLast(httpRequestInterceptor);
        return this;
    }

    public final HttpClientBuilder disableCookieManagement() {
        this.cookieManagementDisabled = true;
        return this;
    }

    public final HttpClientBuilder disableContentCompression() {
        this.contentCompressionDisabled = true;
        return this;
    }

    public final HttpClientBuilder disableAuthCaching() {
        this.authCachingDisabled = true;
        return this;
    }

    public final HttpClientBuilder setHttpProcessor(HttpProcessor httpProcessor) {
        this.httpprocessor = httpProcessor;
        return this;
    }

    public final HttpClientBuilder setDnsResolver(DnsResolver dnsResolver2) {
        this.dnsResolver = dnsResolver2;
        return this;
    }

    public final HttpClientBuilder setRetryHandler(HttpRequestRetryHandler httpRequestRetryHandler) {
        this.retryHandler = httpRequestRetryHandler;
        return this;
    }

    public final HttpClientBuilder disableAutomaticRetries() {
        this.automaticRetriesDisabled = true;
        return this;
    }

    public final HttpClientBuilder setProxy(HttpHost httpHost) {
        this.proxy = httpHost;
        return this;
    }

    public final HttpClientBuilder setRoutePlanner(HttpRoutePlanner httpRoutePlanner) {
        this.routePlanner = httpRoutePlanner;
        return this;
    }

    public final HttpClientBuilder setRedirectStrategy(RedirectStrategy redirectStrategy2) {
        this.redirectStrategy = redirectStrategy2;
        return this;
    }

    public final HttpClientBuilder disableRedirectHandling() {
        this.redirectHandlingDisabled = true;
        return this;
    }

    public final HttpClientBuilder setConnectionBackoffStrategy(ConnectionBackoffStrategy connectionBackoffStrategy2) {
        this.connectionBackoffStrategy = connectionBackoffStrategy2;
        return this;
    }

    public final HttpClientBuilder setBackoffManager(BackoffManager backoffManager2) {
        this.backoffManager = backoffManager2;
        return this;
    }

    public final HttpClientBuilder setServiceUnavailableRetryStrategy(ServiceUnavailableRetryStrategy serviceUnavailableRetryStrategy) {
        this.serviceUnavailStrategy = serviceUnavailableRetryStrategy;
        return this;
    }

    public final HttpClientBuilder setDefaultCookieStore(CookieStore cookieStore2) {
        this.cookieStore = cookieStore2;
        return this;
    }

    public final HttpClientBuilder setDefaultCredentialsProvider(CredentialsProvider credentialsProvider2) {
        this.credentialsProvider = credentialsProvider2;
        return this;
    }

    public final HttpClientBuilder setDefaultAuthSchemeRegistry(Lookup<AuthSchemeProvider> lookup) {
        this.authSchemeRegistry = lookup;
        return this;
    }

    public final HttpClientBuilder setDefaultCookieSpecRegistry(Lookup<CookieSpecProvider> lookup) {
        this.cookieSpecRegistry = lookup;
        return this;
    }

    public final HttpClientBuilder setContentDecoderRegistry(Map<String, InputStreamFactory> map) {
        this.contentDecoderMap = map;
        return this;
    }

    public final HttpClientBuilder setDefaultRequestConfig(RequestConfig requestConfig) {
        this.defaultRequestConfig = requestConfig;
        return this;
    }

    public final HttpClientBuilder useSystemProperties() {
        this.systemProperties = true;
        return this;
    }

    public final HttpClientBuilder evictExpiredConnections() {
        this.evictExpiredConnections = true;
        return this;
    }

    @Deprecated
    public final HttpClientBuilder evictIdleConnections(Long l, TimeUnit timeUnit) {
        return evictIdleConnections(l.longValue(), timeUnit);
    }

    public final HttpClientBuilder evictIdleConnections(long j, TimeUnit timeUnit) {
        this.evictIdleConnections = true;
        this.maxIdleTime = j;
        this.maxIdleTimeUnit = timeUnit;
        return this;
    }

    public final HttpClientBuilder disableDefaultUserAgent() {
        this.defaultUserAgentDisabled = true;
        return this;
    }

    /* access modifiers changed from: protected */
    public ClientExecChain createMainExec(HttpRequestExecutor httpRequestExecutor, HttpClientConnectionManager httpClientConnectionManager, ConnectionReuseStrategy connectionReuseStrategy, ConnectionKeepAliveStrategy connectionKeepAliveStrategy, HttpProcessor httpProcessor, AuthenticationStrategy authenticationStrategy, AuthenticationStrategy authenticationStrategy2, UserTokenHandler userTokenHandler2) {
        MainClientExec mainClientExec = new MainClientExec(httpRequestExecutor, httpClientConnectionManager, connectionReuseStrategy, connectionKeepAliveStrategy, httpProcessor, authenticationStrategy, authenticationStrategy2, userTokenHandler2);
        return mainClientExec;
    }

    /* access modifiers changed from: protected */
    public void addCloseable(Closeable closeable) {
        if (closeable != null) {
            if (this.closeables == null) {
                this.closeables = new ArrayList();
            }
            this.closeables.add(closeable);
        }
    }

    private static String[] split(String str) {
        if (TextUtils.isBlank(str)) {
            return null;
        }
        return str.split(" *, *");
    }

    /* JADX WARNING: type inference failed for: r0v4, types: [org.apache.http.conn.HttpClientConnectionManager] */
    /* JADX WARNING: type inference failed for: r15v0 */
    /* JADX WARNING: type inference failed for: r2v3, types: [org.apache.http.conn.HttpClientConnectionManager] */
    /* JADX WARNING: type inference failed for: r12v1 */
    /* JADX WARNING: type inference failed for: r15v1, types: [org.apache.http.conn.HttpClientConnectionManager] */
    /* JADX WARNING: type inference failed for: r12v2 */
    /* JADX WARNING: type inference failed for: r12v3, types: [org.apache.http.conn.HttpClientConnectionManager] */
    /* JADX WARNING: type inference failed for: r14v2, types: [org.apache.http.conn.HttpClientConnectionManager] */
    /* JADX WARNING: type inference failed for: r12v6 */
    /* JADX WARNING: type inference failed for: r12v7 */
    /* JADX WARNING: type inference failed for: r15v3 */
    /* JADX WARNING: type inference failed for: r15v5 */
    /* JADX WARNING: type inference failed for: r12v8 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 7 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.apache.http.impl.client.CloseableHttpClient build() {
        /*
            r23 = this;
            r9 = r23
            org.apache.http.conn.util.PublicSuffixMatcher r0 = r9.publicSuffixMatcher
            if (r0 != 0) goto L_0x000a
            org.apache.http.conn.util.PublicSuffixMatcher r0 = org.apache.http.conn.util.PublicSuffixMatcherLoader.getDefault()
        L_0x000a:
            r10 = r0
            org.apache.http.protocol.HttpRequestExecutor r0 = r9.requestExec
            if (r0 != 0) goto L_0x0014
            org.apache.http.protocol.HttpRequestExecutor r0 = new org.apache.http.protocol.HttpRequestExecutor
            r0.<init>()
        L_0x0014:
            r1 = r0
            org.apache.http.conn.HttpClientConnectionManager r0 = r9.connManager
            java.lang.String r2 = "http.keepAlive"
            r11 = 2
            java.lang.String r3 = "true"
            if (r0 != 0) goto L_0x00e2
            org.apache.http.conn.socket.LayeredConnectionSocketFactory r0 = r9.sslSocketFactory
            if (r0 != 0) goto L_0x0070
            boolean r0 = r9.systemProperties
            if (r0 == 0) goto L_0x0031
            java.lang.String r0 = "https.protocols"
            java.lang.String r0 = java.lang.System.getProperty(r0)
            java.lang.String[] r0 = split(r0)
            goto L_0x0032
        L_0x0031:
            r0 = 0
        L_0x0032:
            boolean r4 = r9.systemProperties
            if (r4 == 0) goto L_0x0041
            java.lang.String r4 = "https.cipherSuites"
            java.lang.String r4 = java.lang.System.getProperty(r4)
            java.lang.String[] r4 = split(r4)
            goto L_0x0042
        L_0x0041:
            r4 = 0
        L_0x0042:
            javax.net.ssl.HostnameVerifier r5 = r9.hostnameVerifier
            if (r5 != 0) goto L_0x004b
            org.apache.http.conn.ssl.DefaultHostnameVerifier r5 = new org.apache.http.conn.ssl.DefaultHostnameVerifier
            r5.<init>(r10)
        L_0x004b:
            javax.net.ssl.SSLContext r6 = r9.sslContext
            if (r6 == 0) goto L_0x0056
            org.apache.http.conn.ssl.SSLConnectionSocketFactory r7 = new org.apache.http.conn.ssl.SSLConnectionSocketFactory
            r7.<init>(r6, r0, r4, r5)
            r0 = r7
            goto L_0x0070
        L_0x0056:
            boolean r6 = r9.systemProperties
            if (r6 == 0) goto L_0x0067
            org.apache.http.conn.ssl.SSLConnectionSocketFactory r6 = new org.apache.http.conn.ssl.SSLConnectionSocketFactory
            javax.net.SocketFactory r7 = javax.net.ssl.SSLSocketFactory.getDefault()
            javax.net.ssl.SSLSocketFactory r7 = (javax.net.ssl.SSLSocketFactory) r7
            r6.<init>(r7, r0, r4, r5)
            r0 = r6
            goto L_0x0070
        L_0x0067:
            org.apache.http.conn.ssl.SSLConnectionSocketFactory r0 = new org.apache.http.conn.ssl.SSLConnectionSocketFactory
            javax.net.ssl.SSLContext r4 = org.apache.http.ssl.SSLContexts.createDefault()
            r0.<init>(r4, r5)
        L_0x0070:
            org.apache.http.impl.conn.PoolingHttpClientConnectionManager r4 = new org.apache.http.impl.conn.PoolingHttpClientConnectionManager
            org.apache.http.config.RegistryBuilder r5 = org.apache.http.config.RegistryBuilder.create()
            org.apache.http.conn.socket.PlainConnectionSocketFactory r6 = org.apache.http.conn.socket.PlainConnectionSocketFactory.getSocketFactory()
            java.lang.String r7 = "http"
            org.apache.http.config.RegistryBuilder r5 = r5.register(r7, r6)
            java.lang.String r6 = "https"
            org.apache.http.config.RegistryBuilder r0 = r5.register(r6, r0)
            org.apache.http.config.Registry r14 = r0.build()
            r15 = 0
            r16 = 0
            org.apache.http.conn.DnsResolver r0 = r9.dnsResolver
            long r5 = r9.connTimeToLive
            java.util.concurrent.TimeUnit r7 = r9.connTimeToLiveTimeUnit
            if (r7 == 0) goto L_0x0096
            goto L_0x0098
        L_0x0096:
            java.util.concurrent.TimeUnit r7 = java.util.concurrent.TimeUnit.MILLISECONDS
        L_0x0098:
            r20 = r7
            r13 = r4
            r17 = r0
            r18 = r5
            r13.<init>(r14, r15, r16, r17, r18, r20)
            org.apache.http.config.SocketConfig r0 = r9.defaultSocketConfig
            if (r0 == 0) goto L_0x00a9
            r4.setDefaultSocketConfig(r0)
        L_0x00a9:
            org.apache.http.config.ConnectionConfig r0 = r9.defaultConnectionConfig
            if (r0 == 0) goto L_0x00b0
            r4.setDefaultConnectionConfig(r0)
        L_0x00b0:
            boolean r0 = r9.systemProperties
            if (r0 == 0) goto L_0x00d2
            java.lang.String r0 = java.lang.System.getProperty(r2, r3)
            boolean r0 = r3.equalsIgnoreCase(r0)
            if (r0 == 0) goto L_0x00d2
            java.lang.String r0 = "http.maxConnections"
            java.lang.String r5 = "5"
            java.lang.String r0 = java.lang.System.getProperty(r0, r5)
            int r0 = java.lang.Integer.parseInt(r0)
            r4.setDefaultMaxPerRoute(r0)
            int r0 = r0 * 2
            r4.setMaxTotal(r0)
        L_0x00d2:
            int r0 = r9.maxConnTotal
            if (r0 <= 0) goto L_0x00d9
            r4.setMaxTotal(r0)
        L_0x00d9:
            int r0 = r9.maxConnPerRoute
            if (r0 <= 0) goto L_0x00e0
            r4.setDefaultMaxPerRoute(r0)
        L_0x00e0:
            r15 = r4
            goto L_0x00e3
        L_0x00e2:
            r15 = r0
        L_0x00e3:
            org.apache.http.ConnectionReuseStrategy r0 = r9.reuseStrategy
            if (r0 != 0) goto L_0x00fd
            boolean r0 = r9.systemProperties
            if (r0 == 0) goto L_0x00fb
            java.lang.String r0 = java.lang.System.getProperty(r2, r3)
            boolean r0 = r3.equalsIgnoreCase(r0)
            if (r0 == 0) goto L_0x00f8
            org.apache.http.impl.client.DefaultClientConnectionReuseStrategy r0 = org.apache.http.impl.client.DefaultClientConnectionReuseStrategy.INSTANCE
            goto L_0x00fd
        L_0x00f8:
            org.apache.http.impl.NoConnectionReuseStrategy r0 = org.apache.http.impl.NoConnectionReuseStrategy.INSTANCE
            goto L_0x00fd
        L_0x00fb:
            org.apache.http.impl.client.DefaultClientConnectionReuseStrategy r0 = org.apache.http.impl.client.DefaultClientConnectionReuseStrategy.INSTANCE
        L_0x00fd:
            r3 = r0
            org.apache.http.conn.ConnectionKeepAliveStrategy r0 = r9.keepAliveStrategy
            if (r0 != 0) goto L_0x0104
            org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy r0 = org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy.INSTANCE
        L_0x0104:
            r4 = r0
            org.apache.http.client.AuthenticationStrategy r0 = r9.targetAuthStrategy
            if (r0 != 0) goto L_0x010b
            org.apache.http.impl.client.TargetAuthenticationStrategy r0 = org.apache.http.impl.client.TargetAuthenticationStrategy.INSTANCE
        L_0x010b:
            r6 = r0
            org.apache.http.client.AuthenticationStrategy r0 = r9.proxyAuthStrategy
            if (r0 != 0) goto L_0x0112
            org.apache.http.impl.client.ProxyAuthenticationStrategy r0 = org.apache.http.impl.client.ProxyAuthenticationStrategy.INSTANCE
        L_0x0112:
            r7 = r0
            org.apache.http.client.UserTokenHandler r0 = r9.userTokenHandler
            if (r0 != 0) goto L_0x0120
            boolean r0 = r9.connectionStateDisabled
            if (r0 != 0) goto L_0x011e
            org.apache.http.impl.client.DefaultUserTokenHandler r0 = org.apache.http.impl.client.DefaultUserTokenHandler.INSTANCE
            goto L_0x0120
        L_0x011e:
            org.apache.http.impl.client.NoopUserTokenHandler r0 = org.apache.http.impl.client.NoopUserTokenHandler.INSTANCE
        L_0x0120:
            r8 = r0
            java.lang.String r0 = r9.userAgent
            if (r0 != 0) goto L_0x0141
            boolean r2 = r9.systemProperties
            if (r2 == 0) goto L_0x012f
            java.lang.String r0 = "http.agent"
            java.lang.String r0 = java.lang.System.getProperty(r0)
        L_0x012f:
            if (r0 != 0) goto L_0x0141
            boolean r2 = r9.defaultUserAgentDisabled
            if (r2 != 0) goto L_0x0141
            java.lang.Class r0 = r23.getClass()
            java.lang.String r2 = "Apache-HttpClient"
            java.lang.String r5 = "org.apache.http.client"
            java.lang.String r0 = org.apache.http.util.VersionInfo.getUserAgent(r2, r5, r0)
        L_0x0141:
            r13 = r0
            org.apache.http.protocol.ImmutableHttpProcessor r5 = new org.apache.http.protocol.ImmutableHttpProcessor
            org.apache.http.HttpRequestInterceptor[] r0 = new org.apache.http.HttpRequestInterceptor[r11]
            org.apache.http.protocol.RequestTargetHost r2 = new org.apache.http.protocol.RequestTargetHost
            r2.<init>()
            r14 = 0
            r0[r14] = r2
            org.apache.http.protocol.RequestUserAgent r2 = new org.apache.http.protocol.RequestUserAgent
            r2.<init>(r13)
            r12 = 1
            r0[r12] = r2
            r5.<init>(r0)
            r0 = r23
            r2 = r15
            org.apache.http.impl.execchain.ClientExecChain r0 = r0.createMainExec(r1, r2, r3, r4, r5, r6, r7, r8)
            org.apache.http.impl.execchain.ClientExecChain r0 = r9.decorateMainExec(r0)
            org.apache.http.protocol.HttpProcessor r1 = r9.httpprocessor
            if (r1 != 0) goto L_0x0293
            org.apache.http.protocol.HttpProcessorBuilder r1 = org.apache.http.protocol.HttpProcessorBuilder.create()
            java.util.LinkedList<org.apache.http.HttpRequestInterceptor> r2 = r9.requestFirst
            if (r2 == 0) goto L_0x0184
            java.util.Iterator r2 = r2.iterator()
        L_0x0174:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0184
            java.lang.Object r3 = r2.next()
            org.apache.http.HttpRequestInterceptor r3 = (org.apache.http.HttpRequestInterceptor) r3
            r1.addFirst(r3)
            goto L_0x0174
        L_0x0184:
            java.util.LinkedList<org.apache.http.HttpResponseInterceptor> r2 = r9.responseFirst
            if (r2 == 0) goto L_0x019c
            java.util.Iterator r2 = r2.iterator()
        L_0x018c:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x019c
            java.lang.Object r3 = r2.next()
            org.apache.http.HttpResponseInterceptor r3 = (org.apache.http.HttpResponseInterceptor) r3
            r1.addFirst(r3)
            goto L_0x018c
        L_0x019c:
            r2 = 6
            org.apache.http.HttpRequestInterceptor[] r2 = new org.apache.http.HttpRequestInterceptor[r2]
            org.apache.http.client.protocol.RequestDefaultHeaders r3 = new org.apache.http.client.protocol.RequestDefaultHeaders
            java.util.Collection<? extends org.apache.http.Header> r4 = r9.defaultHeaders
            r3.<init>(r4)
            r2[r14] = r3
            org.apache.http.protocol.RequestContent r3 = new org.apache.http.protocol.RequestContent
            r3.<init>()
            r2[r12] = r3
            org.apache.http.protocol.RequestTargetHost r3 = new org.apache.http.protocol.RequestTargetHost
            r3.<init>()
            r2[r11] = r3
            r3 = 3
            org.apache.http.client.protocol.RequestClientConnControl r4 = new org.apache.http.client.protocol.RequestClientConnControl
            r4.<init>()
            r2[r3] = r4
            r3 = 4
            org.apache.http.protocol.RequestUserAgent r4 = new org.apache.http.protocol.RequestUserAgent
            r4.<init>(r13)
            r2[r3] = r4
            r3 = 5
            org.apache.http.client.protocol.RequestExpectContinue r4 = new org.apache.http.client.protocol.RequestExpectContinue
            r4.<init>()
            r2[r3] = r4
            r1.addAll(r2)
            boolean r2 = r9.cookieManagementDisabled
            if (r2 != 0) goto L_0x01dd
            org.apache.http.client.protocol.RequestAddCookies r2 = new org.apache.http.client.protocol.RequestAddCookies
            r2.<init>()
            r1.add(r2)
        L_0x01dd:
            boolean r2 = r9.contentCompressionDisabled
            if (r2 != 0) goto L_0x0202
            java.util.Map<java.lang.String, org.apache.http.client.entity.InputStreamFactory> r2 = r9.contentDecoderMap
            if (r2 == 0) goto L_0x01fa
            java.util.ArrayList r3 = new java.util.ArrayList
            java.util.Set r2 = r2.keySet()
            r3.<init>(r2)
            java.util.Collections.sort(r3)
            org.apache.http.client.protocol.RequestAcceptEncoding r2 = new org.apache.http.client.protocol.RequestAcceptEncoding
            r2.<init>(r3)
            r1.add(r2)
            goto L_0x0202
        L_0x01fa:
            org.apache.http.client.protocol.RequestAcceptEncoding r2 = new org.apache.http.client.protocol.RequestAcceptEncoding
            r2.<init>()
            r1.add(r2)
        L_0x0202:
            boolean r2 = r9.authCachingDisabled
            if (r2 != 0) goto L_0x020e
            org.apache.http.client.protocol.RequestAuthCache r2 = new org.apache.http.client.protocol.RequestAuthCache
            r2.<init>()
            r1.add(r2)
        L_0x020e:
            boolean r2 = r9.cookieManagementDisabled
            if (r2 != 0) goto L_0x021a
            org.apache.http.client.protocol.ResponseProcessCookies r2 = new org.apache.http.client.protocol.ResponseProcessCookies
            r2.<init>()
            r1.add(r2)
        L_0x021a:
            boolean r2 = r9.contentCompressionDisabled
            if (r2 != 0) goto L_0x025f
            java.util.Map<java.lang.String, org.apache.http.client.entity.InputStreamFactory> r2 = r9.contentDecoderMap
            if (r2 == 0) goto L_0x0257
            org.apache.http.config.RegistryBuilder r2 = org.apache.http.config.RegistryBuilder.create()
            java.util.Map<java.lang.String, org.apache.http.client.entity.InputStreamFactory> r3 = r9.contentDecoderMap
            java.util.Set r3 = r3.entrySet()
            java.util.Iterator r3 = r3.iterator()
        L_0x0230:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x024a
            java.lang.Object r4 = r3.next()
            java.util.Map$Entry r4 = (java.util.Map.Entry) r4
            java.lang.Object r5 = r4.getKey()
            java.lang.String r5 = (java.lang.String) r5
            java.lang.Object r4 = r4.getValue()
            r2.register(r5, r4)
            goto L_0x0230
        L_0x024a:
            org.apache.http.client.protocol.ResponseContentEncoding r3 = new org.apache.http.client.protocol.ResponseContentEncoding
            org.apache.http.config.Registry r2 = r2.build()
            r3.<init>(r2)
            r1.add(r3)
            goto L_0x025f
        L_0x0257:
            org.apache.http.client.protocol.ResponseContentEncoding r2 = new org.apache.http.client.protocol.ResponseContentEncoding
            r2.<init>()
            r1.add(r2)
        L_0x025f:
            java.util.LinkedList<org.apache.http.HttpRequestInterceptor> r2 = r9.requestLast
            if (r2 == 0) goto L_0x0277
            java.util.Iterator r2 = r2.iterator()
        L_0x0267:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0277
            java.lang.Object r3 = r2.next()
            org.apache.http.HttpRequestInterceptor r3 = (org.apache.http.HttpRequestInterceptor) r3
            r1.addLast(r3)
            goto L_0x0267
        L_0x0277:
            java.util.LinkedList<org.apache.http.HttpResponseInterceptor> r2 = r9.responseLast
            if (r2 == 0) goto L_0x028f
            java.util.Iterator r2 = r2.iterator()
        L_0x027f:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x028f
            java.lang.Object r3 = r2.next()
            org.apache.http.HttpResponseInterceptor r3 = (org.apache.http.HttpResponseInterceptor) r3
            r1.addLast(r3)
            goto L_0x027f
        L_0x028f:
            org.apache.http.protocol.HttpProcessor r1 = r1.build()
        L_0x0293:
            org.apache.http.impl.execchain.ProtocolExec r2 = new org.apache.http.impl.execchain.ProtocolExec
            r2.<init>(r0, r1)
            org.apache.http.impl.execchain.ClientExecChain r0 = r9.decorateProtocolExec(r2)
            boolean r1 = r9.automaticRetriesDisabled
            if (r1 != 0) goto L_0x02ac
            org.apache.http.client.HttpRequestRetryHandler r1 = r9.retryHandler
            if (r1 != 0) goto L_0x02a6
            org.apache.http.impl.client.DefaultHttpRequestRetryHandler r1 = org.apache.http.impl.client.DefaultHttpRequestRetryHandler.INSTANCE
        L_0x02a6:
            org.apache.http.impl.execchain.RetryExec r2 = new org.apache.http.impl.execchain.RetryExec
            r2.<init>(r0, r1)
            r0 = r2
        L_0x02ac:
            org.apache.http.conn.routing.HttpRoutePlanner r1 = r9.routePlanner
            if (r1 != 0) goto L_0x02d5
            org.apache.http.conn.SchemePortResolver r1 = r9.schemePortResolver
            if (r1 != 0) goto L_0x02b6
            org.apache.http.impl.conn.DefaultSchemePortResolver r1 = org.apache.http.impl.conn.DefaultSchemePortResolver.INSTANCE
        L_0x02b6:
            org.apache.http.HttpHost r2 = r9.proxy
            if (r2 == 0) goto L_0x02c1
            org.apache.http.impl.conn.DefaultProxyRoutePlanner r3 = new org.apache.http.impl.conn.DefaultProxyRoutePlanner
            r3.<init>(r2, r1)
            r2 = r3
            goto L_0x02d6
        L_0x02c1:
            boolean r2 = r9.systemProperties
            if (r2 == 0) goto L_0x02cf
            org.apache.http.impl.conn.SystemDefaultRoutePlanner r2 = new org.apache.http.impl.conn.SystemDefaultRoutePlanner
            java.net.ProxySelector r3 = java.net.ProxySelector.getDefault()
            r2.<init>(r1, r3)
            goto L_0x02d6
        L_0x02cf:
            org.apache.http.impl.conn.DefaultRoutePlanner r2 = new org.apache.http.impl.conn.DefaultRoutePlanner
            r2.<init>(r1)
            goto L_0x02d6
        L_0x02d5:
            r2 = r1
        L_0x02d6:
            org.apache.http.client.ServiceUnavailableRetryStrategy r1 = r9.serviceUnavailStrategy
            if (r1 == 0) goto L_0x02e0
            org.apache.http.impl.execchain.ServiceUnavailableRetryExec r3 = new org.apache.http.impl.execchain.ServiceUnavailableRetryExec
            r3.<init>(r0, r1)
            r0 = r3
        L_0x02e0:
            boolean r1 = r9.redirectHandlingDisabled
            if (r1 != 0) goto L_0x02f0
            org.apache.http.client.RedirectStrategy r1 = r9.redirectStrategy
            if (r1 != 0) goto L_0x02ea
            org.apache.http.impl.client.DefaultRedirectStrategy r1 = org.apache.http.impl.client.DefaultRedirectStrategy.INSTANCE
        L_0x02ea:
            org.apache.http.impl.execchain.RedirectExec r3 = new org.apache.http.impl.execchain.RedirectExec
            r3.<init>(r0, r2, r1)
            r0 = r3
        L_0x02f0:
            org.apache.http.client.BackoffManager r1 = r9.backoffManager
            if (r1 == 0) goto L_0x02fe
            org.apache.http.client.ConnectionBackoffStrategy r3 = r9.connectionBackoffStrategy
            if (r3 == 0) goto L_0x02fe
            org.apache.http.impl.execchain.BackoffStrategyExec r4 = new org.apache.http.impl.execchain.BackoffStrategyExec
            r4.<init>(r0, r3, r1)
            r0 = r4
        L_0x02fe:
            org.apache.http.config.Lookup<org.apache.http.auth.AuthSchemeProvider> r1 = r9.authSchemeRegistry
            if (r1 != 0) goto L_0x0341
            org.apache.http.config.RegistryBuilder r1 = org.apache.http.config.RegistryBuilder.create()
            org.apache.http.impl.auth.BasicSchemeFactory r3 = new org.apache.http.impl.auth.BasicSchemeFactory
            r3.<init>()
            java.lang.String r4 = "Basic"
            org.apache.http.config.RegistryBuilder r1 = r1.register(r4, r3)
            org.apache.http.impl.auth.DigestSchemeFactory r3 = new org.apache.http.impl.auth.DigestSchemeFactory
            r3.<init>()
            java.lang.String r4 = "Digest"
            org.apache.http.config.RegistryBuilder r1 = r1.register(r4, r3)
            org.apache.http.impl.auth.NTLMSchemeFactory r3 = new org.apache.http.impl.auth.NTLMSchemeFactory
            r3.<init>()
            java.lang.String r4 = "NTLM"
            org.apache.http.config.RegistryBuilder r1 = r1.register(r4, r3)
            org.apache.http.impl.auth.SPNegoSchemeFactory r3 = new org.apache.http.impl.auth.SPNegoSchemeFactory
            r3.<init>()
            java.lang.String r4 = "Negotiate"
            org.apache.http.config.RegistryBuilder r1 = r1.register(r4, r3)
            org.apache.http.impl.auth.KerberosSchemeFactory r3 = new org.apache.http.impl.auth.KerberosSchemeFactory
            r3.<init>()
            java.lang.String r4 = "Kerberos"
            org.apache.http.config.RegistryBuilder r1 = r1.register(r4, r3)
            org.apache.http.config.Registry r1 = r1.build()
        L_0x0341:
            org.apache.http.config.Lookup<org.apache.http.cookie.CookieSpecProvider> r3 = r9.cookieSpecRegistry
            if (r3 != 0) goto L_0x0349
            org.apache.http.config.Lookup r3 = org.apache.http.impl.client.CookieSpecRegistries.createDefault(r10)
        L_0x0349:
            org.apache.http.client.CookieStore r4 = r9.cookieStore
            if (r4 != 0) goto L_0x0352
            org.apache.http.impl.client.BasicCookieStore r4 = new org.apache.http.impl.client.BasicCookieStore
            r4.<init>()
        L_0x0352:
            org.apache.http.client.CredentialsProvider r5 = r9.credentialsProvider
            if (r5 != 0) goto L_0x0365
            boolean r5 = r9.systemProperties
            if (r5 == 0) goto L_0x0360
            org.apache.http.impl.client.SystemDefaultCredentialsProvider r5 = new org.apache.http.impl.client.SystemDefaultCredentialsProvider
            r5.<init>()
            goto L_0x0365
        L_0x0360:
            org.apache.http.impl.client.BasicCredentialsProvider r5 = new org.apache.http.impl.client.BasicCredentialsProvider
            r5.<init>()
        L_0x0365:
            java.util.List<java.io.Closeable> r6 = r9.closeables
            if (r6 == 0) goto L_0x0371
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>(r6)
            r16 = r7
            goto L_0x0373
        L_0x0371:
            r16 = 0
        L_0x0373:
            boolean r6 = r9.connManagerShared
            if (r6 != 0) goto L_0x03c7
            if (r16 != 0) goto L_0x037f
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>(r12)
            goto L_0x0381
        L_0x037f:
            r6 = r16
        L_0x0381:
            boolean r7 = r9.evictExpiredConnections
            if (r7 != 0) goto L_0x038c
            boolean r7 = r9.evictIdleConnections
            if (r7 == 0) goto L_0x038a
            goto L_0x038c
        L_0x038a:
            r12 = r15
            goto L_0x03bc
        L_0x038c:
            org.apache.http.impl.client.IdleConnectionEvictor r7 = new org.apache.http.impl.client.IdleConnectionEvictor
            long r10 = r9.maxIdleTime
            r12 = 0
            int r8 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r8 <= 0) goto L_0x0397
            goto L_0x0399
        L_0x0397:
            r10 = 10
        L_0x0399:
            java.util.concurrent.TimeUnit r8 = r9.maxIdleTimeUnit
            if (r8 == 0) goto L_0x039e
            goto L_0x03a0
        L_0x039e:
            java.util.concurrent.TimeUnit r8 = java.util.concurrent.TimeUnit.SECONDS
        L_0x03a0:
            r17 = r8
            long r12 = r9.maxIdleTime
            java.util.concurrent.TimeUnit r8 = r9.maxIdleTimeUnit
            r18 = r12
            r13 = r7
            r14 = r15
            r12 = r15
            r15 = r10
            r20 = r8
            r13.<init>(r14, r15, r17, r18, r20)
            org.apache.http.impl.client.HttpClientBuilder$1 r8 = new org.apache.http.impl.client.HttpClientBuilder$1
            r8.<init>(r7)
            r6.add(r8)
            r7.start()
        L_0x03bc:
            org.apache.http.impl.client.HttpClientBuilder$2 r7 = new org.apache.http.impl.client.HttpClientBuilder$2
            r7.<init>(r12)
            r6.add(r7)
            r22 = r6
            goto L_0x03ca
        L_0x03c7:
            r12 = r15
            r22 = r16
        L_0x03ca:
            org.apache.http.impl.client.InternalHttpClient r6 = new org.apache.http.impl.client.InternalHttpClient
            org.apache.http.client.config.RequestConfig r7 = r9.defaultRequestConfig
            if (r7 == 0) goto L_0x03d1
            goto L_0x03d3
        L_0x03d1:
            org.apache.http.client.config.RequestConfig r7 = org.apache.http.client.config.RequestConfig.DEFAULT
        L_0x03d3:
            r21 = r7
            r13 = r6
            r14 = r0
            r15 = r12
            r16 = r2
            r17 = r3
            r18 = r1
            r19 = r4
            r20 = r5
            r13.<init>(r14, r15, r16, r17, r18, r19, r20, r21, r22)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.client.HttpClientBuilder.build():org.apache.http.impl.client.CloseableHttpClient");
    }
}
