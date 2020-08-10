package org.apache.http.impl.execchain;

import java.net.URI;
import java.net.URISyntaxException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HttpHost;
import org.apache.http.ProtocolException;
import org.apache.http.client.methods.HttpRequestWrapper;
import org.apache.http.client.protocol.RequestClientConnControl;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.protocol.ImmutableHttpProcessor;
import org.apache.http.protocol.RequestContent;
import org.apache.http.protocol.RequestTargetHost;
import org.apache.http.protocol.RequestUserAgent;
import org.apache.http.util.Args;
import org.apache.http.util.VersionInfo;

public class MinimalClientExec implements ClientExecChain {
    private final HttpClientConnectionManager connManager;
    private final HttpProcessor httpProcessor;
    private final ConnectionKeepAliveStrategy keepAliveStrategy;
    private final Log log = LogFactory.getLog(getClass());
    private final HttpRequestExecutor requestExecutor;
    private final ConnectionReuseStrategy reuseStrategy;

    public MinimalClientExec(HttpRequestExecutor httpRequestExecutor, HttpClientConnectionManager httpClientConnectionManager, ConnectionReuseStrategy connectionReuseStrategy, ConnectionKeepAliveStrategy connectionKeepAliveStrategy) {
        Args.notNull(httpRequestExecutor, "HTTP request executor");
        Args.notNull(httpClientConnectionManager, "Client connection manager");
        Args.notNull(connectionReuseStrategy, "Connection reuse strategy");
        Args.notNull(connectionKeepAliveStrategy, "Connection keep alive strategy");
        this.httpProcessor = new ImmutableHttpProcessor(new RequestContent(), new RequestTargetHost(), new RequestClientConnControl(), new RequestUserAgent(VersionInfo.getUserAgent("Apache-HttpClient", "org.apache.http.client", getClass())));
        this.requestExecutor = httpRequestExecutor;
        this.connManager = httpClientConnectionManager;
        this.reuseStrategy = connectionReuseStrategy;
        this.keepAliveStrategy = connectionKeepAliveStrategy;
    }

    static void rewriteRequestURI(HttpRequestWrapper httpRequestWrapper, HttpRoute httpRoute, boolean z) throws ProtocolException {
        URI uri;
        try {
            URI uri2 = httpRequestWrapper.getURI();
            if (uri2 != null) {
                if (uri2.isAbsolute()) {
                    uri = URIUtils.rewriteURI(uri2, (HttpHost) null, z ? URIUtils.DROP_FRAGMENT_AND_NORMALIZE : URIUtils.DROP_FRAGMENT);
                } else {
                    uri = URIUtils.rewriteURI(uri2);
                }
                httpRequestWrapper.setURI(uri);
            }
        } catch (URISyntaxException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid URI: ");
            sb.append(httpRequestWrapper.getRequestLine().getUri());
            throw new ProtocolException(sb.toString(), e);
        }
    }

    /* JADX WARNING: type inference failed for: r9v3, types: [java.lang.Throwable] */
    /* JADX WARNING: type inference failed for: r8v3, types: [java.lang.Throwable] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00b4 A[Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00e4 A[Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00f3 A[Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00fc A[Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.apache.http.client.methods.CloseableHttpResponse execute(org.apache.http.conn.routing.HttpRoute r8, org.apache.http.client.methods.HttpRequestWrapper r9, org.apache.http.client.protocol.HttpClientContext r10, org.apache.http.client.methods.HttpExecutionAware r11) throws java.io.IOException, org.apache.http.HttpException {
        /*
            r7 = this;
            java.lang.String r0 = "HTTP route"
            org.apache.http.util.Args.notNull(r8, r0)
            java.lang.String r0 = "HTTP request"
            org.apache.http.util.Args.notNull(r9, r0)
            java.lang.String r0 = "HTTP context"
            org.apache.http.util.Args.notNull(r10, r0)
            org.apache.http.client.config.RequestConfig r0 = r10.getRequestConfig()
            boolean r0 = r0.isNormalizeUri()
            rewriteRequestURI(r9, r8, r0)
            org.apache.http.conn.HttpClientConnectionManager r0 = r7.connManager
            r1 = 0
            org.apache.http.conn.ConnectionRequest r0 = r0.requestConnection(r8, r1)
            java.lang.String r2 = "Request aborted"
            if (r11 == 0) goto L_0x0038
            boolean r3 = r11.isAborted()
            if (r3 != 0) goto L_0x002f
            r11.setCancellable(r0)
            goto L_0x0038
        L_0x002f:
            r0.cancel()
            org.apache.http.impl.execchain.RequestAbortedException r8 = new org.apache.http.impl.execchain.RequestAbortedException
            r8.<init>(r2)
            throw r8
        L_0x0038:
            org.apache.http.client.config.RequestConfig r3 = r10.getRequestConfig()
            int r4 = r3.getConnectionRequestTimeout()     // Catch:{ InterruptedException -> 0x0145, ExecutionException -> 0x0134 }
            if (r4 <= 0) goto L_0x0044
            long r4 = (long) r4     // Catch:{ InterruptedException -> 0x0145, ExecutionException -> 0x0134 }
            goto L_0x0046
        L_0x0044:
            r4 = 0
        L_0x0046:
            java.util.concurrent.TimeUnit r6 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ InterruptedException -> 0x0145, ExecutionException -> 0x0134 }
            org.apache.http.HttpClientConnection r0 = r0.get(r4, r6)     // Catch:{ InterruptedException -> 0x0145, ExecutionException -> 0x0134 }
            org.apache.http.impl.execchain.ConnectionHolder r4 = new org.apache.http.impl.execchain.ConnectionHolder
            org.apache.commons.logging.Log r5 = r7.log
            org.apache.http.conn.HttpClientConnectionManager r6 = r7.connManager
            r4.<init>(r5, r6, r0)
            if (r11 == 0) goto L_0x006a
            boolean r5 = r11.isAborted()     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
            if (r5 != 0) goto L_0x0061
            r11.setCancellable(r4)     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
            goto L_0x006a
        L_0x0061:
            r4.close()     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
            org.apache.http.impl.execchain.RequestAbortedException r8 = new org.apache.http.impl.execchain.RequestAbortedException     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
            r8.<init>(r2)     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
            throw r8     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
        L_0x006a:
            boolean r11 = r0.isOpen()     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
            if (r11 != 0) goto L_0x0082
            int r11 = r3.getConnectTimeout()     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
            org.apache.http.conn.HttpClientConnectionManager r2 = r7.connManager     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
            if (r11 <= 0) goto L_0x0079
            goto L_0x007a
        L_0x0079:
            r11 = 0
        L_0x007a:
            r2.connect(r0, r8, r11, r10)     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
            org.apache.http.conn.HttpClientConnectionManager r11 = r7.connManager     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
            r11.routeComplete(r0, r8, r10)     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
        L_0x0082:
            int r11 = r3.getSocketTimeout()     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
            if (r11 < 0) goto L_0x008b
            r0.setSocketTimeout(r11)     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
        L_0x008b:
            org.apache.http.HttpRequest r11 = r9.getOriginal()     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
            boolean r2 = r11 instanceof org.apache.http.client.methods.HttpUriRequest     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
            if (r2 == 0) goto L_0x00b1
            org.apache.http.client.methods.HttpUriRequest r11 = (org.apache.http.client.methods.HttpUriRequest) r11     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
            java.net.URI r11 = r11.getURI()     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
            boolean r2 = r11.isAbsolute()     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
            if (r2 == 0) goto L_0x00b1
            org.apache.http.HttpHost r2 = new org.apache.http.HttpHost     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
            java.lang.String r3 = r11.getHost()     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
            int r5 = r11.getPort()     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
            java.lang.String r11 = r11.getScheme()     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
            r2.<init>(r3, r5, r11)     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
            goto L_0x00b2
        L_0x00b1:
            r2 = r1
        L_0x00b2:
            if (r2 != 0) goto L_0x00b8
            org.apache.http.HttpHost r2 = r8.getTargetHost()     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
        L_0x00b8:
            java.lang.String r11 = "http.target_host"
            r10.setAttribute(r11, r2)     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
            java.lang.String r11 = "http.request"
            r10.setAttribute(r11, r9)     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
            java.lang.String r11 = "http.connection"
            r10.setAttribute(r11, r0)     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
            java.lang.String r11 = "http.route"
            r10.setAttribute(r11, r8)     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
            org.apache.http.protocol.HttpProcessor r8 = r7.httpProcessor     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
            r8.process(r9, r10)     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
            org.apache.http.protocol.HttpRequestExecutor r8 = r7.requestExecutor     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
            org.apache.http.HttpResponse r8 = r8.execute(r9, r0, r10)     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
            org.apache.http.protocol.HttpProcessor r9 = r7.httpProcessor     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
            r9.process(r8, r10)     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
            org.apache.http.ConnectionReuseStrategy r9 = r7.reuseStrategy     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
            boolean r9 = r9.keepAlive(r8, r10)     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
            if (r9 == 0) goto L_0x00f3
            org.apache.http.conn.ConnectionKeepAliveStrategy r9 = r7.keepAliveStrategy     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
            long r9 = r9.getKeepAliveDuration(r8, r10)     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
            java.util.concurrent.TimeUnit r11 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
            r4.setValidFor(r9, r11)     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
            r4.markReusable()     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
            goto L_0x00f6
        L_0x00f3:
            r4.markNonReusable()     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
        L_0x00f6:
            org.apache.http.HttpEntity r9 = r8.getEntity()     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
            if (r9 == 0) goto L_0x0109
            boolean r9 = r9.isStreaming()     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
            if (r9 != 0) goto L_0x0103
            goto L_0x0109
        L_0x0103:
            org.apache.http.impl.execchain.HttpResponseProxy r9 = new org.apache.http.impl.execchain.HttpResponseProxy     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
            r9.<init>(r8, r4)     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
            return r9
        L_0x0109:
            r4.releaseConnection()     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
            org.apache.http.impl.execchain.HttpResponseProxy r9 = new org.apache.http.impl.execchain.HttpResponseProxy     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
            r9.<init>(r8, r1)     // Catch:{ ConnectionShutdownException -> 0x0128, HttpException -> 0x0123, IOException -> 0x011e, RuntimeException -> 0x0119, Error -> 0x0112 }
            return r9
        L_0x0112:
            r8 = move-exception
            org.apache.http.conn.HttpClientConnectionManager r9 = r7.connManager
            r9.shutdown()
            throw r8
        L_0x0119:
            r8 = move-exception
            r4.abortConnection()
            throw r8
        L_0x011e:
            r8 = move-exception
            r4.abortConnection()
            throw r8
        L_0x0123:
            r8 = move-exception
            r4.abortConnection()
            throw r8
        L_0x0128:
            r8 = move-exception
            java.io.InterruptedIOException r9 = new java.io.InterruptedIOException
            java.lang.String r10 = "Connection has been shut down"
            r9.<init>(r10)
            r9.initCause(r8)
            throw r9
        L_0x0134:
            r8 = move-exception
            java.lang.Throwable r9 = r8.getCause()
            if (r9 != 0) goto L_0x013c
            goto L_0x013d
        L_0x013c:
            r8 = r9
        L_0x013d:
            org.apache.http.impl.execchain.RequestAbortedException r9 = new org.apache.http.impl.execchain.RequestAbortedException
            java.lang.String r10 = "Request execution failed"
            r9.<init>(r10, r8)
            throw r9
        L_0x0145:
            r8 = move-exception
            java.lang.Thread r9 = java.lang.Thread.currentThread()
            r9.interrupt()
            org.apache.http.impl.execchain.RequestAbortedException r9 = new org.apache.http.impl.execchain.RequestAbortedException
            r9.<init>(r2, r8)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.execchain.MinimalClientExec.execute(org.apache.http.conn.routing.HttpRoute, org.apache.http.client.methods.HttpRequestWrapper, org.apache.http.client.protocol.HttpClientContext, org.apache.http.client.methods.HttpExecutionAware):org.apache.http.client.methods.CloseableHttpResponse");
    }
}
