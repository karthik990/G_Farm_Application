package org.apache.http.conn.ssl;

import javax.net.ssl.SSLException;

@Deprecated
public class BrowserCompatHostnameVerifier extends AbstractVerifier {
    public static final BrowserCompatHostnameVerifier INSTANCE = new BrowserCompatHostnameVerifier();

    public final String toString() {
        return "BROWSER_COMPATIBLE";
    }

    public final void verify(String str, String[] strArr, String[] strArr2) throws SSLException {
        verify(str, strArr, strArr2, false);
    }
}
