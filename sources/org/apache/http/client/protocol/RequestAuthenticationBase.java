package org.apache.http.client.protocol;

import java.util.Queue;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.auth.AuthOption;
import org.apache.http.auth.AuthProtocolState;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.ContextAwareAuthScheme;
import org.apache.http.auth.Credentials;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Asserts;

@Deprecated
abstract class RequestAuthenticationBase implements HttpRequestInterceptor {
    final Log log = LogFactory.getLog(getClass());

    /* renamed from: org.apache.http.client.protocol.RequestAuthenticationBase$1 */
    static /* synthetic */ class C60831 {
        static final /* synthetic */ int[] $SwitchMap$org$apache$http$auth$AuthProtocolState = new int[AuthProtocolState.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|8) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        static {
            /*
                org.apache.http.auth.AuthProtocolState[] r0 = org.apache.http.auth.AuthProtocolState.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$org$apache$http$auth$AuthProtocolState = r0
                int[] r0 = $SwitchMap$org$apache$http$auth$AuthProtocolState     // Catch:{ NoSuchFieldError -> 0x0014 }
                org.apache.http.auth.AuthProtocolState r1 = org.apache.http.auth.AuthProtocolState.FAILURE     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$org$apache$http$auth$AuthProtocolState     // Catch:{ NoSuchFieldError -> 0x001f }
                org.apache.http.auth.AuthProtocolState r1 = org.apache.http.auth.AuthProtocolState.SUCCESS     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$org$apache$http$auth$AuthProtocolState     // Catch:{ NoSuchFieldError -> 0x002a }
                org.apache.http.auth.AuthProtocolState r1 = org.apache.http.auth.AuthProtocolState.CHALLENGED     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.apache.http.client.protocol.RequestAuthenticationBase.C60831.<clinit>():void");
        }
    }

    /* access modifiers changed from: 0000 */
    public void process(AuthState authState, HttpRequest httpRequest, HttpContext httpContext) {
        AuthScheme authScheme = authState.getAuthScheme();
        Credentials credentials = authState.getCredentials();
        int i = C60831.$SwitchMap$org$apache$http$auth$AuthProtocolState[authState.getState().ordinal()];
        if (i != 1) {
            String str = " authentication error: ";
            if (i == 2) {
                ensureAuthScheme(authScheme);
                if (authScheme.isConnectionBased()) {
                    return;
                }
            } else if (i == 3) {
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
                            httpRequest.addHeader(authenticate(authScheme2, credentials2, httpRequest, httpContext));
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
            }
            if (authScheme != null) {
                try {
                    httpRequest.addHeader(authenticate(authScheme, credentials, httpRequest, httpContext));
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
    }

    private void ensureAuthScheme(AuthScheme authScheme) {
        Asserts.notNull(authScheme, "Auth scheme");
    }

    private Header authenticate(AuthScheme authScheme, Credentials credentials, HttpRequest httpRequest, HttpContext httpContext) throws AuthenticationException {
        Asserts.notNull(authScheme, "Auth scheme");
        if (authScheme instanceof ContextAwareAuthScheme) {
            return ((ContextAwareAuthScheme) authScheme).authenticate(credentials, httpRequest, httpContext);
        }
        return authScheme.authenticate(credentials, httpRequest);
    }
}
