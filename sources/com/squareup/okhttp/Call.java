package com.squareup.okhttp;

import androidx.core.app.NotificationCompat;
import com.mobiroller.constants.ECommerceConstant;
import com.squareup.okhttp.Interceptor.Chain;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.internal.NamedRunnable;
import com.squareup.okhttp.internal.http.HttpEngine;
import com.squareup.okhttp.internal.http.RequestException;
import com.squareup.okhttp.internal.http.RouteException;
import com.squareup.okhttp.internal.http.StreamAllocation;
import java.io.IOException;
import java.net.ProtocolException;
import org.apache.http.protocol.HTTP;

public class Call {
    volatile boolean canceled;
    /* access modifiers changed from: private */
    public final OkHttpClient client;
    HttpEngine engine;
    private boolean executed;
    Request originalRequest;

    class ApplicationInterceptorChain implements Chain {
        private final boolean forWebSocket;
        private final int index;
        private final Request request;

        public Connection connection() {
            return null;
        }

        ApplicationInterceptorChain(int i, Request request2, boolean z) {
            this.index = i;
            this.request = request2;
            this.forWebSocket = z;
        }

        public Request request() {
            return this.request;
        }

        public Response proceed(Request request2) throws IOException {
            if (this.index >= Call.this.client.interceptors().size()) {
                return Call.this.getResponse(request2, this.forWebSocket);
            }
            ApplicationInterceptorChain applicationInterceptorChain = new ApplicationInterceptorChain(this.index + 1, request2, this.forWebSocket);
            Interceptor interceptor = (Interceptor) Call.this.client.interceptors().get(this.index);
            Response intercept = interceptor.intercept(applicationInterceptorChain);
            if (intercept != null) {
                return intercept;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("application interceptor ");
            sb.append(interceptor);
            sb.append(" returned null");
            throw new NullPointerException(sb.toString());
        }
    }

    final class AsyncCall extends NamedRunnable {
        private final boolean forWebSocket;
        private final Callback responseCallback;

        private AsyncCall(Callback callback, boolean z) {
            super("OkHttp %s", Call.this.originalRequest.urlString());
            this.responseCallback = callback;
            this.forWebSocket = z;
        }

        /* access modifiers changed from: 0000 */
        public String host() {
            return Call.this.originalRequest.httpUrl().host();
        }

        /* access modifiers changed from: 0000 */
        public Request request() {
            return Call.this.originalRequest;
        }

        /* access modifiers changed from: 0000 */
        public Object tag() {
            return Call.this.originalRequest.tag();
        }

        /* access modifiers changed from: 0000 */
        public void cancel() {
            Call.this.cancel();
        }

        /* access modifiers changed from: 0000 */
        public Call get() {
            return Call.this;
        }

        /* access modifiers changed from: protected */
        /* JADX WARNING: Removed duplicated region for block: B:13:0x003d A[SYNTHETIC, Splitter:B:13:0x003d] */
        /* JADX WARNING: Removed duplicated region for block: B:15:0x005c A[Catch:{ all -> 0x0036 }] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void execute() {
            /*
                r5 = this;
                r0 = 1
                r1 = 0
                com.squareup.okhttp.Call r2 = com.squareup.okhttp.Call.this     // Catch:{ IOException -> 0x0038 }
                boolean r3 = r5.forWebSocket     // Catch:{ IOException -> 0x0038 }
                com.squareup.okhttp.Response r2 = r2.getResponseWithInterceptorChain(r3)     // Catch:{ IOException -> 0x0038 }
                com.squareup.okhttp.Call r3 = com.squareup.okhttp.Call.this     // Catch:{ IOException -> 0x0038 }
                boolean r1 = r3.canceled     // Catch:{ IOException -> 0x0038 }
                if (r1 == 0) goto L_0x0021
                com.squareup.okhttp.Callback r1 = r5.responseCallback     // Catch:{ IOException -> 0x0034 }
                com.squareup.okhttp.Call r2 = com.squareup.okhttp.Call.this     // Catch:{ IOException -> 0x0034 }
                com.squareup.okhttp.Request r2 = r2.originalRequest     // Catch:{ IOException -> 0x0034 }
                java.io.IOException r3 = new java.io.IOException     // Catch:{ IOException -> 0x0034 }
                java.lang.String r4 = "Canceled"
                r3.<init>(r4)     // Catch:{ IOException -> 0x0034 }
                r1.onFailure(r2, r3)     // Catch:{ IOException -> 0x0034 }
                goto L_0x0026
            L_0x0021:
                com.squareup.okhttp.Callback r1 = r5.responseCallback     // Catch:{ IOException -> 0x0034 }
                r1.onResponse(r2)     // Catch:{ IOException -> 0x0034 }
            L_0x0026:
                com.squareup.okhttp.Call r0 = com.squareup.okhttp.Call.this
                com.squareup.okhttp.OkHttpClient r0 = r0.client
                com.squareup.okhttp.Dispatcher r0 = r0.getDispatcher()
                r0.finished(r5)
                goto L_0x0075
            L_0x0034:
                r1 = move-exception
                goto L_0x003b
            L_0x0036:
                r0 = move-exception
                goto L_0x0076
            L_0x0038:
                r0 = move-exception
                r1 = r0
                r0 = 0
            L_0x003b:
                if (r0 == 0) goto L_0x005c
                java.util.logging.Logger r0 = com.squareup.okhttp.internal.Internal.logger     // Catch:{ all -> 0x0036 }
                java.util.logging.Level r2 = java.util.logging.Level.INFO     // Catch:{ all -> 0x0036 }
                java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0036 }
                r3.<init>()     // Catch:{ all -> 0x0036 }
                java.lang.String r4 = "Callback failure for "
                r3.append(r4)     // Catch:{ all -> 0x0036 }
                com.squareup.okhttp.Call r4 = com.squareup.okhttp.Call.this     // Catch:{ all -> 0x0036 }
                java.lang.String r4 = r4.toLoggableString()     // Catch:{ all -> 0x0036 }
                r3.append(r4)     // Catch:{ all -> 0x0036 }
                java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0036 }
                r0.log(r2, r3, r1)     // Catch:{ all -> 0x0036 }
                goto L_0x0026
            L_0x005c:
                com.squareup.okhttp.Call r0 = com.squareup.okhttp.Call.this     // Catch:{ all -> 0x0036 }
                com.squareup.okhttp.internal.http.HttpEngine r0 = r0.engine     // Catch:{ all -> 0x0036 }
                if (r0 != 0) goto L_0x0067
                com.squareup.okhttp.Call r0 = com.squareup.okhttp.Call.this     // Catch:{ all -> 0x0036 }
                com.squareup.okhttp.Request r0 = r0.originalRequest     // Catch:{ all -> 0x0036 }
                goto L_0x006f
            L_0x0067:
                com.squareup.okhttp.Call r0 = com.squareup.okhttp.Call.this     // Catch:{ all -> 0x0036 }
                com.squareup.okhttp.internal.http.HttpEngine r0 = r0.engine     // Catch:{ all -> 0x0036 }
                com.squareup.okhttp.Request r0 = r0.getRequest()     // Catch:{ all -> 0x0036 }
            L_0x006f:
                com.squareup.okhttp.Callback r2 = r5.responseCallback     // Catch:{ all -> 0x0036 }
                r2.onFailure(r0, r1)     // Catch:{ all -> 0x0036 }
                goto L_0x0026
            L_0x0075:
                return
            L_0x0076:
                com.squareup.okhttp.Call r1 = com.squareup.okhttp.Call.this
                com.squareup.okhttp.OkHttpClient r1 = r1.client
                com.squareup.okhttp.Dispatcher r1 = r1.getDispatcher()
                r1.finished(r5)
                goto L_0x0085
            L_0x0084:
                throw r0
            L_0x0085:
                goto L_0x0084
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.Call.AsyncCall.execute():void");
        }
    }

    protected Call(OkHttpClient okHttpClient, Request request) {
        this.client = okHttpClient.copyWithDefaults();
        this.originalRequest = request;
    }

    public Response execute() throws IOException {
        synchronized (this) {
            if (!this.executed) {
                this.executed = true;
            } else {
                throw new IllegalStateException("Already Executed");
            }
        }
        try {
            this.client.getDispatcher().executed(this);
            Response responseWithInterceptorChain = getResponseWithInterceptorChain(false);
            if (responseWithInterceptorChain != null) {
                return responseWithInterceptorChain;
            }
            throw new IOException(ECommerceConstant.CANCELED);
        } finally {
            this.client.getDispatcher().finished(this);
        }
    }

    /* access modifiers changed from: 0000 */
    public Object tag() {
        return this.originalRequest.tag();
    }

    public void enqueue(Callback callback) {
        enqueue(callback, false);
    }

    /* access modifiers changed from: 0000 */
    public void enqueue(Callback callback, boolean z) {
        synchronized (this) {
            if (!this.executed) {
                this.executed = true;
            } else {
                throw new IllegalStateException("Already Executed");
            }
        }
        this.client.getDispatcher().enqueue(new AsyncCall(callback, z));
    }

    public void cancel() {
        this.canceled = true;
        HttpEngine httpEngine = this.engine;
        if (httpEngine != null) {
            httpEngine.cancel();
        }
    }

    public synchronized boolean isExecuted() {
        return this.executed;
    }

    public boolean isCanceled() {
        return this.canceled;
    }

    /* access modifiers changed from: private */
    public String toLoggableString() {
        String str = this.canceled ? "canceled call" : NotificationCompat.CATEGORY_CALL;
        HttpUrl resolve = this.originalRequest.httpUrl().resolve("/...");
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(" to ");
        sb.append(resolve);
        return sb.toString();
    }

    /* access modifiers changed from: private */
    public Response getResponseWithInterceptorChain(boolean z) throws IOException {
        return new ApplicationInterceptorChain(0, this.originalRequest, z).proceed(this.originalRequest);
    }

    /* access modifiers changed from: 0000 */
    public Response getResponse(Request request, boolean z) throws IOException {
        boolean z2;
        Throwable th;
        StreamAllocation streamAllocation;
        RequestBody body = request.body();
        if (body != null) {
            Builder newBuilder = request.newBuilder();
            MediaType contentType = body.contentType();
            if (contentType != null) {
                newBuilder.header("Content-Type", contentType.toString());
            }
            long contentLength = body.contentLength();
            String str = "Content-Length";
            String str2 = "Transfer-Encoding";
            if (contentLength != -1) {
                newBuilder.header(str, Long.toString(contentLength));
                newBuilder.removeHeader(str2);
            } else {
                newBuilder.header(str2, HTTP.CHUNK_CODING);
                newBuilder.removeHeader(str);
            }
            request = newBuilder.build();
        }
        HttpEngine httpEngine = new HttpEngine(this.client, request, false, false, z, null, null, null);
        this.engine = httpEngine;
        int i = 0;
        while (!this.canceled) {
            z2 = true;
            try {
                this.engine.sendRequest();
                this.engine.readResponse();
                Response response = this.engine.getResponse();
                Request followUpRequest = this.engine.followUpRequest();
                if (followUpRequest == null) {
                    if (!z) {
                        this.engine.releaseStreamAllocation();
                    }
                    return response;
                }
                StreamAllocation close = this.engine.close();
                i++;
                if (i <= 20) {
                    if (!this.engine.sameConnection(followUpRequest.httpUrl())) {
                        close.release();
                        streamAllocation = null;
                    } else {
                        streamAllocation = close;
                    }
                    HttpEngine httpEngine2 = new HttpEngine(this.client, followUpRequest, false, false, z, streamAllocation, null, response);
                    this.engine = httpEngine2;
                } else {
                    close.release();
                    StringBuilder sb = new StringBuilder();
                    sb.append("Too many follow-up requests: ");
                    sb.append(i);
                    throw new ProtocolException(sb.toString());
                }
            } catch (RequestException e) {
                throw e.getCause();
            } catch (RouteException e2) {
                HttpEngine recover = this.engine.recover(e2);
                if (recover != null) {
                    this.engine = recover;
                } else {
                    throw e2.getLastConnectException();
                }
            } catch (IOException e3) {
                HttpEngine recover2 = this.engine.recover(e3, null);
                if (recover2 != null) {
                    this.engine = recover2;
                } else {
                    throw e3;
                }
            } catch (Throwable th2) {
                th = th2;
                z2 = false;
            }
        }
        this.engine.releaseStreamAllocation();
        throw new IOException(ECommerceConstant.CANCELED);
        if (z2) {
            this.engine.close().release();
        }
        throw th;
    }
}
