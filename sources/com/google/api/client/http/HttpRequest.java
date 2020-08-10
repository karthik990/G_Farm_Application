package com.google.api.client.http;

import com.google.api.client.util.ObjectParser;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Sleeper;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import p043io.opencensus.trace.AttributeValue;
import p043io.opencensus.trace.Span;
import p043io.opencensus.trace.Tracer;

public final class HttpRequest {
    public static final int DEFAULT_NUMBER_OF_RETRIES = 10;
    public static final String USER_AGENT_SUFFIX = "Google-HTTP-Java-Client/1.30.0 (gzip)";
    public static final String VERSION = "1.30.0";
    @Deprecated
    private BackOffPolicy backOffPolicy;
    private int connectTimeout = 20000;
    private HttpContent content;
    private int contentLoggingLimit = 16384;
    private boolean curlLoggingEnabled = true;
    private HttpEncoding encoding;
    private HttpExecuteInterceptor executeInterceptor;
    private boolean followRedirects = true;
    private HttpHeaders headers = new HttpHeaders();
    private HttpIOExceptionHandler ioExceptionHandler;
    private boolean loggingEnabled = true;
    private int numRetries = 10;
    private ObjectParser objectParser;
    private int readTimeout = 20000;
    private String requestMethod;
    private HttpHeaders responseHeaders = new HttpHeaders();
    private HttpResponseInterceptor responseInterceptor;
    private boolean responseReturnRawInputStream = false;
    @Deprecated
    private boolean retryOnExecuteIOException = false;
    private Sleeper sleeper = Sleeper.DEFAULT;
    private boolean suppressUserAgentSuffix;
    private boolean throwExceptionOnExecuteError = true;
    private final Tracer tracer = OpenCensusUtils.getTracer();
    private final HttpTransport transport;
    private HttpUnsuccessfulResponseHandler unsuccessfulResponseHandler;
    private GenericUrl url;
    private int writeTimeout = 0;

    HttpRequest(HttpTransport httpTransport, String str) {
        this.transport = httpTransport;
        setRequestMethod(str);
    }

    public HttpTransport getTransport() {
        return this.transport;
    }

    public String getRequestMethod() {
        return this.requestMethod;
    }

    public HttpRequest setRequestMethod(String str) {
        Preconditions.checkArgument(str == null || HttpMediaType.matchesToken(str));
        this.requestMethod = str;
        return this;
    }

    public GenericUrl getUrl() {
        return this.url;
    }

    public HttpRequest setUrl(GenericUrl genericUrl) {
        this.url = (GenericUrl) Preconditions.checkNotNull(genericUrl);
        return this;
    }

    public HttpContent getContent() {
        return this.content;
    }

    public HttpRequest setContent(HttpContent httpContent) {
        this.content = httpContent;
        return this;
    }

    public HttpEncoding getEncoding() {
        return this.encoding;
    }

    public HttpRequest setEncoding(HttpEncoding httpEncoding) {
        this.encoding = httpEncoding;
        return this;
    }

    @Deprecated
    public BackOffPolicy getBackOffPolicy() {
        return this.backOffPolicy;
    }

    @Deprecated
    public HttpRequest setBackOffPolicy(BackOffPolicy backOffPolicy2) {
        this.backOffPolicy = backOffPolicy2;
        return this;
    }

    public int getContentLoggingLimit() {
        return this.contentLoggingLimit;
    }

    public HttpRequest setContentLoggingLimit(int i) {
        Preconditions.checkArgument(i >= 0, "The content logging limit must be non-negative.");
        this.contentLoggingLimit = i;
        return this;
    }

    public boolean isLoggingEnabled() {
        return this.loggingEnabled;
    }

    public HttpRequest setLoggingEnabled(boolean z) {
        this.loggingEnabled = z;
        return this;
    }

    public boolean isCurlLoggingEnabled() {
        return this.curlLoggingEnabled;
    }

    public HttpRequest setCurlLoggingEnabled(boolean z) {
        this.curlLoggingEnabled = z;
        return this;
    }

    public int getConnectTimeout() {
        return this.connectTimeout;
    }

    public HttpRequest setConnectTimeout(int i) {
        Preconditions.checkArgument(i >= 0);
        this.connectTimeout = i;
        return this;
    }

    public int getReadTimeout() {
        return this.readTimeout;
    }

    public HttpRequest setReadTimeout(int i) {
        Preconditions.checkArgument(i >= 0);
        this.readTimeout = i;
        return this;
    }

    public int getWriteTimeout() {
        return this.writeTimeout;
    }

    public HttpRequest setWriteTimeout(int i) {
        Preconditions.checkArgument(i >= 0);
        this.writeTimeout = i;
        return this;
    }

    public HttpHeaders getHeaders() {
        return this.headers;
    }

    public HttpRequest setHeaders(HttpHeaders httpHeaders) {
        this.headers = (HttpHeaders) Preconditions.checkNotNull(httpHeaders);
        return this;
    }

    public HttpHeaders getResponseHeaders() {
        return this.responseHeaders;
    }

    public HttpRequest setResponseHeaders(HttpHeaders httpHeaders) {
        this.responseHeaders = (HttpHeaders) Preconditions.checkNotNull(httpHeaders);
        return this;
    }

    public HttpExecuteInterceptor getInterceptor() {
        return this.executeInterceptor;
    }

    public HttpRequest setInterceptor(HttpExecuteInterceptor httpExecuteInterceptor) {
        this.executeInterceptor = httpExecuteInterceptor;
        return this;
    }

    public HttpUnsuccessfulResponseHandler getUnsuccessfulResponseHandler() {
        return this.unsuccessfulResponseHandler;
    }

    public HttpRequest setUnsuccessfulResponseHandler(HttpUnsuccessfulResponseHandler httpUnsuccessfulResponseHandler) {
        this.unsuccessfulResponseHandler = httpUnsuccessfulResponseHandler;
        return this;
    }

    public HttpIOExceptionHandler getIOExceptionHandler() {
        return this.ioExceptionHandler;
    }

    public HttpRequest setIOExceptionHandler(HttpIOExceptionHandler httpIOExceptionHandler) {
        this.ioExceptionHandler = httpIOExceptionHandler;
        return this;
    }

    public HttpResponseInterceptor getResponseInterceptor() {
        return this.responseInterceptor;
    }

    public HttpRequest setResponseInterceptor(HttpResponseInterceptor httpResponseInterceptor) {
        this.responseInterceptor = httpResponseInterceptor;
        return this;
    }

    public int getNumberOfRetries() {
        return this.numRetries;
    }

    public HttpRequest setNumberOfRetries(int i) {
        Preconditions.checkArgument(i >= 0);
        this.numRetries = i;
        return this;
    }

    public HttpRequest setParser(ObjectParser objectParser2) {
        this.objectParser = objectParser2;
        return this;
    }

    public final ObjectParser getParser() {
        return this.objectParser;
    }

    public boolean getFollowRedirects() {
        return this.followRedirects;
    }

    public HttpRequest setFollowRedirects(boolean z) {
        this.followRedirects = z;
        return this;
    }

    public boolean getThrowExceptionOnExecuteError() {
        return this.throwExceptionOnExecuteError;
    }

    public HttpRequest setThrowExceptionOnExecuteError(boolean z) {
        this.throwExceptionOnExecuteError = z;
        return this;
    }

    @Deprecated
    public boolean getRetryOnExecuteIOException() {
        return this.retryOnExecuteIOException;
    }

    @Deprecated
    public HttpRequest setRetryOnExecuteIOException(boolean z) {
        this.retryOnExecuteIOException = z;
        return this;
    }

    public boolean getSuppressUserAgentSuffix() {
        return this.suppressUserAgentSuffix;
    }

    public HttpRequest setSuppressUserAgentSuffix(boolean z) {
        this.suppressUserAgentSuffix = z;
        return this;
    }

    public boolean getResponseReturnRawInputStream() {
        return this.responseReturnRawInputStream;
    }

    public HttpRequest setResponseReturnRawInputStream(boolean z) {
        this.responseReturnRawInputStream = z;
        return this;
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Removed duplicated region for block: B:117:0x02b1 A[SYNTHETIC, Splitter:B:117:0x02b1] */
    /* JADX WARNING: Removed duplicated region for block: B:148:0x0309  */
    /* JADX WARNING: Removed duplicated region for block: B:149:0x030b  */
    /* JADX WARNING: Removed duplicated region for block: B:173:0x0345 A[LOOP:0: B:8:0x0035->B:173:0x0345, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:176:0x0311 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00e8  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0121  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0138  */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x0216  */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x021c  */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x026e A[Catch:{ all -> 0x0280, IOException -> 0x028e }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.api.client.http.HttpResponse execute() throws java.io.IOException {
        /*
            r20 = this;
            r1 = r20
            int r0 = r1.numRetries
            if (r0 < 0) goto L_0x0008
            r0 = 1
            goto L_0x0009
        L_0x0008:
            r0 = 0
        L_0x0009:
            com.google.api.client.util.Preconditions.checkArgument(r0)
            int r0 = r1.numRetries
            com.google.api.client.http.BackOffPolicy r4 = r1.backOffPolicy
            if (r4 == 0) goto L_0x0015
            r4.reset()
        L_0x0015:
            java.lang.String r4 = r1.requestMethod
            com.google.api.client.util.Preconditions.checkNotNull(r4)
            com.google.api.client.http.GenericUrl r4 = r1.url
            com.google.api.client.util.Preconditions.checkNotNull(r4)
            io.opencensus.trace.Tracer r4 = r1.tracer
            java.lang.String r5 = com.google.api.client.http.OpenCensusUtils.SPAN_NAME_HTTP_REQUEST_EXECUTE
            io.opencensus.trace.SpanBuilder r4 = r4.spanBuilder(r5)
            boolean r5 = com.google.api.client.http.OpenCensusUtils.isRecordEvent()
            io.opencensus.trace.SpanBuilder r4 = r4.setRecordEvents(r5)
            io.opencensus.trace.Span r4 = r4.startSpan()
            r6 = r0
            r0 = 0
        L_0x0035:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "retry #"
            r7.append(r8)
            int r8 = r1.numRetries
            int r8 = r8 - r6
            r7.append(r8)
            java.lang.String r7 = r7.toString()
            r4.addAnnotation(r7)
            if (r0 == 0) goto L_0x0051
            r0.ignore()
        L_0x0051:
            com.google.api.client.http.HttpExecuteInterceptor r0 = r1.executeInterceptor
            if (r0 == 0) goto L_0x0058
            r0.intercept(r1)
        L_0x0058:
            com.google.api.client.http.GenericUrl r0 = r1.url
            java.lang.String r0 = r0.build()
            java.lang.String r7 = r1.requestMethod
            java.lang.String r8 = "http.method"
            addSpanAttribute(r4, r8, r7)
            com.google.api.client.http.GenericUrl r7 = r1.url
            java.lang.String r7 = r7.getHost()
            java.lang.String r8 = "http.host"
            addSpanAttribute(r4, r8, r7)
            com.google.api.client.http.GenericUrl r7 = r1.url
            java.lang.String r7 = r7.getRawPath()
            java.lang.String r8 = "http.path"
            addSpanAttribute(r4, r8, r7)
            java.lang.String r7 = "http.url"
            addSpanAttribute(r4, r7, r0)
            com.google.api.client.http.HttpTransport r7 = r1.transport
            java.lang.String r8 = r1.requestMethod
            com.google.api.client.http.LowLevelHttpRequest r7 = r7.buildRequest(r8, r0)
            java.util.logging.Logger r8 = com.google.api.client.http.HttpTransport.LOGGER
            boolean r9 = r1.loggingEnabled
            if (r9 == 0) goto L_0x0098
            java.util.logging.Level r9 = java.util.logging.Level.CONFIG
            boolean r9 = r8.isLoggable(r9)
            if (r9 == 0) goto L_0x0098
            r9 = 1
            goto L_0x0099
        L_0x0098:
            r9 = 0
        L_0x0099:
            if (r9 == 0) goto L_0x00dc
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "-------------- REQUEST  --------------"
            r10.append(r11)
            java.lang.String r11 = com.google.api.client.util.StringUtils.LINE_SEPARATOR
            r10.append(r11)
            java.lang.String r11 = r1.requestMethod
            r10.append(r11)
            r11 = 32
            r10.append(r11)
            r10.append(r0)
            java.lang.String r11 = com.google.api.client.util.StringUtils.LINE_SEPARATOR
            r10.append(r11)
            boolean r11 = r1.curlLoggingEnabled
            if (r11 == 0) goto L_0x00dd
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            java.lang.String r12 = "curl -v --compressed"
            r11.<init>(r12)
            java.lang.String r12 = r1.requestMethod
            java.lang.String r13 = "GET"
            boolean r12 = r12.equals(r13)
            if (r12 != 0) goto L_0x00de
            java.lang.String r12 = " -X "
            r11.append(r12)
            java.lang.String r12 = r1.requestMethod
            r11.append(r12)
            goto L_0x00de
        L_0x00dc:
            r10 = 0
        L_0x00dd:
            r11 = 0
        L_0x00de:
            com.google.api.client.http.HttpHeaders r12 = r1.headers
            java.lang.String r12 = r12.getUserAgent()
            boolean r13 = r1.suppressUserAgentSuffix
            if (r13 != 0) goto L_0x0113
            java.lang.String r13 = "http.user_agent"
            java.lang.String r14 = "Google-HTTP-Java-Client/1.30.0 (gzip)"
            if (r12 != 0) goto L_0x00f7
            com.google.api.client.http.HttpHeaders r15 = r1.headers
            r15.setUserAgent(r14)
            addSpanAttribute(r4, r13, r14)
            goto L_0x0113
        L_0x00f7:
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r12)
            java.lang.String r2 = " "
            r15.append(r2)
            r15.append(r14)
            java.lang.String r2 = r15.toString()
            com.google.api.client.http.HttpHeaders r14 = r1.headers
            r14.setUserAgent(r2)
            addSpanAttribute(r4, r13, r2)
        L_0x0113:
            com.google.api.client.http.HttpHeaders r2 = r1.headers
            com.google.api.client.http.OpenCensusUtils.propagateTracingContext(r4, r2)
            com.google.api.client.http.HttpHeaders r2 = r1.headers
            com.google.api.client.http.HttpHeaders.serializeHeaders(r2, r10, r11, r8, r7)
            boolean r2 = r1.suppressUserAgentSuffix
            if (r2 != 0) goto L_0x0126
            com.google.api.client.http.HttpHeaders r2 = r1.headers
            r2.setUserAgent(r12)
        L_0x0126:
            com.google.api.client.http.HttpContent r2 = r1.content
            if (r2 == 0) goto L_0x0133
            boolean r12 = r2.retrySupported()
            if (r12 == 0) goto L_0x0131
            goto L_0x0133
        L_0x0131:
            r12 = 0
            goto L_0x0134
        L_0x0133:
            r12 = 1
        L_0x0134:
            java.lang.String r15 = "'"
            if (r2 == 0) goto L_0x0216
            com.google.api.client.http.HttpContent r3 = r1.content
            java.lang.String r3 = r3.getType()
            if (r9 == 0) goto L_0x014f
            com.google.api.client.util.LoggingStreamingContent r5 = new com.google.api.client.util.LoggingStreamingContent
            java.util.logging.Logger r13 = com.google.api.client.http.HttpTransport.LOGGER
            java.util.logging.Level r14 = java.util.logging.Level.CONFIG
            r16 = r4
            int r4 = r1.contentLoggingLimit
            r5.<init>(r2, r13, r14, r4)
            r2 = r5
            goto L_0x0151
        L_0x014f:
            r16 = r4
        L_0x0151:
            com.google.api.client.http.HttpEncoding r4 = r1.encoding
            if (r4 != 0) goto L_0x015e
            com.google.api.client.http.HttpContent r4 = r1.content
            long r4 = r4.getLength()
            r13 = r4
            r5 = 0
            goto L_0x0173
        L_0x015e:
            java.lang.String r5 = r4.getName()
            com.google.api.client.http.HttpEncodingStreamingContent r4 = new com.google.api.client.http.HttpEncodingStreamingContent
            com.google.api.client.http.HttpEncoding r13 = r1.encoding
            r4.<init>(r2, r13)
            if (r12 == 0) goto L_0x0170
            long r13 = com.google.api.client.util.IOUtils.computeLength(r4)
            goto L_0x0172
        L_0x0170:
            r13 = -1
        L_0x0172:
            r2 = r4
        L_0x0173:
            if (r9 == 0) goto L_0x0200
            java.lang.String r4 = " -H '"
            if (r3 == 0) goto L_0x01ac
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r17 = r6
            java.lang.String r6 = "Content-Type: "
            r1.append(r6)
            r1.append(r3)
            java.lang.String r1 = r1.toString()
            r10.append(r1)
            java.lang.String r6 = com.google.api.client.util.StringUtils.LINE_SEPARATOR
            r10.append(r6)
            if (r11 == 0) goto L_0x01ae
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            r6.append(r4)
            r6.append(r1)
            r6.append(r15)
            java.lang.String r1 = r6.toString()
            r11.append(r1)
            goto L_0x01ae
        L_0x01ac:
            r17 = r6
        L_0x01ae:
            if (r5 == 0) goto L_0x01e0
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r6 = "Content-Encoding: "
            r1.append(r6)
            r1.append(r5)
            java.lang.String r1 = r1.toString()
            r10.append(r1)
            java.lang.String r6 = com.google.api.client.util.StringUtils.LINE_SEPARATOR
            r10.append(r6)
            if (r11 == 0) goto L_0x01e0
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            r6.append(r4)
            r6.append(r1)
            r6.append(r15)
            java.lang.String r1 = r6.toString()
            r11.append(r1)
        L_0x01e0:
            r18 = 0
            int r1 = (r13 > r18 ? 1 : (r13 == r18 ? 0 : -1))
            if (r1 < 0) goto L_0x0202
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r4 = "Content-Length: "
            r1.append(r4)
            r1.append(r13)
            java.lang.String r1 = r1.toString()
            r10.append(r1)
            java.lang.String r1 = com.google.api.client.util.StringUtils.LINE_SEPARATOR
            r10.append(r1)
            goto L_0x0202
        L_0x0200:
            r17 = r6
        L_0x0202:
            if (r11 == 0) goto L_0x0209
            java.lang.String r1 = " -d '@-'"
            r11.append(r1)
        L_0x0209:
            r7.setContentType(r3)
            r7.setContentEncoding(r5)
            r7.setContentLength(r13)
            r7.setStreamingContent(r2)
            goto L_0x021a
        L_0x0216:
            r16 = r4
            r17 = r6
        L_0x021a:
            if (r9 == 0) goto L_0x0244
            java.lang.String r1 = r10.toString()
            r8.config(r1)
            if (r11 == 0) goto L_0x0244
            java.lang.String r1 = " -- '"
            r11.append(r1)
            java.lang.String r1 = "'\"'\"'"
            java.lang.String r0 = r0.replaceAll(r15, r1)
            r11.append(r0)
            r11.append(r15)
            if (r2 == 0) goto L_0x023d
            java.lang.String r0 = " << $$$"
            r11.append(r0)
        L_0x023d:
            java.lang.String r0 = r11.toString()
            r8.config(r0)
        L_0x0244:
            if (r12 == 0) goto L_0x024a
            if (r17 <= 0) goto L_0x024a
            r2 = 1
            goto L_0x024b
        L_0x024a:
            r2 = 0
        L_0x024b:
            r1 = r20
            int r0 = r1.connectTimeout
            int r3 = r1.readTimeout
            r7.setTimeout(r0, r3)
            int r0 = r1.writeTimeout
            r7.setWriteTimeout(r0)
            io.opencensus.trace.Tracer r0 = r1.tracer
            r3 = r16
            io.opencensus.common.Scope r4 = r0.withSpan(r3)
            long r5 = r7.getContentLength()
            com.google.api.client.http.OpenCensusUtils.recordSentMessageEvent(r3, r5)
            com.google.api.client.http.LowLevelHttpResponse r5 = r7.execute()     // Catch:{ IOException -> 0x028e }
            if (r5 == 0) goto L_0x0275
            long r6 = r5.getContentLength()     // Catch:{ IOException -> 0x028e }
            com.google.api.client.http.OpenCensusUtils.recordReceivedMessageEvent(r3, r6)     // Catch:{ IOException -> 0x028e }
        L_0x0275:
            com.google.api.client.http.HttpResponse r0 = new com.google.api.client.http.HttpResponse     // Catch:{ all -> 0x0280 }
            r0.<init>(r1, r5)     // Catch:{ all -> 0x0280 }
            r4.close()
            r4 = r0
            r5 = 0
            goto L_0x02af
        L_0x0280:
            r0 = move-exception
            java.io.InputStream r5 = r5.getContent()     // Catch:{ IOException -> 0x028e }
            if (r5 == 0) goto L_0x028a
            r5.close()     // Catch:{ IOException -> 0x028e }
        L_0x028a:
            throw r0     // Catch:{ IOException -> 0x028e }
        L_0x028b:
            r0 = move-exception
            goto L_0x0349
        L_0x028e:
            r0 = move-exception
            r5 = r0
            boolean r0 = r1.retryOnExecuteIOException     // Catch:{ all -> 0x028b }
            if (r0 != 0) goto L_0x02a2
            com.google.api.client.http.HttpIOExceptionHandler r0 = r1.ioExceptionHandler     // Catch:{ all -> 0x028b }
            if (r0 == 0) goto L_0x02a1
            com.google.api.client.http.HttpIOExceptionHandler r0 = r1.ioExceptionHandler     // Catch:{ all -> 0x028b }
            boolean r0 = r0.handleIOException(r1, r2)     // Catch:{ all -> 0x028b }
            if (r0 == 0) goto L_0x02a1
            goto L_0x02a2
        L_0x02a1:
            throw r5     // Catch:{ all -> 0x028b }
        L_0x02a2:
            if (r9 == 0) goto L_0x02ab
            java.util.logging.Level r0 = java.util.logging.Level.WARNING     // Catch:{ all -> 0x028b }
            java.lang.String r6 = "exception thrown while executing request"
            r8.log(r0, r6, r5)     // Catch:{ all -> 0x028b }
        L_0x02ab:
            r4.close()
            r4 = 0
        L_0x02af:
            if (r4 == 0) goto L_0x0307
            boolean r0 = r4.isSuccessStatusCode()     // Catch:{ all -> 0x0300 }
            if (r0 != 0) goto L_0x0307
            com.google.api.client.http.HttpUnsuccessfulResponseHandler r0 = r1.unsuccessfulResponseHandler     // Catch:{ all -> 0x0300 }
            if (r0 == 0) goto L_0x02c2
            com.google.api.client.http.HttpUnsuccessfulResponseHandler r0 = r1.unsuccessfulResponseHandler     // Catch:{ all -> 0x0300 }
            boolean r0 = r0.handleResponse(r1, r4, r2)     // Catch:{ all -> 0x0300 }
            goto L_0x02c3
        L_0x02c2:
            r0 = 0
        L_0x02c3:
            if (r0 != 0) goto L_0x02f9
            int r6 = r4.getStatusCode()     // Catch:{ all -> 0x0300 }
            com.google.api.client.http.HttpHeaders r7 = r4.getHeaders()     // Catch:{ all -> 0x0300 }
            boolean r6 = r1.handleRedirect(r6, r7)     // Catch:{ all -> 0x0300 }
            if (r6 == 0) goto L_0x02d5
        L_0x02d3:
            r0 = 1
            goto L_0x02f9
        L_0x02d5:
            if (r2 == 0) goto L_0x02f9
            com.google.api.client.http.BackOffPolicy r6 = r1.backOffPolicy     // Catch:{ all -> 0x0300 }
            if (r6 == 0) goto L_0x02f9
            com.google.api.client.http.BackOffPolicy r6 = r1.backOffPolicy     // Catch:{ all -> 0x0300 }
            int r7 = r4.getStatusCode()     // Catch:{ all -> 0x0300 }
            boolean r6 = r6.isBackOffRequired(r7)     // Catch:{ all -> 0x0300 }
            if (r6 == 0) goto L_0x02f9
            com.google.api.client.http.BackOffPolicy r6 = r1.backOffPolicy     // Catch:{ all -> 0x0300 }
            long r6 = r6.getNextBackOffMillis()     // Catch:{ all -> 0x0300 }
            r8 = -1
            int r10 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r10 == 0) goto L_0x02f9
            com.google.api.client.util.Sleeper r0 = r1.sleeper     // Catch:{ InterruptedException -> 0x02d3 }
            r0.sleep(r6)     // Catch:{ InterruptedException -> 0x02d3 }
            goto L_0x02d3
        L_0x02f9:
            r0 = r0 & r2
            if (r0 == 0) goto L_0x030d
            r4.ignore()     // Catch:{ all -> 0x0300 }
            goto L_0x030d
        L_0x0300:
            r0 = move-exception
            if (r4 == 0) goto L_0x0306
            r4.disconnect()
        L_0x0306:
            throw r0
        L_0x0307:
            if (r4 != 0) goto L_0x030b
            r0 = 1
            goto L_0x030c
        L_0x030b:
            r0 = 0
        L_0x030c:
            r0 = r0 & r2
        L_0x030d:
            int r6 = r17 + -1
            if (r0 != 0) goto L_0x0345
            if (r4 != 0) goto L_0x0315
            r0 = 0
            goto L_0x031d
        L_0x0315:
            int r0 = r4.getStatusCode()
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
        L_0x031d:
            io.opencensus.trace.EndSpanOptions r0 = com.google.api.client.http.OpenCensusUtils.getEndSpanOptions(r0)
            r3.end(r0)
            if (r4 == 0) goto L_0x0344
            com.google.api.client.http.HttpResponseInterceptor r0 = r1.responseInterceptor
            if (r0 == 0) goto L_0x032d
            r0.interceptResponse(r4)
        L_0x032d:
            boolean r0 = r1.throwExceptionOnExecuteError
            if (r0 == 0) goto L_0x0343
            boolean r0 = r4.isSuccessStatusCode()
            if (r0 == 0) goto L_0x0338
            goto L_0x0343
        L_0x0338:
            com.google.api.client.http.HttpResponseException r0 = new com.google.api.client.http.HttpResponseException     // Catch:{ all -> 0x033e }
            r0.<init>(r4)     // Catch:{ all -> 0x033e }
            throw r0     // Catch:{ all -> 0x033e }
        L_0x033e:
            r0 = move-exception
            r4.disconnect()
            throw r0
        L_0x0343:
            return r4
        L_0x0344:
            throw r5
        L_0x0345:
            r0 = r4
            r4 = r3
            goto L_0x0035
        L_0x0349:
            r4.close()
            goto L_0x034e
        L_0x034d:
            throw r0
        L_0x034e:
            goto L_0x034d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.api.client.http.HttpRequest.execute():com.google.api.client.http.HttpResponse");
    }

    public Future<HttpResponse> executeAsync(Executor executor) {
        FutureTask futureTask = new FutureTask(new Callable<HttpResponse>() {
            public HttpResponse call() throws Exception {
                return HttpRequest.this.execute();
            }
        });
        executor.execute(futureTask);
        return futureTask;
    }

    public Future<HttpResponse> executeAsync() {
        return executeAsync(Executors.newFixedThreadPool(1, new ThreadFactoryBuilder().setDaemon(true).build()));
    }

    public boolean handleRedirect(int i, HttpHeaders httpHeaders) {
        String location = httpHeaders.getLocation();
        if (!getFollowRedirects() || !HttpStatusCodes.isRedirect(i) || location == null) {
            return false;
        }
        setUrl(new GenericUrl(this.url.toURL(location)));
        if (i == 303) {
            setRequestMethod("GET");
            setContent(null);
        }
        String str = null;
        this.headers.setAuthorization(str);
        this.headers.setIfMatch(str);
        this.headers.setIfNoneMatch(str);
        this.headers.setIfModifiedSince(str);
        this.headers.setIfUnmodifiedSince(str);
        this.headers.setIfRange(str);
        return true;
    }

    public Sleeper getSleeper() {
        return this.sleeper;
    }

    public HttpRequest setSleeper(Sleeper sleeper2) {
        this.sleeper = (Sleeper) Preconditions.checkNotNull(sleeper2);
        return this;
    }

    private static void addSpanAttribute(Span span, String str, String str2) {
        if (str2 != null) {
            span.putAttribute(str, AttributeValue.stringAttributeValue(str2));
        }
    }
}
