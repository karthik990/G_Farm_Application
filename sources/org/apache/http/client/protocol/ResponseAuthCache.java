package org.apache.http.client.protocol;

import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.auth.AuthProtocolState;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthState;
import org.apache.http.client.AuthCache;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

@Deprecated
public class ResponseAuthCache implements HttpResponseInterceptor {
    private final Log log = LogFactory.getLog(getClass());

    /* renamed from: org.apache.http.client.protocol.ResponseAuthCache$1 */
    static /* synthetic */ class C60841 {
        static final /* synthetic */ int[] $SwitchMap$org$apache$http$auth$AuthProtocolState = new int[AuthProtocolState.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|6) */
        /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        static {
            /*
                org.apache.http.auth.AuthProtocolState[] r0 = org.apache.http.auth.AuthProtocolState.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$org$apache$http$auth$AuthProtocolState = r0
                int[] r0 = $SwitchMap$org$apache$http$auth$AuthProtocolState     // Catch:{ NoSuchFieldError -> 0x0014 }
                org.apache.http.auth.AuthProtocolState r1 = org.apache.http.auth.AuthProtocolState.CHALLENGED     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$org$apache$http$auth$AuthProtocolState     // Catch:{ NoSuchFieldError -> 0x001f }
                org.apache.http.auth.AuthProtocolState r1 = org.apache.http.auth.AuthProtocolState.FAILURE     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.apache.http.client.protocol.ResponseAuthCache.C60841.<clinit>():void");
        }
    }

    public void process(HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpResponse, "HTTP request");
        Args.notNull(httpContext, "HTTP context");
        String str = "http.auth.auth-cache";
        AuthCache authCache = (AuthCache) httpContext.getAttribute(str);
        HttpHost httpHost = (HttpHost) httpContext.getAttribute("http.target_host");
        AuthState authState = (AuthState) httpContext.getAttribute("http.auth.target-scope");
        if (!(httpHost == null || authState == null)) {
            if (this.log.isDebugEnabled()) {
                Log log2 = this.log;
                StringBuilder sb = new StringBuilder();
                sb.append("Target auth state: ");
                sb.append(authState.getState());
                log2.debug(sb.toString());
            }
            if (isCachable(authState)) {
                SchemeRegistry schemeRegistry = (SchemeRegistry) httpContext.getAttribute(ClientContext.SCHEME_REGISTRY);
                if (httpHost.getPort() < 0) {
                    httpHost = new HttpHost(httpHost.getHostName(), schemeRegistry.getScheme(httpHost).resolvePort(httpHost.getPort()), httpHost.getSchemeName());
                }
                if (authCache == null) {
                    authCache = new BasicAuthCache();
                    httpContext.setAttribute(str, authCache);
                }
                int i = C60841.$SwitchMap$org$apache$http$auth$AuthProtocolState[authState.getState().ordinal()];
                if (i == 1) {
                    cache(authCache, httpHost, authState.getAuthScheme());
                } else if (i == 2) {
                    uncache(authCache, httpHost, authState.getAuthScheme());
                }
            }
        }
        HttpHost httpHost2 = (HttpHost) httpContext.getAttribute(ExecutionContext.HTTP_PROXY_HOST);
        AuthState authState2 = (AuthState) httpContext.getAttribute("http.auth.proxy-scope");
        if (httpHost2 != null && authState2 != null) {
            if (this.log.isDebugEnabled()) {
                Log log3 = this.log;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Proxy auth state: ");
                sb2.append(authState2.getState());
                log3.debug(sb2.toString());
            }
            if (isCachable(authState2)) {
                if (authCache == null) {
                    authCache = new BasicAuthCache();
                    httpContext.setAttribute(str, authCache);
                }
                int i2 = C60841.$SwitchMap$org$apache$http$auth$AuthProtocolState[authState2.getState().ordinal()];
                if (i2 == 1) {
                    cache(authCache, httpHost2, authState2.getAuthScheme());
                } else if (i2 == 2) {
                    uncache(authCache, httpHost2, authState2.getAuthScheme());
                }
            }
        }
    }

    private boolean isCachable(AuthState authState) {
        AuthScheme authScheme = authState.getAuthScheme();
        if (authScheme == null || !authScheme.isComplete()) {
            return false;
        }
        String schemeName = authScheme.getSchemeName();
        if (schemeName.equalsIgnoreCase("Basic") || schemeName.equalsIgnoreCase("Digest")) {
            return true;
        }
        return false;
    }

    private void cache(AuthCache authCache, HttpHost httpHost, AuthScheme authScheme) {
        if (this.log.isDebugEnabled()) {
            Log log2 = this.log;
            StringBuilder sb = new StringBuilder();
            sb.append("Caching '");
            sb.append(authScheme.getSchemeName());
            sb.append("' auth scheme for ");
            sb.append(httpHost);
            log2.debug(sb.toString());
        }
        authCache.put(httpHost, authScheme);
    }

    private void uncache(AuthCache authCache, HttpHost httpHost, AuthScheme authScheme) {
        if (this.log.isDebugEnabled()) {
            Log log2 = this.log;
            StringBuilder sb = new StringBuilder();
            sb.append("Removing from cache '");
            sb.append(authScheme.getSchemeName());
            sb.append("' auth scheme for ");
            sb.append(httpHost);
            log2.debug(sb.toString());
        }
        authCache.remove(httpHost);
    }
}
