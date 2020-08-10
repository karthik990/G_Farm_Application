package org.apache.http.impl.execchain;

import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthState;
import org.apache.http.client.AuthenticationStrategy;
import org.apache.http.client.UserTokenHandler;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.routing.BasicRouteDirector;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.HttpRouteDirector;
import org.apache.http.conn.routing.RouteTracker;
import org.apache.http.impl.auth.HttpAuthenticator;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.protocol.ImmutableHttpProcessor;
import org.apache.http.protocol.RequestTargetHost;
import org.apache.http.util.Args;

public class MainClientExec implements ClientExecChain {
    private final HttpAuthenticator authenticator;
    private final HttpClientConnectionManager connManager;
    private final ConnectionKeepAliveStrategy keepAliveStrategy;
    private final Log log;
    private final AuthenticationStrategy proxyAuthStrategy;
    private final HttpProcessor proxyHttpProcessor;
    private final HttpRequestExecutor requestExecutor;
    private final ConnectionReuseStrategy reuseStrategy;
    private final HttpRouteDirector routeDirector;
    private final AuthenticationStrategy targetAuthStrategy;
    private final UserTokenHandler userTokenHandler;

    public MainClientExec(HttpRequestExecutor httpRequestExecutor, HttpClientConnectionManager httpClientConnectionManager, ConnectionReuseStrategy connectionReuseStrategy, ConnectionKeepAliveStrategy connectionKeepAliveStrategy, HttpProcessor httpProcessor, AuthenticationStrategy authenticationStrategy, AuthenticationStrategy authenticationStrategy2, UserTokenHandler userTokenHandler2) {
        this.log = LogFactory.getLog(getClass());
        Args.notNull(httpRequestExecutor, "HTTP request executor");
        Args.notNull(httpClientConnectionManager, "Client connection manager");
        Args.notNull(connectionReuseStrategy, "Connection reuse strategy");
        Args.notNull(connectionKeepAliveStrategy, "Connection keep alive strategy");
        Args.notNull(httpProcessor, "Proxy HTTP processor");
        Args.notNull(authenticationStrategy, "Target authentication strategy");
        Args.notNull(authenticationStrategy2, "Proxy authentication strategy");
        Args.notNull(userTokenHandler2, "User token handler");
        this.authenticator = new HttpAuthenticator();
        this.routeDirector = new BasicRouteDirector();
        this.requestExecutor = httpRequestExecutor;
        this.connManager = httpClientConnectionManager;
        this.reuseStrategy = connectionReuseStrategy;
        this.keepAliveStrategy = connectionKeepAliveStrategy;
        this.proxyHttpProcessor = httpProcessor;
        this.targetAuthStrategy = authenticationStrategy;
        this.proxyAuthStrategy = authenticationStrategy2;
        this.userTokenHandler = userTokenHandler2;
    }

    public MainClientExec(HttpRequestExecutor httpRequestExecutor, HttpClientConnectionManager httpClientConnectionManager, ConnectionReuseStrategy connectionReuseStrategy, ConnectionKeepAliveStrategy connectionKeepAliveStrategy, AuthenticationStrategy authenticationStrategy, AuthenticationStrategy authenticationStrategy2, UserTokenHandler userTokenHandler2) {
        ImmutableHttpProcessor immutableHttpProcessor = new ImmutableHttpProcessor(new RequestTargetHost());
        this(httpRequestExecutor, httpClientConnectionManager, connectionReuseStrategy, connectionKeepAliveStrategy, immutableHttpProcessor, authenticationStrategy, authenticationStrategy2, userTokenHandler2);
    }

    /* JADX WARNING: type inference failed for: r1v5, types: [org.apache.http.auth.AuthState] */
    /* JADX WARNING: type inference failed for: r1v6 */
    /* JADX WARNING: type inference failed for: r15v0 */
    /* JADX WARNING: type inference failed for: r0v1, types: [java.util.concurrent.ExecutionException] */
    /* JADX WARNING: type inference failed for: r1v11, types: [java.util.concurrent.ExecutionException] */
    /* JADX WARNING: type inference failed for: r2v2, types: [java.lang.Throwable] */
    /* JADX WARNING: type inference failed for: r1v12, types: [java.lang.Throwable] */
    /* JADX WARNING: type inference failed for: r1v13 */
    /* JADX WARNING: type inference failed for: r22v0, types: [org.apache.http.auth.AuthState] */
    /* JADX WARNING: type inference failed for: r22v1, types: [org.apache.http.auth.AuthState] */
    /* JADX WARNING: type inference failed for: r15v2 */
    /* JADX WARNING: type inference failed for: r22v3 */
    /* JADX WARNING: type inference failed for: r22v4 */
    /* JADX WARNING: type inference failed for: r22v5 */
    /* JADX WARNING: type inference failed for: r22v6 */
    /* JADX WARNING: type inference failed for: r1v25, types: [org.apache.http.HttpResponse] */
    /* JADX WARNING: type inference failed for: r22v7 */
    /* JADX WARNING: type inference failed for: r22v8 */
    /* JADX WARNING: type inference failed for: r22v9 */
    /* JADX WARNING: type inference failed for: r22v10 */
    /* JADX WARNING: type inference failed for: r22v11, types: [org.apache.http.auth.AuthState] */
    /* JADX WARNING: type inference failed for: r22v12 */
    /* JADX WARNING: type inference failed for: r22v13 */
    /* JADX WARNING: type inference failed for: r6v5 */
    /* JADX WARNING: type inference failed for: r22v14 */
    /* JADX WARNING: type inference failed for: r22v15 */
    /* JADX WARNING: type inference failed for: r5v2, types: [org.apache.http.HttpResponse] */
    /* JADX WARNING: type inference failed for: r22v16 */
    /* JADX WARNING: type inference failed for: r22v17 */
    /* JADX WARNING: type inference failed for: r22v18 */
    /* JADX WARNING: type inference failed for: r3v4, types: [org.apache.http.auth.AuthState] */
    /* JADX WARNING: type inference failed for: r21v6, types: [org.apache.http.HttpResponse] */
    /* JADX WARNING: type inference failed for: r5v3, types: [org.apache.http.HttpResponse] */
    /* JADX WARNING: type inference failed for: r22v19, types: [org.apache.http.auth.AuthState] */
    /* JADX WARNING: type inference failed for: r1v42 */
    /* JADX WARNING: type inference failed for: r15v4 */
    /* JADX WARNING: type inference failed for: r22v20 */
    /* JADX WARNING: type inference failed for: r22v21 */
    /* JADX WARNING: type inference failed for: r22v22 */
    /* JADX WARNING: type inference failed for: r22v23 */
    /* JADX WARNING: type inference failed for: r22v24 */
    /* JADX WARNING: type inference failed for: r22v25 */
    /* JADX WARNING: type inference failed for: r22v26 */
    /* JADX WARNING: type inference failed for: r22v27 */
    /* JADX WARNING: type inference failed for: r6v8 */
    /* JADX WARNING: type inference failed for: r6v9 */
    /* JADX WARNING: type inference failed for: r22v28 */
    /* JADX WARNING: type inference failed for: r6v10 */
    /* JADX WARNING: type inference failed for: r22v29 */
    /* JADX WARNING: type inference failed for: r6v11, types: [org.apache.http.auth.AuthState] */
    /* JADX WARNING: type inference failed for: r22v30 */
    /* JADX WARNING: type inference failed for: r22v31 */
    /* JADX WARNING: type inference failed for: r22v32 */
    /* JADX WARNING: type inference failed for: r2v34, types: [org.apache.http.auth.AuthState] */
    /* JADX WARNING: type inference failed for: r22v33 */
    /* JADX WARNING: type inference failed for: r1v77, types: [org.apache.http.HttpResponse] */
    /* JADX WARNING: type inference failed for: r15v7 */
    /* JADX WARNING: type inference failed for: r22v34 */
    /* JADX WARNING: type inference failed for: r15v8 */
    /* JADX WARNING: type inference failed for: r22v35 */
    /* JADX WARNING: type inference failed for: r1v92, types: [java.lang.Object, org.apache.http.auth.AuthState] */
    /* JADX WARNING: type inference failed for: r15v9 */
    /* JADX WARNING: type inference failed for: r15v10 */
    /* JADX WARNING: type inference failed for: r15v11 */
    /* JADX WARNING: type inference failed for: r15v12 */
    /* JADX WARNING: type inference failed for: r15v13 */
    /* JADX WARNING: type inference failed for: r15v14 */
    /* JADX WARNING: type inference failed for: r15v15 */
    /* JADX WARNING: type inference failed for: r22v36 */
    /* JADX WARNING: type inference failed for: r22v37 */
    /* JADX WARNING: type inference failed for: r22v38 */
    /* JADX WARNING: type inference failed for: r22v39 */
    /* JADX WARNING: type inference failed for: r22v40 */
    /* JADX WARNING: type inference failed for: r22v41 */
    /* JADX WARNING: type inference failed for: r22v42 */
    /* JADX WARNING: type inference failed for: r22v43 */
    /* JADX WARNING: type inference failed for: r22v44 */
    /* JADX WARNING: type inference failed for: r22v45 */
    /* JADX WARNING: type inference failed for: r22v46 */
    /* JADX WARNING: type inference failed for: r22v47 */
    /* JADX WARNING: type inference failed for: r22v48 */
    /* JADX WARNING: type inference failed for: r22v49 */
    /* JADX WARNING: type inference failed for: r22v50 */
    /* JADX WARNING: type inference failed for: r22v51 */
    /* JADX WARNING: type inference failed for: r22v52 */
    /* JADX WARNING: type inference failed for: r22v53 */
    /* JADX WARNING: type inference failed for: r22v54 */
    /* JADX WARNING: type inference failed for: r22v55 */
    /* JADX WARNING: type inference failed for: r6v13 */
    /* JADX WARNING: type inference failed for: r6v14 */
    /* JADX WARNING: type inference failed for: r22v56 */
    /* JADX WARNING: type inference failed for: r22v57 */
    /* JADX WARNING: type inference failed for: r22v58 */
    /* JADX WARNING: type inference failed for: r22v59 */
    /* JADX WARNING: type inference failed for: r22v60 */
    /* JADX WARNING: type inference failed for: r22v61 */
    /* JADX WARNING: type inference failed for: r22v62 */
    /* JADX WARNING: type inference failed for: r22v63 */
    /* JADX WARNING: type inference failed for: r22v64 */
    /* JADX WARNING: type inference failed for: r6v15 */
    /* JADX WARNING: type inference failed for: r6v16 */
    /* JADX WARNING: type inference failed for: r6v17 */
    /* JADX WARNING: type inference failed for: r6v18 */
    /* JADX WARNING: type inference failed for: r6v19 */
    /* JADX WARNING: type inference failed for: r22v65 */
    /* JADX WARNING: type inference failed for: r22v66 */
    /* JADX WARNING: type inference failed for: r22v67 */
    /* JADX WARNING: type inference failed for: r1v94 */
    /* JADX WARNING: type inference failed for: r1v95 */
    /* JADX WARNING: Code restructure failed: missing block: B:126:0x020d, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:127:0x020e, code lost:
        r1 = r0;
        r22 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:128:0x0213, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:129:0x0214, code lost:
        r1 = r0;
        r22 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:150:0x0284, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:151:0x0286, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:152:0x0288, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:153:0x0289, code lost:
        r11 = r21;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:154:0x028b, code lost:
        r1 = r0;
        r22 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:155:0x0290, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:156:0x0291, code lost:
        r11 = r21;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:157:0x0293, code lost:
        r1 = r0;
        r22 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:188:0x032a, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:189:0x032b, code lost:
        r22 = r22;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:190:0x032c, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:191:0x032d, code lost:
        r22 = r22;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:192:0x032f, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:206:0x0356, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:207:0x0357, code lost:
        r22 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:208:0x035a, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:209:0x035b, code lost:
        r22 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:210:0x035e, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:211:0x035f, code lost:
        r22 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:212:0x0362, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:213:0x0363, code lost:
        r22 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:214:0x0366, code lost:
        r0 = e;
        r22 = r22;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:215:0x0367, code lost:
        r11 = r21;
        r22 = r22;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:216:0x036a, code lost:
        r0 = e;
        r22 = r22;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:217:0x036b, code lost:
        r11 = r21;
        r22 = r22;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:218:0x036e, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:219:0x036f, code lost:
        r11 = r21;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:220:0x0372, code lost:
        r7.connManager.shutdown();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:221:0x0377, code lost:
        throw r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:222:0x0378, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:223:0x0379, code lost:
        r11 = r3;
        r22 = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:224:0x037c, code lost:
        r1 = r0;
        r22 = r22;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:227:0x0386, code lost:
        r22.reset();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:230:0x038f, code lost:
        r14.reset();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:232:0x0393, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:233:0x0394, code lost:
        r11 = r3;
        r22 = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:234:0x0397, code lost:
        r1 = r0;
        r22 = r22;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:237:0x03a1, code lost:
        r22.reset();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:240:0x03aa, code lost:
        r14.reset();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:242:0x03ae, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:243:0x03af, code lost:
        r11 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:244:0x03b0, code lost:
        r1 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:245:0x03b1, code lost:
        r11.abortConnection();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:246:0x03b4, code lost:
        throw r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:247:0x03b5, code lost:
        r2 = new java.io.InterruptedIOException("Connection has been shut down");
        r2.initCause(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:248:0x03bf, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00b7, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00b8, code lost:
        r1 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00bb, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00bc, code lost:
        r1 = r0;
        r11 = r3;
        r22 = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00c2, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00c3, code lost:
        r1 = r0;
        r11 = r3;
        r22 = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00c9, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00ca, code lost:
        r1 = r0;
        r11 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00ce, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00cf, code lost:
        r1 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x0147, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x0148, code lost:
        r1 = r0;
        r22 = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x014c, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x014d, code lost:
        r1 = r0;
        r22 = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x0173, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x0174, code lost:
        r1 = r0;
        r11 = r21;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r22v3
      assigns: []
      uses: []
      mth insns count: 473
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:135:0x022e A[SYNTHETIC, Splitter:B:135:0x022e] */
    /* JADX WARNING: Removed duplicated region for block: B:158:0x0298  */
    /* JADX WARNING: Removed duplicated region for block: B:165:0x02b3 A[Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x032f, IOException -> 0x032c, RuntimeException -> 0x032a, Error -> 0x00b7 }] */
    /* JADX WARNING: Removed duplicated region for block: B:192:0x032f A[Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x032f, IOException -> 0x032c, RuntimeException -> 0x032a, Error -> 0x00b7 }, ExcHandler: HttpException (e org.apache.http.HttpException), PHI: r11 
      PHI: (r11v18 org.apache.http.impl.execchain.ConnectionHolder) = (r11v21 org.apache.http.impl.execchain.ConnectionHolder), (r11v23 org.apache.http.impl.execchain.ConnectionHolder), (r11v23 org.apache.http.impl.execchain.ConnectionHolder), (r11v28 org.apache.http.impl.execchain.ConnectionHolder), (r11v28 org.apache.http.impl.execchain.ConnectionHolder) binds: [B:162:0x02ad, B:159:0x029c, B:160:?, B:148:0x027d, B:149:?] A[DONT_GENERATE, DONT_INLINE], Splitter:B:148:0x027d] */
    /* JADX WARNING: Removed duplicated region for block: B:218:0x036e A[ExcHandler: HttpException (e org.apache.http.HttpException), Splitter:B:81:0x015d] */
    /* JADX WARNING: Removed duplicated region for block: B:227:0x0386  */
    /* JADX WARNING: Removed duplicated region for block: B:230:0x038f  */
    /* JADX WARNING: Removed duplicated region for block: B:237:0x03a1  */
    /* JADX WARNING: Removed duplicated region for block: B:240:0x03aa  */
    /* JADX WARNING: Removed duplicated region for block: B:259:0x031a A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00b7 A[Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x00c9, IOException -> 0x00c2, RuntimeException -> 0x00bb, Error -> 0x00b7 }, ExcHandler: Error (r0v38 'e' java.lang.Error A[CUSTOM_DECLARE, Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x00c9, IOException -> 0x00c2, RuntimeException -> 0x00bb, Error -> 0x00b7 }]), Splitter:B:57:0x00f4] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00ce A[Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x00c9, IOException -> 0x00c2, RuntimeException -> 0x00bb, Error -> 0x00b7 }, ExcHandler: ConnectionShutdownException (r0v34 'e' org.apache.http.impl.conn.ConnectionShutdownException A[CUSTOM_DECLARE, Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x00c9, IOException -> 0x00c2, RuntimeException -> 0x00bb, Error -> 0x00b7 }]), Splitter:B:32:0x00b3] */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x0173 A[Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x016d, RuntimeException -> 0x0167, Error -> 0x00b7 }, ExcHandler: HttpException (r0v8 'e' org.apache.http.HttpException A[CUSTOM_DECLARE, Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x016d, RuntimeException -> 0x0167, Error -> 0x00b7 }]), PHI: r21 
      PHI: (r21v0 org.apache.http.impl.execchain.ConnectionHolder) = (r21v5 org.apache.http.impl.execchain.ConnectionHolder), (r21v5 org.apache.http.impl.execchain.ConnectionHolder), (r21v5 org.apache.http.impl.execchain.ConnectionHolder), (r21v5 org.apache.http.impl.execchain.ConnectionHolder), (r21v5 org.apache.http.impl.execchain.ConnectionHolder), (r21v5 org.apache.http.impl.execchain.ConnectionHolder), (r21v5 org.apache.http.impl.execchain.ConnectionHolder), (r21v5 org.apache.http.impl.execchain.ConnectionHolder), (r21v8 org.apache.http.impl.execchain.ConnectionHolder), (r21v8 org.apache.http.impl.execchain.ConnectionHolder), (r21v8 org.apache.http.impl.execchain.ConnectionHolder) binds: [B:140:0x0240, B:116:0x01dd, B:124:0x0209, B:125:?, B:108:0x01b0, B:103:0x0190, B:104:?, B:84:0x0163, B:63:0x0107, B:64:?, B:70:0x012e] A[DONT_GENERATE, DONT_INLINE], Splitter:B:63:0x0107] */
    /* JADX WARNING: Unknown variable types count: 40 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.apache.http.client.methods.CloseableHttpResponse execute(org.apache.http.conn.routing.HttpRoute r25, org.apache.http.client.methods.HttpRequestWrapper r26, org.apache.http.client.protocol.HttpClientContext r27, org.apache.http.client.methods.HttpExecutionAware r28) throws java.io.IOException, org.apache.http.HttpException {
        /*
            r24 = this;
            r7 = r24
            r8 = r25
            r9 = r26
            r10 = r27
            r11 = r28
            java.lang.String r12 = "Proxy-Authorization"
            java.lang.String r13 = "Authorization"
            java.lang.String r1 = "HTTP route"
            org.apache.http.util.Args.notNull(r8, r1)
            java.lang.String r1 = "HTTP request"
            org.apache.http.util.Args.notNull(r9, r1)
            java.lang.String r1 = "HTTP context"
            org.apache.http.util.Args.notNull(r10, r1)
            org.apache.http.auth.AuthState r1 = r27.getTargetAuthState()
            if (r1 != 0) goto L_0x002d
            org.apache.http.auth.AuthState r1 = new org.apache.http.auth.AuthState
            r1.<init>()
            java.lang.String r2 = "http.auth.target-scope"
            r10.setAttribute(r2, r1)
        L_0x002d:
            r14 = r1
            org.apache.http.auth.AuthState r1 = r27.getProxyAuthState()
            if (r1 != 0) goto L_0x003e
            org.apache.http.auth.AuthState r1 = new org.apache.http.auth.AuthState
            r1.<init>()
            java.lang.String r2 = "http.auth.proxy-scope"
            r10.setAttribute(r2, r1)
        L_0x003e:
            r15 = r1
            boolean r1 = r9 instanceof org.apache.http.HttpEntityEnclosingRequest
            if (r1 == 0) goto L_0x0049
            r1 = r9
            org.apache.http.HttpEntityEnclosingRequest r1 = (org.apache.http.HttpEntityEnclosingRequest) r1
            org.apache.http.impl.execchain.RequestEntityProxy.enhance(r1)
        L_0x0049:
            java.lang.Object r6 = r27.getUserToken()
            org.apache.http.conn.HttpClientConnectionManager r1 = r7.connManager
            org.apache.http.conn.ConnectionRequest r1 = r1.requestConnection(r8, r6)
            java.lang.String r5 = "Request aborted"
            if (r11 == 0) goto L_0x006a
            boolean r2 = r28.isAborted()
            if (r2 != 0) goto L_0x0061
            r11.setCancellable(r1)
            goto L_0x006a
        L_0x0061:
            r1.cancel()
            org.apache.http.impl.execchain.RequestAbortedException r1 = new org.apache.http.impl.execchain.RequestAbortedException
            r1.<init>(r5)
            throw r1
        L_0x006a:
            org.apache.http.client.config.RequestConfig r16 = r27.getRequestConfig()
            int r2 = r16.getConnectionRequestTimeout()     // Catch:{ InterruptedException -> 0x03d2, ExecutionException -> 0x03c0 }
            r17 = 0
            if (r2 <= 0) goto L_0x0078
            long r2 = (long) r2     // Catch:{ InterruptedException -> 0x03d2, ExecutionException -> 0x03c0 }
            goto L_0x007a
        L_0x0078:
            r2 = r17
        L_0x007a:
            java.util.concurrent.TimeUnit r4 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ InterruptedException -> 0x03d2, ExecutionException -> 0x03c0 }
            org.apache.http.HttpClientConnection r4 = r1.get(r2, r4)     // Catch:{ InterruptedException -> 0x03d2, ExecutionException -> 0x03c0 }
            java.lang.String r1 = "http.connection"
            r10.setAttribute(r1, r4)
            boolean r1 = r16.isStaleConnectionCheckEnabled()
            if (r1 == 0) goto L_0x00a8
            boolean r1 = r4.isOpen()
            if (r1 == 0) goto L_0x00a8
            org.apache.commons.logging.Log r1 = r7.log
            java.lang.String r2 = "Stale connection check"
            r1.debug(r2)
            boolean r1 = r4.isStale()
            if (r1 == 0) goto L_0x00a8
            org.apache.commons.logging.Log r1 = r7.log
            java.lang.String r2 = "Stale connection detected"
            r1.debug(r2)
            r4.close()
        L_0x00a8:
            org.apache.http.impl.execchain.ConnectionHolder r3 = new org.apache.http.impl.execchain.ConnectionHolder
            org.apache.commons.logging.Log r1 = r7.log
            org.apache.http.conn.HttpClientConnectionManager r2 = r7.connManager
            r3.<init>(r1, r2, r4)
            if (r11 == 0) goto L_0x00d2
            r11.setCancellable(r3)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x00c9, IOException -> 0x00c2, RuntimeException -> 0x00bb, Error -> 0x00b7 }
            goto L_0x00d2
        L_0x00b7:
            r0 = move-exception
            r1 = r0
            goto L_0x0372
        L_0x00bb:
            r0 = move-exception
            r1 = r0
            r11 = r3
            r22 = r15
            goto L_0x037d
        L_0x00c2:
            r0 = move-exception
            r1 = r0
            r11 = r3
            r22 = r15
            goto L_0x0398
        L_0x00c9:
            r0 = move-exception
            r1 = r0
            r11 = r3
            goto L_0x03b1
        L_0x00ce:
            r0 = move-exception
            r1 = r0
            goto L_0x03b5
        L_0x00d2:
            r2 = 1
            r1 = 1
        L_0x00d4:
            if (r1 <= r2) goto L_0x00e5
            boolean r19 = org.apache.http.impl.execchain.RequestEntityProxy.isRepeatable(r26)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x00c9, IOException -> 0x00c2, RuntimeException -> 0x00bb, Error -> 0x00b7 }
            if (r19 == 0) goto L_0x00dd
            goto L_0x00e5
        L_0x00dd:
            org.apache.http.client.NonRepeatableRequestException r1 = new org.apache.http.client.NonRepeatableRequestException     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x00c9, IOException -> 0x00c2, RuntimeException -> 0x00bb, Error -> 0x00b7 }
            java.lang.String r2 = "Cannot retry request with a non-repeatable request entity."
            r1.<init>(r2)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x00c9, IOException -> 0x00c2, RuntimeException -> 0x00bb, Error -> 0x00b7 }
            throw r1     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x00c9, IOException -> 0x00c2, RuntimeException -> 0x00bb, Error -> 0x00b7 }
        L_0x00e5:
            if (r11 == 0) goto L_0x00f4
            boolean r19 = r28.isAborted()     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x00c9, IOException -> 0x00c2, RuntimeException -> 0x00bb, Error -> 0x00b7 }
            if (r19 != 0) goto L_0x00ee
            goto L_0x00f4
        L_0x00ee:
            org.apache.http.impl.execchain.RequestAbortedException r1 = new org.apache.http.impl.execchain.RequestAbortedException     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x00c9, IOException -> 0x00c2, RuntimeException -> 0x00bb, Error -> 0x00b7 }
            r1.<init>(r5)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x00c9, IOException -> 0x00c2, RuntimeException -> 0x00bb, Error -> 0x00b7 }
            throw r1     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x00c9, IOException -> 0x00c2, RuntimeException -> 0x00bb, Error -> 0x00b7 }
        L_0x00f4:
            boolean r19 = r4.isOpen()     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x03ae, IOException -> 0x0393, RuntimeException -> 0x0378, Error -> 0x00b7 }
            if (r19 != 0) goto L_0x0151
            org.apache.commons.logging.Log r2 = r7.log     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x00c9, IOException -> 0x00c2, RuntimeException -> 0x00bb, Error -> 0x00b7 }
            r20 = r1
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x00c9, IOException -> 0x00c2, RuntimeException -> 0x00bb, Error -> 0x00b7 }
            r1.<init>()     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x00c9, IOException -> 0x00c2, RuntimeException -> 0x00bb, Error -> 0x00b7 }
            r21 = r3
            java.lang.String r3 = "Opening connection "
            r1.append(r3)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x014c, RuntimeException -> 0x0147, Error -> 0x00b7 }
            r1.append(r8)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x014c, RuntimeException -> 0x0147, Error -> 0x00b7 }
            java.lang.String r1 = r1.toString()     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x014c, RuntimeException -> 0x0147, Error -> 0x00b7 }
            r2.debug(r1)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x014c, RuntimeException -> 0x0147, Error -> 0x00b7 }
            r1 = r24
            r19 = 1
            r2 = r15
            r8 = r21
            r3 = r4
            r8 = r4
            r4 = r25
            r22 = r15
            r15 = r5
            r5 = r26
            r23 = r6
            r6 = r27
            r1.establishRoute(r2, r3, r4, r5, r6)     // Catch:{ TunnelRefusedException -> 0x012c }
            goto L_0x015d
        L_0x012c:
            r0 = move-exception
            r1 = r0
            org.apache.commons.logging.Log r2 = r7.log     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x016d, RuntimeException -> 0x0167, Error -> 0x00b7 }
            boolean r2 = r2.isDebugEnabled()     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x016d, RuntimeException -> 0x0167, Error -> 0x00b7 }
            if (r2 == 0) goto L_0x013f
            org.apache.commons.logging.Log r2 = r7.log     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x016d, RuntimeException -> 0x0167, Error -> 0x00b7 }
            java.lang.String r3 = r1.getMessage()     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x016d, RuntimeException -> 0x0167, Error -> 0x00b7 }
            r2.debug(r3)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x016d, RuntimeException -> 0x0167, Error -> 0x00b7 }
        L_0x013f:
            org.apache.http.HttpResponse r1 = r1.getResponse()     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x016d, RuntimeException -> 0x0167, Error -> 0x00b7 }
            r11 = r21
            goto L_0x031c
        L_0x0147:
            r0 = move-exception
            r1 = r0
            r22 = r15
            goto L_0x0169
        L_0x014c:
            r0 = move-exception
            r1 = r0
            r22 = r15
            goto L_0x016f
        L_0x0151:
            r20 = r1
            r21 = r3
            r8 = r4
            r23 = r6
            r22 = r15
            r19 = 1
            r15 = r5
        L_0x015d:
            int r1 = r16.getSocketTimeout()     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x036e, IOException -> 0x036a, RuntimeException -> 0x0366, Error -> 0x00b7 }
            if (r1 < 0) goto L_0x0179
            r8.setSocketTimeout(r1)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x016d, RuntimeException -> 0x0167, Error -> 0x00b7 }
            goto L_0x0179
        L_0x0167:
            r0 = move-exception
            r1 = r0
        L_0x0169:
            r11 = r21
            goto L_0x037d
        L_0x016d:
            r0 = move-exception
            r1 = r0
        L_0x016f:
            r11 = r21
            goto L_0x0398
        L_0x0173:
            r0 = move-exception
            r1 = r0
            r11 = r21
            goto L_0x03b1
        L_0x0179:
            if (r11 == 0) goto L_0x0188
            boolean r1 = r28.isAborted()     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x016d, RuntimeException -> 0x0167, Error -> 0x00b7 }
            if (r1 != 0) goto L_0x0182
            goto L_0x0188
        L_0x0182:
            org.apache.http.impl.execchain.RequestAbortedException r1 = new org.apache.http.impl.execchain.RequestAbortedException     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x016d, RuntimeException -> 0x0167, Error -> 0x00b7 }
            r1.<init>(r15)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x016d, RuntimeException -> 0x0167, Error -> 0x00b7 }
            throw r1     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x016d, RuntimeException -> 0x0167, Error -> 0x00b7 }
        L_0x0188:
            org.apache.commons.logging.Log r1 = r7.log     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x036e, IOException -> 0x036a, RuntimeException -> 0x0366, Error -> 0x00b7 }
            boolean r1 = r1.isDebugEnabled()     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x036e, IOException -> 0x036a, RuntimeException -> 0x0366, Error -> 0x00b7 }
            if (r1 == 0) goto L_0x01aa
            org.apache.commons.logging.Log r1 = r7.log     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x016d, RuntimeException -> 0x0167, Error -> 0x00b7 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x016d, RuntimeException -> 0x0167, Error -> 0x00b7 }
            r2.<init>()     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x016d, RuntimeException -> 0x0167, Error -> 0x00b7 }
            java.lang.String r3 = "Executing request "
            r2.append(r3)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x016d, RuntimeException -> 0x0167, Error -> 0x00b7 }
            org.apache.http.RequestLine r3 = r26.getRequestLine()     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x016d, RuntimeException -> 0x0167, Error -> 0x00b7 }
            r2.append(r3)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x016d, RuntimeException -> 0x0167, Error -> 0x00b7 }
            java.lang.String r2 = r2.toString()     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x016d, RuntimeException -> 0x0167, Error -> 0x00b7 }
            r1.debug(r2)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x016d, RuntimeException -> 0x0167, Error -> 0x00b7 }
        L_0x01aa:
            boolean r1 = r9.containsHeader(r13)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x036e, IOException -> 0x036a, RuntimeException -> 0x0366, Error -> 0x00b7 }
            if (r1 != 0) goto L_0x01d7
            org.apache.commons.logging.Log r1 = r7.log     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x016d, RuntimeException -> 0x0167, Error -> 0x00b7 }
            boolean r1 = r1.isDebugEnabled()     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x016d, RuntimeException -> 0x0167, Error -> 0x00b7 }
            if (r1 == 0) goto L_0x01d2
            org.apache.commons.logging.Log r1 = r7.log     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x016d, RuntimeException -> 0x0167, Error -> 0x00b7 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x016d, RuntimeException -> 0x0167, Error -> 0x00b7 }
            r2.<init>()     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x016d, RuntimeException -> 0x0167, Error -> 0x00b7 }
            java.lang.String r3 = "Target auth state: "
            r2.append(r3)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x016d, RuntimeException -> 0x0167, Error -> 0x00b7 }
            org.apache.http.auth.AuthProtocolState r3 = r14.getState()     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x016d, RuntimeException -> 0x0167, Error -> 0x00b7 }
            r2.append(r3)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x016d, RuntimeException -> 0x0167, Error -> 0x00b7 }
            java.lang.String r2 = r2.toString()     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x016d, RuntimeException -> 0x0167, Error -> 0x00b7 }
            r1.debug(r2)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x016d, RuntimeException -> 0x0167, Error -> 0x00b7 }
        L_0x01d2:
            org.apache.http.impl.auth.HttpAuthenticator r1 = r7.authenticator     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x016d, RuntimeException -> 0x0167, Error -> 0x00b7 }
            r1.generateAuthResponse(r9, r14, r10)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x016d, RuntimeException -> 0x0167, Error -> 0x00b7 }
        L_0x01d7:
            boolean r1 = r9.containsHeader(r12)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x036e, IOException -> 0x036a, RuntimeException -> 0x0366, Error -> 0x00b7 }
            if (r1 != 0) goto L_0x0219
            boolean r1 = r25.isTunnelled()     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x016d, RuntimeException -> 0x0167, Error -> 0x00b7 }
            if (r1 != 0) goto L_0x0219
            org.apache.commons.logging.Log r1 = r7.log     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x016d, RuntimeException -> 0x0167, Error -> 0x00b7 }
            boolean r1 = r1.isDebugEnabled()     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x016d, RuntimeException -> 0x0167, Error -> 0x00b7 }
            if (r1 == 0) goto L_0x0205
            org.apache.commons.logging.Log r1 = r7.log     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x016d, RuntimeException -> 0x0167, Error -> 0x00b7 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x016d, RuntimeException -> 0x0167, Error -> 0x00b7 }
            r2.<init>()     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x016d, RuntimeException -> 0x0167, Error -> 0x00b7 }
            java.lang.String r3 = "Proxy auth state: "
            r2.append(r3)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x016d, RuntimeException -> 0x0167, Error -> 0x00b7 }
            org.apache.http.auth.AuthProtocolState r3 = r22.getState()     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x016d, RuntimeException -> 0x0167, Error -> 0x00b7 }
            r2.append(r3)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x016d, RuntimeException -> 0x0167, Error -> 0x00b7 }
            java.lang.String r2 = r2.toString()     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x016d, RuntimeException -> 0x0167, Error -> 0x00b7 }
            r1.debug(r2)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x016d, RuntimeException -> 0x0167, Error -> 0x00b7 }
        L_0x0205:
            org.apache.http.impl.auth.HttpAuthenticator r1 = r7.authenticator     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x016d, RuntimeException -> 0x0167, Error -> 0x00b7 }
            r6 = r22
            r1.generateAuthResponse(r9, r6, r10)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x0213, RuntimeException -> 0x020d, Error -> 0x00b7 }
            goto L_0x021b
        L_0x020d:
            r0 = move-exception
            r1 = r0
            r22 = r6
            goto L_0x0169
        L_0x0213:
            r0 = move-exception
            r1 = r0
            r22 = r6
            goto L_0x016f
        L_0x0219:
            r6 = r22
        L_0x021b:
            java.lang.String r1 = "http.request"
            r10.setAttribute(r1, r9)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x036e, IOException -> 0x0362, RuntimeException -> 0x035e, Error -> 0x00b7 }
            org.apache.http.protocol.HttpRequestExecutor r1 = r7.requestExecutor     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x036e, IOException -> 0x0362, RuntimeException -> 0x035e, Error -> 0x00b7 }
            org.apache.http.HttpResponse r5 = r1.execute(r9, r8, r10)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x036e, IOException -> 0x0362, RuntimeException -> 0x035e, Error -> 0x00b7 }
            org.apache.http.ConnectionReuseStrategy r1 = r7.reuseStrategy     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x036e, IOException -> 0x0362, RuntimeException -> 0x035e, Error -> 0x00b7 }
            boolean r1 = r1.keepAlive(r5, r10)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x036e, IOException -> 0x0362, RuntimeException -> 0x035e, Error -> 0x00b7 }
            if (r1 == 0) goto L_0x0298
            org.apache.http.conn.ConnectionKeepAliveStrategy r1 = r7.keepAliveStrategy     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x036e, IOException -> 0x0290, RuntimeException -> 0x0288, Error -> 0x00b7 }
            long r1 = r1.getKeepAliveDuration(r5, r10)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x036e, IOException -> 0x0290, RuntimeException -> 0x0288, Error -> 0x00b7 }
            org.apache.commons.logging.Log r3 = r7.log     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x036e, IOException -> 0x0290, RuntimeException -> 0x0288, Error -> 0x00b7 }
            boolean r3 = r3.isDebugEnabled()     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x036e, IOException -> 0x0290, RuntimeException -> 0x0288, Error -> 0x00b7 }
            if (r3 == 0) goto L_0x0277
            int r3 = (r1 > r17 ? 1 : (r1 == r17 ? 0 : -1))
            if (r3 <= 0) goto L_0x025c
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x0213, RuntimeException -> 0x020d, Error -> 0x00b7 }
            r3.<init>()     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x0213, RuntimeException -> 0x020d, Error -> 0x00b7 }
            java.lang.String r4 = "for "
            r3.append(r4)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x0213, RuntimeException -> 0x020d, Error -> 0x00b7 }
            r3.append(r1)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x0213, RuntimeException -> 0x020d, Error -> 0x00b7 }
            java.lang.String r4 = " "
            r3.append(r4)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x0213, RuntimeException -> 0x020d, Error -> 0x00b7 }
            java.util.concurrent.TimeUnit r4 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x0213, RuntimeException -> 0x020d, Error -> 0x00b7 }
            r3.append(r4)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x0213, RuntimeException -> 0x020d, Error -> 0x00b7 }
            java.lang.String r3 = r3.toString()     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x0213, RuntimeException -> 0x020d, Error -> 0x00b7 }
            goto L_0x025e
        L_0x025c:
            java.lang.String r3 = "indefinitely"
        L_0x025e:
            org.apache.commons.logging.Log r4 = r7.log     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x0213, RuntimeException -> 0x020d, Error -> 0x00b7 }
            r22 = r5
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x0213, RuntimeException -> 0x020d, Error -> 0x00b7 }
            r5.<init>()     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x0213, RuntimeException -> 0x020d, Error -> 0x00b7 }
            java.lang.String r11 = "Connection can be kept alive "
            r5.append(r11)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x0213, RuntimeException -> 0x020d, Error -> 0x00b7 }
            r5.append(r3)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x0213, RuntimeException -> 0x020d, Error -> 0x00b7 }
            java.lang.String r3 = r5.toString()     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x0213, RuntimeException -> 0x020d, Error -> 0x00b7 }
            r4.debug(r3)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x0173, IOException -> 0x0213, RuntimeException -> 0x020d, Error -> 0x00b7 }
            goto L_0x0279
        L_0x0277:
            r22 = r5
        L_0x0279:
            java.util.concurrent.TimeUnit r3 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x036e, IOException -> 0x0290, RuntimeException -> 0x0288, Error -> 0x00b7 }
            r11 = r21
            r11.setValidFor(r1, r3)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x032f, IOException -> 0x0286, RuntimeException -> 0x0284, Error -> 0x00b7 }
            r11.markReusable()     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x032f, IOException -> 0x0286, RuntimeException -> 0x0284, Error -> 0x00b7 }
            goto L_0x029f
        L_0x0284:
            r0 = move-exception
            goto L_0x028b
        L_0x0286:
            r0 = move-exception
            goto L_0x0293
        L_0x0288:
            r0 = move-exception
            r11 = r21
        L_0x028b:
            r1 = r0
            r22 = r6
            goto L_0x037d
        L_0x0290:
            r0 = move-exception
            r11 = r21
        L_0x0293:
            r1 = r0
            r22 = r6
            goto L_0x0398
        L_0x0298:
            r22 = r5
            r11 = r21
            r11.markNonReusable()     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x032f, IOException -> 0x035a, RuntimeException -> 0x0356, Error -> 0x00b7 }
        L_0x029f:
            r1 = r24
            r2 = r14
            r3 = r6
            r4 = r25
            r21 = r22
            r5 = r21
            r22 = r6
            r6 = r27
            boolean r1 = r1.needAuthentication(r2, r3, r4, r5, r6)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x032f, IOException -> 0x032c, RuntimeException -> 0x032a, Error -> 0x00b7 }
            if (r1 == 0) goto L_0x031a
            org.apache.http.HttpEntity r1 = r21.getEntity()     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x032f, IOException -> 0x032c, RuntimeException -> 0x032a, Error -> 0x00b7 }
            boolean r2 = r11.isReusable()     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x032f, IOException -> 0x032c, RuntimeException -> 0x032a, Error -> 0x00b7 }
            if (r2 == 0) goto L_0x02c1
            org.apache.http.util.EntityUtils.consume(r1)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x032f, IOException -> 0x032c, RuntimeException -> 0x032a, Error -> 0x00b7 }
            goto L_0x02f4
        L_0x02c1:
            r8.close()     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x032f, IOException -> 0x032c, RuntimeException -> 0x032a, Error -> 0x00b7 }
            org.apache.http.auth.AuthProtocolState r1 = r22.getState()     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x032f, IOException -> 0x032c, RuntimeException -> 0x032a, Error -> 0x00b7 }
            org.apache.http.auth.AuthProtocolState r2 = org.apache.http.auth.AuthProtocolState.SUCCESS     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x032f, IOException -> 0x032c, RuntimeException -> 0x032a, Error -> 0x00b7 }
            if (r1 != r2) goto L_0x02dc
            boolean r1 = r22.isConnectionBased()     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x032f, IOException -> 0x032c, RuntimeException -> 0x032a, Error -> 0x00b7 }
            if (r1 == 0) goto L_0x02dc
            org.apache.commons.logging.Log r1 = r7.log     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x032f, IOException -> 0x032c, RuntimeException -> 0x032a, Error -> 0x00b7 }
            java.lang.String r2 = "Resetting proxy auth state"
            r1.debug(r2)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x032f, IOException -> 0x032c, RuntimeException -> 0x032a, Error -> 0x00b7 }
            r22.reset()     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x032f, IOException -> 0x032c, RuntimeException -> 0x032a, Error -> 0x00b7 }
        L_0x02dc:
            org.apache.http.auth.AuthProtocolState r1 = r14.getState()     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x032f, IOException -> 0x032c, RuntimeException -> 0x032a, Error -> 0x00b7 }
            org.apache.http.auth.AuthProtocolState r2 = org.apache.http.auth.AuthProtocolState.SUCCESS     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x032f, IOException -> 0x032c, RuntimeException -> 0x032a, Error -> 0x00b7 }
            if (r1 != r2) goto L_0x02f4
            boolean r1 = r14.isConnectionBased()     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x032f, IOException -> 0x032c, RuntimeException -> 0x032a, Error -> 0x00b7 }
            if (r1 == 0) goto L_0x02f4
            org.apache.commons.logging.Log r1 = r7.log     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x032f, IOException -> 0x032c, RuntimeException -> 0x032a, Error -> 0x00b7 }
            java.lang.String r2 = "Resetting target auth state"
            r1.debug(r2)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x032f, IOException -> 0x032c, RuntimeException -> 0x032a, Error -> 0x00b7 }
            r14.reset()     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x032f, IOException -> 0x032c, RuntimeException -> 0x032a, Error -> 0x00b7 }
        L_0x02f4:
            org.apache.http.HttpRequest r1 = r26.getOriginal()     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x032f, IOException -> 0x032c, RuntimeException -> 0x032a, Error -> 0x00b7 }
            boolean r2 = r1.containsHeader(r13)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x032f, IOException -> 0x032c, RuntimeException -> 0x032a, Error -> 0x00b7 }
            if (r2 != 0) goto L_0x0301
            r9.removeHeaders(r13)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x032f, IOException -> 0x032c, RuntimeException -> 0x032a, Error -> 0x00b7 }
        L_0x0301:
            boolean r1 = r1.containsHeader(r12)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x032f, IOException -> 0x032c, RuntimeException -> 0x032a, Error -> 0x00b7 }
            if (r1 != 0) goto L_0x030a
            r9.removeHeaders(r12)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x032f, IOException -> 0x032c, RuntimeException -> 0x032a, Error -> 0x00b7 }
        L_0x030a:
            int r1 = r20 + 1
            r4 = r8
            r3 = r11
            r5 = r15
            r15 = r22
            r6 = r23
            r2 = 1
            r8 = r25
            r11 = r28
            goto L_0x00d4
        L_0x031a:
            r1 = r21
        L_0x031c:
            if (r23 != 0) goto L_0x0332
            org.apache.http.client.UserTokenHandler r2 = r7.userTokenHandler     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x032f, IOException -> 0x032c, RuntimeException -> 0x032a, Error -> 0x00b7 }
            java.lang.Object r6 = r2.getUserToken(r10)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x032f, IOException -> 0x032c, RuntimeException -> 0x032a, Error -> 0x00b7 }
            java.lang.String r2 = "http.user-token"
            r10.setAttribute(r2, r6)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x032f, IOException -> 0x032c, RuntimeException -> 0x032a, Error -> 0x00b7 }
            goto L_0x0334
        L_0x032a:
            r0 = move-exception
            goto L_0x037c
        L_0x032c:
            r0 = move-exception
            goto L_0x0397
        L_0x032f:
            r0 = move-exception
            goto L_0x03b0
        L_0x0332:
            r6 = r23
        L_0x0334:
            if (r6 == 0) goto L_0x0339
            r11.setState(r6)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x032f, IOException -> 0x032c, RuntimeException -> 0x032a, Error -> 0x00b7 }
        L_0x0339:
            org.apache.http.HttpEntity r2 = r1.getEntity()     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x032f, IOException -> 0x032c, RuntimeException -> 0x032a, Error -> 0x00b7 }
            if (r2 == 0) goto L_0x034c
            boolean r2 = r2.isStreaming()     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x032f, IOException -> 0x032c, RuntimeException -> 0x032a, Error -> 0x00b7 }
            if (r2 != 0) goto L_0x0346
            goto L_0x034c
        L_0x0346:
            org.apache.http.impl.execchain.HttpResponseProxy r2 = new org.apache.http.impl.execchain.HttpResponseProxy     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x032f, IOException -> 0x032c, RuntimeException -> 0x032a, Error -> 0x00b7 }
            r2.<init>(r1, r11)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x032f, IOException -> 0x032c, RuntimeException -> 0x032a, Error -> 0x00b7 }
            return r2
        L_0x034c:
            r11.releaseConnection()     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x032f, IOException -> 0x032c, RuntimeException -> 0x032a, Error -> 0x00b7 }
            org.apache.http.impl.execchain.HttpResponseProxy r2 = new org.apache.http.impl.execchain.HttpResponseProxy     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x032f, IOException -> 0x032c, RuntimeException -> 0x032a, Error -> 0x00b7 }
            r3 = 0
            r2.<init>(r1, r3)     // Catch:{ ConnectionShutdownException -> 0x00ce, HttpException -> 0x032f, IOException -> 0x032c, RuntimeException -> 0x032a, Error -> 0x00b7 }
            return r2
        L_0x0356:
            r0 = move-exception
            r22 = r6
            goto L_0x037c
        L_0x035a:
            r0 = move-exception
            r22 = r6
            goto L_0x0397
        L_0x035e:
            r0 = move-exception
            r22 = r6
            goto L_0x0367
        L_0x0362:
            r0 = move-exception
            r22 = r6
            goto L_0x036b
        L_0x0366:
            r0 = move-exception
        L_0x0367:
            r11 = r21
            goto L_0x037c
        L_0x036a:
            r0 = move-exception
        L_0x036b:
            r11 = r21
            goto L_0x0397
        L_0x036e:
            r0 = move-exception
            r11 = r21
            goto L_0x03b0
        L_0x0372:
            org.apache.http.conn.HttpClientConnectionManager r2 = r7.connManager
            r2.shutdown()
            throw r1
        L_0x0378:
            r0 = move-exception
            r11 = r3
            r22 = r15
        L_0x037c:
            r1 = r0
        L_0x037d:
            r11.abortConnection()
            boolean r2 = r22.isConnectionBased()
            if (r2 == 0) goto L_0x0389
            r22.reset()
        L_0x0389:
            boolean r2 = r14.isConnectionBased()
            if (r2 == 0) goto L_0x0392
            r14.reset()
        L_0x0392:
            throw r1
        L_0x0393:
            r0 = move-exception
            r11 = r3
            r22 = r15
        L_0x0397:
            r1 = r0
        L_0x0398:
            r11.abortConnection()
            boolean r2 = r22.isConnectionBased()
            if (r2 == 0) goto L_0x03a4
            r22.reset()
        L_0x03a4:
            boolean r2 = r14.isConnectionBased()
            if (r2 == 0) goto L_0x03ad
            r14.reset()
        L_0x03ad:
            throw r1
        L_0x03ae:
            r0 = move-exception
            r11 = r3
        L_0x03b0:
            r1 = r0
        L_0x03b1:
            r11.abortConnection()
            throw r1
        L_0x03b5:
            java.io.InterruptedIOException r2 = new java.io.InterruptedIOException
            java.lang.String r3 = "Connection has been shut down"
            r2.<init>(r3)
            r2.initCause(r1)
            throw r2
        L_0x03c0:
            r0 = move-exception
            r1 = r0
            java.lang.Throwable r2 = r1.getCause()
            if (r2 != 0) goto L_0x03c9
            goto L_0x03ca
        L_0x03c9:
            r1 = r2
        L_0x03ca:
            org.apache.http.impl.execchain.RequestAbortedException r2 = new org.apache.http.impl.execchain.RequestAbortedException
            java.lang.String r3 = "Request execution failed"
            r2.<init>(r3, r1)
            throw r2
        L_0x03d2:
            r0 = move-exception
            r15 = r5
            r1 = r0
            java.lang.Thread r2 = java.lang.Thread.currentThread()
            r2.interrupt()
            org.apache.http.impl.execchain.RequestAbortedException r2 = new org.apache.http.impl.execchain.RequestAbortedException
            r2.<init>(r15, r1)
            goto L_0x03e3
        L_0x03e2:
            throw r2
        L_0x03e3:
            goto L_0x03e2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.execchain.MainClientExec.execute(org.apache.http.conn.routing.HttpRoute, org.apache.http.client.methods.HttpRequestWrapper, org.apache.http.client.protocol.HttpClientContext, org.apache.http.client.methods.HttpExecutionAware):org.apache.http.client.methods.CloseableHttpResponse");
    }

    /* access modifiers changed from: 0000 */
    public void establishRoute(AuthState authState, HttpClientConnection httpClientConnection, HttpRoute httpRoute, HttpRequest httpRequest, HttpClientContext httpClientContext) throws HttpException, IOException {
        int nextStep;
        int connectTimeout = httpClientContext.getRequestConfig().getConnectTimeout();
        RouteTracker routeTracker = new RouteTracker(httpRoute);
        do {
            HttpRoute route = routeTracker.toRoute();
            nextStep = this.routeDirector.nextStep(httpRoute, route);
            boolean z = true;
            int i = 0;
            switch (nextStep) {
                case -1:
                    StringBuilder sb = new StringBuilder();
                    sb.append("Unable to establish route: planned = ");
                    sb.append(httpRoute);
                    sb.append("; current = ");
                    sb.append(route);
                    throw new HttpException(sb.toString());
                case 0:
                    this.connManager.routeComplete(httpClientConnection, httpRoute, httpClientContext);
                    continue;
                case 1:
                    HttpClientConnectionManager httpClientConnectionManager = this.connManager;
                    if (connectTimeout > 0) {
                        i = connectTimeout;
                    }
                    httpClientConnectionManager.connect(httpClientConnection, httpRoute, i, httpClientContext);
                    routeTracker.connectTarget(httpRoute.isSecure());
                    continue;
                case 2:
                    this.connManager.connect(httpClientConnection, httpRoute, connectTimeout > 0 ? connectTimeout : 0, httpClientContext);
                    HttpHost proxyHost = httpRoute.getProxyHost();
                    if (!httpRoute.isSecure() || httpRoute.isTunnelled()) {
                        z = false;
                    }
                    routeTracker.connectProxy(proxyHost, z);
                    continue;
                case 3:
                    boolean createTunnelToTarget = createTunnelToTarget(authState, httpClientConnection, httpRoute, httpRequest, httpClientContext);
                    this.log.debug("Tunnel to target created.");
                    routeTracker.tunnelTarget(createTunnelToTarget);
                    continue;
                case 4:
                    int hopCount = route.getHopCount() - 1;
                    boolean createTunnelToProxy = createTunnelToProxy(httpRoute, hopCount, httpClientContext);
                    this.log.debug("Tunnel to proxy created.");
                    routeTracker.tunnelProxy(httpRoute.getHopTarget(hopCount), createTunnelToProxy);
                    continue;
                case 5:
                    this.connManager.upgrade(httpClientConnection, httpRoute, httpClientContext);
                    routeTracker.layerProtocol(httpRoute.isSecure());
                    continue;
                default:
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Unknown step indicator ");
                    sb2.append(nextStep);
                    sb2.append(" from RouteDirector.");
                    throw new IllegalStateException(sb2.toString());
            }
        } while (nextStep > 0);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0092, code lost:
        r3 = r20;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x009a, code lost:
        if (r0.reuseStrategy.keepAlive(r3, r8) == false) goto L_0x00ab;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x009c, code lost:
        r0.log.debug("Connection kept alive");
        org.apache.http.util.EntityUtils.consume(r3.getEntity());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x00ab, code lost:
        r18.close();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean createTunnelToTarget(org.apache.http.auth.AuthState r17, org.apache.http.HttpClientConnection r18, org.apache.http.conn.routing.HttpRoute r19, org.apache.http.HttpRequest r20, org.apache.http.client.protocol.HttpClientContext r21) throws org.apache.http.HttpException, java.io.IOException {
        /*
            r16 = this;
            r0 = r16
            r1 = r18
            r8 = r21
            org.apache.http.client.config.RequestConfig r9 = r21.getRequestConfig()
            int r10 = r9.getConnectTimeout()
            org.apache.http.HttpHost r2 = r19.getTargetHost()
            org.apache.http.HttpHost r11 = r19.getProxyHost()
            java.lang.String r2 = r2.toHostString()
            org.apache.http.message.BasicHttpRequest r12 = new org.apache.http.message.BasicHttpRequest
            org.apache.http.ProtocolVersion r3 = r20.getProtocolVersion()
            java.lang.String r4 = "CONNECT"
            r12.<init>(r4, r2, r3)
            org.apache.http.protocol.HttpRequestExecutor r2 = r0.requestExecutor
            org.apache.http.protocol.HttpProcessor r3 = r0.proxyHttpProcessor
            r2.preProcess(r12, r3, r8)
            r13 = 0
        L_0x002d:
            r2 = r13
        L_0x002e:
            r3 = 0
            if (r2 != 0) goto L_0x00d3
            boolean r2 = r18.isOpen()
            if (r2 != 0) goto L_0x0042
            org.apache.http.conn.HttpClientConnectionManager r2 = r0.connManager
            r14 = r19
            if (r10 <= 0) goto L_0x003e
            r3 = r10
        L_0x003e:
            r2.connect(r1, r14, r3, r8)
            goto L_0x0044
        L_0x0042:
            r14 = r19
        L_0x0044:
            java.lang.String r2 = "Proxy-Authorization"
            r12.removeHeaders(r2)
            org.apache.http.impl.auth.HttpAuthenticator r2 = r0.authenticator
            r15 = r17
            r2.generateAuthResponse(r12, r15, r8)
            org.apache.http.protocol.HttpRequestExecutor r2 = r0.requestExecutor
            org.apache.http.HttpResponse r7 = r2.execute(r12, r1, r8)
            org.apache.http.protocol.HttpRequestExecutor r2 = r0.requestExecutor
            org.apache.http.protocol.HttpProcessor r3 = r0.proxyHttpProcessor
            r2.postProcess(r7, r3, r8)
            org.apache.http.StatusLine r2 = r7.getStatusLine()
            int r2 = r2.getStatusCode()
            r3 = 200(0xc8, float:2.8E-43)
            if (r2 < r3) goto L_0x00b7
            boolean r2 = r9.isAuthenticationEnabled()
            if (r2 == 0) goto L_0x00b3
            org.apache.http.impl.auth.HttpAuthenticator r2 = r0.authenticator
            org.apache.http.client.AuthenticationStrategy r5 = r0.proxyAuthStrategy
            r3 = r11
            r4 = r7
            r6 = r17
            r20 = r7
            r7 = r21
            boolean r2 = r2.isAuthenticationRequested(r3, r4, r5, r6, r7)
            if (r2 == 0) goto L_0x00b0
            org.apache.http.impl.auth.HttpAuthenticator r2 = r0.authenticator
            org.apache.http.client.AuthenticationStrategy r5 = r0.proxyAuthStrategy
            r3 = r11
            r4 = r20
            r6 = r17
            r7 = r21
            boolean r2 = r2.handleAuthChallenge(r3, r4, r5, r6, r7)
            if (r2 == 0) goto L_0x00b0
            org.apache.http.ConnectionReuseStrategy r2 = r0.reuseStrategy
            r3 = r20
            boolean r2 = r2.keepAlive(r3, r8)
            if (r2 == 0) goto L_0x00ab
            org.apache.commons.logging.Log r2 = r0.log
            java.lang.String r4 = "Connection kept alive"
            r2.debug(r4)
            org.apache.http.HttpEntity r2 = r3.getEntity()
            org.apache.http.util.EntityUtils.consume(r2)
            goto L_0x002d
        L_0x00ab:
            r18.close()
            goto L_0x002d
        L_0x00b0:
            r3 = r20
            goto L_0x00b4
        L_0x00b3:
            r3 = r7
        L_0x00b4:
            r2 = r3
            goto L_0x002e
        L_0x00b7:
            r3 = r7
            org.apache.http.HttpException r1 = new org.apache.http.HttpException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r4 = "Unexpected response to CONNECT request: "
            r2.append(r4)
            org.apache.http.StatusLine r3 = r3.getStatusLine()
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x00d3:
            org.apache.http.StatusLine r4 = r2.getStatusLine()
            int r4 = r4.getStatusCode()
            r5 = 299(0x12b, float:4.19E-43)
            if (r4 <= r5) goto L_0x010b
            org.apache.http.HttpEntity r3 = r2.getEntity()
            if (r3 == 0) goto L_0x00ed
            org.apache.http.entity.BufferedHttpEntity r4 = new org.apache.http.entity.BufferedHttpEntity
            r4.<init>(r3)
            r2.setEntity(r4)
        L_0x00ed:
            r18.close()
            org.apache.http.impl.execchain.TunnelRefusedException r1 = new org.apache.http.impl.execchain.TunnelRefusedException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "CONNECT refused by proxy: "
            r3.append(r4)
            org.apache.http.StatusLine r4 = r2.getStatusLine()
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r1.<init>(r3, r2)
            throw r1
        L_0x010b:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.execchain.MainClientExec.createTunnelToTarget(org.apache.http.auth.AuthState, org.apache.http.HttpClientConnection, org.apache.http.conn.routing.HttpRoute, org.apache.http.HttpRequest, org.apache.http.client.protocol.HttpClientContext):boolean");
    }

    private boolean createTunnelToProxy(HttpRoute httpRoute, int i, HttpClientContext httpClientContext) throws HttpException {
        throw new HttpException("Proxy chains are not supported.");
    }

    private boolean needAuthentication(AuthState authState, AuthState authState2, HttpRoute httpRoute, HttpResponse httpResponse, HttpClientContext httpClientContext) {
        if (httpClientContext.getRequestConfig().isAuthenticationEnabled()) {
            HttpHost targetHost = httpClientContext.getTargetHost();
            if (targetHost == null) {
                targetHost = httpRoute.getTargetHost();
            }
            if (targetHost.getPort() < 0) {
                targetHost = new HttpHost(targetHost.getHostName(), httpRoute.getTargetHost().getPort(), targetHost.getSchemeName());
            }
            boolean isAuthenticationRequested = this.authenticator.isAuthenticationRequested(targetHost, httpResponse, this.targetAuthStrategy, authState, httpClientContext);
            HttpHost proxyHost = httpRoute.getProxyHost();
            if (proxyHost == null) {
                proxyHost = httpRoute.getTargetHost();
            }
            boolean isAuthenticationRequested2 = this.authenticator.isAuthenticationRequested(proxyHost, httpResponse, this.proxyAuthStrategy, authState2, httpClientContext);
            if (isAuthenticationRequested) {
                return this.authenticator.handleAuthChallenge(targetHost, httpResponse, this.targetAuthStrategy, authState, httpClientContext);
            } else if (isAuthenticationRequested2) {
                return this.authenticator.handleAuthChallenge(proxyHost, httpResponse, this.proxyAuthStrategy, authState2, httpClientContext);
            }
        }
        return false;
    }
}
