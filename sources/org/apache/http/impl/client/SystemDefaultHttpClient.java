package org.apache.http.impl.client;

import java.net.ProxySelector;
import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.NoConnectionReuseStrategy;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.conn.ProxySelectorRoutePlanner;
import org.apache.http.impl.conn.SchemeRegistryFactory;
import org.apache.http.params.HttpParams;

@Deprecated
public class SystemDefaultHttpClient extends DefaultHttpClient {
    public SystemDefaultHttpClient(HttpParams httpParams) {
        super(null, httpParams);
    }

    public SystemDefaultHttpClient() {
        super(null, null);
    }

    /* access modifiers changed from: protected */
    public ClientConnectionManager createClientConnectionManager() {
        PoolingClientConnectionManager poolingClientConnectionManager = new PoolingClientConnectionManager(SchemeRegistryFactory.createSystemDefault());
        String str = "true";
        if (str.equalsIgnoreCase(System.getProperty("http.keepAlive", str))) {
            int parseInt = Integer.parseInt(System.getProperty("http.maxConnections", "5"));
            poolingClientConnectionManager.setDefaultMaxPerRoute(parseInt);
            poolingClientConnectionManager.setMaxTotal(parseInt * 2);
        }
        return poolingClientConnectionManager;
    }

    /* access modifiers changed from: protected */
    public HttpRoutePlanner createHttpRoutePlanner() {
        return new ProxySelectorRoutePlanner(getConnectionManager().getSchemeRegistry(), ProxySelector.getDefault());
    }

    /* access modifiers changed from: protected */
    public ConnectionReuseStrategy createConnectionReuseStrategy() {
        String str = "true";
        if (str.equalsIgnoreCase(System.getProperty("http.keepAlive", str))) {
            return new DefaultConnectionReuseStrategy();
        }
        return new NoConnectionReuseStrategy();
    }
}
