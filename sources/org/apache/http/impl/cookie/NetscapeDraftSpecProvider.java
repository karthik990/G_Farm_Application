package org.apache.http.impl.cookie;

import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.protocol.HttpContext;

public class NetscapeDraftSpecProvider implements CookieSpecProvider {
    private volatile CookieSpec cookieSpec;
    private final String[] datepatterns;

    public NetscapeDraftSpecProvider(String[] strArr) {
        this.datepatterns = strArr;
    }

    public NetscapeDraftSpecProvider() {
        this(null);
    }

    public CookieSpec create(HttpContext httpContext) {
        if (this.cookieSpec == null) {
            synchronized (this) {
                if (this.cookieSpec == null) {
                    this.cookieSpec = new NetscapeDraftSpec(this.datepatterns);
                }
            }
        }
        return this.cookieSpec;
    }
}
