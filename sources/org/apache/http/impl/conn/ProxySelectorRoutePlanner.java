package org.apache.http.impl.conn;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

@Deprecated
public class ProxySelectorRoutePlanner implements HttpRoutePlanner {
    protected ProxySelector proxySelector;
    protected final SchemeRegistry schemeRegistry;

    /* renamed from: org.apache.http.impl.conn.ProxySelectorRoutePlanner$1 */
    static /* synthetic */ class C61001 {
        static final /* synthetic */ int[] $SwitchMap$java$net$Proxy$Type = new int[Type.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|8) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        static {
            /*
                java.net.Proxy$Type[] r0 = java.net.Proxy.Type.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$java$net$Proxy$Type = r0
                int[] r0 = $SwitchMap$java$net$Proxy$Type     // Catch:{ NoSuchFieldError -> 0x0014 }
                java.net.Proxy$Type r1 = java.net.Proxy.Type.DIRECT     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$java$net$Proxy$Type     // Catch:{ NoSuchFieldError -> 0x001f }
                java.net.Proxy$Type r1 = java.net.Proxy.Type.HTTP     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$java$net$Proxy$Type     // Catch:{ NoSuchFieldError -> 0x002a }
                java.net.Proxy$Type r1 = java.net.Proxy.Type.SOCKS     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.conn.ProxySelectorRoutePlanner.C61001.<clinit>():void");
        }
    }

    public ProxySelectorRoutePlanner(SchemeRegistry schemeRegistry2, ProxySelector proxySelector2) {
        Args.notNull(schemeRegistry2, "SchemeRegistry");
        this.schemeRegistry = schemeRegistry2;
        this.proxySelector = proxySelector2;
    }

    public ProxySelector getProxySelector() {
        return this.proxySelector;
    }

    public void setProxySelector(ProxySelector proxySelector2) {
        this.proxySelector = proxySelector2;
    }

    public HttpRoute determineRoute(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) throws HttpException {
        HttpRoute httpRoute;
        Args.notNull(httpRequest, "HTTP request");
        HttpRoute forcedRoute = ConnRouteParams.getForcedRoute(httpRequest.getParams());
        if (forcedRoute != null) {
            return forcedRoute;
        }
        Asserts.notNull(httpHost, "Target host");
        InetAddress localAddress = ConnRouteParams.getLocalAddress(httpRequest.getParams());
        HttpHost determineProxy = determineProxy(httpHost, httpRequest, httpContext);
        boolean isLayered = this.schemeRegistry.getScheme(httpHost.getSchemeName()).isLayered();
        if (determineProxy == null) {
            httpRoute = new HttpRoute(httpHost, localAddress, isLayered);
        } else {
            httpRoute = new HttpRoute(httpHost, localAddress, determineProxy, isLayered);
        }
        return httpRoute;
    }

    /* access modifiers changed from: protected */
    public HttpHost determineProxy(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) throws HttpException {
        ProxySelector proxySelector2 = this.proxySelector;
        if (proxySelector2 == null) {
            proxySelector2 = ProxySelector.getDefault();
        }
        HttpHost httpHost2 = null;
        if (proxySelector2 == null) {
            return null;
        }
        try {
            Proxy chooseProxy = chooseProxy(proxySelector2.select(new URI(httpHost.toURI())), httpHost, httpRequest, httpContext);
            if (chooseProxy.type() == Type.HTTP) {
                if (chooseProxy.address() instanceof InetSocketAddress) {
                    InetSocketAddress inetSocketAddress = (InetSocketAddress) chooseProxy.address();
                    httpHost2 = new HttpHost(getHost(inetSocketAddress), inetSocketAddress.getPort());
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Unable to handle non-Inet proxy address: ");
                    sb.append(chooseProxy.address());
                    throw new HttpException(sb.toString());
                }
            }
            return httpHost2;
        } catch (URISyntaxException e) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Cannot convert host to URI: ");
            sb2.append(httpHost);
            throw new HttpException(sb2.toString(), e);
        }
    }

    /* access modifiers changed from: protected */
    public String getHost(InetSocketAddress inetSocketAddress) {
        return inetSocketAddress.isUnresolved() ? inetSocketAddress.getHostName() : inetSocketAddress.getAddress().getHostAddress();
    }

    /* access modifiers changed from: protected */
    public Proxy chooseProxy(List<Proxy> list, HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) {
        Args.notEmpty(list, "List of proxies");
        Proxy proxy = null;
        int i = 0;
        while (proxy == null && i < list.size()) {
            Proxy proxy2 = (Proxy) list.get(i);
            int i2 = C61001.$SwitchMap$java$net$Proxy$Type[proxy2.type().ordinal()];
            if (i2 == 1 || i2 == 2) {
                proxy = proxy2;
            }
            i++;
        }
        return proxy == null ? Proxy.NO_PROXY : proxy;
    }
}
