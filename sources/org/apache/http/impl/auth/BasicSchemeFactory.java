package org.apache.http.impl.auth;

import java.nio.charset.Charset;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthSchemeFactory;
import org.apache.http.auth.AuthSchemeProvider;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

public class BasicSchemeFactory implements AuthSchemeFactory, AuthSchemeProvider {
    private final Charset charset;

    public BasicSchemeFactory(Charset charset2) {
        this.charset = charset2;
    }

    public BasicSchemeFactory() {
        this(null);
    }

    public AuthScheme newInstance(HttpParams httpParams) {
        return new BasicScheme();
    }

    public AuthScheme create(HttpContext httpContext) {
        return new BasicScheme(this.charset);
    }
}
