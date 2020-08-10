package org.apache.http.impl.client;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.auth.AuthSchemeProvider;
import org.apache.http.auth.AuthState;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.Configurable;
import org.apache.http.client.methods.HttpExecutionAware;
import org.apache.http.client.methods.HttpRequestWrapper;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.HttpClientParamConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Lookup;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ClientConnectionRequest;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.ManagedClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.impl.execchain.ClientExecChain;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpParamsNames;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

class InternalHttpClient extends CloseableHttpClient implements Configurable {
    private final Lookup<AuthSchemeProvider> authSchemeRegistry;
    private final List<Closeable> closeables;
    /* access modifiers changed from: private */
    public final HttpClientConnectionManager connManager;
    private final Lookup<CookieSpecProvider> cookieSpecRegistry;
    private final CookieStore cookieStore;
    private final CredentialsProvider credentialsProvider;
    private final RequestConfig defaultConfig;
    private final ClientExecChain execChain;
    private final Log log = LogFactory.getLog(getClass());
    private final HttpRoutePlanner routePlanner;

    public InternalHttpClient(ClientExecChain clientExecChain, HttpClientConnectionManager httpClientConnectionManager, HttpRoutePlanner httpRoutePlanner, Lookup<CookieSpecProvider> lookup, Lookup<AuthSchemeProvider> lookup2, CookieStore cookieStore2, CredentialsProvider credentialsProvider2, RequestConfig requestConfig, List<Closeable> list) {
        Args.notNull(clientExecChain, "HTTP client exec chain");
        Args.notNull(httpClientConnectionManager, "HTTP connection manager");
        Args.notNull(httpRoutePlanner, "HTTP route planner");
        this.execChain = clientExecChain;
        this.connManager = httpClientConnectionManager;
        this.routePlanner = httpRoutePlanner;
        this.cookieSpecRegistry = lookup;
        this.authSchemeRegistry = lookup2;
        this.cookieStore = cookieStore2;
        this.credentialsProvider = credentialsProvider2;
        this.defaultConfig = requestConfig;
        this.closeables = list;
    }

    private HttpRoute determineRoute(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) throws HttpException {
        if (httpHost == null) {
            httpHost = (HttpHost) httpRequest.getParams().getParameter(ClientPNames.DEFAULT_HOST);
        }
        return this.routePlanner.determineRoute(httpHost, httpRequest, httpContext);
    }

    private void setupContext(HttpClientContext httpClientContext) {
        String str = "http.auth.target-scope";
        if (httpClientContext.getAttribute(str) == null) {
            httpClientContext.setAttribute(str, new AuthState());
        }
        String str2 = "http.auth.proxy-scope";
        if (httpClientContext.getAttribute(str2) == null) {
            httpClientContext.setAttribute(str2, new AuthState());
        }
        String str3 = "http.authscheme-registry";
        if (httpClientContext.getAttribute(str3) == null) {
            httpClientContext.setAttribute(str3, this.authSchemeRegistry);
        }
        String str4 = "http.cookiespec-registry";
        if (httpClientContext.getAttribute(str4) == null) {
            httpClientContext.setAttribute(str4, this.cookieSpecRegistry);
        }
        String str5 = "http.cookie-store";
        if (httpClientContext.getAttribute(str5) == null) {
            httpClientContext.setAttribute(str5, this.cookieStore);
        }
        String str6 = "http.auth.credentials-provider";
        if (httpClientContext.getAttribute(str6) == null) {
            httpClientContext.setAttribute(str6, this.credentialsProvider);
        }
        String str7 = "http.request-config";
        if (httpClientContext.getAttribute(str7) == null) {
            httpClientContext.setAttribute(str7, this.defaultConfig);
        }
    }

    /* access modifiers changed from: protected */
    public CloseableHttpResponse doExecute(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) throws IOException, ClientProtocolException {
        Args.notNull(httpRequest, "HTTP request");
        RequestConfig requestConfig = null;
        HttpExecutionAware httpExecutionAware = httpRequest instanceof HttpExecutionAware ? (HttpExecutionAware) httpRequest : null;
        try {
            HttpRequestWrapper wrap = HttpRequestWrapper.wrap(httpRequest, httpHost);
            if (httpContext == null) {
                httpContext = new BasicHttpContext();
            }
            HttpClientContext adapt = HttpClientContext.adapt(httpContext);
            if (httpRequest instanceof Configurable) {
                requestConfig = ((Configurable) httpRequest).getConfig();
            }
            if (requestConfig == null) {
                HttpParams params = httpRequest.getParams();
                if (!(params instanceof HttpParamsNames)) {
                    requestConfig = HttpClientParamConfig.getRequestConfig(params, this.defaultConfig);
                } else if (!((HttpParamsNames) params).getNames().isEmpty()) {
                    requestConfig = HttpClientParamConfig.getRequestConfig(params, this.defaultConfig);
                }
            }
            if (requestConfig != null) {
                adapt.setRequestConfig(requestConfig);
            }
            setupContext(adapt);
            return this.execChain.execute(determineRoute(httpHost, wrap, adapt), wrap, adapt, httpExecutionAware);
        } catch (HttpException e) {
            throw new ClientProtocolException((Throwable) e);
        }
    }

    public RequestConfig getConfig() {
        return this.defaultConfig;
    }

    public void close() {
        List<Closeable> list = this.closeables;
        if (list != null) {
            for (Closeable close : list) {
                try {
                    close.close();
                } catch (IOException e) {
                    this.log.error(e.getMessage(), e);
                }
            }
        }
    }

    public HttpParams getParams() {
        throw new UnsupportedOperationException();
    }

    public ClientConnectionManager getConnectionManager() {
        return new ClientConnectionManager() {
            public void shutdown() {
                InternalHttpClient.this.connManager.shutdown();
            }

            public ClientConnectionRequest requestConnection(HttpRoute httpRoute, Object obj) {
                throw new UnsupportedOperationException();
            }

            public void releaseConnection(ManagedClientConnection managedClientConnection, long j, TimeUnit timeUnit) {
                throw new UnsupportedOperationException();
            }

            public SchemeRegistry getSchemeRegistry() {
                throw new UnsupportedOperationException();
            }

            public void closeIdleConnections(long j, TimeUnit timeUnit) {
                InternalHttpClient.this.connManager.closeIdleConnections(j, timeUnit);
            }

            public void closeExpiredConnections() {
                InternalHttpClient.this.connManager.closeExpiredConnections();
            }
        };
    }
}
