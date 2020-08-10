package org.apache.http.impl.auth;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthOption;
import org.apache.http.auth.AuthProtocolState;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.ContextAwareAuthScheme;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.client.AuthenticationStrategy;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Asserts;

public class HttpAuthenticator {
    private final Log log;

    /* renamed from: org.apache.http.impl.auth.HttpAuthenticator$1 */
    static /* synthetic */ class C60901 {
        static final /* synthetic */ int[] $SwitchMap$org$apache$http$auth$AuthProtocolState = new int[AuthProtocolState.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(12:0|1|2|3|4|5|6|7|8|9|10|12) */
        /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
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
                org.apache.http.auth.AuthProtocolState r1 = org.apache.http.auth.AuthProtocolState.HANDSHAKE     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$org$apache$http$auth$AuthProtocolState     // Catch:{ NoSuchFieldError -> 0x002a }
                org.apache.http.auth.AuthProtocolState r1 = org.apache.http.auth.AuthProtocolState.SUCCESS     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = $SwitchMap$org$apache$http$auth$AuthProtocolState     // Catch:{ NoSuchFieldError -> 0x0035 }
                org.apache.http.auth.AuthProtocolState r1 = org.apache.http.auth.AuthProtocolState.FAILURE     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                int[] r0 = $SwitchMap$org$apache$http$auth$AuthProtocolState     // Catch:{ NoSuchFieldError -> 0x0040 }
                org.apache.http.auth.AuthProtocolState r1 = org.apache.http.auth.AuthProtocolState.UNCHALLENGED     // Catch:{ NoSuchFieldError -> 0x0040 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0040 }
            L_0x0040:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.auth.HttpAuthenticator.C60901.<clinit>():void");
        }
    }

    public HttpAuthenticator(Log log2) {
        if (log2 == null) {
            log2 = LogFactory.getLog(getClass());
        }
        this.log = log2;
    }

    public HttpAuthenticator() {
        this(null);
    }

    public boolean isAuthenticationRequested(HttpHost httpHost, HttpResponse httpResponse, AuthenticationStrategy authenticationStrategy, AuthState authState, HttpContext httpContext) {
        if (authenticationStrategy.isAuthenticationRequested(httpHost, httpResponse, httpContext)) {
            this.log.debug("Authentication required");
            if (authState.getState() == AuthProtocolState.SUCCESS) {
                authenticationStrategy.authFailed(httpHost, authState.getAuthScheme(), httpContext);
            }
            return true;
        }
        int i = C60901.$SwitchMap$org$apache$http$auth$AuthProtocolState[authState.getState().ordinal()];
        if (i == 1 || i == 2) {
            this.log.debug("Authentication succeeded");
            authState.setState(AuthProtocolState.SUCCESS);
            authenticationStrategy.authSucceeded(httpHost, authState.getAuthScheme(), httpContext);
        } else if (i != 3) {
            authState.setState(AuthProtocolState.UNCHALLENGED);
        }
        return false;
    }

    public boolean handleAuthChallenge(HttpHost httpHost, HttpResponse httpResponse, AuthenticationStrategy authenticationStrategy, AuthState authState, HttpContext httpContext) {
        Queue select;
        try {
            if (this.log.isDebugEnabled()) {
                Log log2 = this.log;
                StringBuilder sb = new StringBuilder();
                sb.append(httpHost.toHostString());
                sb.append(" requested authentication");
                log2.debug(sb.toString());
            }
            Map challenges = authenticationStrategy.getChallenges(httpHost, httpResponse, httpContext);
            if (challenges.isEmpty()) {
                this.log.debug("Response contains no authentication challenges");
                return false;
            }
            AuthScheme authScheme = authState.getAuthScheme();
            int i = C60901.$SwitchMap$org$apache$http$auth$AuthProtocolState[authState.getState().ordinal()];
            if (i != 1 && i != 2) {
                if (i == 3) {
                    authState.reset();
                } else if (i == 4) {
                    return false;
                } else {
                    if (i != 5) {
                    }
                }
                select = authenticationStrategy.select(challenges, httpHost, httpResponse, httpContext);
                if (select != null || select.isEmpty()) {
                    return false;
                }
                if (this.log.isDebugEnabled()) {
                    Log log3 = this.log;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Selected authentication options: ");
                    sb2.append(select);
                    log3.debug(sb2.toString());
                }
                authState.setState(AuthProtocolState.CHALLENGED);
                authState.update(select);
                return true;
            } else if (authScheme == null) {
                this.log.debug("Auth scheme is null");
                authenticationStrategy.authFailed(httpHost, null, httpContext);
                authState.reset();
                authState.setState(AuthProtocolState.FAILURE);
                return false;
            }
            if (authScheme != null) {
                Header header = (Header) challenges.get(authScheme.getSchemeName().toLowerCase(Locale.ROOT));
                if (header != null) {
                    this.log.debug("Authorization challenge processed");
                    authScheme.processChallenge(header);
                    if (authScheme.isComplete()) {
                        this.log.debug("Authentication failed");
                        authenticationStrategy.authFailed(httpHost, authState.getAuthScheme(), httpContext);
                        authState.reset();
                        authState.setState(AuthProtocolState.FAILURE);
                        return false;
                    }
                    authState.setState(AuthProtocolState.HANDSHAKE);
                    return true;
                }
                authState.reset();
            }
            select = authenticationStrategy.select(challenges, httpHost, httpResponse, httpContext);
            if (select != null) {
            }
            return false;
        } catch (MalformedChallengeException e) {
            if (this.log.isWarnEnabled()) {
                Log log4 = this.log;
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Malformed challenge: ");
                sb3.append(e.getMessage());
                log4.warn(sb3.toString());
            }
            authState.reset();
            return false;
        }
    }

    public void generateAuthResponse(HttpRequest httpRequest, AuthState authState, HttpContext httpContext) throws HttpException, IOException {
        AuthScheme authScheme = authState.getAuthScheme();
        Credentials credentials = authState.getCredentials();
        int i = C60901.$SwitchMap$org$apache$http$auth$AuthProtocolState[authState.getState().ordinal()];
        String str = " authentication error: ";
        if (i == 1) {
            Queue authOptions = authState.getAuthOptions();
            if (authOptions != null) {
                while (!authOptions.isEmpty()) {
                    AuthOption authOption = (AuthOption) authOptions.remove();
                    AuthScheme authScheme2 = authOption.getAuthScheme();
                    Credentials credentials2 = authOption.getCredentials();
                    authState.update(authScheme2, credentials2);
                    if (this.log.isDebugEnabled()) {
                        Log log2 = this.log;
                        StringBuilder sb = new StringBuilder();
                        sb.append("Generating response to an authentication challenge using ");
                        sb.append(authScheme2.getSchemeName());
                        sb.append(" scheme");
                        log2.debug(sb.toString());
                    }
                    try {
                        httpRequest.addHeader(doAuth(authScheme2, credentials2, httpRequest, httpContext));
                        break;
                    } catch (AuthenticationException e) {
                        if (this.log.isWarnEnabled()) {
                            Log log3 = this.log;
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(authScheme2);
                            sb2.append(str);
                            sb2.append(e.getMessage());
                            log3.warn(sb2.toString());
                        }
                    }
                }
                return;
            }
            ensureAuthScheme(authScheme);
        } else if (i == 3) {
            ensureAuthScheme(authScheme);
            if (authScheme.isConnectionBased()) {
                return;
            }
        } else if (i == 4) {
            return;
        }
        if (authScheme != null) {
            try {
                httpRequest.addHeader(doAuth(authScheme, credentials, httpRequest, httpContext));
            } catch (AuthenticationException e2) {
                if (this.log.isErrorEnabled()) {
                    Log log4 = this.log;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(authScheme);
                    sb3.append(str);
                    sb3.append(e2.getMessage());
                    log4.error(sb3.toString());
                }
            }
        }
    }

    private void ensureAuthScheme(AuthScheme authScheme) {
        Asserts.notNull(authScheme, "Auth scheme");
    }

    private Header doAuth(AuthScheme authScheme, Credentials credentials, HttpRequest httpRequest, HttpContext httpContext) throws AuthenticationException {
        return authScheme instanceof ContextAwareAuthScheme ? ((ContextAwareAuthScheme) authScheme).authenticate(credentials, httpRequest, httpContext) : authScheme.authenticate(credentials, httpRequest);
    }
}
