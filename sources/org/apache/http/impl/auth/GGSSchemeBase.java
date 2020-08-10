package org.apache.http.impl.auth;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.KerberosCredentials;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.util.CharArrayBuffer;
import org.ietf.jgss.GSSContext;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;
import org.ietf.jgss.GSSManager;
import org.ietf.jgss.GSSName;
import org.ietf.jgss.Oid;

public abstract class GGSSchemeBase extends AuthSchemeBase {
    private final Base64 base64codec;
    private final Log log;
    private State state;
    private final boolean stripPort;
    private byte[] token;
    private final boolean useCanonicalHostname;

    /* renamed from: org.apache.http.impl.auth.GGSSchemeBase$1 */
    static /* synthetic */ class C60891 {
        static final /* synthetic */ int[] $SwitchMap$org$apache$http$impl$auth$GGSSchemeBase$State = new int[State.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(10:0|1|2|3|4|5|6|7|8|10) */
        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        static {
            /*
                org.apache.http.impl.auth.GGSSchemeBase$State[] r0 = org.apache.http.impl.auth.GGSSchemeBase.State.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$org$apache$http$impl$auth$GGSSchemeBase$State = r0
                int[] r0 = $SwitchMap$org$apache$http$impl$auth$GGSSchemeBase$State     // Catch:{ NoSuchFieldError -> 0x0014 }
                org.apache.http.impl.auth.GGSSchemeBase$State r1 = org.apache.http.impl.auth.GGSSchemeBase.State.UNINITIATED     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$org$apache$http$impl$auth$GGSSchemeBase$State     // Catch:{ NoSuchFieldError -> 0x001f }
                org.apache.http.impl.auth.GGSSchemeBase$State r1 = org.apache.http.impl.auth.GGSSchemeBase.State.FAILED     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$org$apache$http$impl$auth$GGSSchemeBase$State     // Catch:{ NoSuchFieldError -> 0x002a }
                org.apache.http.impl.auth.GGSSchemeBase$State r1 = org.apache.http.impl.auth.GGSSchemeBase.State.CHALLENGE_RECEIVED     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = $SwitchMap$org$apache$http$impl$auth$GGSSchemeBase$State     // Catch:{ NoSuchFieldError -> 0x0035 }
                org.apache.http.impl.auth.GGSSchemeBase$State r1 = org.apache.http.impl.auth.GGSSchemeBase.State.TOKEN_GENERATED     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.auth.GGSSchemeBase.C60891.<clinit>():void");
        }
    }

    enum State {
        UNINITIATED,
        CHALLENGE_RECEIVED,
        TOKEN_GENERATED,
        FAILED
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public byte[] generateToken(byte[] bArr, String str) throws GSSException {
        return null;
    }

    GGSSchemeBase(boolean z, boolean z2) {
        this.log = LogFactory.getLog(getClass());
        this.base64codec = new Base64(0);
        this.stripPort = z;
        this.useCanonicalHostname = z2;
        this.state = State.UNINITIATED;
    }

    GGSSchemeBase(boolean z) {
        this(z, true);
    }

    GGSSchemeBase() {
        this(true, true);
    }

    /* access modifiers changed from: protected */
    public GSSManager getManager() {
        return GSSManager.getInstance();
    }

    /* access modifiers changed from: protected */
    public byte[] generateGSSToken(byte[] bArr, Oid oid, String str) throws GSSException {
        return generateGSSToken(bArr, oid, str, null);
    }

    /* access modifiers changed from: protected */
    public byte[] generateGSSToken(byte[] bArr, Oid oid, String str, Credentials credentials) throws GSSException {
        GSSManager manager = getManager();
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP@");
        sb.append(str);
        GSSContext createGSSContext = createGSSContext(manager, oid, manager.createName(sb.toString(), GSSName.NT_HOSTBASED_SERVICE), credentials instanceof KerberosCredentials ? ((KerberosCredentials) credentials).getGSSCredential() : null);
        return bArr != null ? createGSSContext.initSecContext(bArr, 0, bArr.length) : createGSSContext.initSecContext(new byte[0], 0, 0);
    }

    /* access modifiers changed from: 0000 */
    public GSSContext createGSSContext(GSSManager gSSManager, Oid oid, GSSName gSSName, GSSCredential gSSCredential) throws GSSException {
        GSSContext createContext = gSSManager.createContext(gSSName.canonicalize(oid), oid, gSSCredential, 0);
        createContext.requestMutualAuth(true);
        return createContext;
    }

    /* access modifiers changed from: protected */
    public byte[] generateToken(byte[] bArr, String str, Credentials credentials) throws GSSException {
        return generateToken(bArr, str);
    }

    public boolean isComplete() {
        return this.state == State.TOKEN_GENERATED || this.state == State.FAILED;
    }

    @Deprecated
    public Header authenticate(Credentials credentials, HttpRequest httpRequest) throws AuthenticationException {
        return authenticate(credentials, httpRequest, null);
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(10:14|(2:16|(1:18))(1:19)|20|(2:22|23)|24|25|(1:27)(1:28)|29|(1:31)|32) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:24:0x0061 */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0065 A[Catch:{ GSSException -> 0x0106 }] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0066 A[Catch:{ GSSException -> 0x0106 }] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0086 A[Catch:{ GSSException -> 0x0106 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.apache.http.Header authenticate(org.apache.http.auth.Credentials r3, org.apache.http.HttpRequest r4, org.apache.http.protocol.HttpContext r5) throws org.apache.http.auth.AuthenticationException {
        /*
            r2 = this;
            java.lang.String r0 = "HTTP request"
            org.apache.http.util.Args.notNull(r4, r0)
            int[] r4 = org.apache.http.impl.auth.GGSSchemeBase.C60891.$SwitchMap$org$apache$http$impl$auth$GGSSchemeBase$State
            org.apache.http.impl.auth.GGSSchemeBase$State r0 = r2.state
            int r0 = r0.ordinal()
            r4 = r4[r0]
            r0 = 1
            if (r4 == r0) goto L_0x017f
            r0 = 2
            if (r4 == r0) goto L_0x0164
            r0 = 3
            if (r4 == r0) goto L_0x0036
            r3 = 4
            if (r4 != r3) goto L_0x001d
            goto L_0x00a8
        L_0x001d:
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Illegal state: "
            r4.append(r5)
            org.apache.http.impl.auth.GGSSchemeBase$State r5 = r2.state
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            r3.<init>(r4)
            throw r3
        L_0x0036:
            java.lang.String r4 = "http.route"
            java.lang.Object r4 = r5.getAttribute(r4)     // Catch:{ GSSException -> 0x0106 }
            org.apache.http.conn.routing.HttpRoute r4 = (org.apache.http.conn.routing.HttpRoute) r4     // Catch:{ GSSException -> 0x0106 }
            if (r4 == 0) goto L_0x00fe
            boolean r5 = r2.isProxy()     // Catch:{ GSSException -> 0x0106 }
            if (r5 == 0) goto L_0x0051
            org.apache.http.HttpHost r5 = r4.getProxyHost()     // Catch:{ GSSException -> 0x0106 }
            if (r5 != 0) goto L_0x0055
            org.apache.http.HttpHost r5 = r4.getTargetHost()     // Catch:{ GSSException -> 0x0106 }
            goto L_0x0055
        L_0x0051:
            org.apache.http.HttpHost r5 = r4.getTargetHost()     // Catch:{ GSSException -> 0x0106 }
        L_0x0055:
            java.lang.String r4 = r5.getHostName()     // Catch:{ GSSException -> 0x0106 }
            boolean r0 = r2.useCanonicalHostname     // Catch:{ GSSException -> 0x0106 }
            if (r0 == 0) goto L_0x0061
            java.lang.String r4 = r2.resolveCanonicalHostname(r4)     // Catch:{ UnknownHostException -> 0x0061 }
        L_0x0061:
            boolean r0 = r2.stripPort     // Catch:{ GSSException -> 0x0106 }
            if (r0 == 0) goto L_0x0066
            goto L_0x007e
        L_0x0066:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ GSSException -> 0x0106 }
            r0.<init>()     // Catch:{ GSSException -> 0x0106 }
            r0.append(r4)     // Catch:{ GSSException -> 0x0106 }
            java.lang.String r4 = ":"
            r0.append(r4)     // Catch:{ GSSException -> 0x0106 }
            int r4 = r5.getPort()     // Catch:{ GSSException -> 0x0106 }
            r0.append(r4)     // Catch:{ GSSException -> 0x0106 }
            java.lang.String r4 = r0.toString()     // Catch:{ GSSException -> 0x0106 }
        L_0x007e:
            org.apache.commons.logging.Log r5 = r2.log     // Catch:{ GSSException -> 0x0106 }
            boolean r5 = r5.isDebugEnabled()     // Catch:{ GSSException -> 0x0106 }
            if (r5 == 0) goto L_0x009c
            org.apache.commons.logging.Log r5 = r2.log     // Catch:{ GSSException -> 0x0106 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ GSSException -> 0x0106 }
            r0.<init>()     // Catch:{ GSSException -> 0x0106 }
            java.lang.String r1 = "init "
            r0.append(r1)     // Catch:{ GSSException -> 0x0106 }
            r0.append(r4)     // Catch:{ GSSException -> 0x0106 }
            java.lang.String r0 = r0.toString()     // Catch:{ GSSException -> 0x0106 }
            r5.debug(r0)     // Catch:{ GSSException -> 0x0106 }
        L_0x009c:
            byte[] r5 = r2.token     // Catch:{ GSSException -> 0x0106 }
            byte[] r3 = r2.generateToken(r5, r4, r3)     // Catch:{ GSSException -> 0x0106 }
            r2.token = r3     // Catch:{ GSSException -> 0x0106 }
            org.apache.http.impl.auth.GGSSchemeBase$State r3 = org.apache.http.impl.auth.GGSSchemeBase.State.TOKEN_GENERATED     // Catch:{ GSSException -> 0x0106 }
            r2.state = r3     // Catch:{ GSSException -> 0x0106 }
        L_0x00a8:
            java.lang.String r3 = new java.lang.String
            org.apache.commons.codec.binary.Base64 r4 = r2.base64codec
            byte[] r5 = r2.token
            byte[] r4 = r4.encode(r5)
            r3.<init>(r4)
            org.apache.commons.logging.Log r4 = r2.log
            boolean r4 = r4.isDebugEnabled()
            if (r4 == 0) goto L_0x00d8
            org.apache.commons.logging.Log r4 = r2.log
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r0 = "Sending response '"
            r5.append(r0)
            r5.append(r3)
            java.lang.String r0 = "' back to the auth server"
            r5.append(r0)
            java.lang.String r5 = r5.toString()
            r4.debug(r5)
        L_0x00d8:
            org.apache.http.util.CharArrayBuffer r4 = new org.apache.http.util.CharArrayBuffer
            r5 = 32
            r4.<init>(r5)
            boolean r5 = r2.isProxy()
            if (r5 == 0) goto L_0x00eb
            java.lang.String r5 = "Proxy-Authorization"
            r4.append(r5)
            goto L_0x00f0
        L_0x00eb:
            java.lang.String r5 = "Authorization"
            r4.append(r5)
        L_0x00f0:
            java.lang.String r5 = ": Negotiate "
            r4.append(r5)
            r4.append(r3)
            org.apache.http.message.BufferedHeader r3 = new org.apache.http.message.BufferedHeader
            r3.<init>(r4)
            return r3
        L_0x00fe:
            org.apache.http.auth.AuthenticationException r3 = new org.apache.http.auth.AuthenticationException     // Catch:{ GSSException -> 0x0106 }
            java.lang.String r4 = "Connection route is not available"
            r3.<init>(r4)     // Catch:{ GSSException -> 0x0106 }
            throw r3     // Catch:{ GSSException -> 0x0106 }
        L_0x0106:
            r3 = move-exception
            org.apache.http.impl.auth.GGSSchemeBase$State r4 = org.apache.http.impl.auth.GGSSchemeBase.State.FAILED
            r2.state = r4
            int r4 = r3.getMajor()
            r5 = 9
            if (r4 == r5) goto L_0x015a
            int r4 = r3.getMajor()
            r5 = 8
            if (r4 == r5) goto L_0x015a
            int r4 = r3.getMajor()
            r5 = 13
            if (r4 == r5) goto L_0x0150
            int r4 = r3.getMajor()
            r5 = 10
            if (r4 == r5) goto L_0x0146
            int r4 = r3.getMajor()
            r5 = 19
            if (r4 == r5) goto L_0x0146
            int r4 = r3.getMajor()
            r5 = 20
            if (r4 != r5) goto L_0x013c
            goto L_0x0146
        L_0x013c:
            org.apache.http.auth.AuthenticationException r4 = new org.apache.http.auth.AuthenticationException
            java.lang.String r3 = r3.getMessage()
            r4.<init>(r3)
            throw r4
        L_0x0146:
            org.apache.http.auth.AuthenticationException r4 = new org.apache.http.auth.AuthenticationException
            java.lang.String r5 = r3.getMessage()
            r4.<init>(r5, r3)
            throw r4
        L_0x0150:
            org.apache.http.auth.InvalidCredentialsException r4 = new org.apache.http.auth.InvalidCredentialsException
            java.lang.String r5 = r3.getMessage()
            r4.<init>(r5, r3)
            throw r4
        L_0x015a:
            org.apache.http.auth.InvalidCredentialsException r4 = new org.apache.http.auth.InvalidCredentialsException
            java.lang.String r5 = r3.getMessage()
            r4.<init>(r5, r3)
            throw r4
        L_0x0164:
            org.apache.http.auth.AuthenticationException r3 = new org.apache.http.auth.AuthenticationException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = r2.getSchemeName()
            r4.append(r5)
            java.lang.String r5 = " authentication has failed"
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            r3.<init>(r4)
            throw r3
        L_0x017f:
            org.apache.http.auth.AuthenticationException r3 = new org.apache.http.auth.AuthenticationException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = r2.getSchemeName()
            r4.append(r5)
            java.lang.String r5 = " authentication has not been initiated"
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            r3.<init>(r4)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.auth.GGSSchemeBase.authenticate(org.apache.http.auth.Credentials, org.apache.http.HttpRequest, org.apache.http.protocol.HttpContext):org.apache.http.Header");
    }

    /* access modifiers changed from: protected */
    public void parseChallenge(CharArrayBuffer charArrayBuffer, int i, int i2) throws MalformedChallengeException {
        String substringTrimmed = charArrayBuffer.substringTrimmed(i, i2);
        if (this.log.isDebugEnabled()) {
            Log log2 = this.log;
            StringBuilder sb = new StringBuilder();
            sb.append("Received challenge '");
            sb.append(substringTrimmed);
            sb.append("' from the auth server");
            log2.debug(sb.toString());
        }
        if (this.state == State.UNINITIATED) {
            this.token = Base64.decodeBase64(substringTrimmed.getBytes());
            this.state = State.CHALLENGE_RECEIVED;
            return;
        }
        this.log.debug("Authentication already attempted");
        this.state = State.FAILED;
    }

    private String resolveCanonicalHostname(String str) throws UnknownHostException {
        InetAddress byName = InetAddress.getByName(str);
        String canonicalHostName = byName.getCanonicalHostName();
        return byName.getHostAddress().contentEquals(canonicalHostName) ? str : canonicalHostName;
    }
}
