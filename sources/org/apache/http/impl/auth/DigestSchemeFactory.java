package org.apache.http.impl.auth;

import java.nio.charset.Charset;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthSchemeFactory;
import org.apache.http.auth.AuthSchemeProvider;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

public class DigestSchemeFactory implements AuthSchemeFactory, AuthSchemeProvider {
    private final Charset charset;

    public DigestSchemeFactory(Charset charset2) {
        this.charset = charset2;
    }

    public DigestSchemeFactory() {
        this(null);
    }

    public AuthScheme newInstance(HttpParams httpParams) {
        return new DigestScheme();
    }

    public AuthScheme create(HttpContext httpContext) {
        return new DigestScheme(this.charset);
    }
}
